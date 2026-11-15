package _5bits.organik.platform.conservation.domain.repositories;

import _5bits.organik.platform.conservation.domain.model.aggregates.StorageZone;
import _5bits.organik.platform.conservation.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.conservation.domain.model.valueobjects.ZoneName;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository port for the {@link StorageZone} aggregate.
 * <p>
 * Pure Java interface with no framework dependencies. The infrastructure layer provides the
 * adapter that implements it with Spring Data JPA.
 * </p>
 */
public interface StorageZoneRepository {

    Optional<StorageZone> findById(Long id);

    List<StorageZone> findAll();

    List<StorageZone> findAllByMinimarketId(MinimarketId minimarketId);

    boolean existsByMinimarketIdAndName(MinimarketId minimarketId, ZoneName name);

    StorageZone save(StorageZone storageZone);

    boolean existsById(Long id);
}
