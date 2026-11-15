package _5bits.organik.platform.conservation.interfaces.rest.transform;

import _5bits.organik.platform.conservation.domain.model.aggregates.StorageZone;
import _5bits.organik.platform.conservation.interfaces.rest.resources.StorageZoneResource;


/**
 * Assembler that converts a {@link StorageZone} aggregate into a {@link StorageZoneResource}.
 */
public class StorageZoneResourceFromEntityAssembler {
    public static StorageZoneResource toResourceFromEntity(StorageZone entity) {
        return new StorageZoneResource(
                entity.getId(),
                entity.getMinimarketId().value(),
                entity.getName().value(),
                entity.getStorageCondition().value(),
                entity.getTemperatureRange().min().value(),
                entity.getTemperatureRange().max().value(),
                entity.getHumidityRange().min().value(),
                entity.getHumidityRange().max().value());
    }
}
