package _5bits.organik.platform.inventory.interfaces.rest.transform;

import _5bits.organik.platform.inventory.domain.model.commands.UpdateStockLotCommand;
import _5bits.organik.platform.inventory.domain.model.valueobjects.ExpirationDate;
import _5bits.organik.platform.inventory.domain.model.valueobjects.LotLocation;
import _5bits.organik.platform.inventory.domain.model.valueobjects.LotNotes;
import _5bits.organik.platform.inventory.interfaces.rest.resources.UpdateStockLotResource;

/**
 * Assembler that converts an {@link UpdateStockLotResource} into an {@link UpdateStockLotCommand}.
 */
public class UpdateStockLotCommandFromResourceAssembler {
    public static UpdateStockLotCommand toCommandFromResource(Long stockLotId, UpdateStockLotResource resource) {
        return new UpdateStockLotCommand(
                stockLotId,
                new LotLocation(resource.location()),
                new LotNotes(resource.notes() == null ? "" : resource.notes()),
                new ExpirationDate(resource.expirationDate()));
    }
}
