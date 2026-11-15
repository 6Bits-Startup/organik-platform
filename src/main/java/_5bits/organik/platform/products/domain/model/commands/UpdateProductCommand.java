package _5bits.organik.platform.products.domain.model.commands;

import _5bits.organik.platform.products.domain.model.valueobjects.*;

/**
 * Command to update the information of a product.
 *
 * @param productId product identifier
 * @param name new product name
 * @param categoryId new category
 * @param supplierId new usual supplier, may be {@code null}
 * @param unit new measurement unit
 * @param minimumStock new minimum stock
 */
public record UpdateProductCommand(Long productId, ProductName name, CategoryId categoryId, SupplierId supplierId,
                                   MeasurementUnit unit, MinimumStock minimumStock) {
    public UpdateProductCommand {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("productId cannot be null or less than 1");
        }
        if (name == null) {
            throw new IllegalArgumentException("ProductName cannot be null");
        }
        if (categoryId == null) {
            throw new IllegalArgumentException("CategoryId cannot be null");
        }
        if (unit == null) {
            throw new IllegalArgumentException("MeasurementUnit cannot be null");
        }
        if (minimumStock == null) {
            throw new IllegalArgumentException("MinimumStock cannot be null");
        }
    }
}
