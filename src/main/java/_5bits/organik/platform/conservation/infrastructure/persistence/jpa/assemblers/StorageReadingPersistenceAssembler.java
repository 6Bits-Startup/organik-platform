package _5bits.organik.platform.conservation.infrastructure.persistence.jpa.assemblers;

import _5bits.organik.platform.conservation.domain.model.aggregates.StorageReading;
import _5bits.organik.platform.conservation.infrastructure.persistence.jpa.entities.StorageReadingPersistenceEntity;


/**
 * Assembler that maps between the {@link StorageReading} domain aggregate and its JPA persistence entity.
 * <p>
 * Acts as the anti-corruption layer of the persistence adapter: JPA concerns never leak into the domain.
 * </p>
 */
public final class StorageReadingPersistenceAssembler {

    private StorageReadingPersistenceAssembler() {
    }

    /**
     * Reconstitutes a domain aggregate from its persistence entity.
     *
     * @param entity persistence entity, may be {@code null}
     * @return the reconstituted aggregate, or {@code null} when the entity is {@code null}
     */
    public static StorageReading toDomainFromPersistence(StorageReadingPersistenceEntity entity) {
        if (entity == null) return null;
        return StorageReading.reconstitute(
                entity.getId(),
                entity.getStorageZoneId(),
                entity.getMinimarketId(),
                entity.getTemperature(),
                entity.getHumidity(),
                entity.getRecordedAt(),
                entity.getSource(),
                entity.getWithinRange());
    }

    /**
     * Maps a domain aggregate to a persistence entity.
     *
     * @param storageReading domain aggregate, may be {@code null}
     * @return the persistence entity, or {@code null} when the aggregate is {@code null}
     */
    public static StorageReadingPersistenceEntity toPersistenceFromDomain(StorageReading storageReading) {
        if (storageReading == null) return null;
        var entity = new StorageReadingPersistenceEntity();
        if (storageReading.getId() != null) {
            entity.setId(storageReading.getId());
        }
        entity.setStorageZoneId(storageReading.getStorageZoneId());
        entity.setMinimarketId(storageReading.getMinimarketId());
        entity.setTemperature(storageReading.getTemperature());
        entity.setHumidity(storageReading.getHumidity());
        entity.setRecordedAt(storageReading.getRecordedAt());
        entity.setSource(storageReading.getSource());
        entity.setWithinRange(storageReading.getWithinRange());
        return entity;
    }
}
