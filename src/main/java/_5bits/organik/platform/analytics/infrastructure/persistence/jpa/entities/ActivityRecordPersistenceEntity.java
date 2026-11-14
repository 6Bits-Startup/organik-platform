package _5bits.organik.platform.analytics.infrastructure.persistence.jpa.entities;

import _5bits.organik.platform.analytics.domain.model.valueobjects.ActivityDescription;
import _5bits.organik.platform.analytics.domain.model.valueobjects.ActivityType;
import _5bits.organik.platform.analytics.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.analytics.infrastructure.persistence.jpa.converters.ActivityDescriptionPersistenceConverter;
import _5bits.organik.platform.analytics.infrastructure.persistence.jpa.converters.MinimarketIdPersistenceConverter;
import _5bits.organik.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity for the ActivityRecord aggregate.
 * <p>
 * Pure persistence model: it carries no business behavior. Mapping to and from the
 * {@code ActivityRecord} domain aggregate is done by {@code ActivityRecordPersistenceAssembler}.
 * </p>
 */
@Entity
@Table(name = "activity_records")
@Getter
@Setter
@NoArgsConstructor
public class ActivityRecordPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = MinimarketIdPersistenceConverter.class)
    @Column(name = "minimarket_id")
    private MinimarketId minimarketId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 40)
    private ActivityType type;

    @Convert(converter = ActivityDescriptionPersistenceConverter.class)
    @Column(name = "description", nullable = false, length = 512)
    private ActivityDescription description;
}
