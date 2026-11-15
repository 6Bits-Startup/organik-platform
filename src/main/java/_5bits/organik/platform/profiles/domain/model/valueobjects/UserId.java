package _5bits.organik.platform.profiles.domain.model.valueobjects;

/**
 * Identifier of the IAM user that owns a profile.
 *
 * @param value user identifier
 */
public record UserId(Long value) {
    public UserId {
        if (value == null || value < 1) {
            throw new IllegalArgumentException("User id cannot be null or less than 1");
        }
    }
}
