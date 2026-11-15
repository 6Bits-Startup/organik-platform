package _5bits.organik.platform.suppliers.interfaces.rest;

import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;
import _5bits.organik.platform.shared.interfaces.rest.transform.ProblemDetailFromApplicationErrorAssembler;
import _5bits.organik.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import _5bits.organik.platform.suppliers.application.commandservices.SupplierCommandService;
import _5bits.organik.platform.suppliers.application.queryservices.SupplierQueryService;
import _5bits.organik.platform.suppliers.domain.model.aggregates.Supplier;
import _5bits.organik.platform.suppliers.domain.model.queries.GetAllSuppliersQuery;
import _5bits.organik.platform.suppliers.domain.model.queries.GetSupplierByIdQuery;
import _5bits.organik.platform.suppliers.interfaces.rest.resources.CreateSupplierResource;
import _5bits.organik.platform.suppliers.interfaces.rest.resources.SupplierResource;
import _5bits.organik.platform.suppliers.interfaces.rest.transform.CreateSupplierCommandFromResourceAssembler;
import _5bits.organik.platform.suppliers.interfaces.rest.transform.SupplierResourceFromEntityAssembler;
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
 * REST controller that exposes supplier endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/suppliers", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Suppliers", description = "Supplier management endpoints")
public class SuppliersController {
    private final SupplierCommandService supplierCommandService;
    private final SupplierQueryService supplierQueryService;

    public SuppliersController(SupplierCommandService supplierCommandService, SupplierQueryService supplierQueryService) {
        this.supplierCommandService = supplierCommandService;
        this.supplierQueryService = supplierQueryService;
    }

    @PostMapping
    @Operation(summary = "Register a supplier", description = "Registers an organic supplier with a unique email address.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Supplier created successfully",
                    content = @Content(schema = @Schema(implementation = SupplierResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required"),
            @ApiResponse(responseCode = "409", description = "Conflict - resource already exists"),
            @ApiResponse(responseCode = "404", description = "Referenced resource not found")
    })
    public ResponseEntity<?> createSupplier(@Valid @RequestBody CreateSupplierResource resource) {
        var command = CreateSupplierCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = supplierCommandService.handle(command)
                .flatMap(supplierId -> supplierQueryService.handle(new GetSupplierByIdQuery(supplierId))
                        .<Result<Supplier, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("Supplier", supplierId.toString()))));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                SupplierResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{supplierId}")
    @Operation(summary = "Get supplier by ID", description = "Retrieves a specific supplier by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supplier found",
                    content = @Content(schema = @Schema(implementation = SupplierResource.class))),
            @ApiResponse(responseCode = "404", description = "Supplier not found")
    })
    public ResponseEntity<?> getSupplierById(
            @PathVariable
            @Parameter(description = "Supplier unique identifier", example = "1", required = true)
            Long supplierId
    ) {
        var supplier = supplierQueryService.handle(new GetSupplierByIdQuery(supplierId));
        if (supplier.isEmpty()) {
            var error = ApplicationError.notFound("Supplier", supplierId.toString());
            return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(error);
        }
        return ResponseEntity.ok(SupplierResourceFromEntityAssembler.toResourceFromEntity(supplier.get()));
    }

    @GetMapping
    @Operation(summary = "Get all suppliers", description = "Retrieves every supplier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Suppliers retrieved successfully",
                    content = @Content(schema = @Schema(implementation = SupplierResource.class)))
    })
    public ResponseEntity<List<SupplierResource>> getAllSuppliers() {
        var suppliers = supplierQueryService.handle(new GetAllSuppliersQuery());
        var resources = suppliers.stream()
                .map(SupplierResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}
