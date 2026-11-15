package _5bits.organik.platform.analytics.infrastructure.persistence.jpa.adapters;

import _5bits.organik.platform.analytics.domain.model.aggregates.ActivityRecord;
import _5bits.organik.platform.analytics.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.analytics.domain.repositories.ActivityRecordRepository;
import _5bits.organik.platform.analytics.infrastructure.persistence.jpa.assemblers.ActivityRecordPersistenceAssembler;
import _5bits.organik.platform.analytics.infrastructure.persistence.jpa.repositories.ActivityRecordPersistenceRepository;
import _5bits.organik.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Infrastructure adapter that implements the {@link ActivityRecordRepository} domain port with Spring Data JPA.
 * <p>
 * Maps between domain and persistence models through {@link ActivityRecordPersistenceAssembler} and publishes
 * the domain events registered by the aggregate after it is persisted.
 * </p>
 */
@Repository
public class ActivityRecordRepositoryImpl implements ActivityRecordRepository {
    private final ActivityRecordPersistenceRepository activityRecordPersistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public ActivityRecordRepositoryImpl(
            ActivityRecordPersistenceRepository activityRecordPersistenceRepository,
            DomainEventPublisher domainEventPublisher) {
        this.activityRecordPersistenceRepository = activityRecordPersistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public Optional<ActivityRecord> findById(Long id) {
        return activityRecordPersistenceRepository.findById(id).map(ActivityRecordPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<ActivityRecord> findAll() {
        return activityRecordPersistenceRepository.findAll().stream()
                .map(ActivityRecordPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<ActivityRecord> findAllByMinimarketId(MinimarketId minimarketId) {
        return activityRecordPersistenceRepository.findAllByMinimarketId(minimarketId).stream()
                .map(ActivityRecordPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public ActivityRecord save(ActivityRecord activityRecord) {
        var saved = activityRecordPersistenceRepository.save(ActivityRecordPersistenceAssembler.toPersistenceFromDomain(activityRecord));
        var savedActivityRecord = ActivityRecordPersistenceAssembler.toDomainFromPersistence(saved);
        domainEventPublisher.publishAndClearEvents(activityRecord);
        return savedActivityRecord;
    }

    @Override
    public boolean existsById(Long id) {
        return activityRecordPersistenceRepository.existsById(id);
    }
}
