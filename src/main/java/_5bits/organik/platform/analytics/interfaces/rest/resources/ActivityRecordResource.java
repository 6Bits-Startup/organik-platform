package _5bits.organik.platform.analytics.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

/**
 * Response resource representing an entry of the activity history.
 */
@Schema(name = "ActivityRecordResponse", description = "Activity history entry")
public record ActivityRecordResource(
    @Schema(description = "Activity record unique identifier", example = "1")
    Long id,
    @Schema(description = "Minimarket related to the activity", example = "mm-vida-verde")
    String minimarketId,
    @Schema(description = "Activity type", example = "SHIPMENT_ORDER_ACCEPTED")
    String type,
    @Schema(description = "Activity description", example = "Shipment order 1 from supplier sup-bioandes accepted with 2 lot(s)")
    String description,
    @Schema(description = "Date of the activity")
    Date occurredAt
) {
}
