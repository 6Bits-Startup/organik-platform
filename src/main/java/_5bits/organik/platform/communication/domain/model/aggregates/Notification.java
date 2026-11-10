package _5bits.organik.platform.communication.domain.model.aggregates;

import _5bits.organik.platform.communication.domain.model.commands.CreateNotificationCommand;
import _5bits.organik.platform.communication.domain.model.valueobjects.NotificationTitle;
import _5bits.organik.platform.communication.domain.model.valueobjects.NotificationType;
import _5bits.organik.platform.communication.domain.model.valueobjects.Severity;
import _5bits.organik.platform.communication.infrastructure.persistence.jpa.converters.NotificationTitleConverter;
import _5bits.organik.platform.communication.infrastructure.persistence.jpa.converters.NotificationTypeConverter;
import _5bits.organik.platform.communication.infrastructure.persistence.jpa.converters.SeverityConverter;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    @Convert(converter = NotificationTypeConverter.class)
    private NotificationType type;

    @Column(nullable = false, length = 20)
    @Convert(converter = SeverityConverter.class)
    private Severity severity;

    @Column(nullable = false, length = 150)
    @Convert(converter = NotificationTitleConverter.class)
    private NotificationTitle title;

    @Column(nullable = false)
    private boolean isRead;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Instant updatedAt;

    protected Notification() {
    }

    public Notification(CreateNotificationCommand command) {
        this.type = command.type();
        this.severity = command.severity();
        this.title = command.title();
        this.isRead = false;
    }

    public Notification(NotificationType type, Severity severity, NotificationTitle title) {
        this.type = type;
        this.severity = severity;
        this.title = title;
        this.isRead = false;
    }

    public void markAsRead() {
        this.isRead = true;
    }
}