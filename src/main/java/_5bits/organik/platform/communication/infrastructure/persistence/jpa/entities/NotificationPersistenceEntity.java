package _5bits.organik.platform.communication.infrastructure.persistence.jpa.entities;

import _5bits.organik.platform.communication.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.communication.domain.model.valueobjects.NotificationTitle;
import _5bits.organik.platform.communication.domain.model.valueobjects.NotificationType;
import _5bits.organik.platform.communication.domain.model.valueobjects.Severity;
import _5bits.organik.platform.communication.infrastructure.persistence.jpa.converters.MinimarketIdPersistenceConverter;
import _5bits.organik.platform.communication.infrastructure.persistence.jpa.converters.NotificationTitlePersistenceConverter;
import _5bits.organik.platform.communication.infrastructure.persistence.jpa.converters.NotificationTypePersistenceConverter;
import _5bits.organik.platform.communication.infrastructure.persistence.jpa.converters.SeverityPersistenceConverter;
import _5bits.organik.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity for the Notification aggregate.
 * <p>
 * Pure persistence model: it carries no business behavior. Mapping to and from the
 * {@code Notification} domain aggregate is done by {@code NotificationPersistenceAssembler}.
 * </p>
 */
@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
public class NotificationPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = MinimarketIdPersistenceConverter.class)
    @Column(name = "minimarket_id", nullable = false)
    private MinimarketId minimarketId;

    @Convert(converter = NotificationTypePersistenceConverter.class)
    @Column(name = "type", nullable = false, length = 30)
    private NotificationType type;

    @Convert(converter = SeverityPersistenceConverter.class)
    @Column(name = "severity", nullable = false, length = 20)
    private Severity severity;

    @Convert(converter = NotificationTitlePersistenceConverter.class)
    @Column(name = "title", nullable = false, length = 150)
    private NotificationTitle title;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead;
}
