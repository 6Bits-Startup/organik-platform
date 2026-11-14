package _5bits.organik.platform.procurements.interfaces.rest;

import _5bits.organik.platform.procurements.application.commandservices.ShipmentOrderCommandService;
import _5bits.organik.platform.procurements.application.queryservices.ShipmentOrderQueryService;
import _5bits.organik.platform.procurements.domain.model.aggregates.ShipmentOrder;
import _5bits.organik.platform.procurements.domain.model.commands.AcceptShipmentOrderCommand;
import _5bits.organik.platform.procurements.domain.model.commands.RejectShipmentOrderCommand;
import _5bits.organik.platform.procurements.domain.model.queries.GetShipmentOrderByIdQuery;
import _5bits.organik.platform.procurements.domain.model.queries.GetShipmentOrdersByMinimarketIdQuery;
import _5bits.organik.platform.procurements.domain.model.queries.GetShipmentOrdersBySupplierIdQuery;
import _5bits.organik.platform.procurements.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.procurements.domain.model.valueobjects.RejectionReason;
import _5bits.organik.platform.procurements.domain.model.valueobjects.SupplierId;
import _5bits.organik.platform.procurements.domain.model.valueobjects.UserId;
import _5bits.organik.platform.procurements.interfaces.rest.resources.AcceptShipmentOrderResource;
import _5bits.organik.platform.procurements.interfaces.rest.resources.CreateShipmentOrderResource;
import _5bits.organik.platform.procurements.interfaces.rest.resources.RejectShipmentOrderResource;
import _5bits.organik.platform.procurements.interfaces.rest.resources.ShipmentOrderResource;
import _5bits.organik.platform.procurements.interfaces.rest.transform.CreateShipmentOrderCommandFromResourceAssembler;
import _5bits.organik.platform.procurements.interfaces.rest.transform.ShipmentOrderResourceFromEntityAssembler;
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
 * REST controller that exposes shipment order endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/shipment-orders", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Shipment Orders", description = "Shipment order management endpoints")
public class ShipmentOrdersController {
    private final ShipmentOrderCommandService shipmentOrderCommandService;
    private final ShipmentOrderQueryService shipmentOrderQueryService;

