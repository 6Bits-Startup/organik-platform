package _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.assemblers;

import _5bits.organik.platform.suppliers.domain.model.aggregates.Supplier;
import _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.entities.SupplierPersistenceEntity;

import java.util.HashSet;

/**
 * Assembler that maps between the {@link Supplier} domain aggregate and its JPA persistence entity.
 * <p>
 * Acts as the anti-corruption layer of the persistence adapter: JPA concerns never leak into the domain.
 * </p>
 */
public final class SupplierPersistenceAssembler {

    private SupplierPersistenceAssembler() {
    }

    /**
     * Reconstitutes a domain aggregate from its persistence entity.
     *
     * @param entity persistence entity, may be {@code null}
     * @return the reconstituted aggregate, or {@code null} when the entity is {@code null}
     */
    public static Supplier toDomainFromPersistence(SupplierPersistenceEntity entity) {
        if (entity == null) return null;
        return Supplier.reconstitute(
                entity.getId(),
                entity.getBusinessName(),
                entity.getContactName(),
                entity.getPhoneNumber(),
                entity.getEmail(),
                entity.getCategories() == null ? new HashSet<>() : new HashSet<>(entity.getCategories()),
                entity.getOrganicCertification());
    }

    /**
     * Maps a domain aggregate to a persistence entity.
     *
     * @param supplier domain aggregate, may be {@code null}
     * @return the persistence entity, or {@code null} when the aggregate is {@code null}
     */
    public static SupplierPersistenceEntity toPersistenceFromDomain(Supplier supplier) {
        if (supplier == null) return null;
        var entity = new SupplierPersistenceEntity();
        if (supplier.getId() != null) {
            entity.setId(supplier.getId());
        }
        entity.setBusinessName(supplier.getBusinessName());
        entity.setContactName(supplier.getContactName());
        entity.setPhoneNumber(supplier.getPhoneNumber());
        entity.setEmail(supplier.getEmail());
        entity.setCategories(new HashSet<>(supplier.getCategories()));
        entity.setOrganicCertification(supplier.getOrganicCertification());
        return entity;
    }
}
