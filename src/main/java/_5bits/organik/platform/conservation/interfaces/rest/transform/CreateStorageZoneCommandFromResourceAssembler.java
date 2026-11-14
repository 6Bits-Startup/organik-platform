package _5bits.organik.platform.conservation.interfaces.rest.transform;

import _5bits.organik.platform.conservation.domain.model.commands.CreateStorageZoneCommand;
import _5bits.organik.platform.conservation.interfaces.rest.resources.CreateStorageZoneResource;
import _5bits.organik.platform.conservation.domain.model.valueobjects.*;

/**
 * Assembler that converts a {@link CreateStorageZoneResource} into a {@link CreateStorageZoneCommand}.
 */
public class CreateStorageZoneCommandFromResourceAssembler {
    public static CreateStorageZoneCommand toCommandFromResource(CreateStorageZoneResource resource) {
        return new CreateStorageZoneCommand(
                new ZoneName(resource.name()),
                new StorageCondition(resource.storageCondition()),
                new TemperatureRange(new Temperature(resource.minTemperature()), new Temperature(resource.maxTemperature())),
                new HumidityRange(new Humidity(resource.minHumidity()), new Humidity(resource.maxHumidity())));
    }
}
