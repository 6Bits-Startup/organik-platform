package _5bits.organik.platform.inventory.application.internal.commandservices;

import _5bits.organik.platform.inventory.domain.model.aggregates.InventoryMovement;
import _5bits.organik.platform.inventory.domain.model.aggregates.StockLot;
import _5bits.organik.platform.inventory.domain.model.commands.RegisterInventoryMovementCommand;
import _5bits.organik.platform.inventory.domain.model.valueobjects.*;
import _5bits.organik.platform.inventory.domain.repositories.InventoryMovementRepository;
import _5bits.organik.platform.inventory.domain.repositories.StockLotRepository;
import _5bits.organik.platform.shared.application.result.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryMovementCommandServiceImplTest {

    @Mock
    private InventoryMovementRepository inventoryMovementRepository;

    @Mock
    private StockLotRepository stockLotRepository;

    private InventoryMovementCommandServiceImpl inventoryMovementCommandService;

    @BeforeEach
    void setUp() {
        inventoryMovementCommandService = new InventoryMovementCommandServiceImpl(inventoryMovementRepository, stockLotRepository);
    }

    private StockLot stockLot() {
        return StockLot.reconstitute(1L, new MinimarketId("mm-vida-verde"), new ProductId("ORG-01"), new LotCode("LT-204"),
                new Quantity(30), new Quantity(10), new ExpirationDate(LocalDate.of(2026, 12, 20)));
    }

    @Test
    void handleReturnsNotFoundWhenStockLotDoesNotExist() {
        // Arrange
        when(stockLotRepository.findById(1L)).thenReturn(Optional.empty());
        var command = new RegisterInventoryMovementCommand(new StockLotId(1L), MovementType.EXIT, new Quantity(5), null);

        // Act
        var result = inventoryMovementCommandService.handle(command);

        // Assert
        assertThat(result).isInstanceOf(Result.Failure.class);
        assertThat(((Result.Failure<?, _5bits.organik.platform.shared.application.result.ApplicationError>) result).error().code())
                .isEqualTo("STOCKLOT_NOT_FOUND");
        verify(inventoryMovementRepository, never()).save(any());
    }

    @Test
    void handleReturnsBusinessRuleViolationWhenStockIsInsufficient() {
        // Arrange
        when(stockLotRepository.findById(1L)).thenReturn(Optional.of(stockLot()));
        var command = new RegisterInventoryMovementCommand(new StockLotId(1L), MovementType.EXIT, new Quantity(40), null);

        // Act
        var result = inventoryMovementCommandService.handle(command);

        // Assert
        assertThat(((Result.Failure<?, _5bits.organik.platform.shared.application.result.ApplicationError>) result).error().code())
                .isEqualTo("BUSINESS_RULE_VIOLATION");
        verify(stockLotRepository, never()).save(any());
    }

    @Test
    void handleAppliesTheMovementToTheStockLotAndRegistersIt() {
        // Arrange
        var stockLot = stockLot();
        when(stockLotRepository.findById(1L)).thenReturn(Optional.of(stockLot));
        when(stockLotRepository.save(any(StockLot.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(inventoryMovementRepository.save(any(InventoryMovement.class))).thenAnswer(invocation -> {
            InventoryMovement movement = invocation.getArgument(0);
            return InventoryMovement.reconstitute(7L, movement.getStockLotId(), movement.getType(), movement.getQuantity(),
                    movement.getReason(), null, null);
        });
        var command = new RegisterInventoryMovementCommand(new StockLotId(1L), MovementType.EXIT, new Quantity(12), null);

        // Act
        var result = inventoryMovementCommandService.handle(command);

        // Assert
        assertThat(result.toOptional()).contains(7L);
        assertThat(stockLot.getQuantity().value()).isEqualTo(18);
        verify(stockLotRepository).save(stockLot);
    }
}
