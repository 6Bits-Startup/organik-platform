package _5bits.organik.platform.procurements.interfaces.acl;

/**
 * ACL facade that exposes shipment order indicators to other bounded contexts.
 *
 * <p>Provides a simplified integration surface based on primitive values, without leaking
 * the internal model of the bounded context.</p>
 */
public interface ProcurementsContextFacade {

    /**
     * Counts the shipment orders of a minimarket with the given status.
     *
     * @param minimarketId minimarket identifier
     * @param status order status: PENDING, ACCEPTED or REJECTED
     * @return number of shipment orders
     */
    long countShipmentOrdersByMinimarketIdAndStatus(String minimarketId, String status);
}
