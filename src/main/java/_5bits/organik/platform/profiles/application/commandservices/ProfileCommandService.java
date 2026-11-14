package _5bits.organik.platform.profiles.application.commandservices;

import _5bits.organik.platform.profiles.domain.model.commands.CreateProfileCommand;
import _5bits.organik.platform.profiles.domain.model.commands.UpdateProfilePreferencesCommand;
import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;

/**
 * Application service contract for Profile command operations.
 */
public interface ProfileCommandService {

    /**
     * Handles the creation of the profile of an IAM user.
     *
     * @param command command object
     * @return the identifier of the affected Profile, or an application error
     */
    Result<Long, ApplicationError> handle(CreateProfileCommand command);

    /**
     * Handles the update of the preferences of a profile.
     *
     * @param command command object
     * @return the identifier of the affected Profile, or an application error
     */
    Result<Long, ApplicationError> handle(UpdateProfilePreferencesCommand command);
}
