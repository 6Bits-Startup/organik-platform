package _5bits.organik.platform.conservation.interfaces.acl;

/**
 * ACL facade that exposes conservation indicators to other bounded contexts.
 *
 * <p>Provides a simplified integration surface based on primitive values, without leaking
 * the internal model of the bounded context.</p>
 */
public interface ConservationContextFacade {

    /**
     * Counts the storage readings that were outside the allowed ranges of their zone.
     *
     * @return number of out-of-range readings
     */
    long countOutOfRangeReadings();
}
