package _5bits.organik.platform.suppliers.domain.model.aggregates;

import _5bits.organik.platform.suppliers.domain.model.commands.CreateSupplierCommand;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.*;
import _5bits.organik.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Supplier aggregate root.
 * <p>
 * Represents an organic producer or distributor that supplies minimarkets.
 * </p>
 */
@Getter
public class Supplier extends AbstractDomainAggregateRoot<Supplier> {
    private Long id;
    private BusinessName businessName;
    private ContactName contactName;
    private PhoneNumber phoneNumber;
    private EmailAddress email;
    private Set<SupplierCategory> categories;
    private OrganicCertification organicCertification;

    public Supplier(BusinessName businessName, ContactName contactName, PhoneNumber phoneNumber, EmailAddress email,
                    Set<SupplierCategory> categories, OrganicCertification organicCertification) {
        this.businessName = Objects.requireNonNull(businessName, "businessName must not be null");
        this.contactName = Objects.requireNonNull(contactName, "contactName must not be null");
        this.phoneNumber = Objects.requireNonNull(phoneNumber, "phoneNumber must not be null");
        this.email = Objects.requireNonNull(email, "email must not be null");
        this.categories = categories != null ? new HashSet<>(categories) : new HashSet<>();
        this.organicCertification = Objects.requireNonNull(organicCertification, "organicCertification must not be null");
    }

    public Supplier(CreateSupplierCommand command) {
        this(command.businessName(), command.contactName(), command.phoneNumber(), command.email(),
                command.categories(), command.organicCertification());
    }

    public static Supplier reconstitute(Long id, BusinessName businessName, ContactName contactName, PhoneNumber phoneNumber,
                                        EmailAddress email, Set<SupplierCategory> categories,
                                        OrganicCertification organicCertification) {
        var supplier = new Supplier(businessName, contactName, phoneNumber, email, categories, organicCertification);
        supplier.id = id;
        return supplier;
    }
}
