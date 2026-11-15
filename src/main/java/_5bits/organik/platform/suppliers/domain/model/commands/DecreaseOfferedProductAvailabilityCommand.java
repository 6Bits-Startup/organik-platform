package _5bits.organik.platform.suppliers.domain.model.commands;

import _5bits.organik.platform.suppliers.domain.model.valueobjects.LotCode;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.Quantity;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.SupplierId;

/**
 * Command to decrease the availability of an offered product after a shipment order is accepted.
 *
 * @param supplierId supplier that offers the lot
 * @param lotCode lot code of the offered product
 * @param shippedQuantity units shipped to the minimarket
 */
public record DecreaseOfferedProductAvailabilityCommand(SupplierId supplierId, LotCode lotCode, Quantity shippedQuantity) {
    public DecreaseOfferedProductAvailabilityCommand {
        if (supplierId == null) {
            throw new IllegalArgumentException("SupplierId cannot be null");
        }
        if (lotCode == null) {
            throw new IllegalArgumentException("LotCode cannot be null");
        }
        if (shippedQuantity == null) {
            throw new IllegalArgumentException("Shipped quantity cannot be null");
        }
    }
}
