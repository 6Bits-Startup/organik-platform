package _5bits.organik.platform.products.domain.model.valueobjects;

public record MinimumStock(Integer value) {
    private static final String NOT_NULL_MESSAGE = "minimumStock is required.";
    private static final String MIN_MESSAGE = "minimumStock must not be negative.";

    public MinimumStock {
        if (value == null) {
            throw new IllegalArgumentException(NOT_NULL_MESSAGE);
        }
        if (value < 0) {
            throw new IllegalArgumentException(MIN_MESSAGE);
        }
    }

}
