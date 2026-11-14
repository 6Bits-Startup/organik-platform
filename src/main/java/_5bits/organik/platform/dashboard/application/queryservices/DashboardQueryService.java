package _5bits.organik.platform.dashboard.application.queryservices;

import _5bits.organik.platform.dashboard.domain.model.queries.GetDashboardSummaryByMinimarketIdQuery;
import _5bits.organik.platform.dashboard.domain.model.valueobjects.DashboardSummary;

/**
 * Application service contract for dashboard queries.
 */
public interface DashboardQueryService {

    /**
     * Builds the dashboard summary of a minimarket.
     *
     * @param query query with the minimarket identifier
     * @return the dashboard summary
     */
    DashboardSummary handle(GetDashboardSummaryByMinimarketIdQuery query);
}
