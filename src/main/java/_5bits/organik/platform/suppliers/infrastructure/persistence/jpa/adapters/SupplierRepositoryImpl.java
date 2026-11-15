package _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.adapters;

import _5bits.organik.platform.shared.infrastructure.events.DomainEventPublisher;
import _5bits.organik.platform.suppliers.domain.model.aggregates.Supplier;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.EmailAddress;
import _5bits.organik.platform.suppliers.domain.repositories.SupplierRepository;
import _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.assemblers.SupplierPersistenceAssembler;
import _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.repositories.SupplierPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Infrastructure adapter that implements the {@link SupplierRepository} domain port with Spring Data JPA.
 * <p>
 * Maps between domain and persistence models through {@link SupplierPersistenceAssembler} and publishes
 * the domain events registered by the aggregate after it is persisted.
 * </p>
 */
@Repository
public class SupplierRepositoryImpl implements SupplierRepository {
    private final SupplierPersistenceRepository supplierPersistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public SupplierRepositoryImpl(
            SupplierPersistenceRepository supplierPersistenceRepository,
            DomainEventPublisher domainEventPublisher) {
        this.supplierPersistenceRepository = supplierPersistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public Optional<Supplier> findById(Long id) {
        return supplierPersistenceRepository.findById(id).map(SupplierPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Supplier> findAll() {
        return supplierPersistenceRepository.findAll().stream()
                .map(SupplierPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean existsByEmail(EmailAddress email) {
        return supplierPersistenceRepository.existsByEmail(email);
    }

    @Override
    public Supplier save(Supplier supplier) {
        var saved = supplierPersistenceRepository.save(SupplierPersistenceAssembler.toPersistenceFromDomain(supplier));
        var savedSupplier = SupplierPersistenceAssembler.toDomainFromPersistence(saved);
        domainEventPublisher.publishAndClearEvents(supplier);
        return savedSupplier;
    }

    @Override
    public boolean existsById(Long id) {
        return supplierPersistenceRepository.existsById(id);
    }
}
