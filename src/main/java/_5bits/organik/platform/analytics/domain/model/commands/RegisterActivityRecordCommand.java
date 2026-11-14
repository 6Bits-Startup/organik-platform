package _5bits.organik.platform.analytics.domain.model.commands;

import _5bits.organik.platform.analytics.domain.model.valueobjects.ActivityDescription;
import _5bits.organik.platform.analytics.domain.model.valueobjects.ActivityType;
import _5bits.organik.platform.analytics.domain.model.valueobjects.MinimarketId;

/**
 * Command to register an activity in the history.
 *
 * @param minimarketId minimarket related to the activity, may be {@code null}
 * @param type activity type
 * @param description activity description
 */
public record RegisterActivityRecordCommand(MinimarketId minimarketId, ActivityType type, ActivityDescription description) {
    public RegisterActivityRecordCommand {
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null");
        }
        if (description == null) {
            throw new IllegalArgumentException("description cannot be null");
        }
    }
}
