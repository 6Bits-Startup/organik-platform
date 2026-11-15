package _5bits.organik.platform.suppliers.application.internal.eventhandlers;

import _5bits.organik.platform.procurements.interfaces.events.ShipmentOrderAcceptedIntegrationEvent;
import _5bits.organik.platform.shared.application.result.Result;
import _5bits.organik.platform.suppliers.application.commandservices.OfferedProductCommandService;
import _5bits.organik.platform.suppliers.domain.model.commands.DecreaseOfferedProductAvailabilityCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShipmentOrderAcceptedEventHandlerTest {

    @Mock
    private OfferedProductCommandService offeredProductCommandService;

    @Test
    void decreasesTheAvailabilityOfEveryShippedLot() {
        // Arrange
        when(offeredProductCommandService.handle(any(DecreaseOfferedProductAvailabilityCommand.class))).thenReturn(Result.success(1L));
        var handler = new ShipmentOrderAcceptedEventHandler(offeredProductCommandService);
        var event = new ShipmentOrderAcceptedIntegrationEvent(1L, "2", "mm-1",
                List.of(new ShipmentOrderAcceptedIntegrationEvent.Line("ORG-01", 30, "AG-LECHE-1126", LocalDate.of(2026, 12, 5))));

        // Act
        handler.on(event);

        // Assert
        var captor = ArgumentCaptor.forClass(DecreaseOfferedProductAvailabilityCommand.class);
        verify(offeredProductCommandService).handle(captor.capture());
        assertThat(captor.getValue().supplierId().value()).isEqualTo("2");
        assertThat(captor.getValue().lotCode().value()).isEqualTo("AG-LECHE-1126");
        assertThat(captor.getValue().shippedQuantity().value()).isEqualTo(30);
    }
}
