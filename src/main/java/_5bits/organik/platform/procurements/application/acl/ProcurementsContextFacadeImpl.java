package _5bits.organik.platform.procurements.application.acl;

import _5bits.organik.platform.procurements.application.queryservices.ShipmentOrderQueryService;
import _5bits.organik.platform.procurements.domain.model.queries.GetShipmentOrdersByMinimarketIdQuery;
import _5bits.organik.platform.procurements.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.procurements.domain.model.valueobjects.OrderStatus;
import _5bits.organik.platform.procurements.interfaces.acl.ProcurementsContextFacade;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link ProcurementsContextFacade} ACL facade based on the query services of the bounded context.
 */
@Service
public class ProcurementsContextFacadeImpl implements ProcurementsContextFacade {
    private final ShipmentOrderQueryService shipmentOrderQueryService;

    public ProcurementsContextFacadeImpl(ShipmentOrderQueryService shipmentOrderQueryService) {
        this.shipmentOrderQueryService = shipmentOrderQueryService;
    }

    @Override
    public long countShipmentOrdersByMinimarketIdAndStatus(String minimarketId, String status) {
        var orderStatus = OrderStatus.valueOf(status);
        return shipmentOrderQueryService.handle(new GetShipmentOrdersByMinimarketIdQuery(new MinimarketId(minimarketId))).stream()
                .filter(shipmentOrder -> shipmentOrder.getStatus() == orderStatus)
                .count();
    }
}
