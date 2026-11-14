package _5bits.organik.platform.conservation.domain.repositories;

import _5bits.organik.platform.conservation.domain.model.aggregates.StorageReading;
import _5bits.organik.platform.conservation.domain.model.valueobjects.StorageZoneId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository port for the {@link StorageReading} aggregate.
 * <p>
 * Pure Java interface with no framework dependencies. The infrastructure layer provides the
 * adapter that implements it with Spring Data JPA.
 * </p>
 */
public interface StorageReadingRepository {

    Optional<StorageReading> findById(Long id);

    List<StorageReading> findAll();

    List<StorageReading> findAllByStorageZoneId(StorageZoneId storageZoneId);

    StorageReading save(StorageReading storageReading);

    boolean existsById(Long id);
}
