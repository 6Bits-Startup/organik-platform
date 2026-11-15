package _5bits.organik.platform.conservation.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response resource representing a storage zone.
 */
@Schema(name = "StorageZoneResponse", description = "Storage zone information response")
public record StorageZoneResource(
    @Schema(description = "Storage zone unique identifier", example = "1")
    Long id,
    @Schema(description = "Minimarket that owns the storage zone", example = "mm-vida-verde")
    String minimarketId,
    @Schema(description = "Name of the storage zone", example = "Cold room A")
    String name,
    @Schema(description = "Storage condition of the zone", example = "REFRIGERATED")
    String storageCondition,
    @Schema(description = "Minimum allowed temperature (°C)", example = "2.0")
    Double minTemperature,
    @Schema(description = "Maximum allowed temperature (°C)", example = "8.0")
    Double maxTemperature,
    @Schema(description = "Minimum allowed humidity (%)", example = "80.0")
    Double minHumidity,
    @Schema(description = "Maximum allowed humidity (%)", example = "95.0")
    Double maxHumidity
) {
}
