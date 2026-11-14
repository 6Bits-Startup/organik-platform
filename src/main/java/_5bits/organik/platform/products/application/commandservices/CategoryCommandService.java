package _5bits.organik.platform.products.application.commandservices;

import _5bits.organik.platform.products.domain.model.commands.CreateCategoryCommand;
import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;

/**
 * Application service contract for Category command operations.
 */
public interface CategoryCommandService {

    /**
     * Handles the creation of a category.
     *
     * @param command command object
     * @return the identifier of the affected Category, or an application error
     */
    Result<Long, ApplicationError> handle(CreateCategoryCommand command);
}
