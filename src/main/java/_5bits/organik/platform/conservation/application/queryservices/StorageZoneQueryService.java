package _5bits.organik.platform.conservation.application.queryservices;

import _5bits.organik.platform.conservation.domain.model.aggregates.StorageZone;
import _5bits.organik.platform.conservation.domain.model.queries.GetAllStorageZonesQuery;
import _5bits.organik.platform.conservation.domain.model.queries.GetStorageZoneByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for StorageZone query operations.
 */
public interface StorageZoneQueryService {

    /**
     * Handles the {@link GetStorageZoneByIdQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    Optional<StorageZone> handle(GetStorageZoneByIdQuery query);

    /**
     * Handles the {@link GetAllStorageZonesQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    List<StorageZone> handle(GetAllStorageZonesQuery query);
}
