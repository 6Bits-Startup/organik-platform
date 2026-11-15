package _5bits.organik.platform.analytics.application.internal.queryservices;

import _5bits.organik.platform.analytics.application.queryservices.ActivityRecordQueryService;
import _5bits.organik.platform.analytics.domain.model.aggregates.ActivityRecord;
import _5bits.organik.platform.analytics.domain.model.queries.GetActivityRecordByIdQuery;
import _5bits.organik.platform.analytics.domain.model.queries.GetAllActivityRecordsByMinimarketIdQuery;
import _5bits.organik.platform.analytics.domain.model.queries.GetAllActivityRecordsQuery;
import _5bits.organik.platform.analytics.domain.repositories.ActivityRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service that handles ActivityRecord queries.
 */
@Service
public class ActivityRecordQueryServiceImpl implements ActivityRecordQueryService {
    private final ActivityRecordRepository activityRecordRepository;

    public ActivityRecordQueryServiceImpl(ActivityRecordRepository activityRecordRepository) {
        this.activityRecordRepository = activityRecordRepository;
    }

    @Override
    public Optional<ActivityRecord> handle(GetActivityRecordByIdQuery query) {
        return activityRecordRepository.findById(query.id());
    }

    @Override
    public List<ActivityRecord> handle(GetAllActivityRecordsQuery query) {
        return activityRecordRepository.findAll();
    }

    @Override
    public List<ActivityRecord> handle(GetAllActivityRecordsByMinimarketIdQuery query) {
        return activityRecordRepository.findAllByMinimarketId(query.minimarketId());
    }
}
