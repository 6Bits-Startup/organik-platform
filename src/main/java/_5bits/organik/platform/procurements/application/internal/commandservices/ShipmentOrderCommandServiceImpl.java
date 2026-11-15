package _5bits.organik.platform.procurements.application.internal.commandservices;

import _5bits.organik.platform.procurements.application.commandservices.ShipmentOrderCommandService;
import _5bits.organik.platform.procurements.domain.model.aggregates.ShipmentOrder;
import _5bits.organik.platform.procurements.domain.model.commands.AcceptShipmentOrderCommand;
import _5bits.organik.platform.procurements.domain.model.commands.CreateShipmentOrderCommand;
import _5bits.organik.platform.procurements.domain.model.commands.RejectShipmentOrderCommand;
import _5bits.organik.platform.procurements.domain.repositories.ShipmentOrderRepository;
import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Application service that handles ShipmentOrder commands.
 */
@Service
public class ShipmentOrderCommandServiceImpl implements ShipmentOrderCommandService {
    private final ShipmentOrderRepository shipmentOrderRepository;

    public ShipmentOrderCommandServiceImpl(ShipmentOrderRepository shipmentOrderRepository) {
        this.shipmentOrderRepository = shipmentOrderRepository;
    }

    @Override
    public Result<Long, ApplicationError> handle(CreateShipmentOrderCommand command) {
        var shipmentOrder = new ShipmentOrder(command);
        try {
            shipmentOrder = shipmentOrderRepository.save(shipmentOrder);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-shipment-order", e.getMessage()));
        }
        return Result.success(shipmentOrder.getId());
    }

    @Override
    public Result<Long, ApplicationError> handle(AcceptShipmentOrderCommand command) {
        return shipmentOrderRepository.findById(command.shipmentOrderId()).map(shipmentOrder -> {
            if (!shipmentOrder.isPending())
                return Result.<Long, ApplicationError>failure(ApplicationError.conflict("ShipmentOrder", "Only pending shipment orders can be accepted"));
            shipmentOrder.accept(command.decidedBy());
            return Result.<Long, ApplicationError>success(shipmentOrderRepository.save(shipmentOrder).getId());
        }).orElseGet(() -> Result.failure(ApplicationError.notFound("ShipmentOrder", command.shipmentOrderId().toString())));
    }

    @Override
    public Result<Long, ApplicationError> handle(RejectShipmentOrderCommand command) {
        return shipmentOrderRepository.findById(command.shipmentOrderId()).map(shipmentOrder -> {
            if (!shipmentOrder.isPending())
                return Result.<Long, ApplicationError>failure(ApplicationError.conflict("ShipmentOrder", "Only pending shipment orders can be rejected"));
            shipmentOrder.reject(command.decidedBy(), command.reason());
            return Result.<Long, ApplicationError>success(shipmentOrderRepository.save(shipmentOrder).getId());
        }).orElseGet(() -> Result.failure(ApplicationError.notFound("ShipmentOrder", command.shipmentOrderId().toString())));
    }
}
