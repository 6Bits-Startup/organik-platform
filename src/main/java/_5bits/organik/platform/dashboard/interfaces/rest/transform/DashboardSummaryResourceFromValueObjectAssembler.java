package _5bits.organik.platform.dashboard.interfaces.rest.transform;

import _5bits.organik.platform.dashboard.domain.model.valueobjects.DashboardSummary;
import _5bits.organik.platform.dashboard.interfaces.rest.resources.DashboardSummaryResource;

/**
 * Assembler that converts a {@link DashboardSummary} into a {@link DashboardSummaryResource}.
 */
public class DashboardSummaryResourceFromValueObjectAssembler {
    public static DashboardSummaryResource toResourceFromValueObject(DashboardSummary summary) {
        return new DashboardSummaryResource(
                summary.minimarketId().value(),
                summary.totalStockLots(),
                summary.lowStockLots(),
                summary.expiringSoonLots(),
                summary.pendingShipmentOrders(),
                summary.pendingSupplyRequests(),
                summary.unreadNotifications(),
                summary.requiresAttention());
    }
}
