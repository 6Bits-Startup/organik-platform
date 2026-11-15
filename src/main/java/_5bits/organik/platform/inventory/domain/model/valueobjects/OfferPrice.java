package _5bits.organik.platform.inventory.domain.model.valueobjects;

import java.math.BigDecimal;

public record OfferPrice(BigDecimal value) {
    private static final int MAX_SCALE = 2;
    private static final String NOT_NULL_MESSAGE = "price is required.";
    private static final String POSITIVE_MESSAGE = "price must be greater than zero.";
    private static final String SCALE_MESSAGE = "price must have at most 8 integer digits and 2 decimals.";

    public OfferPrice {
        if (value == null) {
            throw new IllegalArgumentException(NOT_NULL_MESSAGE);
        }
        if (value.signum() <= 0) {
            throw new IllegalArgumentException(POSITIVE_MESSAGE);
        }
        if (value.stripTrailingZeros().scale() > MAX_SCALE) {
            throw new IllegalArgumentException(SCALE_MESSAGE);
        }
    }

}
