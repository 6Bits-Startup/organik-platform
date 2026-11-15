package _5bits.organik.platform.requisition.application.acl;

import _5bits.organik.platform.requisition.application.queryservices.SupplyRequestQueryService;
import _5bits.organik.platform.requisition.domain.model.queries.GetAllSupplyRequestsByMinimarketIdQuery;
import _5bits.organik.platform.requisition.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.requisition.domain.model.valueobjects.RequestStatus;
import _5bits.organik.platform.requisition.interfaces.acl.RequisitionContextFacade;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link RequisitionContextFacade} ACL facade based on the query services of the bounded context.
 */
@Service
public class RequisitionContextFacadeImpl implements RequisitionContextFacade {
    private final SupplyRequestQueryService supplyRequestQueryService;

    public RequisitionContextFacadeImpl(SupplyRequestQueryService supplyRequestQueryService) {
        this.supplyRequestQueryService = supplyRequestQueryService;
    }

    @Override
    public long countSupplyRequestsByMinimarketIdAndStatus(String minimarketId, String status) {
        var requestStatus = RequestStatus.valueOf(status);
        return supplyRequestQueryService.handle(new GetAllSupplyRequestsByMinimarketIdQuery(new MinimarketId(minimarketId))).stream()
                .filter(supplyRequest -> supplyRequest.getStatus() == requestStatus)
                .count();
    }
}
