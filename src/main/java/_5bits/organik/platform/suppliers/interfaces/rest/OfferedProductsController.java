package _5bits.organik.platform.suppliers.interfaces.rest;

import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;
import _5bits.organik.platform.shared.interfaces.rest.transform.ProblemDetailFromApplicationErrorAssembler;
import _5bits.organik.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import _5bits.organik.platform.suppliers.application.commandservices.OfferedProductCommandService;
import _5bits.organik.platform.suppliers.application.queryservices.OfferedProductQueryService;
import _5bits.organik.platform.suppliers.domain.model.aggregates.OfferedProduct;
import _5bits.organik.platform.suppliers.domain.model.queries.GetAllOfferedProductsBySupplierIdQuery;
import _5bits.organik.platform.suppliers.domain.model.queries.GetOfferedProductByIdQuery;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.SupplierId;
import _5bits.organik.platform.suppliers.interfaces.rest.resources.CreateOfferedProductResource;
import _5bits.organik.platform.suppliers.interfaces.rest.resources.OfferedProductResource;
import _5bits.organik.platform.suppliers.interfaces.rest.resources.UpdateOfferedProductResource;
import _5bits.organik.platform.suppliers.interfaces.rest.transform.CreateOfferedProductCommandFromResourceAssembler;
import _5bits.organik.platform.suppliers.interfaces.rest.transform.OfferedProductResourceFromEntityAssembler;
import _5bits.organik.platform.suppliers.interfaces.rest.transform.UpdateOfferedProductCommandFromResourceAssembler;
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
 * REST controller that exposes offered product endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/offered-products", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Offered Products", description = "Supplier catalog management endpoints")
public class OfferedProductsController {
    private final OfferedProductCommandService offeredProductCommandService;
    private final OfferedProductQueryService offeredProductQueryService;

    public OfferedProductsController(OfferedProductCommandService offeredProductCommandService, OfferedProductQueryService offeredProductQueryService) {
        this.offeredProductCommandService = offeredProductCommandService;
        this.offeredProductQueryService = offeredProductQueryService;
    }

    @PostMapping
    @Operation(summary = "Publish an offered product", description = "Publishes a product lot in the catalog of a supplier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Offered product created successfully",
                    content = @Content(schema = @Schema(implementation = OfferedProductResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required"),
            @ApiResponse(responseCode = "409", description = "Conflict - resource already exists"),
            @ApiResponse(responseCode = "404", description = "Referenced resource not found")
    })
    public ResponseEntity<?> createOfferedProduct(@Valid @RequestBody CreateOfferedProductResource resource) {
        var command = CreateOfferedProductCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = offeredProductCommandService.handle(command)
                .flatMap(offeredProductId -> offeredProductQueryService.handle(new GetOfferedProductByIdQuery(offeredProductId))
                        .<Result<OfferedProduct, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("OfferedProduct", offeredProductId.toString()))));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                OfferedProductResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{offeredProductId}")
    @Operation(summary = "Get offered product by ID", description = "Retrieves a specific offered product by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Offered product found",
                    content = @Content(schema = @Schema(implementation = OfferedProductResource.class))),
            @ApiResponse(responseCode = "404", description = "Offered product not found")
    })
    public ResponseEntity<?> getOfferedProductById(
            @PathVariable
            @Parameter(description = "Offered product unique identifier", example = "1", required = true)
            Long offeredProductId
    ) {
        var offeredProduct = offeredProductQueryService.handle(new GetOfferedProductByIdQuery(offeredProductId));
        if (offeredProduct.isEmpty()) {
            var error = ApplicationError.notFound("OfferedProduct", offeredProductId.toString());
            return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(error);
        }
        return ResponseEntity.ok(OfferedProductResourceFromEntityAssembler.toResourceFromEntity(offeredProduct.get()));
    }

    @GetMapping
    @Operation(summary = "Get offered products", description = "Retrieves the offered products filtered by supplierId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Offered products retrieved successfully",
                    content = @Content(schema = @Schema(implementation = OfferedProductResource.class))),
            @ApiResponse(responseCode = "400", description = "Missing or invalid query parameter")
    })
    public ResponseEntity<?> getOfferedProducts(
            @RequestParam(required = false)
            @Parameter(description = "Supplier identifier", example = "sup-bioandes")
            String supplierId
    ) {
        if (supplierId != null) {
            var offeredProducts = offeredProductQueryService.handle(new GetAllOfferedProductsBySupplierIdQuery(new SupplierId(supplierId)));
            return ResponseEntity.ok(offeredProducts.stream().map(OfferedProductResourceFromEntityAssembler::toResourceFromEntity).toList());
        }
        var error = ApplicationError.validationError("supplierId", "Query parameter supplierId is required");
        return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(error);
    }

    @PutMapping("/{offeredProductId}")
    @Operation(summary = "Update offered product", description = "Updates the lot, the availability and the expiration date of an offered product.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Offered product updated successfully",
                    content = @Content(schema = @Schema(implementation = OfferedProductResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required"),
            @ApiResponse(responseCode = "404", description = "Offered product not found"),
            @ApiResponse(responseCode = "409", description = "Conflict - lot code already offered by the supplier")
    })
    public ResponseEntity<?> updateOfferedProduct(
            @PathVariable
            @Parameter(description = "Offered product unique identifier", example = "1", required = true)
            Long offeredProductId,
            @Valid @RequestBody UpdateOfferedProductResource resource
    ) {
        var command = UpdateOfferedProductCommandFromResourceAssembler.toCommandFromResource(offeredProductId, resource);
        var result = offeredProductCommandService.handle(command)
                .flatMap(id -> offeredProductQueryService.handle(new GetOfferedProductByIdQuery(id))
                        .<Result<OfferedProduct, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("OfferedProduct", id.toString()))));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                OfferedProductResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }
}
