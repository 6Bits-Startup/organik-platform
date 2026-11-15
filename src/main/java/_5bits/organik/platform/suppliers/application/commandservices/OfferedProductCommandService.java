package _5bits.organik.platform.suppliers.application.commandservices;

import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;
import _5bits.organik.platform.suppliers.domain.model.commands.CreateOfferedProductCommand;
import _5bits.organik.platform.suppliers.domain.model.commands.DecreaseOfferedProductAvailabilityCommand;
import _5bits.organik.platform.suppliers.domain.model.commands.UpdateOfferedProductCommand;

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

    /**
     * Handles the update of an offered product.
     *
     * @param command command object
     * @return the identifier of the affected OfferedProduct, or an application error
     */
    Result<Long, ApplicationError> handle(UpdateOfferedProductCommand command);

    /**
     * Handles the decrease of the availability of an offered product after a shipment order is accepted.
     *
     * @param command command object
     * @return the identifier of the affected OfferedProduct, or an application error
     */
    Result<Long, ApplicationError> handle(DecreaseOfferedProductAvailabilityCommand command);
}
