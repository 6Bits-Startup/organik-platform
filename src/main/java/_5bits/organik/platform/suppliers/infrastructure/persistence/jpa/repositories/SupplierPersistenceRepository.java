package _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.repositories;

import _5bits.organik.platform.suppliers.domain.model.valueobjects.EmailAddress;
import _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.entities.SupplierPersistenceEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link SupplierPersistenceEntity}.
 * <p>
 * Infrastructure detail used only by the {@code SupplierRepositoryImpl} adapter.
 * </p>
 */
@Repository
public interface SupplierPersistenceRepository extends JpaRepository<SupplierPersistenceEntity, Long> {

    boolean existsByEmail(EmailAddress email);
}
