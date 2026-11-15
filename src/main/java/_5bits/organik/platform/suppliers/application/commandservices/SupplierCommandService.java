package _5bits.organik.platform.suppliers.application.commandservices;

import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;
import _5bits.organik.platform.suppliers.domain.model.commands.CreateSupplierCommand;

/**
 * Application service contract for Supplier command operations.
 */
public interface SupplierCommandService {

    /**
     * Handles the registration of a supplier.
     *
     * @param command command object
     * @return the identifier of the affected Supplier, or an application error
     */
    Result<Long, ApplicationError> handle(CreateSupplierCommand command);
}
