package _5bits.organik.platform.profiles.application.internal.outboundservices.acl;

import _5bits.organik.platform.iam.interfaces.acl.IamContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Outbound service that queries the IAM bounded context through its ACL facade.
 */
@Service
public class ExternalIamService {
    private final IamContextFacade iamContextFacade;

    public ExternalIamService(IamContextFacade iamContextFacade) {
        this.iamContextFacade = iamContextFacade;
    }

    /**
     * Fetches the username of an IAM user.
     *
     * @param userId user identifier
     * @return the username when the user exists, otherwise empty
     */
    public Optional<String> fetchUsernameByUserId(Long userId) {
        var username = iamContextFacade.fetchUsernameByUserId(userId);
        return username == null || username.isBlank() ? Optional.empty() : Optional.of(username);
    }
}
