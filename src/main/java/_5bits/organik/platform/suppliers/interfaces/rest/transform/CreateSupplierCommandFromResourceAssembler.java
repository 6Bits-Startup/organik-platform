package _5bits.organik.platform.suppliers.interfaces.rest.transform;

import _5bits.organik.platform.suppliers.domain.model.commands.CreateSupplierCommand;
import _5bits.organik.platform.suppliers.interfaces.rest.resources.CreateSupplierResource;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.*;
import java.util.stream.Collectors;

/**
 * Assembler that converts a {@link CreateSupplierResource} into a {@link CreateSupplierCommand}.
 */
public class CreateSupplierCommandFromResourceAssembler {
    public static CreateSupplierCommand toCommandFromResource(CreateSupplierResource resource) {
        return new CreateSupplierCommand(
                new BusinessName(resource.businessName()),
                new ContactName(resource.contactName()),
                new PhoneNumber(resource.phoneNumber()),
                new EmailAddress(resource.email()),
                resource.categories().stream().map(SupplierCategory::valueOf).collect(Collectors.toSet()),
                new OrganicCertification(resource.organicCertification()));
    }
}
