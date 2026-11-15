package _5bits.organik.platform.inventory.application.commandservices;

import _5bits.organik.platform.inventory.domain.model.commands.CreateStockLotCommand;
import _5bits.organik.platform.inventory.domain.model.commands.UpdateStockLotCommand;
import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;

/**
 * Application service contract for StockLot command operations.
 */
public interface StockLotCommandService {

    /**
     * Handles the registration of a stock lot.
     *
     * @param command command object
     * @return the identifier of the affected StockLot, or an application error
     */
    Result<Long, ApplicationError> handle(CreateStockLotCommand command);

    /**
     * Handles the update of the information of a stock lot.
     *
     * @param command command object
     * @return the identifier of the affected StockLot, or an application error
     */
    Result<Long, ApplicationError> handle(UpdateStockLotCommand command);
}
