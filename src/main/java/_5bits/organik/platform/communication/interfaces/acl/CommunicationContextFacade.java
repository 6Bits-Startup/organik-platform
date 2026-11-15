package _5bits.organik.platform.communication.interfaces.acl;

/**
 * ACL facade that exposes notification indicators to other bounded contexts.
 *
 * <p>Provides a simplified integration surface based on primitive values, without leaking
 * the internal model of the bounded context.</p>
 */
public interface CommunicationContextFacade {

    /**
     * Counts the notifications of a minimarket that have not been read.
     *
     * @param minimarketId minimarket whose staff receives the notifications
     * @return number of unread notifications
     */
    long countUnreadNotifications(String minimarketId);
}
