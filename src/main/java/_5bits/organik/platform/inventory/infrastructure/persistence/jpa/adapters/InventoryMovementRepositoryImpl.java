package _5bits.organik.platform.inventory.infrastructure.persistence.jpa.adapters;

import _5bits.organik.platform.inventory.domain.model.aggregates.InventoryMovement;
import _5bits.organik.platform.inventory.domain.model.valueobjects.StockLotId;
import _5bits.organik.platform.inventory.domain.repositories.InventoryMovementRepository;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.assemblers.InventoryMovementPersistenceAssembler;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.repositories.InventoryMovementPersistenceRepository;
import _5bits.organik.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Infrastructure adapter that implements the {@link InventoryMovementRepository} domain port with Spring Data JPA.
 * <p>
 * Maps between domain and persistence models through {@link InventoryMovementPersistenceAssembler} and publishes
 * the domain events registered by the aggregate after it is persisted.
 * </p>
 */
@Repository
public class InventoryMovementRepositoryImpl implements InventoryMovementRepository {
    private final InventoryMovementPersistenceRepository inventoryMovementPersistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public InventoryMovementRepositoryImpl(
            InventoryMovementPersistenceRepository inventoryMovementPersistenceRepository,
            DomainEventPublisher domainEventPublisher) {
        this.inventoryMovementPersistenceRepository = inventoryMovementPersistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public Optional<InventoryMovement> findById(Long id) {
        return inventoryMovementPersistenceRepository.findById(id).map(InventoryMovementPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<InventoryMovement> findAll() {
        return inventoryMovementPersistenceRepository.findAll().stream()
                .map(InventoryMovementPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<InventoryMovement> findAllByStockLotId(StockLotId stockLotId) {
        return inventoryMovementPersistenceRepository.findAllByStockLotId(stockLotId).stream()
                .map(InventoryMovementPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public InventoryMovement save(InventoryMovement inventoryMovement) {
        var saved = inventoryMovementPersistenceRepository.save(InventoryMovementPersistenceAssembler.toPersistenceFromDomain(inventoryMovement));
        var savedInventoryMovement = InventoryMovementPersistenceAssembler.toDomainFromPersistence(saved);
        domainEventPublisher.publishAndClearEvents(inventoryMovement);
        return savedInventoryMovement;
    }

    @Override
    public boolean existsById(Long id) {
        return inventoryMovementPersistenceRepository.existsById(id);
    }
}
