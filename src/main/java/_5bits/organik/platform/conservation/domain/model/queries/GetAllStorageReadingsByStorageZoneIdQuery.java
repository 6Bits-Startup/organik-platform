package _5bits.organik.platform.conservation.domain.model.queries;

import _5bits.organik.platform.conservation.domain.model.valueobjects.StorageZoneId;

/**
 * Query to retrieve the readings of a storage zone.
 *
 * @param storageZoneId storage zone identifier
 */
public record GetAllStorageReadingsByStorageZoneIdQuery(StorageZoneId storageZoneId) {
    public GetAllStorageReadingsByStorageZoneIdQuery {
        if (storageZoneId == null) throw new IllegalArgumentException("Storage zone id is required.");
    }
}
