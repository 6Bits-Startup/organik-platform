package _5bits.organik.platform.inventory.domain.model.aggregates;

import _5bits.organik.platform.inventory.domain.model.commands.RegisterInventoryMovementCommand;
import _5bits.organik.platform.inventory.domain.model.valueobjects.MovementReason;
import _5bits.organik.platform.inventory.domain.model.valueobjects.MovementType;
import _5bits.organik.platform.inventory.domain.model.valueobjects.Quantity;
import _5bits.organik.platform.inventory.domain.model.valueobjects.StockLotId;
import _5bits.organik.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.util.Date;
import java.util.Objects;

/**
 * Inventory movement aggregate root.
 * <p>
 * Records an entry, exit, adjustment or waste applied to a stock lot.
 * </p>
 */
@Getter
public class InventoryMovement extends AbstractDomainAggregateRoot<InventoryMovement> {
    private Long id;
    private StockLotId stockLotId;
    private MovementType type;
    private Quantity quantity;
    private MovementReason reason;
    private Date createdAt;
    private Date updatedAt;

    public InventoryMovement(StockLotId stockLotId, MovementType type, Quantity quantity, MovementReason reason) {
        this.stockLotId = Objects.requireNonNull(stockLotId, "stockLotId must not be null");
        this.type = Objects.requireNonNull(type, "type must not be null");
        this.quantity = Objects.requireNonNull(quantity, "quantity must not be null");
        this.reason = reason;
    }

    public InventoryMovement(RegisterInventoryMovementCommand command) {
        this(command.stockLotId(), command.type(), command.quantity(), command.reason());
    }

    public static InventoryMovement reconstitute(Long id, StockLotId stockLotId, MovementType type, Quantity quantity,
                                                 MovementReason reason, Date createdAt, Date updatedAt) {
        var movement = new InventoryMovement(stockLotId, type, quantity, reason);
        movement.id = id;
        movement.createdAt = createdAt;
        movement.updatedAt = updatedAt;
        return movement;
    }
}
