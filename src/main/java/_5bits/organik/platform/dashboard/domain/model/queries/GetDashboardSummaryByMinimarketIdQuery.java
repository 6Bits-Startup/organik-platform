package _5bits.organik.platform.dashboard.domain.model.queries;

import _5bits.organik.platform.dashboard.domain.model.valueobjects.MinimarketId;

/**
 * Query to build the dashboard summary of a minimarket.
 *
 * @param minimarketId minimarket identifier
 */
public record GetDashboardSummaryByMinimarketIdQuery(MinimarketId minimarketId) {
    public GetDashboardSummaryByMinimarketIdQuery {
        if (minimarketId == null) throw new IllegalArgumentException("Minimarket id is required.");
    }
}
