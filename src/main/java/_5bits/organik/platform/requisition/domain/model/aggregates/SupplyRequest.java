package _5bits.organik.platform.requisition.domain.model.aggregates;

import _5bits.organik.platform.requisition.domain.model.commands.CreateSupplyRequestCommand;
import _5bits.organik.platform.requisition.domain.model.valueobjects.*;
import _5bits.organik.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Supply request aggregate root.
 * <p>
 * A minimarket shares what it needs; the supplier accepts or rejects the request.
 * </p>
 */
@Getter
public class SupplyRequest extends AbstractDomainAggregateRoot<SupplyRequest> {
    private Long id;
    private ProductId productId;
    private SupplierId supplierId;
    private MinimarketId minimarketId;
    private Quantity quantity;
    private RequestReason reason;
    private LocalDate requiredDate;
    private RequestPriority priority;
    private RequestStatus status;

    public SupplyRequest(ProductId productId, SupplierId supplierId, MinimarketId minimarketId, Quantity quantity,
                         RequestReason reason, LocalDate requiredDate, RequestPriority priority) {
        this.productId = Objects.requireNonNull(productId, "productId must not be null");
        this.supplierId = Objects.requireNonNull(supplierId, "supplierId must not be null");
        this.minimarketId = Objects.requireNonNull(minimarketId, "minimarketId must not be null");
        this.quantity = Objects.requireNonNull(quantity, "quantity must not be null");
        this.reason = Objects.requireNonNull(reason, "reason must not be null");
        this.requiredDate = Objects.requireNonNull(requiredDate, "requiredDate must not be null");
        this.priority = Objects.requireNonNull(priority, "priority must not be null");
        this.status = RequestStatus.PENDING;
    }

    public SupplyRequest(CreateSupplyRequestCommand command) {
        this(command.productId(), command.supplierId(), command.minimarketId(), command.quantity(),
                command.reason(), command.requiredDate(), command.priority());
    }

    public static SupplyRequest reconstitute(Long id, ProductId productId, SupplierId supplierId, MinimarketId minimarketId,
                                             Quantity quantity, RequestReason reason, LocalDate requiredDate,
                                             RequestPriority priority, RequestStatus status) {
        var supplyRequest = new SupplyRequest(productId, supplierId, minimarketId, quantity, reason, requiredDate, priority);
        supplyRequest.id = id;
        supplyRequest.status = status;
        return supplyRequest;
    }

    /**
     * @return {@code true} when the supplier has not answered the request yet
     */
    public boolean isPending() {
        return this.status == RequestStatus.PENDING;
    }

    /**
     * Accepts the supply request.
     */
    public void accept() {
        ensurePending();
        this.status = RequestStatus.ACCEPTED;
    }

    /**
     * Rejects the supply request.
     */
    public void reject() {
        ensurePending();
        this.status = RequestStatus.REJECTED;
    }

    private void ensurePending() {
        if (!isPending()) {
            throw new IllegalStateException("Only pending supply requests can be answered");
        }
    }
}
