package _5bits.organik.platform.procurements.application.commandservices;

import _5bits.organik.platform.procurements.domain.model.commands.AcceptShipmentOrderCommand;
import _5bits.organik.platform.procurements.domain.model.commands.CreateShipmentOrderCommand;
import _5bits.organik.platform.procurements.domain.model.commands.RejectShipmentOrderCommand;
import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;

/**
 * Application service contract for ShipmentOrder command operations.
 */
public interface ShipmentOrderCommandService {

    /**
     * Handles the creation of a shipment order.
     *
     * @param command command object
     * @return the identifier of the affected ShipmentOrder, or an application error
     */
    Result<Long, ApplicationError> handle(CreateShipmentOrderCommand command);

    /**
     * Handles the acceptance of a pending shipment order.
     *
     * @param command command object
     * @return the identifier of the affected ShipmentOrder, or an application error
     */
    Result<Long, ApplicationError> handle(AcceptShipmentOrderCommand command);

    /**
     * Handles the rejection of a pending shipment order.
     *
     * @param command command object
     * @return the identifier of the affected ShipmentOrder, or an application error
     */
    Result<Long, ApplicationError> handle(RejectShipmentOrderCommand command);
}
