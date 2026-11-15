package _5bits.organik.platform.conservation.infrastructure.persistence.jpa.repositories;

import _5bits.organik.platform.conservation.domain.model.valueobjects.StorageZoneId;
import _5bits.organik.platform.conservation.infrastructure.persistence.jpa.entities.StorageReadingPersistenceEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link StorageReadingPersistenceEntity}.
 * <p>
 * Infrastructure detail used only by the {@code StorageReadingRepositoryImpl} adapter.
 * </p>
 */
@Repository
public interface StorageReadingPersistenceRepository extends JpaRepository<StorageReadingPersistenceEntity, Long> {

    List<StorageReadingPersistenceEntity> findAllByStorageZoneId(StorageZoneId storageZoneId);
}
