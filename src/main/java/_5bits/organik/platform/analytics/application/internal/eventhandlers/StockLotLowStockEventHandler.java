package _5bits.organik.platform.analytics.application.internal.eventhandlers;

import _5bits.organik.platform.analytics.application.commandservices.ActivityRecordCommandService;
import _5bits.organik.platform.analytics.domain.model.commands.RegisterActivityRecordCommand;
import _5bits.organik.platform.analytics.domain.model.valueobjects.ActivityDescription;
import _5bits.organik.platform.analytics.domain.model.valueobjects.ActivityType;
import _5bits.organik.platform.analytics.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.inventory.interfaces.events.StockLotLowStockIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Event handler that records a low stock lot in the activity history.
 */
@Service("analyticsStockLotLowStockEventHandler")
@Slf4j
public class StockLotLowStockEventHandler {
    private final ActivityRecordCommandService activityRecordCommandService;

    public StockLotLowStockEventHandler(ActivityRecordCommandService activityRecordCommandService) {
        this.activityRecordCommandService = activityRecordCommandService;
    }

    /**
     * Registers the activity when the integration event is received.
     *
     * @param event integration event published by another bounded context
     */
    @EventListener
    public void on(StockLotLowStockIntegrationEvent event) {
        var command = new RegisterActivityRecordCommand(
                new MinimarketId(event.minimarketId()),
                ActivityType.LOW_STOCK_DETECTED,
                new ActivityDescription("Lot %s of product %s reached %d units (minimum %d)".formatted(
                        event.lotCode(), event.productId(), event.quantity(), event.minimumStock())));
        var result = activityRecordCommandService.handle(command);
        if (result instanceof _5bits.organik.platform.shared.application.result.Result.Failure(var error)) {
            log.warn("Failed to register activity for StockLotLowStockIntegrationEvent: {}", error.message());
        }
    }
}
