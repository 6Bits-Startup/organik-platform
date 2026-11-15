package _5bits.organik.platform.conservation.interfaces.rest.transform;

import _5bits.organik.platform.conservation.domain.model.commands.RecordStorageReadingCommand;
import _5bits.organik.platform.conservation.interfaces.rest.resources.RecordStorageReadingResource;
import _5bits.organik.platform.conservation.domain.model.valueobjects.*;

/**
 * Assembler that converts a {@link RecordStorageReadingResource} into a {@link RecordStorageReadingCommand}.
 */
public class RecordStorageReadingCommandFromResourceAssembler {
    public static RecordStorageReadingCommand toCommandFromResource(RecordStorageReadingResource resource) {
        return new RecordStorageReadingCommand(
                new StorageZoneId(resource.storageZoneId()),
                new Temperature(resource.temperature()),
                new Humidity(resource.humidity()),
                resource.recordedAt(),
                new ReadingSource(resource.source()));
    }
}
