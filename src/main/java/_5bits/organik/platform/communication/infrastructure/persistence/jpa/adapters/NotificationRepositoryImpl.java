package _5bits.organik.platform.communication.infrastructure.persistence.jpa.adapters;

import _5bits.organik.platform.communication.domain.model.aggregates.Notification;
import _5bits.organik.platform.communication.domain.repositories.NotificationRepository;
import _5bits.organik.platform.communication.infrastructure.persistence.jpa.assemblers.NotificationPersistenceAssembler;
import _5bits.organik.platform.communication.infrastructure.persistence.jpa.repositories.NotificationPersistenceRepository;
import _5bits.organik.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Infrastructure adapter that implements the {@link NotificationRepository} domain port with Spring Data JPA.
 * <p>
 * Maps between domain and persistence models through {@link NotificationPersistenceAssembler} and publishes
 * the domain events registered by the aggregate after it is persisted.
 * </p>
 */
@Repository
public class NotificationRepositoryImpl implements NotificationRepository {
    private final NotificationPersistenceRepository notificationPersistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public NotificationRepositoryImpl(
            NotificationPersistenceRepository notificationPersistenceRepository,
            DomainEventPublisher domainEventPublisher) {
        this.notificationPersistenceRepository = notificationPersistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return notificationPersistenceRepository.findById(id).map(NotificationPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Notification> findAll() {
        return notificationPersistenceRepository.findAll().stream()
                .map(NotificationPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Notification save(Notification notification) {
        var saved = notificationPersistenceRepository.save(NotificationPersistenceAssembler.toPersistenceFromDomain(notification));
        var savedNotification = NotificationPersistenceAssembler.toDomainFromPersistence(saved);
        domainEventPublisher.publishAndClearEvents(notification);
        return savedNotification;
    }

    @Override
    public boolean existsById(Long id) {
        return notificationPersistenceRepository.existsById(id);
    }
}
