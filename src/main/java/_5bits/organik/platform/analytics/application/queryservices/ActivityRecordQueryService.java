package _5bits.organik.platform.analytics.application.queryservices;

import _5bits.organik.platform.analytics.domain.model.aggregates.ActivityRecord;
import _5bits.organik.platform.analytics.domain.model.queries.GetActivityRecordByIdQuery;
import _5bits.organik.platform.analytics.domain.model.queries.GetAllActivityRecordsByMinimarketIdQuery;
import _5bits.organik.platform.analytics.domain.model.queries.GetAllActivityRecordsQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for ActivityRecord query operations.
 */
public interface ActivityRecordQueryService {

    /**
     * Handles the {@link GetActivityRecordByIdQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    Optional<ActivityRecord> handle(GetActivityRecordByIdQuery query);

    /**
     * Handles the {@link GetAllActivityRecordsQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    List<ActivityRecord> handle(GetAllActivityRecordsQuery query);

    /**
     * Handles the {@link GetAllActivityRecordsByMinimarketIdQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    List<ActivityRecord> handle(GetAllActivityRecordsByMinimarketIdQuery query);
}
