package _5bits.organik.platform.dashboard.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response resource representing the dashboard summary of a minimarket.
 */
@Schema(name = "DashboardSummaryResponse", description = "Operational summary of a minimarket")
public record DashboardSummaryResource(
    @Schema(description = "Minimarket identifier", example = "mm-vida-verde")
    String minimarketId,
    @Schema(description = "Number of stock lots", example = "12")
    long totalStockLots,
    @Schema(description = "Stock lots at or below their minimum stock", example = "2")
    long lowStockLots,
    @Schema(description = "Days of the warning period used to count the lots about to expire", example = "15")
    int expirationWarningDays,
    @Schema(description = "Stock lots that expire within the warning period", example = "3")
    long expiringSoonLots,
    @Schema(description = "Shipment orders waiting for a decision", example = "1")
    long pendingShipmentOrders,
    @Schema(description = "Supply requests waiting for an answer", example = "2")
    long pendingSupplyRequests,
    @Schema(description = "Notifications not read yet", example = "4")
    long unreadNotifications,
    @Schema(description = "Whether something needs the attention of the administrator", example = "true")
    boolean requiresAttention
) {
}
