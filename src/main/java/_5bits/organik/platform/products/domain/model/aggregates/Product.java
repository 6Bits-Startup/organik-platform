package _5bits.organik.platform.products.domain.model.aggregates;

import _5bits.organik.platform.products.domain.model.commands.CreateProductCommand;
import _5bits.organik.platform.products.domain.model.valueobjects.*;
import _5bits.organik.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.util.Objects;

/**
 * Product aggregate root.
 * <p>
 * Represents an organic product of the minimarket catalog.
 * </p>
 */
@Getter
public class Product extends AbstractDomainAggregateRoot<Product> {
    private Long id;
    private Sku sku;
    private ProductName name;
    private CategoryId categoryId;
    private SupplierId supplierId;
    private MeasurementUnit unit;
    private MinimumStock minimumStock;

    public Product(Sku sku, ProductName name, CategoryId categoryId, SupplierId supplierId,
                   MeasurementUnit unit, MinimumStock minimumStock) {
        this.sku = Objects.requireNonNull(sku, "sku must not be null");
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.categoryId = Objects.requireNonNull(categoryId, "categoryId must not be null");
        this.supplierId = supplierId;
        this.unit = Objects.requireNonNull(unit, "unit must not be null");
        this.minimumStock = Objects.requireNonNull(minimumStock, "minimumStock must not be null");
    }

    public Product(CreateProductCommand command) {
        this(command.sku(), command.name(), command.categoryId(), command.supplierId(), command.unit(), command.minimumStock());
    }

    public static Product reconstitute(Long id, Sku sku, ProductName name, CategoryId categoryId, SupplierId supplierId,
                                       MeasurementUnit unit, MinimumStock minimumStock) {
        var product = new Product(sku, name, categoryId, supplierId, unit, minimumStock);
        product.id = id;
        return product;
    }

    /**
     * Updates the information of the product. The SKU never changes.
     *
     * @param name new name
     * @param categoryId new category
     * @param supplierId new usual supplier, may be {@code null}
     * @param unit new measurement unit
     * @param minimumStock new minimum stock
     * @return the updated product
     */
    public Product updateInformation(ProductName name, CategoryId categoryId, SupplierId supplierId,
                                     MeasurementUnit unit, MinimumStock minimumStock) {
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.categoryId = Objects.requireNonNull(categoryId, "categoryId must not be null");
        this.supplierId = supplierId;
        this.unit = Objects.requireNonNull(unit, "unit must not be null");
        this.minimumStock = Objects.requireNonNull(minimumStock, "minimumStock must not be null");
        return this;
    }
}
