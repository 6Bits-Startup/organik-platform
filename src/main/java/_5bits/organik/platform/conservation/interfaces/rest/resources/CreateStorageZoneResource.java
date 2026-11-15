package _5bits.organik.platform.conservation.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

/**
 * Request resource used to create a storage zone.
 */
@Schema(name = "CreateStorageZoneRequest", description = "Request payload for creating a storage zone")
public record CreateStorageZoneResource(
    @Schema(description = "Minimarket that owns the storage zone", example = "mm-vida-verde")
    @NotBlank(message = "{storageZone.error.minimarketId.notBlank}")
    @Size(max = 256, message = "{storageZone.error.minimarketId.size}")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "{storageZone.error.minimarketId.pattern}")
    String minimarketId,
    @Schema(description = "Name of the storage zone", example = "Cold room A", maxLength = 100)
    @NotBlank(message = "{storageZone.error.name.notBlank}")
    @Size(max = 100, message = "{storageZone.error.name.size}")
    String name,

    @Schema(description = "Storage condition of the zone", example = "REFRIGERATED",
            allowableValues = {"REFRIGERATED", "COOL", "AMBIENT", "FROZEN"})
    @NotBlank(message = "{storageZone.error.storageCondition.notBlank}")
    @Pattern(regexp = "^(REFRIGERATED|COOL|AMBIENT|FROZEN)$", message = "{storageZone.error.storageCondition.pattern}")
    String storageCondition,

    @Schema(description = "Minimum allowed temperature (°C)", example = "2.0")
    @NotNull(message = "{storageZone.error.temperature.notNull}")
    @DecimalMin(value = "-50.0", message = "{storageZone.error.temperature.range}")
    @DecimalMax(value = "60.0", message = "{storageZone.error.temperature.range}")
    Double minTemperature,

    @Schema(description = "Maximum allowed temperature (°C)", example = "8.0")
    @NotNull(message = "{storageZone.error.temperature.notNull}")
    @DecimalMin(value = "-50.0", message = "{storageZone.error.temperature.range}")
    @DecimalMax(value = "60.0", message = "{storageZone.error.temperature.range}")
    Double maxTemperature,

    @Schema(description = "Minimum allowed humidity (%)", example = "80.0")
    @NotNull(message = "{storageZone.error.humidity.notNull}")
    @DecimalMin(value = "0.0", message = "{storageZone.error.humidity.range}")
    @DecimalMax(value = "100.0", message = "{storageZone.error.humidity.range}")
    Double minHumidity,

    @Schema(description = "Maximum allowed humidity (%)", example = "95.0")
    @NotNull(message = "{storageZone.error.humidity.notNull}")
    @DecimalMin(value = "0.0", message = "{storageZone.error.humidity.range}")
    @DecimalMax(value = "100.0", message = "{storageZone.error.humidity.range}")
    Double maxHumidity
) {
}
