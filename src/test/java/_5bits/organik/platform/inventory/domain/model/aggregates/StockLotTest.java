package _5bits.organik.platform.inventory.domain.model.aggregates;

import _5bits.organik.platform.inventory.domain.model.events.StockLotLowStockDetectedEvent;
import _5bits.organik.platform.inventory.domain.model.valueobjects.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StockLotTest {

    private StockLot stockLotWith(int quantity, int minimumStock) {
        return StockLot.reconstitute(1L, new MinimarketId("mm-vida-verde"), new ProductId("ORG-01"), new LotCode("LT-204"),
                new Quantity(quantity), new Quantity(minimumStock), new ExpirationDate(LocalDate.of(2026, 12, 20)),
                new LotLocation("Cold room 1"), new LotNotes(""));
    }

    @Test
    void entryIncreasesTheQuantity() {
        var stockLot = stockLotWith(30, 10);

        stockLot.applyMovement(MovementType.ENTRY, new Quantity(5));

        assertThat(stockLot.getQuantity().value()).isEqualTo(35);
        assertThat(stockLot.domainEvents()).isEmpty();
    }

    @Test
    void exitDecreasesTheQuantity() {
        var stockLot = stockLotWith(30, 10);

        stockLot.applyMovement(MovementType.EXIT, new Quantity(12));

        assertThat(stockLot.getQuantity().value()).isEqualTo(18);
    }

    @Test
    void adjustmentSetsTheCountedQuantity() {
        var stockLot = stockLotWith(30, 10);

        stockLot.applyMovement(MovementType.ADJUSTMENT, new Quantity(27));

        assertThat(stockLot.getQuantity().value()).isEqualTo(27);
    }

    @Test
    void exitGreaterThanTheStockIsRejected() {
        var stockLot = stockLotWith(30, 10);

        assertThat(stockLot.canApplyMovement(MovementType.EXIT, new Quantity(31))).isFalse();
        assertThrows(IllegalArgumentException.class, () -> stockLot.applyMovement(MovementType.EXIT, new Quantity(31)));
    }

    @Test
    void reachingTheMinimumStockRegistersLowStockEvent() {
        var stockLot = stockLotWith(30, 20);

        stockLot.applyMovement(MovementType.WASTE, new Quantity(12));

        assertThat(stockLot.isLowStock()).isTrue();
        assertThat(stockLot.domainEvents()).singleElement().isInstanceOf(StockLotLowStockDetectedEvent.class);
        var event = (StockLotLowStockDetectedEvent) stockLot.domainEvents().iterator().next();
        assertThat(event.quantity()).isEqualTo(18);
        assertThat(event.lotCode()).isEqualTo("LT-204");
    }
}
