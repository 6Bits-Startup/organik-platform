package _5bits.organik.platform.procurements.domain.model.valueobjects;

public record UserId(Long value) {
    private static final long MIN_VALUE = 1;
    private static final String NOT_NULL_MESSAGE = "userId is required.";
    private static final String MIN_MESSAGE = "userId must be greater than 0.";

    public UserId {
        if (value == null) {
            throw new IllegalArgumentException(NOT_NULL_MESSAGE);
        }
        if (value < MIN_VALUE) {
            throw new IllegalArgumentException(MIN_MESSAGE);
        }
    }

}
