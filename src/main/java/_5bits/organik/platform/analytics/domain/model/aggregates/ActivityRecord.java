package _5bits.organik.platform.analytics.domain.model.aggregates;

import _5bits.organik.platform.analytics.domain.model.commands.RegisterActivityRecordCommand;
import _5bits.organik.platform.analytics.domain.model.valueobjects.ActivityDescription;
import _5bits.organik.platform.analytics.domain.model.valueobjects.ActivityType;
import _5bits.organik.platform.analytics.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.util.Date;
import java.util.Objects;

/**
 * Activity record aggregate root.
 * <p>
 * An entry of the activity history: accepted orders, low stock and conservation alerts.
 * </p>
 */
@Getter
public class ActivityRecord extends AbstractDomainAggregateRoot<ActivityRecord> {
    private Long id;
    private MinimarketId minimarketId;
    private ActivityType type;
    private ActivityDescription description;
    private Date createdAt;
    private Date updatedAt;

    public ActivityRecord(MinimarketId minimarketId, ActivityType type, ActivityDescription description) {
        this.minimarketId = minimarketId;
        this.type = Objects.requireNonNull(type, "type must not be null");
        this.description = Objects.requireNonNull(description, "description must not be null");
    }

    public ActivityRecord(RegisterActivityRecordCommand command) {
        this(command.minimarketId(), command.type(), command.description());
    }

    public static ActivityRecord reconstitute(Long id, MinimarketId minimarketId, ActivityType type,
                                              ActivityDescription description, Date createdAt, Date updatedAt) {
        var activityRecord = new ActivityRecord(minimarketId, type, description);
        activityRecord.id = id;
        activityRecord.createdAt = createdAt;
        activityRecord.updatedAt = updatedAt;
        return activityRecord;
    }
}
