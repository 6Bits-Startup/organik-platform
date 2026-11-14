package _5bits.organik.platform.analytics.application.internal.outboundservices.acl;

import _5bits.organik.platform.procurements.interfaces.acl.ProcurementsContextFacade;
import org.springframework.stereotype.Service;

/**
 * Outbound service that queries the procurements bounded context through its ACL facade.
 */
@Service("analyticsExternalProcurementsService")
public class ExternalProcurementsService {
    private final ProcurementsContextFacade procurementsContextFacade;

    public ExternalProcurementsService(ProcurementsContextFacade procurementsContextFacade) {
        this.procurementsContextFacade = procurementsContextFacade;
    }

    public long countAcceptedShipmentOrders(String minimarketId) {
        return procurementsContextFacade.countShipmentOrdersByMinimarketIdAndStatus(minimarketId, "ACCEPTED");
    }

    public long countRejectedShipmentOrders(String minimarketId) {
        return procurementsContextFacade.countShipmentOrdersByMinimarketIdAndStatus(minimarketId, "REJECTED");
    }
}
