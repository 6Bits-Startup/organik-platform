package _5bits.organik.platform.communication.domain.model.commands;

import _5bits.organik.platform.communication.domain.model.valueobjects.NotificationTitle;
import _5bits.organik.platform.communication.domain.model.valueobjects.NotificationType;
import _5bits.organik.platform.communication.domain.model.valueobjects.Severity;

public record CreateNotificationCommand(
        NotificationType type,
        Severity severity,
        NotificationTitle title
) {
    public CreateNotificationCommand {
        if (type == null) {
            throw new IllegalArgumentException("NotificationType cannot be null");
        }
        if (severity == null) {
            throw new IllegalArgumentException("Severity cannot be null");
        }
        if (title == null) {
            throw new IllegalArgumentException("NotificationTitle cannot be null");
        }
    }
}