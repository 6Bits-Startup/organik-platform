package _5bits.organik.platform.dashboard.application.internal.outboundservices.acl;

import _5bits.organik.platform.communication.interfaces.acl.CommunicationContextFacade;
import org.springframework.stereotype.Service;

/**
 * Outbound service that queries the communication bounded context through its ACL facade.
 */
@Service("dashboardExternalCommunicationService")
public class ExternalCommunicationService {
    private final CommunicationContextFacade communicationContextFacade;

    public ExternalCommunicationService(CommunicationContextFacade communicationContextFacade) {
        this.communicationContextFacade = communicationContextFacade;
    }

    public long countUnreadNotifications() {
        return communicationContextFacade.countUnreadNotifications();
    }
}
