package _5bits.organik.platform.analytics.infrastructure.persistence.jpa.assemblers;

import _5bits.organik.platform.analytics.domain.model.aggregates.ActivityRecord;
import _5bits.organik.platform.analytics.infrastructure.persistence.jpa.entities.ActivityRecordPersistenceEntity;


/**
 * Assembler that maps between the {@link ActivityRecord} domain aggregate and its JPA persistence entity.
 * <p>
 * Acts as the anti-corruption layer of the persistence adapter: JPA concerns never leak into the domain.
 * </p>
 */
public final class ActivityRecordPersistenceAssembler {

    private ActivityRecordPersistenceAssembler() {
    }

    /**
     * Reconstitutes a domain aggregate from its persistence entity.
     *
     * @param entity persistence entity, may be {@code null}
     * @return the reconstituted aggregate, or {@code null} when the entity is {@code null}
     */
    public static ActivityRecord toDomainFromPersistence(ActivityRecordPersistenceEntity entity) {
        if (entity == null) return null;
        return ActivityRecord.reconstitute(
                entity.getId(),
                entity.getMinimarketId(),
                entity.getType(),
                entity.getDescription(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    /**
     * Maps a domain aggregate to a persistence entity.
     *
     * @param activityRecord domain aggregate, may be {@code null}
     * @return the persistence entity, or {@code null} when the aggregate is {@code null}
     */
    public static ActivityRecordPersistenceEntity toPersistenceFromDomain(ActivityRecord activityRecord) {
        if (activityRecord == null) return null;
        var entity = new ActivityRecordPersistenceEntity();
        if (activityRecord.getId() != null) {
            entity.setId(activityRecord.getId());
        }
        entity.setMinimarketId(activityRecord.getMinimarketId());
        entity.setType(activityRecord.getType());
        entity.setDescription(activityRecord.getDescription());
        entity.setCreatedAt(activityRecord.getCreatedAt());
        return entity;
    }
}
