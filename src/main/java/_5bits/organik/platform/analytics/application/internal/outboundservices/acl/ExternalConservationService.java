package _5bits.organik.platform.analytics.application.internal.outboundservices.acl;

import _5bits.organik.platform.conservation.interfaces.acl.ConservationContextFacade;
import org.springframework.stereotype.Service;

/**
 * Outbound service that queries the conservation bounded context through its ACL facade.
 */
@Service("analyticsExternalConservationService")
public class ExternalConservationService {
    private final ConservationContextFacade conservationContextFacade;

    public ExternalConservationService(ConservationContextFacade conservationContextFacade) {
        this.conservationContextFacade = conservationContextFacade;
    }

    public long countOutOfRangeReadings() {
        return conservationContextFacade.countOutOfRangeReadings();
    }
}
