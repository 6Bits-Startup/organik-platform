package _5bits.organik.platform.conservation.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

/**
 * Request resource used to record a storage reading.
 */
@Schema(name = "RecordStorageReadingRequest", description = "Request payload for recording a storage reading")
public record RecordStorageReadingResource(
    @Schema(description = "Storage zone where the reading was taken", example = "1")
    @NotNull(message = "{storageReading.error.storageZoneId.notNull}")
    @Min(value = 1, message = "{storageReading.error.storageZoneId.min}")
    Long storageZoneId,

    @Schema(description = "Measured temperature (°C)", example = "5.5")
    @NotNull(message = "{storageReading.error.temperature.notNull}")
    @DecimalMin(value = "-50.0", message = "{storageZone.error.temperature.range}")
    @DecimalMax(value = "60.0", message = "{storageZone.error.temperature.range}")
    Double temperature,

    @Schema(description = "Measured humidity (%)", example = "88.0")
    @NotNull(message = "{storageReading.error.humidity.notNull}")
    @DecimalMin(value = "0.0", message = "{storageZone.error.humidity.range}")
    @DecimalMax(value = "100.0", message = "{storageZone.error.humidity.range}")
    Double humidity,

    @Schema(description = "Date and time of the reading", example = "2026-11-14T09:30:00")
    @NotNull(message = "{storageReading.error.recordedAt.notNull}")
    LocalDateTime recordedAt,

    @Schema(description = "Source of the reading", example = "MANUAL", allowableValues = {"SENSOR", "MANUAL"})
    @NotBlank(message = "{storageReading.error.source.notBlank}")
    @Pattern(regexp = "^(SENSOR|MANUAL)$", message = "{storageReading.error.source.pattern}")
    String source
) {
}
