package _5bits.organik.platform.conservation.interfaces.events;

import java.time.LocalDateTime;

/**
 * Integration event published by the conservation bounded context when a storage reading is out of range.
 *
 * @param storageReadingId storage reading identifier
 * @param storageZoneId storage zone identifier
 * @param minimarketId minimarket that owns the storage zone
 * @param temperature measured temperature
 * @param humidity measured humidity
 * @param recordedAt date and time of the reading
 */
public record StorageReadingOutOfRangeIntegrationEvent(
        Long storageReadingId,
        Long storageZoneId,
        String minimarketId,
        Double temperature,
        Double humidity,
        LocalDateTime recordedAt) {
}
