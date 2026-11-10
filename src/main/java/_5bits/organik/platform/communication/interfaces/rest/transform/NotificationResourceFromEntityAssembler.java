package _5bits.organik.platform.communication.interfaces.rest.transform;

import _5bits.organik.platform.communication.domain.model.aggregates.Notification;
import _5bits.organik.platform.communication.interfaces.rest.resources.NotificationResource;

public class NotificationResourceFromEntityAssembler {
    public static NotificationResource toResourceFromEntity(Notification entity) {
        return new NotificationResource(
                entity.getId(),
                entity.getType().value(),
                entity.getSeverity().value(),
                entity.getTitle().value(),
                entity.isRead(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }
}