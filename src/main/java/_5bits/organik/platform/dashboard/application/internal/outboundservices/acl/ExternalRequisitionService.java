package _5bits.organik.platform.dashboard.application.internal.outboundservices.acl;

import _5bits.organik.platform.requisition.interfaces.acl.RequisitionContextFacade;
import org.springframework.stereotype.Service;

/**
 * Outbound service that queries the requisition bounded context through its ACL facade.
 */
@Service("dashboardExternalRequisitionService")
public class ExternalRequisitionService {
    private final RequisitionContextFacade requisitionContextFacade;

    public ExternalRequisitionService(RequisitionContextFacade requisitionContextFacade) {
        this.requisitionContextFacade = requisitionContextFacade;
    }

    public long countPendingSupplyRequests(String minimarketId) {
        return requisitionContextFacade.countSupplyRequestsByMinimarketIdAndStatus(minimarketId, "PENDING");
    }
}
