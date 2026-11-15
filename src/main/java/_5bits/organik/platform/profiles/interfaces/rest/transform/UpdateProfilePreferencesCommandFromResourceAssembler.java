package _5bits.organik.platform.profiles.interfaces.rest.transform;

import _5bits.organik.platform.profiles.domain.model.commands.UpdateProfilePreferencesCommand;
import _5bits.organik.platform.profiles.interfaces.rest.resources.UpdateProfilePreferencesResource;
import _5bits.organik.platform.profiles.domain.model.valueobjects.ExpirationThreshold;
import _5bits.organik.platform.profiles.domain.model.valueobjects.Language;
import _5bits.organik.platform.profiles.domain.model.valueobjects.Preferences;

/**
 * Assembler that converts a {@link UpdateProfilePreferencesResource} into a {@link UpdateProfilePreferencesCommand}.
 */
public class UpdateProfilePreferencesCommandFromResourceAssembler {
    public static UpdateProfilePreferencesCommand toCommandFromResource(Long profileId, UpdateProfilePreferencesResource resource) {
        return new UpdateProfilePreferencesCommand(
                profileId,
                new Preferences(
                        new Language(resource.language()),
                        new ExpirationThreshold(resource.warningExpirationDays()),
                        new ExpirationThreshold(resource.criticalExpirationDays())));
    }
}
