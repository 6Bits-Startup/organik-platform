package _5bits.organik.platform.communication.application.internal.eventhandlers;

import _5bits.organik.platform.communication.application.commandservices.NotificationCommandService;
import _5bits.organik.platform.communication.domain.model.commands.CreateNotificationCommand;
import _5bits.organik.platform.communication.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.communication.domain.model.valueobjects.NotificationTitle;
import _5bits.organik.platform.communication.domain.model.valueobjects.NotificationType;
import _5bits.organik.platform.communication.domain.model.valueobjects.Severity;
import _5bits.organik.platform.procurements.interfaces.events.ShipmentOrderAcceptedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Event handler that creates an order notification when a shipment order is accepted.
 */
@Service("communicationShipmentOrderAcceptedEventHandler")
@Slf4j
public class ShipmentOrderAcceptedEventHandler {
    private final NotificationCommandService notificationCommandService;

    public ShipmentOrderAcceptedEventHandler(NotificationCommandService notificationCommandService) {
        this.notificationCommandService = notificationCommandService;
    }

    /**
     * Creates a notification when the integration event is received.
     *
     * @param event integration event published by another bounded context
     */
    @EventListener
    public void on(ShipmentOrderAcceptedIntegrationEvent event) {
        var command = new CreateNotificationCommand(
                new MinimarketId(event.minimarketId()),
                new NotificationType("ORDER"),
                new Severity("INFO"),
                new NotificationTitle("Shipment order %d from supplier %s was accepted".formatted(
                        event.shipmentOrderId(), event.supplierId())));
        var result = notificationCommandService.handle(command);
        if (result instanceof _5bits.organik.platform.shared.application.result.Result.Failure(var error)) {
            log.warn("Failed to create notification for ShipmentOrderAcceptedIntegrationEvent: {}", error.message());
        }
    }
}
