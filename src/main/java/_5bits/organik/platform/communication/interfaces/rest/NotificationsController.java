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
import _5bits.organik.platform.communication.interfaces.rest.transform.ResponseEntityFromNotificationCommandResultAssembler;
import _5bits.organik.platform.communication.interfaces.rest.transform.ResponseEntityFromNotificationQueryResultAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/notifications", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Notifications", description = "Endpoints for notifications")
public class NotificationsController {
    private final NotificationCommandService notificationCommandService;
    private final NotificationQueryService notificationQueryService;
    private final MessageSource messageSource;

    public NotificationsController(
            NotificationCommandService notificationCommandService,
            NotificationQueryService notificationQueryService,
            MessageSource messageSource) {
        this.notificationCommandService = notificationCommandService;
        this.notificationQueryService = notificationQueryService;
        this.messageSource = messageSource;
    }

    @Operation(
            summary = "Create a notification",
            description = "Creates a notification with the provided type, severity and title",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Notification creation request",
                    content = @Content(schema = @Schema(implementation = CreateNotificationResource.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notification created",
                    content = @Content(schema = @Schema(implementation = NotificationResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<?> createNotification(@Valid @RequestBody CreateNotificationResource resource) {
        log.debug("POST /api/v1/notifications – type={}, severity={}", resource.type(), resource.severity());
        var result = notificationCommandService
                .handle(CreateNotificationCommandFromResourceAssembler.toCommandFromResource(resource));
        var response = ResponseEntityFromNotificationCommandResultAssembler
                .toResponseEntityFromResult(result, messageSource, HttpStatus.CREATED);
        log.debug("POST /api/v1/notifications – response status={}", response.getStatusCode());
        return response;
    }

    @Operation(
            summary = "Mark a notification as read",
            description = "Marks an existing notification as read")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification marked as read",
                    content = @Content(schema = @Schema(implementation = NotificationResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Notification not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping("{id}/mark-as-read")
    public ResponseEntity<?> markNotificationAsRead(@PathVariable Long id) {
        log.debug("POST /api/v1/notifications/{}/mark-as-read", id);
        if (id == null || id <= 0) {
            return ResponseEntityFromNotificationQueryResultAssembler.badRequest(
                    messageSource,
                    "notification.error.id.invalid");
        }
        var result = notificationCommandService.handle(new MarkNotificationAsReadCommand(id));
        return ResponseEntityFromNotificationCommandResultAssembler
                .toResponseEntityFromResult(result, messageSource, HttpStatus.OK);
    }

    @Operation(
            summary = "Get a notification by ID",
            description = "Gets a notification by the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification found",
                    content = @Content(schema = @Schema(implementation = NotificationResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Notification not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("{id}")
    public ResponseEntity<?> getNotificationById(@PathVariable Long id) {
        log.debug("GET /api/v1/notifications/{}", id);
        if (id == null || id <= 0) {
            return ResponseEntityFromNotificationQueryResultAssembler.badRequest(
                    messageSource,
                    "notification.error.id.invalid");
        }
        Optional<Notification> notification = notificationQueryService.handle(new GetNotificationByIdQuery(id));
        if (notification.isEmpty()) {
            log.debug("Notification not found for id={}", id);
            return ResponseEntityFromNotificationQueryResultAssembler.notFound(
                    messageSource,
                    "notification.error.notFoundById",
                    id);
        }
        return ResponseEntityFromNotificationQueryResultAssembler.toResponseEntityFromNotification(notification.orElseThrow());
    }

    @Operation(
            summary = "Get all notifications",
            description = "Gets every registered notification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notifications found",
                    content = @Content(schema = @Schema(implementation = NotificationResource[].class)))
    })
    @GetMapping
    public ResponseEntity<?> getAllNotifications() {
        log.debug("GET /api/v1/notifications");
        var notifications = notificationQueryService.handle(new GetAllNotificationsQuery());
        return ResponseEntityFromNotificationQueryResultAssembler.toResponseEntityFromList(notifications);
    }
}