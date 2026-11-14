package _5bits.organik.platform.conservation.application.queryservices;

import _5bits.organik.platform.conservation.domain.model.aggregates.StorageReading;
import _5bits.organik.platform.conservation.domain.model.queries.GetAllStorageReadingsByStorageZoneIdQuery;
import _5bits.organik.platform.conservation.domain.model.queries.GetStorageReadingByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for StorageReading query operations.
 */
public interface StorageReadingQueryService {

    /**
     * Handles the {@link GetStorageReadingByIdQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    Optional<StorageReading> handle(GetStorageReadingByIdQuery query);

    /**
     * Handles the {@link GetAllStorageReadingsByStorageZoneIdQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    List<StorageReading> handle(GetAllStorageReadingsByStorageZoneIdQuery query);
}
