package _5bits.organik.platform.conservation.application.internal.commandservices;

import _5bits.organik.platform.conservation.application.commandservices.StorageReadingCommandService;
import _5bits.organik.platform.conservation.domain.model.aggregates.StorageReading;
import _5bits.organik.platform.conservation.domain.model.commands.RecordStorageReadingCommand;
import _5bits.organik.platform.conservation.domain.repositories.StorageReadingRepository;
import _5bits.organik.platform.conservation.domain.repositories.StorageZoneRepository;
import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Application service that handles StorageReading commands.
 */
@Service
public class StorageReadingCommandServiceImpl implements StorageReadingCommandService {
    private final StorageReadingRepository storageReadingRepository;
    private final StorageZoneRepository storageZoneRepository;

    public StorageReadingCommandServiceImpl(StorageReadingRepository storageReadingRepository, StorageZoneRepository storageZoneRepository) {
        this.storageReadingRepository = storageReadingRepository;
        this.storageZoneRepository = storageZoneRepository;
    }

    @Override
    public Result<Long, ApplicationError> handle(RecordStorageReadingCommand command) {
        var storageZone = storageZoneRepository.findById(command.storageZoneId().value());
        if (storageZone.isEmpty())
            return Result.failure(ApplicationError.notFound("StorageZone", command.storageZoneId().value().toString()));
        var storageReading = new StorageReading(command, storageZone.get());
        try {
            storageReading = storageReadingRepository.save(storageReading);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("record-storage-reading", e.getMessage()));
        }
        return Result.success(storageReading.getId());
    }
}
