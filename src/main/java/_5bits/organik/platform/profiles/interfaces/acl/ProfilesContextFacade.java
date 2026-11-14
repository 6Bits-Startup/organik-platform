package _5bits.organik.platform.profiles.interfaces.acl;

/**
 * ACL facade that exposes Profiles bounded context capabilities to other contexts.
 */
public interface ProfilesContextFacade {
    /**
     * Fetches the number of days before expiration that the user configured to raise a warning alert.
     *
     * @param userId IAM user identifier
     * @return warning expiration days, or {@code 0} when the user has no profile
     */
    Integer fetchWarningExpirationDaysByUserId(Long userId);
}
