package _5bits.organik.platform.inventory.interfaces.acl;

/**
 * ACL facade that exposes inventory indicators to other bounded contexts.
 *
 * <p>Provides a simplified integration surface based on primitive values, without leaking
 * the internal model of the bounded context.</p>
 */
public interface InventoryContextFacade {

    /**
     * Counts the stock lots of a minimarket.
     *
     * @param minimarketId minimarket identifier
     * @return number of stock lots
     */
    long countStockLotsByMinimarketId(String minimarketId);

    /**
     * Counts the stock lots of a minimarket that are at or below their minimum stock.
     *
     * @param minimarketId minimarket identifier
     * @return number of low stock lots
     */
    long countLowStockLotsByMinimarketId(String minimarketId);

    /**
     * Counts the stock lots of a minimarket that expire from today until the given number of days.
     *
     * @param minimarketId minimarket identifier
     * @param days number of days from today
     * @return number of stock lots about to expire
     */
    long countStockLotsExpiringWithinDays(String minimarketId, int days);

    /**
     * Counts the stock lots of a minimarket that are already expired.
     *
     * @param minimarketId minimarket identifier
     * @return number of expired stock lots
     */
    long countExpiredStockLotsByMinimarketId(String minimarketId);

    /**
     * Sums the units registered as waste in the stock lots of a minimarket.
     *
     * @param minimarketId minimarket identifier
     * @return wasted units
     */
    long sumWasteUnitsByMinimarketId(String minimarketId);
}
