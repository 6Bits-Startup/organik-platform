package _5bits.organik.platform.conservation.domain.model.aggregates;

import _5bits.organik.platform.conservation.domain.model.commands.RecordStorageReadingCommand;
import _5bits.organik.platform.conservation.domain.model.events.StorageReadingOutOfRangeEvent;
import _5bits.organik.platform.conservation.domain.model.valueobjects.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class StorageReadingTest {

    private final StorageZone coldRoom = StorageZone.reconstitute(1L, new ZoneName("Cold room A"), new StorageCondition("REFRIGERATED"),
            new TemperatureRange(new Temperature(2.0), new Temperature(8.0)),
            new HumidityRange(new Humidity(80.0), new Humidity(95.0)));

    private StorageReading readingWith(double temperature) {
        var command = new RecordStorageReadingCommand(new StorageZoneId(1L), new Temperature(temperature), new Humidity(85.0),
                LocalDateTime.of(2026, 11, 14, 9, 30), new ReadingSource("MANUAL"));
        var reading = new StorageReading(command, coldRoom);
        return StorageReading.reconstitute(10L, reading.getStorageZoneId(), reading.getTemperature(), reading.getHumidity(),
                reading.getRecordedAt(), reading.getSource(), reading.getWithinRange());
    }

    @Test
    void readingInsideTheRangesIsWithinRangeAndRaisesNoEvent() {
        var reading = readingWith(5.0);

        reading.onRecorded();

        assertThat(reading.getWithinRange()).isTrue();
        assertThat(reading.domainEvents()).isEmpty();
    }

    @Test
    void readingOutsideTheRangesRaisesOutOfRangeEvent() {
        var reading = readingWith(11.5);

        reading.onRecorded();

        assertThat(reading.getWithinRange()).isFalse();
        var event = (StorageReadingOutOfRangeEvent) reading.domainEvents().iterator().next();
        assertThat(event.storageReadingId()).isEqualTo(10L);
        assertThat(event.temperature()).isEqualTo(11.5);
    }
}
