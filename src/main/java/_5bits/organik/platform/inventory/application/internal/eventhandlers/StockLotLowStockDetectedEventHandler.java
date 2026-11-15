package _5bits.organik.platform.inventory.application.internal.eventhandlers;

import _5bits.organik.platform.inventory.domain.model.events.StockLotLowStockDetectedEvent;
import _5bits.organik.platform.inventory.interfaces.events.StockLotLowStockIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Event handler that translates the {@link StockLotLowStockDetectedEvent} domain event into the
 * {@link StockLotLowStockIntegrationEvent} integration event consumed by other bounded contexts.
 */
@Service("inventoryStockLotLowStockDetectedEventHandler")
public class StockLotLowStockDetectedEventHandler {
    private final ApplicationEventPublisher eventPublisher;

    public StockLotLowStockDetectedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(StockLotLowStockDetectedEvent event) {
        eventPublisher.publishEvent(new StockLotLowStockIntegrationEvent(
                event.stockLotId(),
                event.minimarketId(),
                event.productId(),
                event.lotCode(),
                event.quantity(),
                event.minimumStock()));
    }
}
