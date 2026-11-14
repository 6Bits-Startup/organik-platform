package _5bits.organik.platform.inventory.interfaces.rest.transform;

import _5bits.organik.platform.inventory.domain.model.aggregates.StockLot;
import _5bits.organik.platform.inventory.interfaces.rest.resources.StockLotResource;


/**
 * Assembler that converts a {@link StockLot} aggregate into a {@link StockLotResource}.
 */
public class StockLotResourceFromEntityAssembler {
    public static StockLotResource toResourceFromEntity(StockLot entity) {
        return new StockLotResource(
                entity.getId(),
                entity.getMinimarketId().value(),
                entity.getProductId().value(),
                entity.getLotCode().value(),
                entity.getQuantity().value(),
                entity.getMinimumStock().value(),
                entity.getExpirationDate().value(),
                entity.isLowStock());
    }
}
