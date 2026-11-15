package _5bits.organik.platform.conservation.application.internal.commandservices;

import _5bits.organik.platform.conservation.application.commandservices.StorageZoneCommandService;
import _5bits.organik.platform.conservation.domain.model.aggregates.StorageZone;
import _5bits.organik.platform.conservation.domain.model.commands.CreateStorageZoneCommand;
import _5bits.organik.platform.conservation.domain.repositories.StorageZoneRepository;
import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Application service that handles StorageZone commands.
 */
@Service
public class StorageZoneCommandServiceImpl implements StorageZoneCommandService {
    private final StorageZoneRepository storageZoneRepository;

    public StorageZoneCommandServiceImpl(StorageZoneRepository storageZoneRepository) {
        this.storageZoneRepository = storageZoneRepository;
    }

    @Override
    public Result<Long, ApplicationError> handle(CreateStorageZoneCommand command) {
        if (storageZoneRepository.existsByMinimarketIdAndName(command.minimarketId(), command.name()))
            return Result.failure(ApplicationError.conflict("StorageZone",
                    "Name '%s' already exists in minimarket '%s'".formatted(command.name().value(), command.minimarketId().value())));
        var storageZone = new StorageZone(command);
        try {
            storageZone = storageZoneRepository.save(storageZone);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-storage-zone", e.getMessage()));
        }
        return Result.success(storageZone.getId());
    }
}
