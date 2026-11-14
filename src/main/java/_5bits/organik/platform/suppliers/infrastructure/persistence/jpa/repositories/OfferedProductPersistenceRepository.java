package _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.repositories;

import _5bits.organik.platform.suppliers.domain.model.valueobjects.LotCode;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.SupplierId;
import _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.entities.OfferedProductPersistenceEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link OfferedProductPersistenceEntity}.
 * <p>
 * Infrastructure detail used only by the {@code OfferedProductRepositoryImpl} adapter.
 * </p>
 */
@Repository
public interface OfferedProductPersistenceRepository extends JpaRepository<OfferedProductPersistenceEntity, Long> {

    List<OfferedProductPersistenceEntity> findAllBySupplierId(SupplierId supplierId);

    boolean existsBySupplierIdAndLotCode(SupplierId supplierId, LotCode lotCode);
}
