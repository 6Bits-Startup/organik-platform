package _5bits.organik.platform.procurements.domain.model.events;

import java.time.LocalDate;
import java.util.List;

/**
 * Domain event raised when the minimarket accepts a shipment order.
 *
 * @param shipmentOrderId shipment order identifier
 * @param supplierId supplier that sent the order
 * @param minimarketId minimarket that receives the order
 * @param lines accepted lines
 */
public record ShipmentOrderAcceptedEvent(
        Long shipmentOrderId,
        String supplierId,
        String minimarketId,
        List<AcceptedLine> lines) {

    /**
     * Accepted line of the shipment order.
     *
     * @param productId product identifier
     * @param quantity received quantity
     * @param lotCode lot code
     * @param expiresOn expiration date
     */
    public record AcceptedLine(String productId, Integer quantity, String lotCode, LocalDate expiresOn) {
    }
}
