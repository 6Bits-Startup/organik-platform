package _5bits.organik.platform.dashboard.domain.model.valueobjects;

/**
 * Snapshot of the operational state of a minimarket shown on the dashboard.
 *
 * @param minimarketId minimarket identifier
 * @param totalStockLots number of stock lots
 * @param lowStockLots stock lots at or below their minimum stock
 * @param expirationWarningDays days of the warning period used to count the lots about to expire
 * @param expiringSoonLots stock lots that expire within the warning period
 * @param pendingShipmentOrders shipment orders waiting for a decision
 * @param pendingSupplyRequests supply requests waiting for an answer
 * @param unreadNotifications notifications not read yet
 */
public record DashboardSummary(
        MinimarketId minimarketId,
        long totalStockLots,
        long lowStockLots,
        int expirationWarningDays,
        long expiringSoonLots,
        long pendingShipmentOrders,
        long pendingSupplyRequests,
        long unreadNotifications) {

    public DashboardSummary {
        if (minimarketId == null) {
            throw new IllegalArgumentException("minimarketId cannot be null");
        }
    }

    /**
     * @return {@code true} when there is something that needs the attention of the administrator
     */
    public boolean requiresAttention() {
        return lowStockLots > 0 || expiringSoonLots > 0 || pendingShipmentOrders > 0 || unreadNotifications > 0;
    }
}
