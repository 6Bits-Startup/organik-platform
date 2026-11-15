package _5bits.organik.platform.requisition.interfaces.acl;

/**
 * ACL facade that exposes supply request indicators to other bounded contexts.
 *
 * <p>Provides a simplified integration surface based on primitive values, without leaking
 * the internal model of the bounded context.</p>
 */
public interface RequisitionContextFacade {

    /**
     * Counts the supply requests of a minimarket with the given status.
     *
     * @param minimarketId minimarket identifier
     * @param status request status: PENDING, ACCEPTED or REJECTED
     * @return number of supply requests
     */
    long countSupplyRequestsByMinimarketIdAndStatus(String minimarketId, String status);
}
