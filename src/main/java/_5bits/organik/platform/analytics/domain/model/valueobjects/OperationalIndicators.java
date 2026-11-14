package _5bits.organik.platform.analytics.domain.model.valueobjects;

/**
 * Operational indicators of a minimarket used for decision making.
 *
 * @param minimarketId minimarket identifier
 * @param expiredLots stock lots already expired
 * @param lotsExpiringWithin7Days stock lots that expire in the next 7 days
 * @param lotsExpiringWithin15Days stock lots that expire in the next 15 days
 * @param wasteUnits units registered as waste
 * @param outOfRangeReadings storage readings outside the allowed ranges
 * @param acceptedShipmentOrders shipment orders accepted by the minimarket
 * @param rejectedShipmentOrders shipment orders rejected by the minimarket
 */
public record OperationalIndicators(
        MinimarketId minimarketId,
        long expiredLots,
        long lotsExpiringWithin7Days,
        long lotsExpiringWithin15Days,
        long wasteUnits,
        long outOfRangeReadings,
        long acceptedShipmentOrders,
        long rejectedShipmentOrders) {

    public OperationalIndicators {
        if (minimarketId == null) {
            throw new IllegalArgumentException("minimarketId cannot be null");
        }
    }

    /**
     * @return percentage of decided shipment orders that were accepted, or 0 when none was decided
     */
    public double shipmentOrderAcceptanceRate() {
        var decided = acceptedShipmentOrders + rejectedShipmentOrders;
        return decided == 0 ? 0.0 : Math.round(acceptedShipmentOrders * 1000.0 / decided) / 10.0;
    }
}
