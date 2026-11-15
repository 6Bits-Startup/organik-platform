package _5bits.organik.platform.dashboard.application.internal.outboundservices.acl;

import _5bits.organik.platform.inventory.interfaces.acl.InventoryContextFacade;
import org.springframework.stereotype.Service;

/**
 * Outbound service that queries the inventory bounded context through its ACL facade.
 */
@Service("dashboardExternalInventoryService")
public class ExternalInventoryService {
    private final InventoryContextFacade inventoryContextFacade;

    public ExternalInventoryService(InventoryContextFacade inventoryContextFacade) {
        this.inventoryContextFacade = inventoryContextFacade;
    }

    public long countStockLots(String minimarketId) {
        return inventoryContextFacade.countStockLotsByMinimarketId(minimarketId);
    }

    public long countLowStockLots(String minimarketId) {
        return inventoryContextFacade.countLowStockLotsByMinimarketId(minimarketId);
    }

    public long countStockLotsExpiringWithinDays(String minimarketId, int days) {
        return inventoryContextFacade.countStockLotsExpiringWithinDays(minimarketId, days);
    }
}
