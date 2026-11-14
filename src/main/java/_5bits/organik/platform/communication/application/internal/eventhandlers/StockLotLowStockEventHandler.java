package _5bits.organik.platform.communication.application.internal.eventhandlers;

import _5bits.organik.platform.communication.application.commandservices.NotificationCommandService;
import _5bits.organik.platform.communication.domain.model.commands.CreateNotificationCommand;
import _5bits.organik.platform.communication.domain.model.valueobjects.NotificationTitle;
import _5bits.organik.platform.communication.domain.model.valueobjects.NotificationType;
import _5bits.organik.platform.communication.domain.model.valueobjects.Severity;
import _5bits.organik.platform.inventory.interfaces.events.StockLotLowStockIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Event handler that creates a low stock notification when a stock lot reaches its minimum stock.
 */
@Service("communicationStockLotLowStockEventHandler")
@Slf4j
public class StockLotLowStockEventHandler {
    private final NotificationCommandService notificationCommandService;

    public StockLotLowStockEventHandler(NotificationCommandService notificationCommandService) {
        this.notificationCommandService = notificationCommandService;
    }

    /**
     * Creates a notification when the integration event is received.
     *
     * @param event integration event published by another bounded context
     */
    @EventListener
    public void on(StockLotLowStockIntegrationEvent event) {
        var command = new CreateNotificationCommand(
                new NotificationType("LOW_STOCK"),
                new Severity("WARNING"),
                new NotificationTitle("Low stock in lot %s of product %s: %d units left".formatted(
                        event.lotCode(), event.productId(), event.quantity())));
        var result = notificationCommandService.handle(command);
        if (result instanceof _5bits.organik.platform.shared.application.result.Result.Failure(var error)) {
            log.warn("Failed to create notification for StockLotLowStockIntegrationEvent: {}", error.message());
        }
    }
}
