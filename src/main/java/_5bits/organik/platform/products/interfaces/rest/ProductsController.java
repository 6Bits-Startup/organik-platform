package _5bits.organik.platform.products.interfaces.rest;

import _5bits.organik.platform.products.application.commandservices.ProductCommandService;
import _5bits.organik.platform.products.application.queryservices.ProductQueryService;
import _5bits.organik.platform.products.domain.model.aggregates.Product;
import _5bits.organik.platform.products.domain.model.queries.GetAllProductsByCategoryIdQuery;
import _5bits.organik.platform.products.domain.model.queries.GetAllProductsQuery;
import _5bits.organik.platform.products.domain.model.queries.GetProductByIdQuery;
import _5bits.organik.platform.products.domain.model.valueobjects.CategoryId;
import _5bits.organik.platform.products.interfaces.rest.resources.CreateProductResource;
import _5bits.organik.platform.products.interfaces.rest.resources.ProductResource;
import _5bits.organik.platform.products.interfaces.rest.resources.UpdateProductResource;
import _5bits.organik.platform.products.interfaces.rest.transform.CreateProductCommandFromResourceAssembler;
import _5bits.organik.platform.products.interfaces.rest.transform.ProductResourceFromEntityAssembler;
import _5bits.organik.platform.products.interfaces.rest.transform.UpdateProductCommandFromResourceAssembler;
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
 * REST controller that exposes product endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/products", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Products", description = "Product catalog management endpoints")
public class ProductsController {
    private final ProductCommandService productCommandService;
    private final ProductQueryService productQueryService;

    public ProductsController(ProductCommandService productCommandService, ProductQueryService productQueryService) {
        this.productCommandService = productCommandService;
        this.productQueryService = productQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a product", description = "Creates a product of the catalog with a unique SKU.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully",
                    content = @Content(schema = @Schema(implementation = ProductResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required"),
            @ApiResponse(responseCode = "409", description = "Conflict - resource already exists"),
            @ApiResponse(responseCode = "404", description = "Referenced resource not found")
    })
    public ResponseEntity<?> createProduct(@Valid @RequestBody CreateProductResource resource) {
        var command = CreateProductCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = productCommandService.handle(command)
                .flatMap(productId -> productQueryService.handle(new GetProductByIdQuery(productId))
                        .<Result<Product, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("Product", productId.toString()))));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ProductResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Get product by ID", description = "Retrieves a specific product by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found",
                    content = @Content(schema = @Schema(implementation = ProductResource.class))),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<?> getProductById(
            @PathVariable
            @Parameter(description = "Product unique identifier", example = "1", required = true)
            Long productId
    ) {
        var product = productQueryService.handle(new GetProductByIdQuery(productId));
        if (product.isEmpty()) {
            var error = ApplicationError.notFound("Product", productId.toString());
            return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(error);
        }
        return ResponseEntity.ok(ProductResourceFromEntityAssembler.toResourceFromEntity(product.get()));
    }

    @GetMapping
    @Operation(summary = "Get products", description = "Retrieves the products filtered by categoryId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ProductResource.class))),
            @ApiResponse(responseCode = "400", description = "Missing or invalid query parameter")
    })
    public ResponseEntity<?> getProducts(
            @RequestParam(required = false)
            @Parameter(description = "Category identifier", example = "1")
            Long categoryId
    ) {
        if (categoryId != null) {
            var products = productQueryService.handle(new GetAllProductsByCategoryIdQuery(new CategoryId(categoryId)));
            return ResponseEntity.ok(products.stream().map(ProductResourceFromEntityAssembler::toResourceFromEntity).toList());
        }
        var products = productQueryService.handle(new GetAllProductsQuery());
        return ResponseEntity.ok(products.stream().map(ProductResourceFromEntityAssembler::toResourceFromEntity).toList());
    }

    @PutMapping("/{productId}")
    @Operation(summary = "Update product", description = "Updates the name, category, supplier, unit and minimum stock of a product.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully",
                    content = @Content(schema = @Schema(implementation = ProductResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Product or category not found")
    })
    public ResponseEntity<?> updateProduct(
            @PathVariable
            @Parameter(description = "Product unique identifier", example = "1", required = true)
            Long productId,
            @Valid @RequestBody UpdateProductResource resource
    ) {
        var command = UpdateProductCommandFromResourceAssembler.toCommandFromResource(productId, resource);
        var result = productCommandService.handle(command)
                .flatMap(id -> productQueryService.handle(new GetProductByIdQuery(id))
                        .<Result<Product, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("Product", id.toString()))));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ProductResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }
}
