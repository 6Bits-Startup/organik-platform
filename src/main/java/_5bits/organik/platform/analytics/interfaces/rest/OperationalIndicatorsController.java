package _5bits.organik.platform.analytics.interfaces.rest;

import _5bits.organik.platform.analytics.application.queryservices.OperationalIndicatorsQueryService;
import _5bits.organik.platform.analytics.domain.model.queries.GetOperationalIndicatorsByMinimarketIdQuery;
import _5bits.organik.platform.analytics.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.analytics.interfaces.rest.resources.OperationalIndicatorsResource;
import _5bits.organik.platform.analytics.interfaces.rest.transform.OperationalIndicatorsResourceFromValueObjectAssembler;
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
 * REST controller that exposes the operational indicators of a minimarket.
 */
@RestController
@RequestMapping(value = "/api/v1/operational-indicators", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Operational Indicators", description = "Analytics endpoints")
public class OperationalIndicatorsController {
    private final OperationalIndicatorsQueryService operationalIndicatorsQueryService;

    public OperationalIndicatorsController(OperationalIndicatorsQueryService operationalIndicatorsQueryService) {
        this.operationalIndicatorsQueryService = operationalIndicatorsQueryService;
    }

    @GetMapping
    @Operation(summary = "Get operational indicators",
            description = "Computes expirations, waste, conservation alerts and supplier order indicators of a minimarket.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operational indicators retrieved successfully",
                    content = @Content(schema = @Schema(implementation = OperationalIndicatorsResource.class))),
            @ApiResponse(responseCode = "400", description = "Missing or invalid minimarketId")
    })
    public ResponseEntity<?> getOperationalIndicators(
            @RequestParam(required = false)
            @Parameter(description = "Minimarket identifier", example = "mm-vida-verde", required = true)
            String minimarketId
    ) {
        if (minimarketId == null) {
            var error = ApplicationError.validationError("minimarketId", "Query parameter minimarketId is required");
            return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(error);
        }
        var indicators = operationalIndicatorsQueryService.handle(
                new GetOperationalIndicatorsByMinimarketIdQuery(new MinimarketId(minimarketId)));
        return ResponseEntity.ok(OperationalIndicatorsResourceFromValueObjectAssembler.toResourceFromValueObject(indicators));
    }
}
