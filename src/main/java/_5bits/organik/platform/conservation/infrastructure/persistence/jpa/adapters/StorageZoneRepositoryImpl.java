package _5bits.organik.platform.conservation.infrastructure.persistence.jpa.adapters;

import _5bits.organik.platform.conservation.domain.model.aggregates.StorageZone;
import _5bits.organik.platform.conservation.domain.model.valueobjects.ZoneName;
import _5bits.organik.platform.conservation.domain.repositories.StorageZoneRepository;
import _5bits.organik.platform.conservation.infrastructure.persistence.jpa.assemblers.StorageZonePersistenceAssembler;
import _5bits.organik.platform.conservation.infrastructure.persistence.jpa.repositories.StorageZonePersistenceRepository;
import _5bits.organik.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Infrastructure adapter that implements the {@link StorageZoneRepository} domain port with Spring Data JPA.
 * <p>
 * Maps between domain and persistence models through {@link StorageZonePersistenceAssembler} and publishes
 * the domain events registered by the aggregate after it is persisted.
 * </p>
 */
@Repository
public class StorageZoneRepositoryImpl implements StorageZoneRepository {
    private final StorageZonePersistenceRepository storageZonePersistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public StorageZoneRepositoryImpl(
            StorageZonePersistenceRepository storageZonePersistenceRepository,
            DomainEventPublisher domainEventPublisher) {
        this.storageZonePersistenceRepository = storageZonePersistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public Optional<StorageZone> findById(Long id) {
        return storageZonePersistenceRepository.findById(id).map(StorageZonePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<StorageZone> findAll() {
        return storageZonePersistenceRepository.findAll().stream()
                .map(StorageZonePersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean existsByName(ZoneName name) {
        return storageZonePersistenceRepository.existsByName(name);
    }

    @Override
    public StorageZone save(StorageZone storageZone) {
        var saved = storageZonePersistenceRepository.save(StorageZonePersistenceAssembler.toPersistenceFromDomain(storageZone));
        var savedStorageZone = StorageZonePersistenceAssembler.toDomainFromPersistence(saved);
        domainEventPublisher.publishAndClearEvents(storageZone);
        return savedStorageZone;
    }

    @Override
    public boolean existsById(Long id) {
        return storageZonePersistenceRepository.existsById(id);
    }
}
