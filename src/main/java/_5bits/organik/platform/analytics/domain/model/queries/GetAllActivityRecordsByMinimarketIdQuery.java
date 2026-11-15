package _5bits.organik.platform.analytics.domain.model.queries;

import _5bits.organik.platform.analytics.domain.model.valueobjects.MinimarketId;

/**
 * Query object for GetAllActivityRecordsByMinimarketId.
 */
public record GetAllActivityRecordsByMinimarketIdQuery(MinimarketId minimarketId) {
    public GetAllActivityRecordsByMinimarketIdQuery {
        if (minimarketId == null) throw new IllegalArgumentException("Minimarket id is required.");
    }
}
