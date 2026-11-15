package _5bits.organik.platform.conservation.application.internal.eventhandlers;

import _5bits.organik.platform.conservation.domain.model.events.StorageReadingOutOfRangeEvent;
import _5bits.organik.platform.conservation.interfaces.events.StorageReadingOutOfRangeIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Event handler that translates the {@link StorageReadingOutOfRangeEvent} domain event into the
 * {@link StorageReadingOutOfRangeIntegrationEvent} integration event consumed by other bounded contexts.
 */
@Service("conservationStorageReadingOutOfRangeEventHandler")
public class StorageReadingOutOfRangeEventHandler {
    private final ApplicationEventPublisher eventPublisher;

    public StorageReadingOutOfRangeEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(StorageReadingOutOfRangeEvent event) {
        eventPublisher.publishEvent(new StorageReadingOutOfRangeIntegrationEvent(
                event.storageReadingId(),
                event.storageZoneId(),
                event.minimarketId(),
                event.temperature(),
                event.humidity(),
                event.recordedAt()));
    }
}
