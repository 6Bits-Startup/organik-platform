package _5bits.organik.platform.profiles.domain.model.queries;

/**
 * Query object for GetProfileById.
 */
public record GetProfileByIdQuery(Long id) {
    public GetProfileByIdQuery {
        if (id == null || id <= 0) throw new IllegalArgumentException("Profile id is required.");
    }
}
