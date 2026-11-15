package _5bits.organik.platform.suppliers.application.internal.eventhandlers;

import _5bits.organik.platform.procurements.interfaces.events.ShipmentOrderAcceptedIntegrationEvent;
import _5bits.organik.platform.shared.application.result.Result;
import _5bits.organik.platform.suppliers.application.commandservices.OfferedProductCommandService;
import _5bits.organik.platform.suppliers.domain.model.commands.DecreaseOfferedProductAvailabilityCommand;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.LotCode;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.Quantity;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.SupplierId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Event handler that decreases the availability of the supplier catalog once a minimarket accepts a shipment order.
 */
@Service("suppliersShipmentOrderAcceptedEventHandler")
@Slf4j
public class ShipmentOrderAcceptedEventHandler {
    private final OfferedProductCommandService offeredProductCommandService;

    public ShipmentOrderAcceptedEventHandler(OfferedProductCommandService offeredProductCommandService) {
        this.offeredProductCommandService = offeredProductCommandService;
    }

    @EventListener
    public void on(ShipmentOrderAcceptedIntegrationEvent event) {
        event.lines().forEach(line -> {
            var command = new DecreaseOfferedProductAvailabilityCommand(
                    new SupplierId(event.supplierId()),
                    new LotCode(line.lotCode()),
                    new Quantity(line.quantity()));
            var result = offeredProductCommandService.handle(command);
            if (result instanceof Result.Failure(var error)) {
                log.warn("Failed to decrease the availability of lot {} for shipment order {}: {}",
                        line.lotCode(), event.shipmentOrderId(), error.message());
            }
        });
    }
}
