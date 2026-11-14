package _5bits.organik.platform.dashboard.domain.model.queries;

import _5bits.organik.platform.dashboard.domain.model.valueobjects.MinimarketId;

/**
 * Query to build the dashboard summary of a minimarket.
 *
 * @param minimarketId minimarket identifier
 * @param userId IAM user that views the dashboard, may be {@code null} to use the default preferences
 */
public record GetDashboardSummaryByMinimarketIdQuery(MinimarketId minimarketId, Long userId) {
    public GetDashboardSummaryByMinimarketIdQuery {
        if (minimarketId == null) throw new IllegalArgumentException("Minimarket id is required.");
        if (userId != null && userId < 1) throw new IllegalArgumentException("User id cannot be less than 1");
    }
}
