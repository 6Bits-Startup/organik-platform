package _5bits.organik.platform.procurements.interfaces.events;

import java.time.LocalDate;
import java.util.List;

/**
 * Integration event published by the procurements bounded context when a shipment order is accepted.
 *
 * @param shipmentOrderId shipment order identifier
 * @param supplierId supplier that sent the order
 * @param minimarketId minimarket that receives the order
 * @param lines accepted lines
 */
public record ShipmentOrderAcceptedIntegrationEvent(
        Long shipmentOrderId,
        String supplierId,
        String minimarketId,
        List<Line> lines) {

    /**
     * Accepted line of the shipment order.
     *
     * @param productId product identifier
     * @param quantity received quantity
     * @param lotCode lot code
     * @param expiresOn expiration date
     */
    public record Line(String productId, Integer quantity, String lotCode, LocalDate expiresOn) {
    }
}
