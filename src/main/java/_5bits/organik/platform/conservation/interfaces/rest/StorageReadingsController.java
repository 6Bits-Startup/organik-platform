package _5bits.organik.platform.conservation.interfaces.rest;

import _5bits.organik.platform.conservation.application.commandservices.StorageReadingCommandService;
import _5bits.organik.platform.conservation.application.queryservices.StorageReadingQueryService;
import _5bits.organik.platform.conservation.domain.model.aggregates.StorageReading;
import _5bits.organik.platform.conservation.domain.model.queries.GetAllStorageReadingsByStorageZoneIdQuery;
import _5bits.organik.platform.conservation.domain.model.queries.GetStorageReadingByIdQuery;
import _5bits.organik.platform.conservation.domain.model.valueobjects.StorageZoneId;
import _5bits.organik.platform.conservation.interfaces.rest.resources.RecordStorageReadingResource;
import _5bits.organik.platform.conservation.interfaces.rest.resources.StorageReadingResource;
import _5bits.organik.platform.conservation.interfaces.rest.transform.RecordStorageReadingCommandFromResourceAssembler;
import _5bits.organik.platform.conservation.interfaces.rest.transform.StorageReadingResourceFromEntityAssembler;
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
 * REST controller that exposes storage reading endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/storage-readings", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Storage Readings", description = "Storage reading management endpoints")
public class StorageReadingsController {
    private final StorageReadingCommandService storageReadingCommandService;
    private final StorageReadingQueryService storageReadingQueryService;

    public StorageReadingsController(StorageReadingCommandService storageReadingCommandService, StorageReadingQueryService storageReadingQueryService) {
        this.storageReadingCommandService = storageReadingCommandService;
        this.storageReadingQueryService = storageReadingQueryService;
    }

    @PostMapping
    @Operation(summary = "Record a storage reading", description = "Records a temperature and humidity reading and checks it against the ranges of its zone.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Storage reading created successfully",
                    content = @Content(schema = @Schema(implementation = StorageReadingResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required"),
            @ApiResponse(responseCode = "404", description = "Referenced resource not found")
    })
    public ResponseEntity<?> createStorageReading(@Valid @RequestBody RecordStorageReadingResource resource) {
        var command = RecordStorageReadingCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = storageReadingCommandService.handle(command)
                .flatMap(storageReadingId -> storageReadingQueryService.handle(new GetStorageReadingByIdQuery(storageReadingId))
                        .<Result<StorageReading, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("StorageReading", storageReadingId.toString()))));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                StorageReadingResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{storageReadingId}")
    @Operation(summary = "Get storage reading by ID", description = "Retrieves a specific storage reading by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Storage reading found",
                    content = @Content(schema = @Schema(implementation = StorageReadingResource.class))),
            @ApiResponse(responseCode = "404", description = "Storage reading not found")
    })
    public ResponseEntity<?> getStorageReadingById(
            @PathVariable
            @Parameter(description = "Storage reading unique identifier", example = "1", required = true)
            Long storageReadingId
    ) {
        var storageReading = storageReadingQueryService.handle(new GetStorageReadingByIdQuery(storageReadingId));
        if (storageReading.isEmpty()) {
            var error = ApplicationError.notFound("StorageReading", storageReadingId.toString());
            return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(error);
        }
        return ResponseEntity.ok(StorageReadingResourceFromEntityAssembler.toResourceFromEntity(storageReading.get()));
    }

    @GetMapping
    @Operation(summary = "Get storage readings", description = "Retrieves the storage readings filtered by storageZoneId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Storage readings retrieved successfully",
                    content = @Content(schema = @Schema(implementation = StorageReadingResource.class))),
            @ApiResponse(responseCode = "400", description = "Missing or invalid query parameter")
    })
    public ResponseEntity<?> getStorageReadings(
            @RequestParam(required = false)
            @Parameter(description = "Storage zone identifier", example = "1")
            Long storageZoneId
    ) {
        if (storageZoneId != null) {
            var storageReadings = storageReadingQueryService.handle(new GetAllStorageReadingsByStorageZoneIdQuery(new StorageZoneId(storageZoneId)));
            return ResponseEntity.ok(storageReadings.stream().map(StorageReadingResourceFromEntityAssembler::toResourceFromEntity).toList());
        }
        var error = ApplicationError.validationError("storageZoneId", "Query parameter storageZoneId is required");
        return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(error);
    }
}
