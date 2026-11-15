package _5bits.organik.platform.conservation.domain.model.events;

import java.time.LocalDateTime;

/**
 * Domain event raised when a storage reading is outside the allowed ranges of its zone.
 *
 * @param storageReadingId storage reading identifier
 * @param storageZoneId storage zone identifier
 * @param minimarketId minimarket that owns the storage zone
 * @param temperature measured temperature
 * @param humidity measured humidity
 * @param recordedAt date and time of the reading
 */
public record StorageReadingOutOfRangeEvent(
        Long storageReadingId,
        Long storageZoneId,
        String minimarketId,
        Double temperature,
        Double humidity,
        LocalDateTime recordedAt) {
}
