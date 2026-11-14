package _5bits.organik.platform.suppliers.interfaces.rest.transform;

import _5bits.organik.platform.suppliers.domain.model.aggregates.Supplier;
import _5bits.organik.platform.suppliers.interfaces.rest.resources.SupplierResource;


/**
 * Assembler that converts a {@link Supplier} aggregate into a {@link SupplierResource}.
 */
public class SupplierResourceFromEntityAssembler {
    public static SupplierResource toResourceFromEntity(Supplier entity) {
        return new SupplierResource(
                entity.getId(),
                entity.getBusinessName().value(),
                entity.getContactName().value(),
                entity.getPhoneNumber().value(),
                entity.getEmail().value(),
                entity.getCategories().stream().map(Enum::name).sorted().toList(),
                entity.getOrganicCertification().value());
    }
}
