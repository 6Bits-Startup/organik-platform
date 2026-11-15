package _5bits.organik.platform.procurements.infrastructure.persistence.jpa.entities;

import _5bits.organik.platform.procurements.domain.model.valueobjects.LotCode;
import _5bits.organik.platform.procurements.domain.model.valueobjects.ProductId;
import _5bits.organik.platform.procurements.domain.model.valueobjects.Quantity;
import _5bits.organik.platform.procurements.infrastructure.persistence.jpa.converters.LotCodePersistenceConverter;
import _5bits.organik.platform.procurements.infrastructure.persistence.jpa.converters.ProductIdPersistenceConverter;
import _5bits.organik.platform.procurements.infrastructure.persistence.jpa.converters.QuantityPersistenceConverter;
import _5bits.organik.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * JPA persistence entity for the ShipmentOrderLine aggregate.
 * <p>
 * Pure persistence model: it carries no business behavior. Mapping to and from the
 * {@code ShipmentOrderLine} domain aggregate is done by {@code ShipmentOrderLinePersistenceAssembler}.
 * </p>
 */
@Entity
@Table(name = "shipment_order_lines")
@Getter
@Setter
@NoArgsConstructor
public class ShipmentOrderLinePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = ProductIdPersistenceConverter.class)
    @Column(name = "product_id", nullable = false)
    private ProductId productId;

    @Convert(converter = QuantityPersistenceConverter.class)
    @Column(name = "quantity", nullable = false)
    private Quantity quantity;

    @Convert(converter = LotCodePersistenceConverter.class)
    @Column(name = "lot_code", nullable = false, length = 32)
    private LotCode lotCode;

    @Column(name = "expires_on", nullable = false)
    private LocalDate expiresOn;

    @ManyToOne
    @JoinColumn(name = "shipment_order_id", nullable = false)
    private ShipmentOrderPersistenceEntity shipmentOrder;
}
