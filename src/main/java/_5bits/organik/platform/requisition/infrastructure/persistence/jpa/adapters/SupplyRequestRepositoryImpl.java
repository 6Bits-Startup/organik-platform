package _5bits.organik.platform.requisition.infrastructure.persistence.jpa.adapters;

import _5bits.organik.platform.requisition.domain.model.aggregates.SupplyRequest;
import _5bits.organik.platform.requisition.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.requisition.domain.model.valueobjects.SupplierId;
import _5bits.organik.platform.requisition.domain.repositories.SupplyRequestRepository;
import _5bits.organik.platform.requisition.infrastructure.persistence.jpa.assemblers.SupplyRequestPersistenceAssembler;
import _5bits.organik.platform.requisition.infrastructure.persistence.jpa.repositories.SupplyRequestPersistenceRepository;
import _5bits.organik.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Infrastructure adapter that implements the {@link SupplyRequestRepository} domain port with Spring Data JPA.
 * <p>
 * Maps between domain and persistence models through {@link SupplyRequestPersistenceAssembler} and publishes
 * the domain events registered by the aggregate after it is persisted.
 * </p>
 */
@Repository
public class SupplyRequestRepositoryImpl implements SupplyRequestRepository {
    private final SupplyRequestPersistenceRepository supplyRequestPersistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public SupplyRequestRepositoryImpl(
            SupplyRequestPersistenceRepository supplyRequestPersistenceRepository,
            DomainEventPublisher domainEventPublisher) {
        this.supplyRequestPersistenceRepository = supplyRequestPersistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public Optional<SupplyRequest> findById(Long id) {
        return supplyRequestPersistenceRepository.findById(id).map(SupplyRequestPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<SupplyRequest> findAll() {
        return supplyRequestPersistenceRepository.findAll().stream()
                .map(SupplyRequestPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<SupplyRequest> findAllByMinimarketId(MinimarketId minimarketId) {
        return supplyRequestPersistenceRepository.findAllByMinimarketId(minimarketId).stream()
                .map(SupplyRequestPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<SupplyRequest> findAllBySupplierId(SupplierId supplierId) {
        return supplyRequestPersistenceRepository.findAllBySupplierId(supplierId).stream()
                .map(SupplyRequestPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public SupplyRequest save(SupplyRequest supplyRequest) {
        var saved = supplyRequestPersistenceRepository.save(SupplyRequestPersistenceAssembler.toPersistenceFromDomain(supplyRequest));
        var savedSupplyRequest = SupplyRequestPersistenceAssembler.toDomainFromPersistence(saved);
        domainEventPublisher.publishAndClearEvents(supplyRequest);
        return savedSupplyRequest;
    }

    @Override
    public boolean existsById(Long id) {
        return supplyRequestPersistenceRepository.existsById(id);
    }
}
