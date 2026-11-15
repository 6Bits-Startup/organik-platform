package _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.entities;

import _5bits.organik.platform.suppliers.domain.model.valueobjects.BusinessName;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.ContactName;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.EmailAddress;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.OrganicCertification;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.PhoneNumber;
import _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.converters.BusinessNamePersistenceConverter;
import _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.converters.ContactNamePersistenceConverter;
import _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.converters.EmailAddressPersistenceConverter;
import _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.converters.OrganicCertificationPersistenceConverter;
import _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.converters.PhoneNumberPersistenceConverter;
import _5bits.organik.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import _5bits.organik.platform.suppliers.domain.model.valueobjects.SupplierCategory;
import java.util.HashSet;
import java.util.Set;

/**
 * JPA persistence entity for the Supplier aggregate.
 * <p>
 * Pure persistence model: it carries no business behavior. Mapping to and from the
 * {@code Supplier} domain aggregate is done by {@code SupplierPersistenceAssembler}.
 * </p>
 */
@Entity
@Table(name = "suppliers")
@Getter
@Setter
@NoArgsConstructor
public class SupplierPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = BusinessNamePersistenceConverter.class)
    @Column(name = "business_name", nullable = false)
    private BusinessName businessName;

    @Convert(converter = ContactNamePersistenceConverter.class)
    @Column(name = "contact_name", nullable = false)
    private ContactName contactName;

    @Convert(converter = PhoneNumberPersistenceConverter.class)
    @Column(name = "phone_number", nullable = false, length = 20)
    private PhoneNumber phoneNumber;

    @Convert(converter = EmailAddressPersistenceConverter.class)
    @Column(name = "email", nullable = false, unique = true)
    private EmailAddress email;

    @Convert(converter = OrganicCertificationPersistenceConverter.class)
    @Column(name = "organic_certification", nullable = false, length = 64)
    private OrganicCertification organicCertification;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "supplier_categories", joinColumns = @JoinColumn(name = "supplier_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 30)
    private Set<SupplierCategory> categories = new HashSet<>();
}
