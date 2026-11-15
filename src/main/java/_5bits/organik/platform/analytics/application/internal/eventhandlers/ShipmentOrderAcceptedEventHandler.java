package _5bits.organik.platform.analytics.application.internal.eventhandlers;

import _5bits.organik.platform.analytics.application.commandservices.ActivityRecordCommandService;
import _5bits.organik.platform.analytics.domain.model.commands.RegisterActivityRecordCommand;
import _5bits.organik.platform.analytics.domain.model.valueobjects.ActivityDescription;
import _5bits.organik.platform.analytics.domain.model.valueobjects.ActivityType;
import _5bits.organik.platform.analytics.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.procurements.interfaces.events.ShipmentOrderAcceptedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Event handler that records the acceptance of a shipment order in the activity history.
 */
@Service("analyticsShipmentOrderAcceptedEventHandler")
@Slf4j
public class ShipmentOrderAcceptedEventHandler {
    private final ActivityRecordCommandService activityRecordCommandService;

    public ShipmentOrderAcceptedEventHandler(ActivityRecordCommandService activityRecordCommandService) {
        this.activityRecordCommandService = activityRecordCommandService;
    }

    /**
     * Registers the activity when the integration event is received.
     *
     * @param event integration event published by another bounded context
     */
    @EventListener
    public void on(ShipmentOrderAcceptedIntegrationEvent event) {
        var command = new RegisterActivityRecordCommand(
                new MinimarketId(event.minimarketId()),
                ActivityType.SHIPMENT_ORDER_ACCEPTED,
                new ActivityDescription("Shipment order %d from supplier %s accepted with %d lot(s)".formatted(
                        event.shipmentOrderId(), event.supplierId(), event.lines().size())));
        var result = activityRecordCommandService.handle(command);
        if (result instanceof _5bits.organik.platform.shared.application.result.Result.Failure(var error)) {
            log.warn("Failed to register activity for ShipmentOrderAcceptedIntegrationEvent: {}", error.message());
        }
    }
}
