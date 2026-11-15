package _5bits.organik.platform.suppliers.domain.model.commands;

import _5bits.organik.platform.suppliers.domain.model.valueobjects.LotCode;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.Quantity;

import java.time.LocalDate;

/**
 * Command to update a product offered in a supplier catalog.
 *
 * @param offeredProductId offered product identifier
 * @param lotCode new lot code
 * @param availableQuantity new available quantity
 * @param expiresOn new expiration date of the lot
 */
public record UpdateOfferedProductCommand(Long offeredProductId, LotCode lotCode, Quantity availableQuantity, LocalDate expiresOn) {
    public UpdateOfferedProductCommand {
        if (offeredProductId == null || offeredProductId <= 0) {
            throw new IllegalArgumentException("offeredProductId cannot be null or less than 1");
        }
        if (lotCode == null) {
            throw new IllegalArgumentException("LotCode cannot be null");
        }
        if (availableQuantity == null) {
            throw new IllegalArgumentException("Available quantity cannot be null");
        }
        if (expiresOn == null) {
            throw new IllegalArgumentException("Expiration date cannot be null");
        }
    }
}
