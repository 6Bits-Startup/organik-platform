package _5bits.organik.platform.analytics.application.commandservices;

import _5bits.organik.platform.analytics.domain.model.commands.RegisterActivityRecordCommand;
import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;

/**
 * Application service contract for ActivityRecord command operations.
 */
public interface ActivityRecordCommandService {

    /**
     * Handles the registration of an activity in the history.
     *
     * @param command command object
     * @return the identifier of the affected ActivityRecord, or an application error
     */
    Result<Long, ApplicationError> handle(RegisterActivityRecordCommand command);
}
