package _5bits.organik.platform.inventory.domain.model.commands;

import _5bits.organik.platform.inventory.domain.model.valueobjects.ExpirationDate;
import _5bits.organik.platform.inventory.domain.model.valueobjects.LotLocation;
import _5bits.organik.platform.inventory.domain.model.valueobjects.LotNotes;

/**
 * Command to update the information of a stock lot that does not change its stock.
 *
 * @param stockLotId stock lot identifier
 * @param location new location of the lot
 * @param notes new notes of the lot
 * @param expirationDate new expiration date of the lot
 */
public record UpdateStockLotCommand(Long stockLotId, LotLocation location, LotNotes notes, ExpirationDate expirationDate) {
    public UpdateStockLotCommand {
        if (stockLotId == null || stockLotId <= 0) {
            throw new IllegalArgumentException("stockLotId cannot be null or less than 1");
        }
        if (location == null) {
            throw new IllegalArgumentException("LotLocation cannot be null");
        }
        if (notes == null) {
            throw new IllegalArgumentException("LotNotes cannot be null");
        }
        if (expirationDate == null) {
            throw new IllegalArgumentException("ExpirationDate cannot be null");
        }
    }
}
