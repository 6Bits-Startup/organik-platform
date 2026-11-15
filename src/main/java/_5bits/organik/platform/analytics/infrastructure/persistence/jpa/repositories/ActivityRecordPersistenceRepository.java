package _5bits.organik.platform.analytics.infrastructure.persistence.jpa.repositories;

import _5bits.organik.platform.analytics.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.analytics.infrastructure.persistence.jpa.entities.ActivityRecordPersistenceEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link ActivityRecordPersistenceEntity}.
 * <p>
 * Infrastructure detail used only by the {@code ActivityRecordRepositoryImpl} adapter.
 * </p>
 */
@Repository
public interface ActivityRecordPersistenceRepository extends JpaRepository<ActivityRecordPersistenceEntity, Long> {

    List<ActivityRecordPersistenceEntity> findAllByMinimarketId(MinimarketId minimarketId);
}
