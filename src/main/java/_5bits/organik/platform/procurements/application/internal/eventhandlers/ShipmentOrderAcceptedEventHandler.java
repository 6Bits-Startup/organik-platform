package _5bits.organik.platform.procurements.application.internal.eventhandlers;

import _5bits.organik.platform.procurements.domain.model.events.ShipmentOrderAcceptedEvent;
import _5bits.organik.platform.procurements.interfaces.events.ShipmentOrderAcceptedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Event handler that translates the {@link ShipmentOrderAcceptedEvent} domain event into the
 * {@link ShipmentOrderAcceptedIntegrationEvent} integration event consumed by other bounded contexts.
 */
@Service("procurementsShipmentOrderAcceptedEventHandler")
public class ShipmentOrderAcceptedEventHandler {
    private final ApplicationEventPublisher eventPublisher;

    public ShipmentOrderAcceptedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(ShipmentOrderAcceptedEvent event) {
        eventPublisher.publishEvent(new ShipmentOrderAcceptedIntegrationEvent(
                event.shipmentOrderId(),
                event.supplierId(),
                event.minimarketId(),
                event.lines().stream()
                        .map(line -> new ShipmentOrderAcceptedIntegrationEvent.Line(
                                line.productId(), line.quantity(), line.lotCode(), line.expiresOn()))
                        .toList()));
    }
}
