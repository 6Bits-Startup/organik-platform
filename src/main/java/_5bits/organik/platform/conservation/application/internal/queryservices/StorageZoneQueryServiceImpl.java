package _5bits.organik.platform.conservation.application.internal.queryservices;

import _5bits.organik.platform.conservation.application.queryservices.StorageZoneQueryService;
import _5bits.organik.platform.conservation.domain.model.aggregates.StorageZone;
import _5bits.organik.platform.conservation.domain.model.queries.GetAllStorageZonesByMinimarketIdQuery;
import _5bits.organik.platform.conservation.domain.model.queries.GetAllStorageZonesQuery;
import _5bits.organik.platform.conservation.domain.model.queries.GetStorageZoneByIdQuery;
import _5bits.organik.platform.conservation.domain.repositories.StorageZoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service that handles StorageZone queries.
 */
@Service
public class StorageZoneQueryServiceImpl implements StorageZoneQueryService {
    private final StorageZoneRepository storageZoneRepository;

    public StorageZoneQueryServiceImpl(StorageZoneRepository storageZoneRepository) {
        this.storageZoneRepository = storageZoneRepository;
    }

    @Override
    public Optional<StorageZone> handle(GetStorageZoneByIdQuery query) {
        return storageZoneRepository.findById(query.id());
    }

    @Override
    public List<StorageZone> handle(GetAllStorageZonesQuery query) {
        return storageZoneRepository.findAll();
    }

    @Override
    public List<StorageZone> handle(GetAllStorageZonesByMinimarketIdQuery query) {
        return storageZoneRepository.findAllByMinimarketId(query.minimarketId());
    }
}
