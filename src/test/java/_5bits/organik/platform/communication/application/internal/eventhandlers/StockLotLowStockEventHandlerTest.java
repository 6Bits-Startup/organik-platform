package _5bits.organik.platform.communication.application.internal.eventhandlers;

import _5bits.organik.platform.communication.application.commandservices.NotificationCommandService;
import _5bits.organik.platform.communication.domain.model.commands.CreateNotificationCommand;
import _5bits.organik.platform.inventory.interfaces.events.StockLotLowStockIntegrationEvent;
import _5bits.organik.platform.shared.application.result.Result;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockLotLowStockEventHandlerTest {

    @Mock
    private NotificationCommandService notificationCommandService;

    @Test
    void createsLowStockWarningNotification() {
        // Arrange
        when(notificationCommandService.handle(any(CreateNotificationCommand.class))).thenReturn(Result.success(1L));
        var handler = new StockLotLowStockEventHandler(notificationCommandService);

        // Act
        handler.on(new StockLotLowStockIntegrationEvent(1L, "mm-vida-verde", "ORG-01", "LT-204", 18, 20));

        // Assert
        var captor = ArgumentCaptor.forClass(CreateNotificationCommand.class);
        verify(notificationCommandService).handle(captor.capture());
        assertThat(captor.getValue().type().value()).isEqualTo("LOW_STOCK");
        assertThat(captor.getValue().severity().value()).isEqualTo("WARNING");
        assertThat(captor.getValue().title().value()).contains("LT-204");
    }
}
