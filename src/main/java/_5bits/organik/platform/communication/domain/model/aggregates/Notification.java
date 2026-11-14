package _5bits.organik.platform.communication.domain.model.aggregates;

import _5bits.organik.platform.communication.domain.model.commands.CreateNotificationCommand;
import _5bits.organik.platform.communication.domain.model.valueobjects.NotificationTitle;
import _5bits.organik.platform.communication.domain.model.valueobjects.NotificationType;
import _5bits.organik.platform.communication.domain.model.valueobjects.Severity;
import _5bits.organik.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.util.Date;
import java.util.Objects;

/**
 * Notification aggregate root.
 * <p>
 * Represents an alert shown to the minimarket staff: expirations, conservation problems,
 * low stock, replenishment and order updates.
 * </p>
 */
@Getter
public class Notification extends AbstractDomainAggregateRoot<Notification> {
    private Long id;
    private NotificationType type;
    private Severity severity;
    private NotificationTitle title;
    private Boolean isRead;
    private Date createdAt;
    private Date updatedAt;

    /**
     * Creates a new unread notification.
     *
     * @param type notification type
     * @param severity notification severity
     * @param title notification title
     */
    public Notification(NotificationType type, Severity severity, NotificationTitle title) {
        this.type = Objects.requireNonNull(type, "type must not be null");
        this.severity = Objects.requireNonNull(severity, "severity must not be null");
        this.title = Objects.requireNonNull(title, "title must not be null");
        this.isRead = false;
    }

    /**
     * Creates a new unread notification from a create command.
     *
     * @param command command with the notification data
     */
    public Notification(CreateNotificationCommand command) {
        this(command.type(), command.severity(), command.title());
    }

    /**
     * Rebuilds a notification loaded from storage.
     *
     * @param id notification identifier
     * @param type notification type
     * @param severity notification severity
     * @param title notification title
     * @param isRead whether the notification was read
     * @param createdAt creation date
     * @param updatedAt last update date
     * @return the reconstituted notification
     */
    public static Notification reconstitute(Long id, NotificationType type, Severity severity, NotificationTitle title,
                                            Boolean isRead, Date createdAt, Date updatedAt) {
        var notification = new Notification(type, severity, title);
        notification.id = id;
        notification.isRead = Boolean.TRUE.equals(isRead);
        notification.createdAt = createdAt;
        notification.updatedAt = updatedAt;
        return notification;
    }

    /**
     * Marks the notification as read.
     */
    public void markAsRead() {
        this.isRead = true;
    }

    /**
     * @return {@code true} when the notification was already read
     */
    public boolean isAlreadyRead() {
        return Boolean.TRUE.equals(this.isRead);
    }
}
