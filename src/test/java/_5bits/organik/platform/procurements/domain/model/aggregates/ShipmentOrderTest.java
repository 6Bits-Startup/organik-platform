package _5bits.organik.platform.procurements.domain.model.aggregates;

import _5bits.organik.platform.procurements.domain.model.commands.CreateShipmentOrderCommand;
import _5bits.organik.platform.procurements.domain.model.events.ShipmentOrderAcceptedEvent;
import _5bits.organik.platform.procurements.domain.model.valueobjects.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShipmentOrderTest {

    private ShipmentOrder pendingOrder() {
        var command = new CreateShipmentOrderCommand(new SupplierId("sup-bioandes"), new MinimarketId("mm-vida-verde"),
                List.of(new CreateShipmentOrderCommand.Line(new ProductId("ORG-01"), new Quantity(30), new LotCode("LT-512"),
                        LocalDate.of(2026, 12, 20))));
        var order = new ShipmentOrder(command);
        return ShipmentOrder.reconstitute(1L, order.getSupplierId(), order.getMinimarketId(), order.getLines(),
                order.getStatus(), null, null, null, null, null);
    }

    @Test
    void newShipmentOrderIsPending() {
        assertThat(pendingOrder().isPending()).isTrue();
    }

    @Test
    void acceptRegistersShipmentOrderAcceptedEventWithItsLines() {
        var order = pendingOrder();

        order.accept(new UserId(1L));

        assertThat(order.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
        assertThat(order.getDecidedAt()).isNotNull();
        var event = (ShipmentOrderAcceptedEvent) order.domainEvents().iterator().next();
        assertThat(event.shipmentOrderId()).isEqualTo(1L);
        assertThat(event.lines()).singleElement().extracting(ShipmentOrderAcceptedEvent.AcceptedLine::lotCode).isEqualTo("LT-512");
    }

    @Test
    void rejectStoresTheReason() {
        var order = pendingOrder();

        order.reject(new UserId(1L), new RejectionReason("Products arrived damaged"));

        assertThat(order.getStatus()).isEqualTo(OrderStatus.REJECTED);
        assertThat(order.getRejectionReason().value()).isEqualTo("Products arrived damaged");
        assertThat(order.domainEvents()).isEmpty();
    }

    @Test
    void decidedOrderCannotBeDecidedAgain() {
        var order = pendingOrder();
        order.accept(new UserId(1L));

        assertThrows(IllegalStateException.class, () -> order.reject(new UserId(1L), new RejectionReason("late")));
    }
}
