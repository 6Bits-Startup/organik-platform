package _5bits.organik.platform.conservation.infrastructure.persistence.jpa.repositories;

import _5bits.organik.platform.conservation.domain.model.valueobjects.ZoneName;
import _5bits.organik.platform.conservation.infrastructure.persistence.jpa.entities.StorageZonePersistenceEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link StorageZonePersistenceEntity}.
 * <p>
 * Infrastructure detail used only by the {@code StorageZoneRepositoryImpl} adapter.
 * </p>
 */
@Repository
public interface StorageZonePersistenceRepository extends JpaRepository<StorageZonePersistenceEntity, Long> {

    boolean existsByName(ZoneName name);
}
