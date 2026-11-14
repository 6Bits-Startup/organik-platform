package _5bits.organik.platform.inventory.domain.model.valueobjects;

public record StockLotId(Long value) {
    private static final long MIN_VALUE = 1L;
    private static final String NOT_NULL_MESSAGE = "stockLotId is required.";
    private static final String MIN_MESSAGE = "stockLotId must be greater than zero.";

    public StockLotId {
        if (value == null) {
            throw new IllegalArgumentException(NOT_NULL_MESSAGE);
        }
        if (value < MIN_VALUE) {
            throw new IllegalArgumentException(MIN_MESSAGE);
        }
    }

}
