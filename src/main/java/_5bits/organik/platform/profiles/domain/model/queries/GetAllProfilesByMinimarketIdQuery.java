package _5bits.organik.platform.profiles.domain.model.queries;

import _5bits.organik.platform.profiles.domain.model.valueobjects.MinimarketId;

/**
 * Query object for GetAllProfilesByMinimarketId.
 */
public record GetAllProfilesByMinimarketIdQuery(MinimarketId minimarketId) {
    public GetAllProfilesByMinimarketIdQuery {
        if (minimarketId == null) throw new IllegalArgumentException("Minimarket id is required.");
    }
}
