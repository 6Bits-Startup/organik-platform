package _5bits.organik.platform.inventory.interfaces.rest.transform;

import _5bits.organik.platform.inventory.domain.model.commands.RegisterInventoryMovementCommand;
import _5bits.organik.platform.inventory.interfaces.rest.resources.RegisterInventoryMovementResource;
import _5bits.organik.platform.inventory.domain.model.valueobjects.MovementReason;
import _5bits.organik.platform.inventory.domain.model.valueobjects.MovementType;
import _5bits.organik.platform.inventory.domain.model.valueobjects.Quantity;
import _5bits.organik.platform.inventory.domain.model.valueobjects.StockLotId;

/**
 * Assembler that converts a {@link RegisterInventoryMovementResource} into a {@link RegisterInventoryMovementCommand}.
 */
public class RegisterInventoryMovementCommandFromResourceAssembler {
    public static RegisterInventoryMovementCommand toCommandFromResource(RegisterInventoryMovementResource resource) {
        return new RegisterInventoryMovementCommand(
                new StockLotId(resource.stockLotId()),
                MovementType.valueOf(resource.type()),
                new Quantity(resource.quantity()),
                resource.reason() == null || resource.reason().isBlank() ? null : new MovementReason(resource.reason()));
    }
}
