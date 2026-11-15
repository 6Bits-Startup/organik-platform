package _5bits.organik.platform.inventory.interfaces.rest.transform;

import _5bits.organik.platform.inventory.domain.model.commands.CreateStockLotCommand;
import _5bits.organik.platform.inventory.interfaces.rest.resources.CreateStockLotResource;
import _5bits.organik.platform.inventory.domain.model.valueobjects.*;

/**
 * Assembler that converts a {@link CreateStockLotResource} into a {@link CreateStockLotCommand}.
 */
public class CreateStockLotCommandFromResourceAssembler {
    public static CreateStockLotCommand toCommandFromResource(CreateStockLotResource resource) {
        return new CreateStockLotCommand(
                new MinimarketId(resource.minimarketId()),
                new ProductId(resource.productId()),
                new LotCode(resource.lotCode()),
                new Quantity(resource.quantity()),
                new Quantity(resource.minimumStock()),
                new ExpirationDate(resource.expirationDate()),
                new LotLocation(resource.location()),
                new LotNotes(resource.notes() == null ? "" : resource.notes()));
    }
}
