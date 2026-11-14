package _5bits.organik.platform.conservation.application.internal.queryservices;

import _5bits.organik.platform.conservation.application.queryservices.StorageReadingQueryService;
import _5bits.organik.platform.conservation.domain.model.aggregates.StorageReading;
import _5bits.organik.platform.conservation.domain.model.queries.GetAllStorageReadingsByStorageZoneIdQuery;
import _5bits.organik.platform.conservation.domain.model.queries.GetStorageReadingByIdQuery;
import _5bits.organik.platform.conservation.domain.repositories.StorageReadingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service that handles StorageReading queries.
 */
@Service
public class StorageReadingQueryServiceImpl implements StorageReadingQueryService {
    private final StorageReadingRepository storageReadingRepository;

    public StorageReadingQueryServiceImpl(StorageReadingRepository storageReadingRepository) {
        this.storageReadingRepository = storageReadingRepository;
    }

    @Override
    public Optional<StorageReading> handle(GetStorageReadingByIdQuery query) {
        return storageReadingRepository.findById(query.id());
    }

    @Override
    public List<StorageReading> handle(GetAllStorageReadingsByStorageZoneIdQuery query) {
        return storageReadingRepository.findAllByStorageZoneId(query.storageZoneId());
    }
}
