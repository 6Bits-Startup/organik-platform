package _5bits.organik.platform.inventory.domain.model.events;

/**
 * Domain event raised when a stock lot reaches or goes below its minimum stock.
 *
 * @param stockLotId stock lot identifier
 * @param minimarketId minimarket that owns the lot
 * @param productId product of the lot
 * @param lotCode lot code
 * @param quantity remaining quantity
 * @param minimumStock minimum stock of the lot
 */
public record StockLotLowStockDetectedEvent(
        Long stockLotId,
        String minimarketId,
        String productId,
        String lotCode,
        Integer quantity,
        Integer minimumStock) {
}
