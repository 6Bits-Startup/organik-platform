package _5bits.organik.platform.analytics.application.internal.eventhandlers;

import _5bits.organik.platform.analytics.application.commandservices.ActivityRecordCommandService;
import _5bits.organik.platform.analytics.domain.model.commands.RegisterActivityRecordCommand;
import _5bits.organik.platform.analytics.domain.model.valueobjects.ActivityType;
import _5bits.organik.platform.procurements.interfaces.events.ShipmentOrderAcceptedIntegrationEvent;
import _5bits.organik.platform.shared.application.result.Result;
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
    private ActivityRecordCommandService activityRecordCommandService;

    @Test
    void registersTheAcceptedOrderInTheActivityHistory() {
        // Arrange
        when(activityRecordCommandService.handle(any(RegisterActivityRecordCommand.class))).thenReturn(Result.success(1L));
        var handler = new ShipmentOrderAcceptedEventHandler(activityRecordCommandService);
        var event = new ShipmentOrderAcceptedIntegrationEvent(1L, "sup-bioandes", "mm-vida-verde",
                List.of(new ShipmentOrderAcceptedIntegrationEvent.Line("ORG-01", 30, "LT-512", LocalDate.of(2026, 12, 20))));

        // Act
        handler.on(event);

        // Assert
        var captor = ArgumentCaptor.forClass(RegisterActivityRecordCommand.class);
        verify(activityRecordCommandService).handle(captor.capture());
        assertThat(captor.getValue().type()).isEqualTo(ActivityType.SHIPMENT_ORDER_ACCEPTED);
        assertThat(captor.getValue().minimarketId().value()).isEqualTo("mm-vida-verde");
        assertThat(captor.getValue().description().value()).contains("1 lot(s)");
    }
}
