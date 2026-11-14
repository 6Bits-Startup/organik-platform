package _5bits.organik.platform.communication.interfaces.rest.transform;

import _5bits.organik.platform.communication.domain.model.commands.CreateNotificationCommand;
import _5bits.organik.platform.communication.domain.model.valueobjects.NotificationTitle;
import _5bits.organik.platform.communication.domain.model.valueobjects.NotificationType;
import _5bits.organik.platform.communication.domain.model.valueobjects.Severity;
import _5bits.organik.platform.communication.interfaces.rest.resources.CreateNotificationResource;

/**
 * Assembler that converts a {@link CreateNotificationResource} into a {@link CreateNotificationCommand}.
 */
public class CreateNotificationCommandFromResourceAssembler {
    public static CreateNotificationCommand toCommandFromResource(CreateNotificationResource resource) {
        return new CreateNotificationCommand(
                new NotificationType(resource.type()),
                new Severity(resource.severity()),
                new NotificationTitle(resource.title()));
    }
}
