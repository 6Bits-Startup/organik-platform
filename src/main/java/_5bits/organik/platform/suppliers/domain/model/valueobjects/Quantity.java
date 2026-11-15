package _5bits.organik.platform.suppliers.domain.model.valueobjects;

public record Quantity(Integer value) {
    private static final int MIN_VALUE = 0;
    private static final String NOT_NULL_MESSAGE = "availableQuantity is required.";
    private static final String MIN_MESSAGE = "availableQuantity must not be negative.";

    public Quantity {
        if (value == null) {
            throw new IllegalArgumentException(NOT_NULL_MESSAGE);
        }
        if (value < MIN_VALUE) {
            throw new IllegalArgumentException(MIN_MESSAGE);
        }
    }

}
