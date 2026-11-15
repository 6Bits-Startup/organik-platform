package _5bits.organik.platform.inventory.domain.model.valueobjects;

/**
 * Quantity of units of a stock lot or an inventory movement.
 *
 * @param value number of units, zero or greater
 */
public record Quantity(Integer value) {
    private static final int MIN_VALUE = 0;
    private static final String NOT_NULL_MESSAGE = "quantity is required.";
    private static final String MIN_MESSAGE = "quantity must not be negative.";

    public Quantity {
        if (value == null) {
            throw new IllegalArgumentException(NOT_NULL_MESSAGE);
        }
        if (value < MIN_VALUE) {
            throw new IllegalArgumentException(MIN_MESSAGE);
        }
    }

    public boolean isPositive() {
        return value > MIN_VALUE;
    }

    public boolean isLessThanOrEqualTo(Quantity other) {
        return value <= other.value();
    }

    /**
     * @param other quantity to add
     * @return a new quantity with the sum of both values
     */
    public Quantity plus(Quantity other) {
        return new Quantity(value + other.value());
    }

    /**
     * @param other quantity to subtract
     * @return a new quantity with the difference of both values
     */
    public Quantity minus(Quantity other) {
        return new Quantity(value - other.value());
    }
}
