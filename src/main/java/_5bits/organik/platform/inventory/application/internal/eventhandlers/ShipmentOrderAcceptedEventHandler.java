package _5bits.organik.platform.inventory.application.internal.eventhandlers;

import _5bits.organik.platform.inventory.application.commandservices.StockLotCommandService;
import _5bits.organik.platform.inventory.domain.model.commands.CreateStockLotCommand;
import _5bits.organik.platform.inventory.domain.model.valueobjects.*;
import _5bits.organik.platform.procurements.interfaces.events.ShipmentOrderAcceptedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Event handler that registers the stock lots of a shipment order once the minimarket accepts it.
 */
@Service("inventoryShipmentOrderAcceptedEventHandler")
@Slf4j
public class ShipmentOrderAcceptedEventHandler {
    private static final int DEFAULT_MINIMUM_STOCK = 0;
    private static final String RECEPTION_LOCATION = "Reception";
    private final StockLotCommandService stockLotCommandService;

    public ShipmentOrderAcceptedEventHandler(StockLotCommandService stockLotCommandService) {
        this.stockLotCommandService = stockLotCommandService;
    }

    @EventListener
    public void on(ShipmentOrderAcceptedIntegrationEvent event) {
        event.lines().forEach(line -> {
            var command = new CreateStockLotCommand(
                    new MinimarketId(event.minimarketId()),
                    new ProductId(line.productId()),
                    new LotCode(line.lotCode()),
                    new Quantity(line.quantity()),
                    new Quantity(DEFAULT_MINIMUM_STOCK),
                    new ExpirationDate(line.expiresOn()),
                    new LotLocation(RECEPTION_LOCATION),
                    new LotNotes("Shipment order %d".formatted(event.shipmentOrderId())));
            var result = stockLotCommandService.handle(command);
            if (result instanceof _5bits.organik.platform.shared.application.result.Result.Failure(var error)) {
                log.warn("Failed to register stock lot {} for shipment order {}: {}",
                        line.lotCode(), event.shipmentOrderId(), error.message());
            }
        });
    }
}