    public ShipmentOrdersController(ShipmentOrderCommandService shipmentOrderCommandService, ShipmentOrderQueryService shipmentOrderQueryService) {
        this.shipmentOrderCommandService = shipmentOrderCommandService;
        this.shipmentOrderQueryService = shipmentOrderQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a shipment order", description = "Creates a pending shipment order sent by a supplier to a minimarket.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Shipment order created successfully",
                    content = @Content(schema = @Schema(implementation = ShipmentOrderResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required"),
            @ApiResponse(responseCode = "404", description = "Referenced resource not found")
    })
    public ResponseEntity<?> createShipmentOrder(@Valid @RequestBody CreateShipmentOrderResource resource) {
        var command = CreateShipmentOrderCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = shipmentOrderCommandService.handle(command)
                .flatMap(shipmentOrderId -> shipmentOrderQueryService.handle(new GetShipmentOrderByIdQuery(shipmentOrderId))
                        .<Result<ShipmentOrder, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("ShipmentOrder", shipmentOrderId.toString()))));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ShipmentOrderResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{shipmentOrderId}")
    @Operation(summary = "Get shipment order by ID", description = "Retrieves a specific shipment order by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shipment order found",
                    content = @Content(schema = @Schema(implementation = ShipmentOrderResource.class))),
            @ApiResponse(responseCode = "404", description = "Shipment order not found")
    })
    public ResponseEntity<?> getShipmentOrderById(
            @PathVariable
            @Parameter(description = "Shipment order unique identifier", example = "1", required = true)
            Long shipmentOrderId
    ) {
        var shipmentOrder = shipmentOrderQueryService.handle(new GetShipmentOrderByIdQuery(shipmentOrderId));
        if (shipmentOrder.isEmpty()) {
            var error = ApplicationError.notFound("ShipmentOrder", shipmentOrderId.toString());
            return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(error);
        }
        return ResponseEntity.ok(ShipmentOrderResourceFromEntityAssembler.toResourceFromEntity(shipmentOrder.get()));
    }

    @GetMapping
    @Operation(summary = "Get shipment orders", description = "Retrieves the shipment orders filtered by supplierId or minimarketId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shipment orders retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ShipmentOrderResource.class))),
            @ApiResponse(responseCode = "400", description = "Missing or invalid query parameter")
    })
    public ResponseEntity<?> getShipmentOrders(
            @RequestParam(required = false)
            @Parameter(description = "Supplier identifier", example = "sup-bioandes")
            String supplierId,
            @RequestParam(required = false)
            @Parameter(description = "Minimarket identifier", example = "mm-vida-verde")
            String minimarketId
    ) {
        if (supplierId != null) {
            var shipmentOrders = shipmentOrderQueryService.handle(new GetShipmentOrdersBySupplierIdQuery(new SupplierId(supplierId)));
            return ResponseEntity.ok(shipmentOrders.stream().map(ShipmentOrderResourceFromEntityAssembler::toResourceFromEntity).toList());
        }
        if (minimarketId != null) {
            var shipmentOrders = shipmentOrderQueryService.handle(new GetShipmentOrdersByMinimarketIdQuery(new MinimarketId(minimarketId)));
            return ResponseEntity.ok(shipmentOrders.stream().map(ShipmentOrderResourceFromEntityAssembler::toResourceFromEntity).toList());
        }
        var error = ApplicationError.validationError("supplierId or minimarketId", "Query parameter supplierId or minimarketId is required");
        return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(error);
    }

    @PostMapping("/{shipmentOrderId}/acceptances")
    @Operation(summary = "Accept shipment order", description = "Accepts a pending shipment order; its lots are registered in the minimarket inventory.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shipment order accepted",
                    content = @Content(schema = @Schema(implementation = ShipmentOrderResource.class))),
            @ApiResponse(responseCode = "404", description = "Shipment order not found"),
            @ApiResponse(responseCode = "409", description = "Conflict - shipment order already decided")
    })
    public ResponseEntity<?> acceptShipmentOrder(
            @PathVariable
            @Parameter(description = "Shipment order unique identifier", example = "1", required = true)
            Long shipmentOrderId,
            @Valid @RequestBody AcceptShipmentOrderResource resource
    ) {
        var command = new AcceptShipmentOrderCommand(shipmentOrderId, new UserId(resource.decidedBy()));
        var result = shipmentOrderCommandService.handle(command)
                .flatMap(id -> shipmentOrderQueryService.handle(new GetShipmentOrderByIdQuery(id))
                        .<Result<ShipmentOrder, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("ShipmentOrder", id.toString()))));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ShipmentOrderResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @PostMapping("/{shipmentOrderId}/rejections")
    @Operation(summary = "Reject shipment order", description = "Rejects a pending shipment order with a reason.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shipment order rejected",
                    content = @Content(schema = @Schema(implementation = ShipmentOrderResource.class))),
            @ApiResponse(responseCode = "404", description = "Shipment order not found"),
            @ApiResponse(responseCode = "409", description = "Conflict - shipment order already decided")
    })
    public ResponseEntity<?> rejectShipmentOrder(
            @PathVariable
            @Parameter(description = "Shipment order unique identifier", example = "1", required = true)
            Long shipmentOrderId,
            @Valid @RequestBody RejectShipmentOrderResource resource
    ) {
        var command = new RejectShipmentOrderCommand(shipmentOrderId, new UserId(resource.decidedBy()), new RejectionReason(resource.reason()));
        var result = shipmentOrderCommandService.handle(command)
                .flatMap(id -> shipmentOrderQueryService.handle(new GetShipmentOrderByIdQuery(id))
                        .<Result<ShipmentOrder, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("ShipmentOrder", id.toString()))));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ShipmentOrderResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }
}
