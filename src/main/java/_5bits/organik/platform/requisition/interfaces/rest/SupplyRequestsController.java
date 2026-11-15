package _5bits.organik.platform.requisition.interfaces.rest;

import _5bits.organik.platform.requisition.application.commandservices.SupplyRequestCommandService;
import _5bits.organik.platform.requisition.application.queryservices.SupplyRequestQueryService;
import _5bits.organik.platform.requisition.domain.model.aggregates.SupplyRequest;
import _5bits.organik.platform.requisition.domain.model.commands.AcceptSupplyRequestCommand;
import _5bits.organik.platform.requisition.domain.model.commands.RejectSupplyRequestCommand;
import _5bits.organik.platform.requisition.domain.model.queries.GetAllSupplyRequestsByMinimarketIdQuery;
import _5bits.organik.platform.requisition.domain.model.queries.GetAllSupplyRequestsBySupplierIdQuery;
import _5bits.organik.platform.requisition.domain.model.queries.GetSupplyRequestByIdQuery;
import _5bits.organik.platform.requisition.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.requisition.domain.model.valueobjects.SupplierId;
import _5bits.organik.platform.requisition.interfaces.rest.resources.CreateSupplyRequestResource;
import _5bits.organik.platform.requisition.interfaces.rest.resources.SupplyRequestResource;
import _5bits.organik.platform.requisition.interfaces.rest.transform.CreateSupplyRequestCommandFromResourceAssembler;
import _5bits.organik.platform.requisition.interfaces.rest.transform.SupplyRequestResourceFromEntityAssembler;
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
 * REST controller that exposes supply request endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/supply-requests", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Supply Requests", description = "Supply request management endpoints")
public class SupplyRequestsController {
    private final SupplyRequestCommandService supplyRequestCommandService;
    private final SupplyRequestQueryService supplyRequestQueryService;

    public SupplyRequestsController(SupplyRequestCommandService supplyRequestCommandService, SupplyRequestQueryService supplyRequestQueryService) {
        this.supplyRequestCommandService = supplyRequestCommandService;
        this.supplyRequestQueryService = supplyRequestQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a supply request", description = "Creates a pending supply request from a minimarket to a supplier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Supply request created successfully",
                    content = @Content(schema = @Schema(implementation = SupplyRequestResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required"),
            @ApiResponse(responseCode = "404", description = "Referenced resource not found")
    })
    public ResponseEntity<?> createSupplyRequest(@Valid @RequestBody CreateSupplyRequestResource resource) {
        var command = CreateSupplyRequestCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = supplyRequestCommandService.handle(command)
                .flatMap(supplyRequestId -> supplyRequestQueryService.handle(new GetSupplyRequestByIdQuery(supplyRequestId))
                        .<Result<SupplyRequest, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("SupplyRequest", supplyRequestId.toString()))));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                SupplyRequestResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{supplyRequestId}")
    @Operation(summary = "Get supply request by ID", description = "Retrieves a specific supply request by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supply request found",
                    content = @Content(schema = @Schema(implementation = SupplyRequestResource.class))),
            @ApiResponse(responseCode = "404", description = "Supply request not found")
    })
    public ResponseEntity<?> getSupplyRequestById(
            @PathVariable
            @Parameter(description = "Supply request unique identifier", example = "1", required = true)
            Long supplyRequestId
    ) {
        var supplyRequest = supplyRequestQueryService.handle(new GetSupplyRequestByIdQuery(supplyRequestId));
        if (supplyRequest.isEmpty()) {
            var error = ApplicationError.notFound("SupplyRequest", supplyRequestId.toString());
            return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(error);
        }
        return ResponseEntity.ok(SupplyRequestResourceFromEntityAssembler.toResourceFromEntity(supplyRequest.get()));
    }

    @GetMapping
    @Operation(summary = "Get supply requests", description = "Retrieves the supply requests filtered by minimarketId or supplierId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supply requests retrieved successfully",
                    content = @Content(schema = @Schema(implementation = SupplyRequestResource.class))),
            @ApiResponse(responseCode = "400", description = "Missing or invalid query parameter")
    })
    public ResponseEntity<?> getSupplyRequests(
            @RequestParam(required = false)
            @Parameter(description = "Minimarket identifier", example = "mm-vida-verde")
            String minimarketId,
            @RequestParam(required = false)
            @Parameter(description = "Supplier identifier", example = "sup-bioandes")
            String supplierId
    ) {
        if (minimarketId != null) {
            var supplyRequests = supplyRequestQueryService.handle(new GetAllSupplyRequestsByMinimarketIdQuery(new MinimarketId(minimarketId)));
            return ResponseEntity.ok(supplyRequests.stream().map(SupplyRequestResourceFromEntityAssembler::toResourceFromEntity).toList());
        }
        if (supplierId != null) {
            var supplyRequests = supplyRequestQueryService.handle(new GetAllSupplyRequestsBySupplierIdQuery(new SupplierId(supplierId)));
            return ResponseEntity.ok(supplyRequests.stream().map(SupplyRequestResourceFromEntityAssembler::toResourceFromEntity).toList());
        }
        var error = ApplicationError.validationError("minimarketId or supplierId", "Query parameter minimarketId or supplierId is required");
        return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(error);
    }

    @PostMapping("/{supplyRequestId}/acceptances")
    @Operation(summary = "Accept supply request", description = "The supplier accepts a pending supply request.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supply request accepts",
                    content = @Content(schema = @Schema(implementation = SupplyRequestResource.class))),
            @ApiResponse(responseCode = "404", description = "Supply request not found"),
            @ApiResponse(responseCode = "409", description = "Conflict - supply request already answered")
    })
    public ResponseEntity<?> acceptSupplyRequest(
            @PathVariable
            @Parameter(description = "Supply request unique identifier", example = "1", required = true)
            Long supplyRequestId
    ) {
        var result = supplyRequestCommandService.handle(new AcceptSupplyRequestCommand(supplyRequestId))
                .flatMap(id -> supplyRequestQueryService.handle(new GetSupplyRequestByIdQuery(id))
                        .<Result<SupplyRequest, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("SupplyRequest", id.toString()))));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                SupplyRequestResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @PostMapping("/{supplyRequestId}/rejections")
    @Operation(summary = "Reject supply request", description = "The supplier rejects a pending supply request.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supply request rejects",
                    content = @Content(schema = @Schema(implementation = SupplyRequestResource.class))),
            @ApiResponse(responseCode = "404", description = "Supply request not found"),
            @ApiResponse(responseCode = "409", description = "Conflict - supply request already answered")
    })
    public ResponseEntity<?> rejectSupplyRequest(
            @PathVariable
            @Parameter(description = "Supply request unique identifier", example = "1", required = true)
            Long supplyRequestId
    ) {
        var result = supplyRequestCommandService.handle(new RejectSupplyRequestCommand(supplyRequestId))
                .flatMap(id -> supplyRequestQueryService.handle(new GetSupplyRequestByIdQuery(id))
                        .<Result<SupplyRequest, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("SupplyRequest", id.toString()))));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                SupplyRequestResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }
}
