package _5bits.organik.platform.products.infrastructure.persistence.jpa.entities;

import _5bits.organik.platform.products.domain.model.valueobjects.CategoryId;
import _5bits.organik.platform.products.domain.model.valueobjects.MeasurementUnit;
import _5bits.organik.platform.products.domain.model.valueobjects.MinimumStock;
import _5bits.organik.platform.products.domain.model.valueobjects.ProductName;
import _5bits.organik.platform.products.domain.model.valueobjects.Sku;
import _5bits.organik.platform.products.domain.model.valueobjects.SupplierId;
import _5bits.organik.platform.products.infrastructure.persistence.jpa.converters.CategoryIdPersistenceConverter;
import _5bits.organik.platform.products.infrastructure.persistence.jpa.converters.MinimumStockPersistenceConverter;
import _5bits.organik.platform.products.infrastructure.persistence.jpa.converters.ProductNamePersistenceConverter;
import _5bits.organik.platform.products.infrastructure.persistence.jpa.converters.SkuPersistenceConverter;
import _5bits.organik.platform.products.infrastructure.persistence.jpa.converters.SupplierIdPersistenceConverter;
import _5bits.organik.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity for the Product aggregate.
 * <p>
 * Pure persistence model: it carries no business behavior. Mapping to and from the
 * {@code Product} domain aggregate is done by {@code ProductPersistenceAssembler}.
 * </p>
 */
@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class ProductPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = SkuPersistenceConverter.class)
    @Column(name = "sku", nullable = false, unique = true, length = 32)
    private Sku sku;

    @Convert(converter = ProductNamePersistenceConverter.class)
    @Column(name = "name", nullable = false)
    private ProductName name;

    @Convert(converter = CategoryIdPersistenceConverter.class)
    @Column(name = "category_id", nullable = false)
    private CategoryId categoryId;

    @Convert(converter = SupplierIdPersistenceConverter.class)
    @Column(name = "supplier_id")
    private SupplierId supplierId;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit", nullable = false, length = 20)
    private MeasurementUnit unit;

    @Convert(converter = MinimumStockPersistenceConverter.class)
    @Column(name = "minimum_stock", nullable = false)
    private MinimumStock minimumStock;
}
