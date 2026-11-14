package _5bits.organik.platform.dashboard.application.internal.outboundservices.acl;

import _5bits.organik.platform.profiles.interfaces.acl.ProfilesContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Outbound service that queries the profiles bounded context through its ACL facade.
 */
@Service("dashboardExternalProfileService")
public class ExternalProfileService {
    private final ProfilesContextFacade profilesContextFacade;

    public ExternalProfileService(ProfilesContextFacade profilesContextFacade) {
        this.profilesContextFacade = profilesContextFacade;
    }

    /**
     * Fetches the warning expiration days configured by a user.
     *
     * @param userId IAM user identifier
     * @return the warning expiration days when the user has a profile, otherwise empty
     */
    public Optional<Integer> fetchWarningExpirationDaysByUserId(Long userId) {
        var warningExpirationDays = profilesContextFacade.fetchWarningExpirationDaysByUserId(userId);
        return warningExpirationDays == 0 ? Optional.empty() : Optional.of(warningExpirationDays);
    }
}
