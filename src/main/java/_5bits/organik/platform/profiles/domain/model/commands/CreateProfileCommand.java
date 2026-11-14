package _5bits.organik.platform.profiles.domain.model.commands;

import _5bits.organik.platform.profiles.domain.model.valueobjects.*;

/**
 * Command to create the profile of an IAM user.
 *
 * @param userId IAM user that owns the profile
 * @param fullName full name
 * @param email email address
 * @param minimarketId minimarket where the user works, may be {@code null}
 * @param supplierId supplier the user belongs to, may be {@code null}
 */
public record CreateProfileCommand(UserId userId, FullName fullName, EmailAddress email,
                                   MinimarketId minimarketId, SupplierId supplierId) {
    public CreateProfileCommand {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        if (fullName == null) {
            throw new IllegalArgumentException("fullName cannot be null");
        }
        if (email == null) {
            throw new IllegalArgumentException("email cannot be null");
        }
    }
}
