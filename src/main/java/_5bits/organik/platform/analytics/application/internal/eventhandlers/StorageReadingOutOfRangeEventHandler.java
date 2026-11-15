package _5bits.organik.platform.analytics.application.internal.eventhandlers;

import _5bits.organik.platform.analytics.application.commandservices.ActivityRecordCommandService;
import _5bits.organik.platform.analytics.domain.model.commands.RegisterActivityRecordCommand;
import _5bits.organik.platform.analytics.domain.model.valueobjects.ActivityDescription;
import _5bits.organik.platform.analytics.domain.model.valueobjects.ActivityType;
import _5bits.organik.platform.analytics.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.conservation.interfaces.events.StorageReadingOutOfRangeIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Event handler that records an out-of-range storage reading in the activity history.
 */
@Service("analyticsStorageReadingOutOfRangeEventHandler")
@Slf4j
public class StorageReadingOutOfRangeEventHandler {
    private final ActivityRecordCommandService activityRecordCommandService;

    public StorageReadingOutOfRangeEventHandler(ActivityRecordCommandService activityRecordCommandService) {
        this.activityRecordCommandService = activityRecordCommandService;
    }

    /**
     * Registers the activity when the integration event is received.
     *
     * @param event integration event published by another bounded context
     */
    @EventListener
    public void on(StorageReadingOutOfRangeIntegrationEvent event) {
        var command = new RegisterActivityRecordCommand(
                new MinimarketId(event.minimarketId()),
                ActivityType.STORAGE_READING_OUT_OF_RANGE,
                new ActivityDescription("Storage zone %d recorded %.1f °C and %.1f %% at %s".formatted(
                        event.storageZoneId(), event.temperature(), event.humidity(), event.recordedAt())));
        var result = activityRecordCommandService.handle(command);
        if (result instanceof _5bits.organik.platform.shared.application.result.Result.Failure(var error)) {
            log.warn("Failed to register activity for StorageReadingOutOfRangeIntegrationEvent: {}", error.message());
        }
    }
}
