package _5bits.organik.platform.communication.interfaces.rest;

import _5bits.organik.platform.communication.application.commandservices.NotificationCommandService;
import _5bits.organik.platform.communication.application.queryservices.NotificationQueryService;
import _5bits.organik.platform.communication.domain.model.aggregates.Notification;
import _5bits.organik.platform.communication.domain.model.commands.MarkNotificationAsReadCommand;
import _5bits.organik.platform.communication.domain.model.queries.GetAllNotificationsQuery;
import _5bits.organik.platform.communication.domain.model.queries.GetNotificationByIdQuery;
import _5bits.organik.platform.communication.interfaces.rest.resources.CreateNotificationResource;
import _5bits.organik.platform.communication.interfaces.rest.resources.NotificationResource;
import _5bits.organik.platform.communication.interfaces.rest.transform.CreateNotificationCommandFromResourceAssembler;
import _5bits.organik.platform.communication.interfaces.rest.transform.NotificationResourceFromEntityAssembler;
import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;
import _5bits.organik.platform.shared.interfaces.rest.transform.ProblemDetailFromApplicationErrorAssembler;
import _5bits.organik.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes notification endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/notifications", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Notifications", description = "Notification management endpoints")
public class NotificationsController {
    private final NotificationCommandService notificationCommandService;
    private final NotificationQueryService notificationQueryService;

    public NotificationsController(NotificationCommandService notificationCommandService, NotificationQueryService notificationQueryService) {
        this.notificationCommandService = notificationCommandService;
        this.notificationQueryService = notificationQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a notification", description = "Creates a new unread notification for the minimarket staff.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notification created successfully",
                    content = @Content(schema = @Schema(implementation = NotificationResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required")
    })
    public ResponseEntity<?> createNotification(@Valid @RequestBody CreateNotificationResource resource) {
        var createNotificationCommand = CreateNotificationCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = notificationCommandService.handle(createNotificationCommand)
                .flatMap(notificationId -> notificationQueryService.handle(new GetNotificationByIdQuery(notificationId))
                        .<Result<Notification, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("Notification", notificationId.toString()))));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                NotificationResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{notificationId}")
    @Operation(summary = "Get notification by ID", description = "Retrieves a specific notification by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification found",
                    content = @Content(schema = @Schema(implementation = NotificationResource.class))),
            @ApiResponse(responseCode = "404", description = "Notification not found")
    })
    public ResponseEntity<?> getNotificationById(
            @PathVariable
            @Parameter(description = "Notification unique identifier", example = "1", required = true)
            Long notificationId
    ) {
        var notification = notificationQueryService.handle(new GetNotificationByIdQuery(notificationId));
        if (notification.isEmpty()) {
            var error = ApplicationError.notFound("Notification", notificationId.toString());
            return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(error);
        }
        return ResponseEntity.ok(NotificationResourceFromEntityAssembler.toResourceFromEntity(notification.get()));
    }

    @GetMapping
    @Operation(summary = "Get all notifications", description = "Retrieves every notification of the minimarket.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notifications retrieved successfully",
                    content = @Content(schema = @Schema(implementation = NotificationResource.class)))
    })
    public ResponseEntity<List<NotificationResource>> getAllNotifications() {
        var notifications = notificationQueryService.handle(new GetAllNotificationsQuery());
        var notificationResources = notifications.stream()
                .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(notificationResources);
    }

    @PostMapping("/{notificationId}/readings")
    @Operation(summary = "Mark notification as read", description = "Marks an unread notification as read.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification marked as read",
                    content = @Content(schema = @Schema(implementation = NotificationResource.class))),
            @ApiResponse(responseCode = "404", description = "Notification not found"),
            @ApiResponse(responseCode = "409", description = "Conflict - notification already read")
    })
    public ResponseEntity<?> markNotificationAsRead(
            @PathVariable
            @Parameter(description = "Notification unique identifier", example = "1", required = true)
            Long notificationId
    ) {
        var result = notificationCommandService.handle(new MarkNotificationAsReadCommand(notificationId))
                .flatMap(id -> notificationQueryService.handle(new GetNotificationByIdQuery(id))
                        .<Result<Notification, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("Notification", id.toString()))));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                NotificationResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }
}
