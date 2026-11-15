package _5bits.organik.platform.inventory.infrastructure.persistence.jpa.entities;

import _5bits.organik.platform.inventory.domain.model.valueobjects.MovementReason;
import _5bits.organik.platform.inventory.domain.model.valueobjects.MovementType;
import _5bits.organik.platform.inventory.domain.model.valueobjects.Quantity;
import _5bits.organik.platform.inventory.domain.model.valueobjects.StockLotId;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.converters.MovementReasonPersistenceConverter;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.converters.QuantityPersistenceConverter;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.converters.StockLotIdPersistenceConverter;
import _5bits.organik.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity for the InventoryMovement aggregate.
 * <p>
 * Pure persistence model: it carries no business behavior. Mapping to and from the
 * {@code InventoryMovement} domain aggregate is done by {@code InventoryMovementPersistenceAssembler}.
 * </p>
 */
@Entity
@Table(name = "inventory_movements")
@Getter
@Setter
@NoArgsConstructor
public class InventoryMovementPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = StockLotIdPersistenceConverter.class)
    @Column(name = "stock_lot_id", nullable = false)
    private StockLotId stockLotId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private MovementType type;

    @Convert(converter = QuantityPersistenceConverter.class)
    @Column(name = "quantity", nullable = false)
    private Quantity quantity;

    @Convert(converter = MovementReasonPersistenceConverter.class)
    @Column(name = "reason")
    private MovementReason reason;
}
