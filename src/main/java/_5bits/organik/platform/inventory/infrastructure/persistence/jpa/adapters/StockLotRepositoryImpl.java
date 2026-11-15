package _5bits.organik.platform.inventory.infrastructure.persistence.jpa.adapters;

import _5bits.organik.platform.inventory.domain.model.aggregates.StockLot;
import _5bits.organik.platform.inventory.domain.model.valueobjects.LotCode;
import _5bits.organik.platform.inventory.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.inventory.domain.repositories.StockLotRepository;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.assemblers.StockLotPersistenceAssembler;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.repositories.StockLotPersistenceRepository;
import _5bits.organik.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Infrastructure adapter that implements the {@link StockLotRepository} domain port with Spring Data JPA.
 * <p>
 * Maps between domain and persistence models through {@link StockLotPersistenceAssembler} and publishes
 * the domain events registered by the aggregate after it is persisted.
 * </p>
 */
@Repository
public class StockLotRepositoryImpl implements StockLotRepository {
    private final StockLotPersistenceRepository stockLotPersistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public StockLotRepositoryImpl(
            StockLotPersistenceRepository stockLotPersistenceRepository,
            DomainEventPublisher domainEventPublisher) {
        this.stockLotPersistenceRepository = stockLotPersistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public Optional<StockLot> findById(Long id) {
        return stockLotPersistenceRepository.findById(id).map(StockLotPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<StockLot> findAll() {
        return stockLotPersistenceRepository.findAll().stream()
                .map(StockLotPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<StockLot> findAllByMinimarketId(MinimarketId minimarketId) {
        return stockLotPersistenceRepository.findAllByMinimarketId(minimarketId).stream()
                .map(StockLotPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean existsByMinimarketIdAndLotCode(MinimarketId minimarketId, LotCode lotCode) {
        return stockLotPersistenceRepository.existsByMinimarketIdAndLotCode(minimarketId, lotCode);
    }

    @Override
    public StockLot save(StockLot stockLot) {
        var saved = stockLotPersistenceRepository.save(StockLotPersistenceAssembler.toPersistenceFromDomain(stockLot));
        var savedStockLot = StockLotPersistenceAssembler.toDomainFromPersistence(saved);
        domainEventPublisher.publishAndClearEvents(stockLot);
        return savedStockLot;
    }

    @Override
    public boolean existsById(Long id) {
        return stockLotPersistenceRepository.existsById(id);
    }
}
