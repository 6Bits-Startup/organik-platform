package _5bits.organik.platform.conservation.domain.model.valueobjects;

/**
 * Allowed temperature range of a storage zone.
 *
 * @param min minimum allowed temperature
 * @param max maximum allowed temperature
 */
public record TemperatureRange(Temperature min, Temperature max) {
    public TemperatureRange {
        if (min == null || max == null) {
            throw new IllegalArgumentException("Minimum and maximum temperature cannot be null");
        }
        if (min.value() > max.value()) {
            throw new IllegalArgumentException("Minimum temperature cannot be greater than maximum temperature");
        }
    }

    /**
     * @param temperature measured temperature
     * @return {@code true} when the value is inside the range
     */
    public boolean includes(Temperature temperature) {
        return temperature.value() >= min.value() && temperature.value() <= max.value();
    }
}
