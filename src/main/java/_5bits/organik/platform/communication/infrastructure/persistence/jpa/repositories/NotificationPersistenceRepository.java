package _5bits.organik.platform.communication.infrastructure.persistence.jpa.repositories;

import _5bits.organik.platform.communication.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.communication.infrastructure.persistence.jpa.entities.NotificationPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for {@link NotificationPersistenceEntity}.
 * <p>
 * Infrastructure detail used only by the {@code NotificationRepositoryImpl} adapter.
 * </p>
 */
@Repository
public interface NotificationPersistenceRepository extends JpaRepository<NotificationPersistenceEntity, Long> {
    List<NotificationPersistenceEntity> findAllByMinimarketId(MinimarketId minimarketId);
}
