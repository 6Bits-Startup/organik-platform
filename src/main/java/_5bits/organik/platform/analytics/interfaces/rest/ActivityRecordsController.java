package _5bits.organik.platform.analytics.interfaces.rest;

import _5bits.organik.platform.analytics.application.queryservices.ActivityRecordQueryService;
import _5bits.organik.platform.analytics.domain.model.queries.GetActivityRecordByIdQuery;
import _5bits.organik.platform.analytics.domain.model.queries.GetAllActivityRecordsByMinimarketIdQuery;
import _5bits.organik.platform.analytics.domain.model.queries.GetAllActivityRecordsQuery;
import _5bits.organik.platform.analytics.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.analytics.interfaces.rest.resources.ActivityRecordResource;
import _5bits.organik.platform.analytics.interfaces.rest.transform.ActivityRecordResourceFromEntityAssembler;
import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.interfaces.rest.transform.ProblemDetailFromApplicationErrorAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes the activity history.
 */
@RestController
@RequestMapping(value = "/api/v1/activity-records", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Activity Records", description = "Activity history endpoints")
public class ActivityRecordsController {
    private final ActivityRecordQueryService activityRecordQueryService;

    public ActivityRecordsController(ActivityRecordQueryService activityRecordQueryService) {
        this.activityRecordQueryService = activityRecordQueryService;
    }

    @GetMapping("/{activityRecordId}")
    @Operation(summary = "Get activity record by ID", description = "Retrieves a specific entry of the activity history.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activity record found",
                    content = @Content(schema = @Schema(implementation = ActivityRecordResource.class))),
            @ApiResponse(responseCode = "404", description = "Activity record not found")
    })
    public ResponseEntity<?> getActivityRecordById(
            @PathVariable
            @Parameter(description = "Activity record unique identifier", example = "1", required = true)
            Long activityRecordId
    ) {
        var activityRecord = activityRecordQueryService.handle(new GetActivityRecordByIdQuery(activityRecordId));
        if (activityRecord.isEmpty()) {
            var error = ApplicationError.notFound("ActivityRecord", activityRecordId.toString());
            return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(error);
        }
        return ResponseEntity.ok(ActivityRecordResourceFromEntityAssembler.toResourceFromEntity(activityRecord.get()));
    }

    @GetMapping
    @Operation(summary = "Get activity history", description = "Retrieves the activity history, optionally filtered by minimarket.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activity history retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ActivityRecordResource.class)))
    })
    public ResponseEntity<List<ActivityRecordResource>> getActivityRecords(
            @RequestParam(required = false)
            @Parameter(description = "Minimarket identifier", example = "mm-vida-verde")
            String minimarketId
    ) {
        var activityRecords = minimarketId == null
                ? activityRecordQueryService.handle(new GetAllActivityRecordsQuery())
                : activityRecordQueryService.handle(new GetAllActivityRecordsByMinimarketIdQuery(new MinimarketId(minimarketId)));
        return ResponseEntity.ok(activityRecords.stream()
                .map(ActivityRecordResourceFromEntityAssembler::toResourceFromEntity)
                .toList());
    }
}
