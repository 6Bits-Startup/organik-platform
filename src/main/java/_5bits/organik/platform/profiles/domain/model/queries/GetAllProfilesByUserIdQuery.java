package _5bits.organik.platform.profiles.domain.model.queries;

import _5bits.organik.platform.profiles.domain.model.valueobjects.UserId;

/**
 * Query object for GetAllProfilesByUserId.
 */
public record GetAllProfilesByUserIdQuery(UserId userId) {
    public GetAllProfilesByUserIdQuery {
        if (userId == null) throw new IllegalArgumentException("User id is required.");
    }
}
