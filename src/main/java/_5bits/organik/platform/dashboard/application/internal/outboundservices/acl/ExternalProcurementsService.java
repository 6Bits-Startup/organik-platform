package _5bits.organik.platform.dashboard.application.internal.outboundservices.acl;

import _5bits.organik.platform.procurements.interfaces.acl.ProcurementsContextFacade;
import org.springframework.stereotype.Service;

/**
 * Outbound service that queries the procurements bounded context through its ACL facade.
 */
@Service("dashboardExternalProcurementsService")
public class ExternalProcurementsService {
    private final ProcurementsContextFacade procurementsContextFacade;

    public ExternalProcurementsService(ProcurementsContextFacade procurementsContextFacade) {
        this.procurementsContextFacade = procurementsContextFacade;
    }

    public long countPendingShipmentOrders(String minimarketId) {
        return procurementsContextFacade.countShipmentOrdersByMinimarketIdAndStatus(minimarketId, "PENDING");
    }
}
