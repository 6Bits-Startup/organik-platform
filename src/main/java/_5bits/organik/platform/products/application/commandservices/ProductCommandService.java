package _5bits.organik.platform.products.application.commandservices;

import _5bits.organik.platform.products.domain.model.commands.CreateProductCommand;
import _5bits.organik.platform.products.domain.model.commands.UpdateProductCommand;
import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;

/**
 * Application service contract for Product command operations.
 */
public interface ProductCommandService {

    /**
     * Handles the creation of a product.
     *
     * @param command command object
     * @return the identifier of the affected Product, or an application error
     */
    Result<Long, ApplicationError> handle(CreateProductCommand command);

    /**
     * Handles the update of a product.
     *
     * @param command command object
     * @return the identifier of the affected Product, or an application error
     */
    Result<Long, ApplicationError> handle(UpdateProductCommand command);
}
