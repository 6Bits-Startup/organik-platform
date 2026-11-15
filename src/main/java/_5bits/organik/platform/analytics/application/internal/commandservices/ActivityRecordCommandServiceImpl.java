package _5bits.organik.platform.analytics.application.internal.commandservices;

import _5bits.organik.platform.analytics.application.commandservices.ActivityRecordCommandService;
import _5bits.organik.platform.analytics.domain.model.aggregates.ActivityRecord;
import _5bits.organik.platform.analytics.domain.model.commands.RegisterActivityRecordCommand;
import _5bits.organik.platform.analytics.domain.repositories.ActivityRecordRepository;
import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Application service that handles ActivityRecord commands.
 */
@Service
public class ActivityRecordCommandServiceImpl implements ActivityRecordCommandService {
    private final ActivityRecordRepository activityRecordRepository;

    public ActivityRecordCommandServiceImpl(ActivityRecordRepository activityRecordRepository) {
        this.activityRecordRepository = activityRecordRepository;
    }

    @Override
    public Result<Long, ApplicationError> handle(RegisterActivityRecordCommand command) {
        var activityRecord = new ActivityRecord(command);
        try {
            activityRecord = activityRecordRepository.save(activityRecord);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("register-activity-record", e.getMessage()));
        }
        return Result.success(activityRecord.getId());
    }
}
