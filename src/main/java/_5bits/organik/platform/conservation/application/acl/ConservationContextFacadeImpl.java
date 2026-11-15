package _5bits.organik.platform.conservation.application.acl;

import _5bits.organik.platform.conservation.application.queryservices.StorageReadingQueryService;
import _5bits.organik.platform.conservation.domain.model.queries.GetAllStorageReadingsQuery;
import _5bits.organik.platform.conservation.interfaces.acl.ConservationContextFacade;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link ConservationContextFacade} ACL facade based on the query services of the bounded context.
 */
@Service
public class ConservationContextFacadeImpl implements ConservationContextFacade {
    private final StorageReadingQueryService storageReadingQueryService;

    public ConservationContextFacadeImpl(StorageReadingQueryService storageReadingQueryService) {
        this.storageReadingQueryService = storageReadingQueryService;
    }

    @Override
    public long countOutOfRangeReadings() {
        return storageReadingQueryService.handle(new GetAllStorageReadingsQuery()).stream()
                .filter(storageReading -> !Boolean.TRUE.equals(storageReading.getWithinRange()))
                .count();
    }
}
