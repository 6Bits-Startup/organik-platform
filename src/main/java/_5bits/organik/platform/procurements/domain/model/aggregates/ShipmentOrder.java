package _5bits.organik.platform.procurements.domain.model.aggregates;

import _5bits.organik.platform.procurements.domain.model.commands.CreateShipmentOrderCommand;
import _5bits.organik.platform.procurements.domain.model.entities.ShipmentOrderLine;
import _5bits.organik.platform.procurements.domain.model.events.ShipmentOrderAcceptedEvent;
import _5bits.organik.platform.procurements.domain.model.valueobjects.*;
import _5bits.organik.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Shipment order aggregate root.
 * <p>
 * Represents the products a supplier sends to a minimarket. The minimarket administrator
 * accepts or rejects it; once accepted, its lots enter the minimarket inventory.
 * </p>
 */
@Getter
public class ShipmentOrder extends AbstractDomainAggregateRoot<ShipmentOrder> {
    private Long id;
    private SupplierId supplierId;
    private MinimarketId minimarketId;
    private List<ShipmentOrderLine> lines;
    private OrderStatus status;
    private UserId decidedBy;
    private Date decidedAt;
    private RejectionReason rejectionReason;
    private Date createdAt;
    private Date updatedAt;

    public ShipmentOrder(SupplierId supplierId, MinimarketId minimarketId, List<ShipmentOrderLine> lines) {
        this.supplierId = Objects.requireNonNull(supplierId, "supplierId must not be null");
        this.minimarketId = Objects.requireNonNull(minimarketId, "minimarketId must not be null");
        this.lines = lines != null ? new ArrayList<>(lines) : new ArrayList<>();
        this.status = OrderStatus.PENDING;
    }

    public ShipmentOrder(CreateShipmentOrderCommand command) {
        this(command.supplierId(), command.minimarketId(), command.lines().stream()
                .map(line -> new ShipmentOrderLine(line.productId(), line.quantity(), line.lotCode(), line.expiresOn()))
                .toList());
    }

    public static ShipmentOrder reconstitute(Long id, SupplierId supplierId, MinimarketId minimarketId,
                                             List<ShipmentOrderLine> lines, OrderStatus status, UserId decidedBy,
                                             Date decidedAt, RejectionReason rejectionReason,
                                             Date createdAt, Date updatedAt) {
        var shipmentOrder = new ShipmentOrder(supplierId, minimarketId, lines);
        shipmentOrder.id = id;
        shipmentOrder.status = status;
        shipmentOrder.decidedBy = decidedBy;
        shipmentOrder.decidedAt = decidedAt;
        shipmentOrder.rejectionReason = rejectionReason;
        shipmentOrder.createdAt = createdAt;
        shipmentOrder.updatedAt = updatedAt;
        return shipmentOrder;
    }

    /**
     * @return {@code true} when the order has not been accepted or rejected yet
     */
    public boolean isPending() {
        return this.status == OrderStatus.PENDING;
    }

    /**
     * Accepts the shipment order and registers the {@link ShipmentOrderAcceptedEvent}.
     *
     * @param decidedBy user that accepts the order
     */
    public void accept(UserId decidedBy) {
        ensurePending();
        this.status = OrderStatus.ACCEPTED;
        this.decidedBy = Objects.requireNonNull(decidedBy, "decidedBy must not be null");
        this.decidedAt = new Date();
        this.rejectionReason = null;
        this.registerDomainEvent(new ShipmentOrderAcceptedEvent(
                id,
                supplierId.value(),
                minimarketId.value(),
                lines.stream()
                        .map(line -> new ShipmentOrderAcceptedEvent.AcceptedLine(
                                line.getProductId().value(),
                                line.getQuantity().value(),
                                line.getLotCode().value(),
                                line.getExpiresOn()))
                        .toList()));
    }

    /**
     * Rejects the shipment order.
     *
     * @param decidedBy user that rejects the order
     * @param reason rejection reason
     */
    public void reject(UserId decidedBy, RejectionReason reason) {
        ensurePending();
        this.status = OrderStatus.REJECTED;
        this.decidedBy = Objects.requireNonNull(decidedBy, "decidedBy must not be null");
        this.decidedAt = new Date();
        this.rejectionReason = Objects.requireNonNull(reason, "reason must not be null");
    }

    private void ensurePending() {
        if (!isPending()) {
            throw new IllegalStateException("Only pending shipment orders can be decided");
        }
    }
}
