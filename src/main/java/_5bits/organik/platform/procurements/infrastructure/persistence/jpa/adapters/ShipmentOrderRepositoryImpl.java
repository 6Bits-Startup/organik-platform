package _5bits.organik.platform.procurements.infrastructure.persistence.jpa.adapters;

import _5bits.organik.platform.procurements.domain.model.aggregates.ShipmentOrder;
import _5bits.organik.platform.procurements.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.procurements.domain.model.valueobjects.SupplierId;
import _5bits.organik.platform.procurements.domain.repositories.ShipmentOrderRepository;
import _5bits.organik.platform.procurements.infrastructure.persistence.jpa.assemblers.ShipmentOrderPersistenceAssembler;
import _5bits.organik.platform.procurements.infrastructure.persistence.jpa.repositories.ShipmentOrderPersistenceRepository;
import _5bits.organik.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Infrastructure adapter that implements the {@link ShipmentOrderRepository} domain port with Spring Data JPA.
 * <p>
 * Maps between domain and persistence models through {@link ShipmentOrderPersistenceAssembler} and publishes
 * the domain events registered by the aggregate after it is persisted.
 * </p>
 */
@Repository
public class ShipmentOrderRepositoryImpl implements ShipmentOrderRepository {
    private final ShipmentOrderPersistenceRepository shipmentOrderPersistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public ShipmentOrderRepositoryImpl(
            ShipmentOrderPersistenceRepository shipmentOrderPersistenceRepository,
            DomainEventPublisher domainEventPublisher) {
        this.shipmentOrderPersistenceRepository = shipmentOrderPersistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public Optional<ShipmentOrder> findById(Long id) {
        return shipmentOrderPersistenceRepository.findById(id).map(ShipmentOrderPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<ShipmentOrder> findAll() {
        return shipmentOrderPersistenceRepository.findAll().stream()
                .map(ShipmentOrderPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<ShipmentOrder> findAllBySupplierId(SupplierId supplierId) {
        return shipmentOrderPersistenceRepository.findAllBySupplierId(supplierId).stream()
                .map(ShipmentOrderPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<ShipmentOrder> findAllByMinimarketId(MinimarketId minimarketId) {
        return shipmentOrderPersistenceRepository.findAllByMinimarketId(minimarketId).stream()
                .map(ShipmentOrderPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public ShipmentOrder save(ShipmentOrder shipmentOrder) {
        var saved = shipmentOrderPersistenceRepository.save(ShipmentOrderPersistenceAssembler.toPersistenceFromDomain(shipmentOrder));
        var savedShipmentOrder = ShipmentOrderPersistenceAssembler.toDomainFromPersistence(saved);
        domainEventPublisher.publishAndClearEvents(shipmentOrder);
        return savedShipmentOrder;
    }

    @Override
    public boolean existsById(Long id) {
        return shipmentOrderPersistenceRepository.existsById(id);
    }
}
