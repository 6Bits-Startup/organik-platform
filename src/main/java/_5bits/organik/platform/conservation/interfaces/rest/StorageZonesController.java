package _5bits.organik.platform.conservation.interfaces.rest;

import _5bits.organik.platform.conservation.application.commandservices.StorageZoneCommandService;
import _5bits.organik.platform.conservation.application.queryservices.StorageZoneQueryService;
import _5bits.organik.platform.conservation.domain.model.aggregates.StorageZone;
import _5bits.organik.platform.conservation.domain.model.queries.GetAllStorageZonesByMinimarketIdQuery;
import _5bits.organik.platform.conservation.domain.model.queries.GetStorageZoneByIdQuery;
import _5bits.organik.platform.conservation.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.conservation.interfaces.rest.resources.CreateStorageZoneResource;
import _5bits.organik.platform.conservation.interfaces.rest.resources.StorageZoneResource;
import _5bits.organik.platform.conservation.interfaces.rest.transform.CreateStorageZoneCommandFromResourceAssembler;
import _5bits.organik.platform.conservation.interfaces.rest.transform.StorageZoneResourceFromEntityAssembler;
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
 * REST controller that exposes storage zone endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/storage-zones", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Storage Zones", description = "Storage zone management endpoints")
public class StorageZonesController {
    private final StorageZoneCommandService storageZoneCommandService;
    private final StorageZoneQueryService storageZoneQueryService;

    public StorageZonesController(StorageZoneCommandService storageZoneCommandService, StorageZoneQueryService storageZoneQueryService) {
        this.storageZoneCommandService = storageZoneCommandService;
        this.storageZoneQueryService = storageZoneQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a storage zone", description = "Creates a storage zone with its allowed temperature and humidity ranges.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Storage zone created successfully",
                    content = @Content(schema = @Schema(implementation = StorageZoneResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required"),
            @ApiResponse(responseCode = "409", description = "Conflict - resource already exists"),
            @ApiResponse(responseCode = "404", description = "Referenced resource not found")
    })
    public ResponseEntity<?> createStorageZone(@Valid @RequestBody CreateStorageZoneResource resource) {
        var command = CreateStorageZoneCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = storageZoneCommandService.handle(command)
                .flatMap(storageZoneId -> storageZoneQueryService.handle(new GetStorageZoneByIdQuery(storageZoneId))
                        .<Result<StorageZone, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("StorageZone", storageZoneId.toString()))));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                StorageZoneResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{storageZoneId}")
    @Operation(summary = "Get storage zone by ID", description = "Retrieves a specific storage zone by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Storage zone found",
                    content = @Content(schema = @Schema(implementation = StorageZoneResource.class))),
            @ApiResponse(responseCode = "404", description = "Storage zone not found")
    })
    public ResponseEntity<?> getStorageZoneById(
            @PathVariable
            @Parameter(description = "Storage zone unique identifier", example = "1", required = true)
            Long storageZoneId
    ) {
        var storageZone = storageZoneQueryService.handle(new GetStorageZoneByIdQuery(storageZoneId));
        if (storageZone.isEmpty()) {
            var error = ApplicationError.notFound("StorageZone", storageZoneId.toString());
            return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(error);
        }
        return ResponseEntity.ok(StorageZoneResourceFromEntityAssembler.toResourceFromEntity(storageZone.get()));
    }

    @GetMapping
    @Operation(summary = "Get storage zones", description = "Retrieves the storage zones filtered by minimarketId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Storage zones retrieved successfully",
                    content = @Content(schema = @Schema(implementation = StorageZoneResource.class))),
            @ApiResponse(responseCode = "400", description = "Missing or invalid query parameter")
    })
    public ResponseEntity<?> getStorageZones(
            @RequestParam(required = false)
            @Parameter(description = "Minimarket identifier", example = "mm-vida-verde")
            String minimarketId
    ) {
        if (minimarketId != null) {
            var storageZones = storageZoneQueryService.handle(new GetAllStorageZonesByMinimarketIdQuery(new MinimarketId(minimarketId)));
            return ResponseEntity.ok(storageZones.stream().map(StorageZoneResourceFromEntityAssembler::toResourceFromEntity).toList());
        }
        var error = ApplicationError.validationError("minimarketId", "Query parameter minimarketId is required");
        return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(error);
    }
}
