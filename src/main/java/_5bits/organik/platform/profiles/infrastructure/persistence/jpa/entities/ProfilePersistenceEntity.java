package _5bits.organik.platform.profiles.infrastructure.persistence.jpa.entities;

import _5bits.organik.platform.profiles.domain.model.valueobjects.EmailAddress;
import _5bits.organik.platform.profiles.domain.model.valueobjects.FullName;
import _5bits.organik.platform.profiles.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.profiles.domain.model.valueobjects.SupplierId;
import _5bits.organik.platform.profiles.domain.model.valueobjects.UserId;
import _5bits.organik.platform.profiles.infrastructure.persistence.jpa.converters.EmailAddressPersistenceConverter;
import _5bits.organik.platform.profiles.infrastructure.persistence.jpa.converters.FullNamePersistenceConverter;
import _5bits.organik.platform.profiles.infrastructure.persistence.jpa.converters.MinimarketIdPersistenceConverter;
import _5bits.organik.platform.profiles.infrastructure.persistence.jpa.converters.SupplierIdPersistenceConverter;
import _5bits.organik.platform.profiles.infrastructure.persistence.jpa.converters.UserIdPersistenceConverter;
import _5bits.organik.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import _5bits.organik.platform.profiles.infrastructure.persistence.jpa.embeddables.PreferencesPersistenceEmbeddable;

/**
 * JPA persistence entity for the Profile aggregate.
 * <p>
 * Pure persistence model: it carries no business behavior. Mapping to and from the
 * {@code Profile} domain aggregate is done by {@code ProfilePersistenceAssembler}.
 * </p>
 */
@Entity
@Table(name = "profiles")
@Getter
@Setter
@NoArgsConstructor
public class ProfilePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = UserIdPersistenceConverter.class)
    @Column(name = "user_id", nullable = false, unique = true)
    private UserId userId;

    @Convert(converter = FullNamePersistenceConverter.class)
    @Column(name = "full_name", nullable = false)
    private FullName fullName;

    @Convert(converter = EmailAddressPersistenceConverter.class)
    @Column(name = "email", nullable = false, unique = true)
    private EmailAddress email;

    @Convert(converter = MinimarketIdPersistenceConverter.class)
    @Column(name = "minimarket_id")
    private MinimarketId minimarketId;

    @Convert(converter = SupplierIdPersistenceConverter.class)
    @Column(name = "supplier_id")
    private SupplierId supplierId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "language", column = @Column(name = "language", nullable = false, length = 10)),
            @AttributeOverride(name = "warningExpirationDays", column = @Column(name = "warning_expiration_days", nullable = false)),
            @AttributeOverride(name = "criticalExpirationDays", column = @Column(name = "critical_expiration_days", nullable = false))})
    private PreferencesPersistenceEmbeddable preferences;
}
