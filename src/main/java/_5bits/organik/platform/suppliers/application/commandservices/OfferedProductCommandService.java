package _5bits.organik.platform.suppliers.application.commandservices;

import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;
import _5bits.organik.platform.suppliers.domain.model.commands.CreateOfferedProductCommand;

/**
 * Application service contract for OfferedProduct command operations.
 */
public interface OfferedProductCommandService {

    /**
     * Handles the publication of an offered product in a supplier catalog.
     *
     * @param command command object
     * @return the identifier of the affected OfferedProduct, or an application error
     */
    Result<Long, ApplicationError> handle(CreateOfferedProductCommand command);
}
