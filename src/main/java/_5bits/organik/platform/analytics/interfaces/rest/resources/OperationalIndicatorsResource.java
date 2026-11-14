package _5bits.organik.platform.analytics.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response resource representing the operational indicators of a minimarket.
 */
@Schema(name = "OperationalIndicatorsResponse", description = "Operational indicators of a minimarket")
public record OperationalIndicatorsResource(
    @Schema(description = "Minimarket identifier", example = "mm-vida-verde")
    String minimarketId,
    @Schema(description = "Stock lots already expired", example = "1")
    long expiredLots,
    @Schema(description = "Stock lots that expire in the next 7 days", example = "2")
    long lotsExpiringWithin7Days,
    @Schema(description = "Stock lots that expire in the next 15 days", example = "4")
    long lotsExpiringWithin15Days,
    @Schema(description = "Units registered as waste", example = "6")
    long wasteUnits,
    @Schema(description = "Storage readings outside the allowed ranges", example = "1")
    long outOfRangeReadings,
    @Schema(description = "Shipment orders accepted", example = "8")
    long acceptedShipmentOrders,
    @Schema(description = "Shipment orders rejected", example = "2")
    long rejectedShipmentOrders,
    @Schema(description = "Percentage of decided shipment orders that were accepted", example = "80.0")
    double shipmentOrderAcceptanceRate
) {
}
