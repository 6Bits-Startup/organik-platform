package _5bits.organik.platform.conservation.domain.model.queries;

/**
 * Query object for GetStorageZoneById.
 */
public record GetStorageZoneByIdQuery(Long id) {
    public GetStorageZoneByIdQuery {
        if (id == null || id <= 0) throw new IllegalArgumentException("Storage zone id is required.");
    }
}
