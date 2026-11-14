package _5bits.organik.platform.dashboard.interfaces.rest;

import _5bits.organik.platform.dashboard.application.queryservices.DashboardQueryService;
import _5bits.organik.platform.dashboard.domain.model.queries.GetDashboardSummaryByMinimarketIdQuery;
import _5bits.organik.platform.dashboard.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.dashboard.interfaces.rest.resources.DashboardSummaryResource;
import _5bits.organik.platform.dashboard.interfaces.rest.transform.DashboardSummaryResourceFromValueObjectAssembler;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes the dashboard of a minimarket.
 */
@RestController
@RequestMapping(value = "/api/v1/dashboard", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Dashboard", description = "Dashboard endpoints")
public class DashboardController {
    private final DashboardQueryService dashboardQueryService;

    public DashboardController(DashboardQueryService dashboardQueryService) {
        this.dashboardQueryService = dashboardQueryService;
    }

    @GetMapping
    @Operation(summary = "Get dashboard summary",
            description = "Retrieves the operational summary of a minimarket: stock, expirations, pending orders, pending requests and unread notifications. When userId is sent, the lots about to expire are counted with the warning days of that user profile.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dashboard summary retrieved successfully",
                    content = @Content(schema = @Schema(implementation = DashboardSummaryResource.class))),
            @ApiResponse(responseCode = "400", description = "Missing or invalid minimarketId"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required")
    })
    public ResponseEntity<?> getDashboardSummary(
            @RequestParam(required = false)
            @Parameter(description = "Minimarket identifier", example = "mm-vida-verde", required = true)
            String minimarketId,
            @RequestParam(required = false)
            @Parameter(description = "IAM user that views the dashboard", example = "1")
            Long userId
    ) {
        if (minimarketId == null) {
            var error = ApplicationError.validationError("minimarketId", "Query parameter minimarketId is required");
            return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(error);
        }
        var summary = dashboardQueryService.handle(new GetDashboardSummaryByMinimarketIdQuery(new MinimarketId(minimarketId), userId));
        return ResponseEntity.ok(DashboardSummaryResourceFromValueObjectAssembler.toResourceFromValueObject(summary));
    }
}
