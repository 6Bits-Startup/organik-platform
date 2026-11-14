package _5bits.organik.platform.inventory.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Request resource used to create an offer for a stock lot.
 */
@Schema(name = "CreateOfferRequest", description = "Request payload for creating an offer")
public record CreateOfferResource(
    @Schema(description = "Stock lot of the offer", example = "1")
    @NotNull(message = "{inventory.error.stockLotId.notNull}")
    @Positive(message = "{inventory.error.stockLotId.min}")
    Long stockLotId,

    @Schema(description = "Offer price", example = "9.90")
    @NotNull(message = "{inventory.error.offerPrice.notNull}")
    @DecimalMin(value = "0.00", inclusive = false, message = "{inventory.error.offerPrice.positive}")
    @Digits(integer = 8, fraction = 2, message = "{inventory.error.offerPrice.scale}")
    BigDecimal price,

    @Schema(description = "First day of the offer", example = "2026-11-15")
    @NotNull(message = "{offer.error.startsOn.notNull}")
    LocalDate startsOn,

    @Schema(description = "Last day of the offer", example = "2026-11-20")
    @NotNull(message = "{offer.error.endsOn.notNull}")
    LocalDate endsOn
) {
    @AssertTrue(message = "{offer.error.period.invalid}")
    @Schema(hidden = true)
    public boolean isPeriodValid() {
        return startsOn == null || endsOn == null || !endsOn.isBefore(startsOn);
    }
}
