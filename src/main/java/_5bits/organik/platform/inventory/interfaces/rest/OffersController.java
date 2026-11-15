package _5bits.organik.platform.inventory.interfaces.rest;

import _5bits.organik.platform.inventory.application.commandservices.OfferCommandService;
import _5bits.organik.platform.inventory.application.queryservices.OfferQueryService;
import _5bits.organik.platform.inventory.domain.model.aggregates.Offer;
import _5bits.organik.platform.inventory.domain.model.queries.GetAllOffersByStockLotIdQuery;
import _5bits.organik.platform.inventory.domain.model.queries.GetOfferByIdQuery;
import _5bits.organik.platform.inventory.domain.model.valueobjects.StockLotId;
import _5bits.organik.platform.inventory.interfaces.rest.resources.CreateOfferResource;
import _5bits.organik.platform.inventory.interfaces.rest.resources.OfferResource;
import _5bits.organik.platform.inventory.interfaces.rest.transform.CreateOfferCommandFromResourceAssembler;
import _5bits.organik.platform.inventory.interfaces.rest.transform.OfferResourceFromEntityAssembler;
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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes offer endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/offers", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Offers", description = "Offer management endpoints")
public class OffersController {
    private final OfferCommandService offerCommandService;
    private final OfferQueryService offerQueryService;

    public OffersController(OfferCommandService offerCommandService, OfferQueryService offerQueryService) {
        this.offerCommandService = offerCommandService;
        this.offerQueryService = offerQueryService;
    }

    @PostMapping
    @Operation(summary = "Create an offer", description = "Creates a special price for a stock lot that ends on or before its expiration date.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Offer created successfully",
                    content = @Content(schema = @Schema(implementation = OfferResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required"),
            @ApiResponse(responseCode = "404", description = "Referenced resource not found")
    })
    public ResponseEntity<?> createOffer(@Valid @RequestBody CreateOfferResource resource) {
        var command = CreateOfferCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = offerCommandService.handle(command)
                .flatMap(offerId -> offerQueryService.handle(new GetOfferByIdQuery(offerId))
                        .<Result<Offer, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("Offer", offerId.toString()))));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                OfferResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{offerId}")
    @Operation(summary = "Get offer by ID", description = "Retrieves a specific offer by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Offer found",
                    content = @Content(schema = @Schema(implementation = OfferResource.class))),
            @ApiResponse(responseCode = "404", description = "Offer not found")
    })
    public ResponseEntity<?> getOfferById(
            @PathVariable
            @Parameter(description = "Offer unique identifier", example = "1", required = true)
            Long offerId
    ) {
        var offer = offerQueryService.handle(new GetOfferByIdQuery(offerId));
        if (offer.isEmpty()) {
            var error = ApplicationError.notFound("Offer", offerId.toString());
            return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(error);
        }
        return ResponseEntity.ok(OfferResourceFromEntityAssembler.toResourceFromEntity(offer.get()));
    }

    @GetMapping
    @Operation(summary = "Get offers", description = "Retrieves the offers filtered by stockLotId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Offers retrieved successfully",
                    content = @Content(schema = @Schema(implementation = OfferResource.class))),
            @ApiResponse(responseCode = "400", description = "Missing or invalid query parameter")
    })
    public ResponseEntity<?> getOffers(
            @RequestParam(required = false)
            @Parameter(description = "Stock lot identifier", example = "1")
            Long stockLotId
    ) {
        if (stockLotId != null) {
            var offers = offerQueryService.handle(new GetAllOffersByStockLotIdQuery(new StockLotId(stockLotId)));
            return ResponseEntity.ok(offers.stream().map(OfferResourceFromEntityAssembler::toResourceFromEntity).toList());
        }
        var error = ApplicationError.validationError("stockLotId", "Query parameter stockLotId is required");
        return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(error);
    }
}
