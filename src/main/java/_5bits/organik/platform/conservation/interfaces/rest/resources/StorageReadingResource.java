package _5bits.organik.platform.conservation.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * Response resource representing a storage reading.
 */
@Schema(name = "StorageReadingResponse", description = "Storage reading information response")
public record StorageReadingResource(
    @Schema(description = "Storage reading unique identifier", example = "1")
    Long id,
    @Schema(description = "Storage zone where the reading was taken", example = "1")
    Long storageZoneId,
    @Schema(description = "Measured temperature (°C)", example = "5.5")
    Double temperature,
    @Schema(description = "Measured humidity (%)", example = "88.0")
    Double humidity,
    @Schema(description = "Date and time of the reading", example = "2026-11-14T09:30:00")
    LocalDateTime recordedAt,
    @Schema(description = "Source of the reading", example = "MANUAL")
    String source,
    @Schema(description = "Whether the reading is inside the allowed ranges of the zone", example = "true")
    Boolean withinRange
) {
}
