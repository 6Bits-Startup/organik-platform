package _5bits.organik.platform.communication.infrastructure.persistence.jpa.assemblers;

import _5bits.organik.platform.communication.domain.model.aggregates.Notification;
import _5bits.organik.platform.communication.infrastructure.persistence.jpa.entities.NotificationPersistenceEntity;


/**
 * Assembler that maps between the {@link Notification} domain aggregate and its JPA persistence entity.
 * <p>
 * Acts as the anti-corruption layer of the persistence adapter: JPA concerns never leak into the domain.
 * </p>
 */
public final class NotificationPersistenceAssembler {

    private NotificationPersistenceAssembler() {
    }

    /**
     * Reconstitutes a domain aggregate from its persistence entity.
     *
     * @param entity persistence entity, may be {@code null}
     * @return the reconstituted aggregate, or {@code null} when the entity is {@code null}
     */
    public static Notification toDomainFromPersistence(NotificationPersistenceEntity entity) {
        if (entity == null) return null;
        return Notification.reconstitute(
                entity.getId(),
                entity.getMinimarketId(),
                entity.getType(),
                entity.getSeverity(),
                entity.getTitle(),
                entity.getIsRead(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    /**
     * Maps a domain aggregate to a persistence entity.
     *
     * @param notification domain aggregate, may be {@code null}
     * @return the persistence entity, or {@code null} when the aggregate is {@code null}
     */
    public static NotificationPersistenceEntity toPersistenceFromDomain(Notification notification) {
        if (notification == null) return null;
        var entity = new NotificationPersistenceEntity();
        if (notification.getId() != null) {
            entity.setId(notification.getId());
        }
        entity.setMinimarketId(notification.getMinimarketId());
        entity.setType(notification.getType());
        entity.setSeverity(notification.getSeverity());
        entity.setTitle(notification.getTitle());
        entity.setIsRead(notification.getIsRead());
        entity.setCreatedAt(notification.getCreatedAt());
        return entity;
    }
}
