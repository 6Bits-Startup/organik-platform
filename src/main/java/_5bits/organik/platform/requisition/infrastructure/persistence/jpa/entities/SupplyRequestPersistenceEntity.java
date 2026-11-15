package _5bits.organik.platform.requisition.infrastructure.persistence.jpa.entities;

import _5bits.organik.platform.requisition.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.requisition.domain.model.valueobjects.ProductId;
import _5bits.organik.platform.requisition.domain.model.valueobjects.Quantity;
import _5bits.organik.platform.requisition.domain.model.valueobjects.RequestPriority;
import _5bits.organik.platform.requisition.domain.model.valueobjects.RequestReason;
import _5bits.organik.platform.requisition.domain.model.valueobjects.RequestStatus;
import _5bits.organik.platform.requisition.domain.model.valueobjects.SupplierId;
import _5bits.organik.platform.requisition.infrastructure.persistence.jpa.converters.MinimarketIdPersistenceConverter;
import _5bits.organik.platform.requisition.infrastructure.persistence.jpa.converters.ProductIdPersistenceConverter;
import _5bits.organik.platform.requisition.infrastructure.persistence.jpa.converters.QuantityPersistenceConverter;
import _5bits.organik.platform.requisition.infrastructure.persistence.jpa.converters.RequestReasonPersistenceConverter;
import _5bits.organik.platform.requisition.infrastructure.persistence.jpa.converters.SupplierIdPersistenceConverter;
import _5bits.organik.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * JPA persistence entity for the SupplyRequest aggregate.
 * <p>
 * Pure persistence model: it carries no business behavior. Mapping to and from the
 * {@code SupplyRequest} domain aggregate is done by {@code SupplyRequestPersistenceAssembler}.
 * </p>
 */
@Entity
@Table(name = "supply_requests")
@Getter
@Setter
@NoArgsConstructor
public class SupplyRequestPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = ProductIdPersistenceConverter.class)
    @Column(name = "product_id", nullable = false)
    private ProductId productId;

    @Convert(converter = SupplierIdPersistenceConverter.class)
    @Column(name = "supplier_id", nullable = false)
    private SupplierId supplierId;

    @Convert(converter = MinimarketIdPersistenceConverter.class)
    @Column(name = "minimarket_id", nullable = false)
    private MinimarketId minimarketId;

    @Convert(converter = QuantityPersistenceConverter.class)
    @Column(name = "quantity", nullable = false)
    private Quantity quantity;

    @Convert(converter = RequestReasonPersistenceConverter.class)
    @Column(name = "reason", nullable = false, length = 512)
    private RequestReason reason;

    @Column(name = "required_date", nullable = false)
    private LocalDate requiredDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false, length = 20)
    private RequestPriority priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private RequestStatus status;
}
