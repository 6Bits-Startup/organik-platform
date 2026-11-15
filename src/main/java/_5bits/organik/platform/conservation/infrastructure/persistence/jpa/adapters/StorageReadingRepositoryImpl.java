package _5bits.organik.platform.conservation.infrastructure.persistence.jpa.adapters;

import _5bits.organik.platform.conservation.domain.model.aggregates.StorageReading;
import _5bits.organik.platform.conservation.domain.model.valueobjects.StorageZoneId;
import _5bits.organik.platform.conservation.domain.repositories.StorageReadingRepository;
import _5bits.organik.platform.conservation.infrastructure.persistence.jpa.assemblers.StorageReadingPersistenceAssembler;
import _5bits.organik.platform.conservation.infrastructure.persistence.jpa.repositories.StorageReadingPersistenceRepository;
import _5bits.organik.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Infrastructure adapter that implements the {@link StorageReadingRepository} domain port with Spring Data JPA.
 * <p>
 * Maps between domain and persistence models through {@link StorageReadingPersistenceAssembler} and publishes
 * the domain events registered by the aggregate after it is persisted.
 * </p>
 */
@Repository
public class StorageReadingRepositoryImpl implements StorageReadingRepository {
    private final StorageReadingPersistenceRepository storageReadingPersistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public StorageReadingRepositoryImpl(
            StorageReadingPersistenceRepository storageReadingPersistenceRepository,
            DomainEventPublisher domainEventPublisher) {
        this.storageReadingPersistenceRepository = storageReadingPersistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public Optional<StorageReading> findById(Long id) {
        return storageReadingPersistenceRepository.findById(id).map(StorageReadingPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<StorageReading> findAll() {
        return storageReadingPersistenceRepository.findAll().stream()
                .map(StorageReadingPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<StorageReading> findAllByStorageZoneId(StorageZoneId storageZoneId) {
        return storageReadingPersistenceRepository.findAllByStorageZoneId(storageZoneId).stream()
                .map(StorageReadingPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public StorageReading save(StorageReading storageReading) {
        boolean isNew = storageReading.getId() == null;
        var saved = storageReadingPersistenceRepository.save(StorageReadingPersistenceAssembler.toPersistenceFromDomain(storageReading));
        var savedStorageReading = StorageReadingPersistenceAssembler.toDomainFromPersistence(saved);
        if (isNew) {
            savedStorageReading.onRecorded();
            domainEventPublisher.publishAndClearEvents(savedStorageReading);
        }
        domainEventPublisher.publishAndClearEvents(storageReading);
        return savedStorageReading;
    }

    @Override
    public boolean existsById(Long id) {
        return storageReadingPersistenceRepository.existsById(id);
    }
}
