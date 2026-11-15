package _5bits.organik.platform.inventory.interfaces.rest;

import _5bits.organik.platform.inventory.application.commandservices.InventoryMovementCommandService;
import _5bits.organik.platform.inventory.application.queryservices.InventoryMovementQueryService;
import _5bits.organik.platform.inventory.domain.model.aggregates.InventoryMovement;
import _5bits.organik.platform.inventory.domain.model.queries.GetAllInventoryMovementsByStockLotIdQuery;
import _5bits.organik.platform.inventory.domain.model.queries.GetInventoryMovementByIdQuery;
import _5bits.organik.platform.inventory.domain.model.valueobjects.StockLotId;
import _5bits.organik.platform.inventory.interfaces.rest.resources.InventoryMovementResource;
import _5bits.organik.platform.inventory.interfaces.rest.resources.RegisterInventoryMovementResource;
import _5bits.organik.platform.inventory.interfaces.rest.transform.InventoryMovementResourceFromEntityAssembler;
import _5bits.organik.platform.inventory.interfaces.rest.transform.RegisterInventoryMovementCommandFromResourceAssembler;
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
 * REST controller that exposes inventory movement endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/inventory-movements", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Inventory Movements", description = "Inventory movement management endpoints")
public class InventoryMovementsController {
    private final InventoryMovementCommandService inventoryMovementCommandService;
    private final InventoryMovementQueryService inventoryMovementQueryService;

    public InventoryMovementsController(InventoryMovementCommandService inventoryMovementCommandService, InventoryMovementQueryService inventoryMovementQueryService) {
        this.inventoryMovementCommandService = inventoryMovementCommandService;
        this.inventoryMovementQueryService = inventoryMovementQueryService;
    }

    @PostMapping
    @Operation(summary = "Register an inventory movement", description = "Registers an entry, exit, adjustment or waste and applies it to the stock of the lot.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inventory movement created successfully",
                    content = @Content(schema = @Schema(implementation = InventoryMovementResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required"),
            @ApiResponse(responseCode = "404", description = "Referenced resource not found")
    })
    public ResponseEntity<?> createInventoryMovement(@Valid @RequestBody RegisterInventoryMovementResource resource) {
        var command = RegisterInventoryMovementCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = inventoryMovementCommandService.handle(command)
                .flatMap(inventoryMovementId -> inventoryMovementQueryService.handle(new GetInventoryMovementByIdQuery(inventoryMovementId))
                        .<Result<InventoryMovement, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("InventoryMovement", inventoryMovementId.toString()))));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                InventoryMovementResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{inventoryMovementId}")
    @Operation(summary = "Get inventory movement by ID", description = "Retrieves a specific inventory movement by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventory movement found",
                    content = @Content(schema = @Schema(implementation = InventoryMovementResource.class))),
            @ApiResponse(responseCode = "404", description = "Inventory movement not found")
    })
    public ResponseEntity<?> getInventoryMovementById(
            @PathVariable
            @Parameter(description = "Inventory movement unique identifier", example = "1", required = true)
            Long inventoryMovementId
    ) {
        var inventoryMovement = inventoryMovementQueryService.handle(new GetInventoryMovementByIdQuery(inventoryMovementId));
        if (inventoryMovement.isEmpty()) {
            var error = ApplicationError.notFound("InventoryMovement", inventoryMovementId.toString());
            return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(error);
        }
        return ResponseEntity.ok(InventoryMovementResourceFromEntityAssembler.toResourceFromEntity(inventoryMovement.get()));
    }

    @GetMapping
    @Operation(summary = "Get inventory movements", description = "Retrieves the inventory movements filtered by stockLotId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventory movements retrieved successfully",
                    content = @Content(schema = @Schema(implementation = InventoryMovementResource.class))),
            @ApiResponse(responseCode = "400", description = "Missing or invalid query parameter")
    })
    public ResponseEntity<?> getInventoryMovements(
            @RequestParam(required = false)
            @Parameter(description = "Stock lot identifier", example = "1")
            Long stockLotId
    ) {
        if (stockLotId != null) {
            var inventoryMovements = inventoryMovementQueryService.handle(new GetAllInventoryMovementsByStockLotIdQuery(new StockLotId(stockLotId)));
            return ResponseEntity.ok(inventoryMovements.stream().map(InventoryMovementResourceFromEntityAssembler::toResourceFromEntity).toList());
        }
        var error = ApplicationError.validationError("stockLotId", "Query parameter stockLotId is required");
        return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(error);
    }
}
