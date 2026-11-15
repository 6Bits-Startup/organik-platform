package _5bits.organik.platform.analytics.application.internal.outboundservices.acl;

import _5bits.organik.platform.inventory.interfaces.acl.InventoryContextFacade;
import org.springframework.stereotype.Service;

/**
 * Outbound service that queries the inventory bounded context through its ACL facade.
 */
@Service("analyticsExternalInventoryService")
public class ExternalInventoryService {
    private final InventoryContextFacade inventoryContextFacade;

    public ExternalInventoryService(InventoryContextFacade inventoryContextFacade) {
        this.inventoryContextFacade = inventoryContextFacade;
    }

    public long countExpiredStockLots(String minimarketId) {
        return inventoryContextFacade.countExpiredStockLotsByMinimarketId(minimarketId);
    }

    public long countStockLotsExpiringWithinDays(String minimarketId, int days) {
        return inventoryContextFacade.countStockLotsExpiringWithinDays(minimarketId, days);
    }

    public long sumWasteUnits(String minimarketId) {
        return inventoryContextFacade.sumWasteUnitsByMinimarketId(minimarketId);
    }
}
