package _5bits.organik.platform.procurements.infrastructure.persistence.jpa.entities;

import _5bits.organik.platform.procurements.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.procurements.domain.model.valueobjects.OrderStatus;
import _5bits.organik.platform.procurements.domain.model.valueobjects.RejectionReason;
import _5bits.organik.platform.procurements.domain.model.valueobjects.SupplierId;
import _5bits.organik.platform.procurements.domain.model.valueobjects.UserId;
import _5bits.organik.platform.procurements.infrastructure.persistence.jpa.converters.MinimarketIdPersistenceConverter;
import _5bits.organik.platform.procurements.infrastructure.persistence.jpa.converters.RejectionReasonPersistenceConverter;
import _5bits.organik.platform.procurements.infrastructure.persistence.jpa.converters.SupplierIdPersistenceConverter;
import _5bits.organik.platform.procurements.infrastructure.persistence.jpa.converters.UserIdPersistenceConverter;
import _5bits.organik.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * JPA persistence entity for the ShipmentOrder aggregate.
 * <p>
 * Pure persistence model: it carries no business behavior. Mapping to and from the
 * {@code ShipmentOrder} domain aggregate is done by {@code ShipmentOrderPersistenceAssembler}.
 * </p>
 */
@Entity
@Table(name = "shipment_orders")
@Getter
@Setter
@NoArgsConstructor
public class ShipmentOrderPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = SupplierIdPersistenceConverter.class)
    @Column(name = "supplier_id", nullable = false)
    private SupplierId supplierId;

    @Convert(converter = MinimarketIdPersistenceConverter.class)
    @Column(name = "minimarket_id", nullable = false)
    private MinimarketId minimarketId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private OrderStatus status;

    @Convert(converter = UserIdPersistenceConverter.class)
    @Column(name = "decided_by")
    private UserId decidedBy;

    @Column(name = "decided_at")
    private Date decidedAt;

    @Convert(converter = RejectionReasonPersistenceConverter.class)
    @Column(name = "rejection_reason", length = 512)
    private RejectionReason rejectionReason;

    @OneToMany(mappedBy = "shipmentOrder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ShipmentOrderLinePersistenceEntity> lines = new ArrayList<>();
}
