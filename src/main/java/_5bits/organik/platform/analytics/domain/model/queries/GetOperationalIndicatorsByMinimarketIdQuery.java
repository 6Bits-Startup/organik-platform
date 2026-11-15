package _5bits.organik.platform.analytics.domain.model.queries;

import _5bits.organik.platform.analytics.domain.model.valueobjects.MinimarketId;

/**
 * Query object for GetOperationalIndicatorsByMinimarketId.
 */
public record GetOperationalIndicatorsByMinimarketIdQuery(MinimarketId minimarketId) {
    public GetOperationalIndicatorsByMinimarketIdQuery {
        if (minimarketId == null) throw new IllegalArgumentException("Minimarket id is required.");
    }
}
