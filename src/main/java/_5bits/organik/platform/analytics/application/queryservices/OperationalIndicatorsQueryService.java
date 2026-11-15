package _5bits.organik.platform.analytics.application.queryservices;

import _5bits.organik.platform.analytics.domain.model.queries.GetOperationalIndicatorsByMinimarketIdQuery;
import _5bits.organik.platform.analytics.domain.model.valueobjects.OperationalIndicators;

/**
 * Application service contract for operational indicator queries.
 */
public interface OperationalIndicatorsQueryService {

    /**
     * Computes the operational indicators of a minimarket.
     *
     * @param query query with the minimarket identifier
     * @return the operational indicators
     */
    OperationalIndicators handle(GetOperationalIndicatorsByMinimarketIdQuery query);
}
