package _5bits.organik.platform.communication.application.internal.eventhandlers;

import _5bits.organik.platform.communication.application.commandservices.NotificationCommandService;
import _5bits.organik.platform.communication.domain.model.commands.CreateNotificationCommand;
import _5bits.organik.platform.communication.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.communication.domain.model.valueobjects.NotificationTitle;
import _5bits.organik.platform.communication.domain.model.valueobjects.NotificationType;
import _5bits.organik.platform.communication.domain.model.valueobjects.Severity;
import _5bits.organik.platform.conservation.interfaces.events.StorageReadingOutOfRangeIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Event handler that creates a critical conservation notification when a storage reading is out of range.
 */
@Service("communicationStorageReadingOutOfRangeEventHandler")
@Slf4j
public class StorageReadingOutOfRangeEventHandler {
    private final NotificationCommandService notificationCommandService;

    public StorageReadingOutOfRangeEventHandler(NotificationCommandService notificationCommandService) {
        this.notificationCommandService = notificationCommandService;
    }

    /**
     * Creates a notification when the integration event is received.
     *
     * @param event integration event published by another bounded context
     */
    @EventListener
    public void on(StorageReadingOutOfRangeIntegrationEvent event) {
        var command = new CreateNotificationCommand(
                new MinimarketId(event.minimarketId()),
                new NotificationType("CONSERVATION"),
                new Severity("CRITICAL"),
                new NotificationTitle("Storage zone %d reading out of range: %.1f °C, %.1f %%".formatted(
                        event.storageZoneId(), event.temperature(), event.humidity())));
        var result = notificationCommandService.handle(command);
        if (result instanceof _5bits.organik.platform.shared.application.result.Result.Failure(var error)) {
            log.warn("Failed to create notification for StorageReadingOutOfRangeIntegrationEvent: {}", error.message());
        }
    }
}
