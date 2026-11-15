package _5bits.organik.platform.procurements.domain.model.valueobjects;

public record Quantity(Integer value) {
    private static final int MIN_VALUE = 1;
    private static final String NOT_NULL_MESSAGE = "quantity is required.";
    private static final String MIN_MESSAGE = "quantity must be greater than zero.";

    public Quantity {
        if (value == null) {
            throw new IllegalArgumentException(NOT_NULL_MESSAGE);
        }
        if (value < MIN_VALUE) {
            throw new IllegalArgumentException(MIN_MESSAGE);
        }
    }

}
