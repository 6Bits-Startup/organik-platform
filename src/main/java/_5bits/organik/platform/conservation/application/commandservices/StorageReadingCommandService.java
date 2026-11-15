package _5bits.organik.platform.conservation.application.commandservices;

import _5bits.organik.platform.conservation.domain.model.commands.RecordStorageReadingCommand;
import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;

/**
 * Application service contract for StorageReading command operations.
 */
public interface StorageReadingCommandService {

    /**
     * Handles the registration of a storage reading.
     *
     * @param command command object
     * @return the identifier of the affected StorageReading, or an application error
     */
    Result<Long, ApplicationError> handle(RecordStorageReadingCommand command);
}
