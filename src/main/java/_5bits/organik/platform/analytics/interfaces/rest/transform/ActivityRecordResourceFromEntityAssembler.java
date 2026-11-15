package _5bits.organik.platform.analytics.interfaces.rest.transform;

import _5bits.organik.platform.analytics.domain.model.aggregates.ActivityRecord;
import _5bits.organik.platform.analytics.interfaces.rest.resources.ActivityRecordResource;


/**
 * Assembler that converts a {@link ActivityRecord} aggregate into a {@link ActivityRecordResource}.
 */
public class ActivityRecordResourceFromEntityAssembler {
    public static ActivityRecordResource toResourceFromEntity(ActivityRecord entity) {
        return new ActivityRecordResource(
                entity.getId(),
                entity.getMinimarketId() == null ? null : entity.getMinimarketId().value(),
                entity.getType().name(),
                entity.getDescription().value(),
                entity.getCreatedAt());
    }
}
