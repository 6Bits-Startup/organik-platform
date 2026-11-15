package _5bits.organik.platform.communication.domain.repositories;

import _5bits.organik.platform.communication.domain.model.aggregates.Notification;
import _5bits.organik.platform.communication.domain.model.valueobjects.MinimarketId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository port for the {@link Notification} aggregate.
 * <p>
 * Pure Java interface with no framework dependencies. The infrastructure layer provides the
 * adapter that implements it with Spring Data JPA.
 * </p>
 */
public interface NotificationRepository {

    Optional<Notification> findById(Long id);

    List<Notification> findAll();

    List<Notification> findAllByMinimarketId(MinimarketId minimarketId);

    Notification save(Notification notification);

    boolean existsById(Long id);
}
