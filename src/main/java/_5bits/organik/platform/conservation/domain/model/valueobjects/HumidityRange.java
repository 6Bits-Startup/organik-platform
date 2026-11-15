package _5bits.organik.platform.conservation.domain.model.valueobjects;

/**
 * Allowed humidity range of a storage zone.
 *
 * @param min minimum allowed humidity
 * @param max maximum allowed humidity
 */
public record HumidityRange(Humidity min, Humidity max) {
    public HumidityRange {
        if (min == null || max == null) {
            throw new IllegalArgumentException("Minimum and maximum humidity cannot be null");
        }
        if (min.value() > max.value()) {
            throw new IllegalArgumentException("Minimum humidity cannot be greater than maximum humidity");
        }
    }

    /**
     * @param humidity measured humidity
     * @return {@code true} when the value is inside the range
     */
    public boolean includes(Humidity humidity) {
        return humidity.value() >= min.value() && humidity.value() <= max.value();
    }
}
