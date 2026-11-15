package _5bits.organik.platform.profiles.application.acl;

import _5bits.organik.platform.profiles.application.queryservices.ProfileQueryService;
import _5bits.organik.platform.profiles.domain.model.queries.GetAllProfilesByUserIdQuery;
import _5bits.organik.platform.profiles.domain.model.valueobjects.UserId;
import _5bits.organik.platform.profiles.interfaces.acl.ProfilesContextFacade;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link ProfilesContextFacade} ACL facade based on the profile query service.
 */
@Service
public class ProfilesContextFacadeImpl implements ProfilesContextFacade {
    private final ProfileQueryService profileQueryService;

    public ProfilesContextFacadeImpl(ProfileQueryService profileQueryService) {
        this.profileQueryService = profileQueryService;
    }

    @Override
    public Integer fetchWarningExpirationDaysByUserId(Long userId) {
        var profiles = profileQueryService.handle(new GetAllProfilesByUserIdQuery(new UserId(userId)));
        return profiles.isEmpty() ? Integer.valueOf(0) : profiles.getFirst().getPreferences().warningExpirationDays().days();
    }
}
