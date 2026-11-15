package _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.entities;

import _5bits.organik.platform.suppliers.domain.model.valueobjects.LotCode;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.ProductId;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.Quantity;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.SupplierId;
import _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.converters.LotCodePersistenceConverter;
import _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.converters.ProductIdPersistenceConverter;
import _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.converters.QuantityPersistenceConverter;
import _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.converters.SupplierIdPersistenceConverter;
import _5bits.organik.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * JPA persistence entity for the OfferedProduct aggregate.
 * <p>
 * Pure persistence model: it carries no business behavior. Mapping to and from the
 * {@code OfferedProduct} domain aggregate is done by {@code OfferedProductPersistenceAssembler}.
 * </p>
 */
@Entity
@Table(name = "offered_products", uniqueConstraints = {
        @UniqueConstraint(name = "uk_offered_product_supplier_lot_code", columnNames = {"supplier_id", "lot_code"})
})
@Getter
@Setter
@NoArgsConstructor
public class OfferedProductPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = SupplierIdPersistenceConverter.class)
    @Column(name = "supplier_id", nullable = false)
    private SupplierId supplierId;

    @Convert(converter = ProductIdPersistenceConverter.class)
    @Column(name = "product_id", nullable = false)
    private ProductId productId;

    @Convert(converter = LotCodePersistenceConverter.class)
    @Column(name = "lot_code", nullable = false, length = 32)
    private LotCode lotCode;

    @Convert(converter = QuantityPersistenceConverter.class)
    @Column(name = "available_quantity", nullable = false)
    private Quantity availableQuantity;

    @Column(name = "expires_on", nullable = false)
    private LocalDate expiresOn;
}
