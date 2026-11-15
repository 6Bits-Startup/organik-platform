package _5bits.organik.platform.inventory.interfaces.rest;

import _5bits.organik.platform.inventory.application.commandservices.StockLotCommandService;
import _5bits.organik.platform.inventory.application.queryservices.StockLotQueryService;
import _5bits.organik.platform.inventory.domain.model.aggregates.StockLot;
import _5bits.organik.platform.inventory.domain.model.queries.GetAllStockLotsByMinimarketIdQuery;
import _5bits.organik.platform.inventory.domain.model.queries.GetStockLotByIdQuery;
import _5bits.organik.platform.inventory.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.inventory.interfaces.rest.resources.CreateStockLotResource;
import _5bits.organik.platform.inventory.interfaces.rest.resources.StockLotResource;
import _5bits.organik.platform.inventory.interfaces.rest.resources.UpdateStockLotResource;
import _5bits.organik.platform.inventory.interfaces.rest.transform.CreateStockLotCommandFromResourceAssembler;
import _5bits.organik.platform.inventory.interfaces.rest.transform.StockLotResourceFromEntityAssembler;
import _5bits.organik.platform.inventory.interfaces.rest.transform.UpdateStockLotCommandFromResourceAssembler;
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
 * REST controller that exposes stock lot endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/stock-lots", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Stock Lots", description = "Stock lot management endpoints")
public class StockLotsController {
    private final StockLotCommandService stockLotCommandService;
    private final StockLotQueryService stockLotQueryService;

    public StockLotsController(StockLotCommandService stockLotCommandService, StockLotQueryService stockLotQueryService) {
        this.stockLotCommandService = stockLotCommandService;
        this.stockLotQueryService = stockLotQueryService;
    }

    @PostMapping
    @Operation(summary = "Register a stock lot", description = "Registers a lot of a product received by a minimarket.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Stock lot created successfully",
                    content = @Content(schema = @Schema(implementation = StockLotResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required"),
            @ApiResponse(responseCode = "409", description = "Conflict - resource already exists"),
            @ApiResponse(responseCode = "404", description = "Referenced resource not found")
    })
    public ResponseEntity<?> createStockLot(@Valid @RequestBody CreateStockLotResource resource) {
        var command = CreateStockLotCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = stockLotCommandService.handle(command)
                .flatMap(stockLotId -> stockLotQueryService.handle(new GetStockLotByIdQuery(stockLotId))
                        .<Result<StockLot, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("StockLot", stockLotId.toString()))));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                StockLotResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{stockLotId}")
    @Operation(summary = "Get stock lot by ID", description = "Retrieves a specific stock lot by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock lot found",
                    content = @Content(schema = @Schema(implementation = StockLotResource.class))),
            @ApiResponse(responseCode = "404", description = "Stock lot not found")
    })
    public ResponseEntity<?> getStockLotById(
            @PathVariable
            @Parameter(description = "Stock lot unique identifier", example = "1", required = true)
            Long stockLotId
    ) {
        var stockLot = stockLotQueryService.handle(new GetStockLotByIdQuery(stockLotId));
        if (stockLot.isEmpty()) {
            var error = ApplicationError.notFound("StockLot", stockLotId.toString());
            return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(error);
        }
        return ResponseEntity.ok(StockLotResourceFromEntityAssembler.toResourceFromEntity(stockLot.get()));
    }

    @GetMapping
    @Operation(summary = "Get stock lots", description = "Retrieves the stock lots filtered by minimarketId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock lots retrieved successfully",
                    content = @Content(schema = @Schema(implementation = StockLotResource.class))),
            @ApiResponse(responseCode = "400", description = "Missing or invalid query parameter")
    })
    public ResponseEntity<?> getStockLots(
            @RequestParam(required = false)
            @Parameter(description = "Minimarket identifier", example = "mm-vida-verde")
            String minimarketId
    ) {
        if (minimarketId != null) {
            var stockLots = stockLotQueryService.handle(new GetAllStockLotsByMinimarketIdQuery(new MinimarketId(minimarketId)));
            return ResponseEntity.ok(stockLots.stream().map(StockLotResourceFromEntityAssembler::toResourceFromEntity).toList());
        }
        var error = ApplicationError.validationError("minimarketId", "Query parameter minimarketId is required");
        return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(error);
    }

    @PutMapping("/{stockLotId}")
    @Operation(summary = "Update stock lot", description = "Updates the location, notes and expiration date of a stock lot. Its quantity only changes through inventory movements.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock lot updated successfully",
                    content = @Content(schema = @Schema(implementation = StockLotResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required"),
            @ApiResponse(responseCode = "404", description = "Stock lot not found")
    })
    public ResponseEntity<?> updateStockLot(
            @PathVariable
            @Parameter(description = "Stock lot unique identifier", example = "1", required = true)
            Long stockLotId,
            @Valid @RequestBody UpdateStockLotResource resource
    ) {
        var command = UpdateStockLotCommandFromResourceAssembler.toCommandFromResource(stockLotId, resource);
        var result = stockLotCommandService.handle(command)
                .flatMap(id -> stockLotQueryService.handle(new GetStockLotByIdQuery(id))
                        .<Result<StockLot, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("StockLot", id.toString()))));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                StockLotResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }
}
