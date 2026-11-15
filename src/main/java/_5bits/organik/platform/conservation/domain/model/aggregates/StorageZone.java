package _5bits.organik.platform.conservation.domain.model.aggregates;

import _5bits.organik.platform.conservation.domain.model.commands.CreateStorageZoneCommand;
import _5bits.organik.platform.conservation.domain.model.valueobjects.*;
import _5bits.organik.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.util.Objects;

/**
 * Storage zone aggregate root.
 * <p>
 * A physical area of a minimarket (cold room, shelf, dry storage) with the temperature and
 * humidity ranges its products require.
 * </p>
 */
@Getter
public class StorageZone extends AbstractDomainAggregateRoot<StorageZone> {
    private Long id;
    private MinimarketId minimarketId;
    private ZoneName name;
    private StorageCondition storageCondition;
    private TemperatureRange temperatureRange;
    private HumidityRange humidityRange;

    public StorageZone(MinimarketId minimarketId, ZoneName name, StorageCondition storageCondition,
                       TemperatureRange temperatureRange, HumidityRange humidityRange) {
        this.minimarketId = Objects.requireNonNull(minimarketId, "minimarketId must not be null");
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.storageCondition = Objects.requireNonNull(storageCondition, "storageCondition must not be null");
        this.temperatureRange = Objects.requireNonNull(temperatureRange, "temperatureRange must not be null");
        this.humidityRange = Objects.requireNonNull(humidityRange, "humidityRange must not be null");
    }

    public StorageZone(CreateStorageZoneCommand command) {
        this(command.minimarketId(), command.name(), command.condition(), command.temperatureRange(), command.humidityRange());
    }

    public static StorageZone reconstitute(Long id, MinimarketId minimarketId, ZoneName name, StorageCondition storageCondition,
                                           TemperatureRange temperatureRange, HumidityRange humidityRange) {
        var storageZone = new StorageZone(minimarketId, name, storageCondition, temperatureRange, humidityRange);
        storageZone.id = id;
        return storageZone;
    }

    /**
     * @param temperature measured temperature
     * @param humidity measured humidity
     * @return {@code true} when both values are inside the allowed ranges
     */
    public boolean isReadingWithinRange(Temperature temperature, Humidity humidity) {
        return temperatureRange.includes(temperature) && humidityRange.includes(humidity);
    }
}
