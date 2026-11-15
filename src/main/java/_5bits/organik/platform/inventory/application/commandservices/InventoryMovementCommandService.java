package _5bits.organik.platform.inventory.application.commandservices;

import _5bits.organik.platform.inventory.domain.model.commands.RegisterInventoryMovementCommand;
import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;

/**
 * Application service contract for InventoryMovement command operations.
 */
public interface InventoryMovementCommandService {

    /**
     * Handles the registration of an inventory movement and applies it to the stock lot.
     *
     * @param command command object
     * @return the identifier of the affected InventoryMovement, or an application error
     */
    Result<Long, ApplicationError> handle(RegisterInventoryMovementCommand command);
}
