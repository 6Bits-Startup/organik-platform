package _5bits.organik.platform.conservation.interfaces.rest.transform;

import _5bits.organik.platform.conservation.domain.model.aggregates.StorageReading;
import _5bits.organik.platform.conservation.interfaces.rest.resources.StorageReadingResource;


/**
 * Assembler that converts a {@link StorageReading} aggregate into a {@link StorageReadingResource}.
 */
public class StorageReadingResourceFromEntityAssembler {
    public static StorageReadingResource toResourceFromEntity(StorageReading entity) {
        return new StorageReadingResource(
                entity.getId(),
                entity.getStorageZoneId().value(),
                entity.getTemperature().value(),
                entity.getHumidity().value(),
                entity.getRecordedAt(),
                entity.getSource().value(),
                entity.getWithinRange());
    }
}
