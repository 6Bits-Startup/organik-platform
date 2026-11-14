package _5bits.organik.platform.inventory.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Response resource representing an offer.
 */
@Schema(name = "OfferResponse", description = "Offer information response")
public record OfferResource(
    @Schema(description = "Offer unique identifier", example = "1")
    Long id,
    @Schema(description = "Stock lot of the offer", example = "1")
    Long stockLotId,
    @Schema(description = "Offer price", example = "9.90")
    BigDecimal price,
    @Schema(description = "First day of the offer", example = "2026-11-15")
    LocalDate startsOn,
    @Schema(description = "Last day of the offer", example = "2026-11-20")
    LocalDate endsOn,
    @Schema(description = "Whether the offer is active today", example = "true")
    boolean active
) {
}
