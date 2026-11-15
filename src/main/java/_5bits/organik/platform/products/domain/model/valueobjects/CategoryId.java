package _5bits.organik.platform.products.domain.model.valueobjects;

public record CategoryId(Long value) {
    private static final String NOT_NULL_MESSAGE = "categoryId is required.";
    private static final String INVALID_MESSAGE = "categoryId must be greater than 0.";

    public CategoryId {
        if (value == null) {
            throw new IllegalArgumentException(NOT_NULL_MESSAGE);
        }
        if (value <= 0) {
            throw new IllegalArgumentException(INVALID_MESSAGE);
        }
    }

}
