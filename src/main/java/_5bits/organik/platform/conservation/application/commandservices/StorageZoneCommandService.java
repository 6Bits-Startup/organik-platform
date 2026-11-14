package _5bits.organik.platform.conservation.application.commandservices;

import _5bits.organik.platform.conservation.domain.model.commands.CreateStorageZoneCommand;
import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;

/**
 * Application service contract for StorageZone command operations.
 */
public interface StorageZoneCommandService {

    /**
     * Handles the creation of a storage zone.
     *
     * @param command command object
     * @return the identifier of the affected StorageZone, or an application error
     */
    Result<Long, ApplicationError> handle(CreateStorageZoneCommand command);
}
