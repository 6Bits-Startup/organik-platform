package _5bits.organik.platform.inventory.infrastructure.persistence.jpa.adapters;

import _5bits.organik.platform.inventory.domain.model.events.StockLotLowStockDetectedEvent;
import _5bits.organik.platform.inventory.domain.model.valueobjects.*;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.assemblers.StockLotPersistenceAssembler;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.entities.StockLotPersistenceEntity;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.repositories.StockLotPersistenceRepository;
import _5bits.organik.platform.shared.infrastructure.events.DomainEventPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockLotRepositoryImplTest {

    @Mock
    private StockLotPersistenceRepository stockLotPersistenceRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    private StockLotRepositoryImpl stockLotRepository;

    @BeforeEach
    void setUp() {
        stockLotRepository = new StockLotRepositoryImpl(stockLotPersistenceRepository, new DomainEventPublisher(eventPublisher));
    }

    private StockLotPersistenceEntity persistenceEntity(int quantity) {
        var entity = new StockLotPersistenceEntity();
        entity.setId(1L);
        entity.setMinimarketId(new MinimarketId("mm-vida-verde"));
        entity.setProductId(new ProductId("ORG-01"));
        entity.setLotCode(new LotCode("LT-204"));
        entity.setQuantity(new Quantity(quantity));
        entity.setMinimumStock(new Quantity(20));
        entity.setExpirationDate(new ExpirationDate(LocalDate.of(2026, 12, 20)));
        return entity;
    }

    @Test
    void findByIdShouldReturnStockLotWhenFound() {
        // Arrange
        when(stockLotPersistenceRepository.findById(1L)).thenReturn(Optional.of(persistenceEntity(30)));

        // Act
        var found = stockLotRepository.findById(1L);

        // Assert
        assertThat(found).isPresent();
        assertThat(found.get().getLotCode().value()).isEqualTo("LT-204");
        assertThat(found.get().isLowStock()).isFalse();
    }

    @Test
    void saveShouldPersistStockLotAndPublishDomainEvents() {
        // Arrange
        var stockLot = StockLotPersistenceAssembler.toDomainFromPersistence(persistenceEntity(30));
        stockLot.applyMovement(MovementType.EXIT, new Quantity(12));
        when(stockLotPersistenceRepository.save(any(StockLotPersistenceEntity.class))).thenReturn(persistenceEntity(18));

        // Act
        var saved = stockLotRepository.save(stockLot);

        // Assert
        assertThat(saved.getQuantity().value()).isEqualTo(18);
        verify(eventPublisher).publishEvent(any(StockLotLowStockDetectedEvent.class));
        assertThat(stockLot.domainEvents()).isEmpty();
    }
}
