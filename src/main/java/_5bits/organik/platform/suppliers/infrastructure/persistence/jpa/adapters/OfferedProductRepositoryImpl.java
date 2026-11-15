package _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.adapters;

import _5bits.organik.platform.shared.infrastructure.events.DomainEventPublisher;
import _5bits.organik.platform.suppliers.domain.model.aggregates.OfferedProduct;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.LotCode;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.SupplierId;
import _5bits.organik.platform.suppliers.domain.repositories.OfferedProductRepository;
import _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.assemblers.OfferedProductPersistenceAssembler;
import _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.repositories.OfferedProductPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Infrastructure adapter that implements the {@link OfferedProductRepository} domain port with Spring Data JPA.
 * <p>
 * Maps between domain and persistence models through {@link OfferedProductPersistenceAssembler} and publishes
 * the domain events registered by the aggregate after it is persisted.
 * </p>
 */
@Repository
public class OfferedProductRepositoryImpl implements OfferedProductRepository {
    private final OfferedProductPersistenceRepository offeredProductPersistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public OfferedProductRepositoryImpl(
            OfferedProductPersistenceRepository offeredProductPersistenceRepository,
            DomainEventPublisher domainEventPublisher) {
        this.offeredProductPersistenceRepository = offeredProductPersistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public Optional<OfferedProduct> findById(Long id) {
        return offeredProductPersistenceRepository.findById(id).map(OfferedProductPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<OfferedProduct> findAll() {
        return offeredProductPersistenceRepository.findAll().stream()
                .map(OfferedProductPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<OfferedProduct> findAllBySupplierId(SupplierId supplierId) {
        return offeredProductPersistenceRepository.findAllBySupplierId(supplierId).stream()
                .map(OfferedProductPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Optional<OfferedProduct> findBySupplierIdAndLotCode(SupplierId supplierId, LotCode lotCode) {
        return offeredProductPersistenceRepository.findBySupplierIdAndLotCode(supplierId, lotCode)
                .map(OfferedProductPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public boolean existsBySupplierIdAndLotCode(SupplierId supplierId, LotCode lotCode) {
        return offeredProductPersistenceRepository.existsBySupplierIdAndLotCode(supplierId, lotCode);
    }

    @Override
    public boolean existsBySupplierIdAndLotCodeAndIdIsNot(SupplierId supplierId, LotCode lotCode, Long id) {
        return offeredProductPersistenceRepository.existsBySupplierIdAndLotCodeAndIdIsNot(supplierId, lotCode, id);
    }

    @Override
    public OfferedProduct save(OfferedProduct offeredProduct) {
        var saved = offeredProductPersistenceRepository.save(OfferedProductPersistenceAssembler.toPersistenceFromDomain(offeredProduct));
        var savedOfferedProduct = OfferedProductPersistenceAssembler.toDomainFromPersistence(saved);
        domainEventPublisher.publishAndClearEvents(offeredProduct);
        return savedOfferedProduct;
    }

    @Override
    public boolean existsById(Long id) {
        return offeredProductPersistenceRepository.existsById(id);
    }
}
