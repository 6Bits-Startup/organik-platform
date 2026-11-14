package _5bits.organik.platform.suppliers.domain.repositories;

import _5bits.organik.platform.suppliers.domain.model.aggregates.Supplier;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.EmailAddress;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository port for the {@link Supplier} aggregate.
 * <p>
 * Pure Java interface with no framework dependencies. The infrastructure layer provides the
 * adapter that implements it with Spring Data JPA.
 * </p>
 */
public interface SupplierRepository {

    Optional<Supplier> findById(Long id);

    List<Supplier> findAll();

    boolean existsByEmail(EmailAddress email);

    Supplier save(Supplier supplier);

    boolean existsById(Long id);
}
