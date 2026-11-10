package _5bits.organik.platform.communication.domain.model.commands;

public record MarkNotificationAsReadCommand(Long notificationId) {
    public MarkNotificationAsReadCommand {
        if (notificationId == null || notificationId <= 0) {
            throw new IllegalArgumentException("notification.error.id.invalid");
        }
    }
}