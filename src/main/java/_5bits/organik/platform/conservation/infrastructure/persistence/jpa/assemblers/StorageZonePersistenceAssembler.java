package _5bits.organik.platform.conservation.infrastructure.persistence.jpa.assemblers;

import _5bits.organik.platform.conservation.domain.model.aggregates.StorageZone;
import _5bits.organik.platform.conservation.domain.model.valueobjects.Humidity;
import _5bits.organik.platform.conservation.domain.model.valueobjects.HumidityRange;
import _5bits.organik.platform.conservation.domain.model.valueobjects.Temperature;
import _5bits.organik.platform.conservation.domain.model.valueobjects.TemperatureRange;
import _5bits.organik.platform.conservation.infrastructure.persistence.jpa.embeddables.HumidityRangePersistenceEmbeddable;
import _5bits.organik.platform.conservation.infrastructure.persistence.jpa.embeddables.TemperatureRangePersistenceEmbeddable;
import _5bits.organik.platform.conservation.infrastructure.persistence.jpa.entities.StorageZonePersistenceEntity;

/**
 * Assembler that maps between the {@link StorageZone} domain aggregate and its JPA persistence entity.
 * <p>
 * Acts as the anti-corruption layer of the persistence adapter: JPA concerns never leak into the domain.
 * </p>
 */
public final class StorageZonePersistenceAssembler {

    private StorageZonePersistenceAssembler() {
    }

    public static StorageZone toDomainFromPersistence(StorageZonePersistenceEntity entity) {
        if (entity == null) return null;
        return StorageZone.reconstitute(
                entity.getId(),
                entity.getName(),
                entity.getStorageCondition(),
                toDomainFromPersistence(entity.getTemperatureRange()),
                toDomainFromPersistence(entity.getHumidityRange()));
    }

    public static StorageZonePersistenceEntity toPersistenceFromDomain(StorageZone storageZone) {
        if (storageZone == null) return null;
        var entity = new StorageZonePersistenceEntity();
        if (storageZone.getId() != null) {
            entity.setId(storageZone.getId());
        }
        entity.setName(storageZone.getName());
        entity.setStorageCondition(storageZone.getStorageCondition());
        entity.setTemperatureRange(toPersistenceFromDomain(storageZone.getTemperatureRange()));
        entity.setHumidityRange(toPersistenceFromDomain(storageZone.getHumidityRange()));
        return entity;
    }

    private static TemperatureRange toDomainFromPersistence(TemperatureRangePersistenceEmbeddable value) {
        return value == null ? null : new TemperatureRange(new Temperature(value.getMinTemperature()), new Temperature(value.getMaxTemperature()));
    }

    private static HumidityRange toDomainFromPersistence(HumidityRangePersistenceEmbeddable value) {
        return value == null ? null : new HumidityRange(new Humidity(value.getMinHumidity()), new Humidity(value.getMaxHumidity()));
    }

    private static TemperatureRangePersistenceEmbeddable toPersistenceFromDomain(TemperatureRange value) {
        return value == null ? null : new TemperatureRangePersistenceEmbeddable(value.min().value(), value.max().value());
    }

    private static HumidityRangePersistenceEmbeddable toPersistenceFromDomain(HumidityRange value) {
        return value == null ? null : new HumidityRangePersistenceEmbeddable(value.min().value(), value.max().value());
    }
}
