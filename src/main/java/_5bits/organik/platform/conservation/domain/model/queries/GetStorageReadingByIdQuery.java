package _5bits.organik.platform.conservation.domain.model.queries;

/**
 * Query object for GetStorageReadingById.
 */
public record GetStorageReadingByIdQuery(Long id) {
    public GetStorageReadingByIdQuery {
        if (id == null || id <= 0) throw new IllegalArgumentException("Storage reading id is required.");
    }
}
