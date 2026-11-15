package _5bits.organik.platform.requisition.application.commandservices;

import _5bits.organik.platform.requisition.domain.model.commands.AcceptSupplyRequestCommand;
import _5bits.organik.platform.requisition.domain.model.commands.CreateSupplyRequestCommand;
import _5bits.organik.platform.requisition.domain.model.commands.RejectSupplyRequestCommand;
import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;

/**
 * Application service contract for SupplyRequest command operations.
 */
public interface SupplyRequestCommandService {

    /**
     * Handles the creation of a supply request.
     *
     * @param command command object
     * @return the identifier of the affected SupplyRequest, or an application error
     */
    Result<Long, ApplicationError> handle(CreateSupplyRequestCommand command);

    /**
     * Handles the acceptance of a pending supply request by the supplier.
     *
     * @param command command object
     * @return the identifier of the affected SupplyRequest, or an application error
     */
    Result<Long, ApplicationError> handle(AcceptSupplyRequestCommand command);

    /**
     * Handles the rejection of a pending supply request by the supplier.
     *
     * @param command command object
     * @return the identifier of the affected SupplyRequest, or an application error
     */
    Result<Long, ApplicationError> handle(RejectSupplyRequestCommand command);
}
