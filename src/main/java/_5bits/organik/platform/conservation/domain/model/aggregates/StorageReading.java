package _5bits.organik.platform.conservation.domain.model.aggregates;

import _5bits.organik.platform.conservation.domain.model.commands.RecordStorageReadingCommand;
import _5bits.organik.platform.conservation.domain.model.events.StorageReadingOutOfRangeEvent;
import _5bits.organik.platform.conservation.domain.model.valueobjects.Humidity;
import _5bits.organik.platform.conservation.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.conservation.domain.model.valueobjects.ReadingSource;
import _5bits.organik.platform.conservation.domain.model.valueobjects.StorageZoneId;
import _5bits.organik.platform.conservation.domain.model.valueobjects.Temperature;
import _5bits.organik.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Storage reading aggregate root.
 * <p>
 * A temperature and humidity measurement taken in a storage zone of a minimarket, by a sensor or manually.
 * </p>
 */
@Getter
public class StorageReading extends AbstractDomainAggregateRoot<StorageReading> {
    private Long id;
    private StorageZoneId storageZoneId;
    private MinimarketId minimarketId;
    private Temperature temperature;
    private Humidity humidity;
    private LocalDateTime recordedAt;
    private ReadingSource source;
    private Boolean withinRange;

    public StorageReading(StorageZoneId storageZoneId, MinimarketId minimarketId, Temperature temperature, Humidity humidity,
                          LocalDateTime recordedAt, ReadingSource source, Boolean withinRange) {
        this.storageZoneId = Objects.requireNonNull(storageZoneId, "storageZoneId must not be null");
        this.minimarketId = Objects.requireNonNull(minimarketId, "minimarketId must not be null");
        this.temperature = Objects.requireNonNull(temperature, "temperature must not be null");
        this.humidity = Objects.requireNonNull(humidity, "humidity must not be null");
        this.recordedAt = Objects.requireNonNull(recordedAt, "recordedAt must not be null");
        this.source = Objects.requireNonNull(source, "source must not be null");
        this.withinRange = Objects.requireNonNull(withinRange, "withinRange must not be null");
    }

    /**
     * Creates a reading evaluated against the ranges of its storage zone.
     *
     * @param command command with the reading data
     * @param storageZone storage zone where the reading was taken
     */
    public StorageReading(RecordStorageReadingCommand command, StorageZone storageZone) {
        this(command.storageZoneId(), storageZone.getMinimarketId(), command.temperature(), command.humidity(), command.recordedAt(), command.source(),
                storageZone.isReadingWithinRange(command.temperature(), command.humidity()));
    }

    public static StorageReading reconstitute(Long id, StorageZoneId storageZoneId, MinimarketId minimarketId, Temperature temperature,
                                              Humidity humidity, LocalDateTime recordedAt, ReadingSource source, Boolean withinRange) {
        var storageReading = new StorageReading(storageZoneId, minimarketId, temperature, humidity, recordedAt, source, withinRange);
        storageReading.id = id;
        return storageReading;
    }

    /**
     * Registers the {@link StorageReadingOutOfRangeEvent} when the persisted reading is out of range.
     */
    public void onRecorded() {
        if (!Boolean.TRUE.equals(withinRange)) {
            registerDomainEvent(new StorageReadingOutOfRangeEvent(
                    id, storageZoneId.value(), minimarketId.value(), temperature.value(), humidity.value(), recordedAt));
        }
    }
}
