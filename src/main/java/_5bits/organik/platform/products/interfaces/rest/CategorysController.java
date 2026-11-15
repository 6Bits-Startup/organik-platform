package _5bits.organik.platform.products.interfaces.rest;

import _5bits.organik.platform.products.application.commandservices.CategoryCommandService;
import _5bits.organik.platform.products.application.queryservices.CategoryQueryService;
import _5bits.organik.platform.products.domain.model.aggregates.Category;
import _5bits.organik.platform.products.domain.model.queries.GetAllCategoriesQuery;
import _5bits.organik.platform.products.domain.model.queries.GetCategoryByIdQuery;
import _5bits.organik.platform.products.interfaces.rest.resources.CategoryResource;
import _5bits.organik.platform.products.interfaces.rest.resources.CreateCategoryResource;
import _5bits.organik.platform.products.interfaces.rest.transform.CategoryResourceFromEntityAssembler;
import _5bits.organik.platform.products.interfaces.rest.transform.CreateCategoryCommandFromResourceAssembler;
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

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes category endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/categories", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Categories", description = "Product category management endpoints")
public class CategorysController {
    private final CategoryCommandService categoryCommandService;
    private final CategoryQueryService categoryQueryService;

    public CategorysController(CategoryCommandService categoryCommandService, CategoryQueryService categoryQueryService) {
        this.categoryCommandService = categoryCommandService;
        this.categoryQueryService = categoryQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a category", description = "Creates a product category with a unique name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully",
                    content = @Content(schema = @Schema(implementation = CategoryResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required"),
            @ApiResponse(responseCode = "409", description = "Conflict - resource already exists"),
            @ApiResponse(responseCode = "404", description = "Referenced resource not found")
    })
    public ResponseEntity<?> createCategory(@Valid @RequestBody CreateCategoryResource resource) {
        var command = CreateCategoryCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = categoryCommandService.handle(command)
                .flatMap(categoryId -> categoryQueryService.handle(new GetCategoryByIdQuery(categoryId))
                        .<Result<Category, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("Category", categoryId.toString()))));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                CategoryResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{categoryId}")
    @Operation(summary = "Get category by ID", description = "Retrieves a specific category by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found",
                    content = @Content(schema = @Schema(implementation = CategoryResource.class))),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<?> getCategoryById(
            @PathVariable
            @Parameter(description = "Category unique identifier", example = "1", required = true)
            Long categoryId
    ) {
        var category = categoryQueryService.handle(new GetCategoryByIdQuery(categoryId));
        if (category.isEmpty()) {
            var error = ApplicationError.notFound("Category", categoryId.toString());
            return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(error);
        }
        return ResponseEntity.ok(CategoryResourceFromEntityAssembler.toResourceFromEntity(category.get()));
    }

    @GetMapping
    @Operation(summary = "Get all categories", description = "Retrieves every category.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories retrieved successfully",
                    content = @Content(schema = @Schema(implementation = CategoryResource.class)))
    })
    public ResponseEntity<List<CategoryResource>> getAllCategorys() {
        var categorys = categoryQueryService.handle(new GetAllCategoriesQuery());
        var resources = categorys.stream()
                .map(CategoryResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}
