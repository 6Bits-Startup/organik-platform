package _5bits.organik.platform.inventory.application.commandservices;

import _5bits.organik.platform.inventory.domain.model.commands.CreateOfferCommand;
import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;

/**
 * Application service contract for Offer command operations.
 */
public interface OfferCommandService {

    /**
     * Handles the creation of an offer for a stock lot.
     *
     * @param command command object
     * @return the identifier of the affected Offer, or an application error
     */
    Result<Long, ApplicationError> handle(CreateOfferCommand command);
}
