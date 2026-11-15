package _5bits.organik.platform.suppliers.domain.repositories;

import _5bits.organik.platform.suppliers.domain.model.aggregates.OfferedProduct;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.LotCode;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.SupplierId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository port for the {@link OfferedProduct} aggregate.
 * <p>
 * Pure Java interface with no framework dependencies. The infrastructure layer provides the
 * adapter that implements it with Spring Data JPA.
 * </p>
 */
public interface OfferedProductRepository {

    Optional<OfferedProduct> findById(Long id);

    List<OfferedProduct> findAll();

    List<OfferedProduct> findAllBySupplierId(SupplierId supplierId);

    Optional<OfferedProduct> findBySupplierIdAndLotCode(SupplierId supplierId, LotCode lotCode);

    boolean existsBySupplierIdAndLotCode(SupplierId supplierId, LotCode lotCode);

    boolean existsBySupplierIdAndLotCodeAndIdIsNot(SupplierId supplierId, LotCode lotCode, Long id);

    OfferedProduct save(OfferedProduct offeredProduct);

    boolean existsById(Long id);
}
