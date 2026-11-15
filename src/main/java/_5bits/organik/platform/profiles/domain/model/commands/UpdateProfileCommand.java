package _5bits.organik.platform.profiles.domain.model.commands;

import _5bits.organik.platform.profiles.domain.model.valueobjects.FullName;

/**
 * Command to update the personal information of a profile.
 *
 * @param profileId profile identifier
 * @param fullName new full name
 */
public record UpdateProfileCommand(Long profileId, FullName fullName) {
    public UpdateProfileCommand {
        if (profileId == null || profileId <= 0) {
            throw new IllegalArgumentException("profileId cannot be null or less than 1");
        }
        if (fullName == null) {
            throw new IllegalArgumentException("fullName cannot be null");
        }
    }
}
