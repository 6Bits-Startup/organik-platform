package _5bits.organik.platform.profiles.domain.model.commands;

import _5bits.organik.platform.profiles.domain.model.valueobjects.Preferences;

/**
 * Command to update the preferences of a profile.
 *
 * @param profileId profile identifier
 * @param preferences new preferences
 */
public record UpdateProfilePreferencesCommand(Long profileId, Preferences preferences) {
    public UpdateProfilePreferencesCommand {
        if (profileId == null || profileId <= 0) {
            throw new IllegalArgumentException("profileId cannot be null or less than 1");
        }
        if (preferences == null) {
            throw new IllegalArgumentException("preferences cannot be null");
        }
    }
}
