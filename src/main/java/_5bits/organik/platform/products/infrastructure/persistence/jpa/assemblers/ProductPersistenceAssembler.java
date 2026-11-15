package _5bits.organik.platform.products.infrastructure.persistence.jpa.assemblers;

import _5bits.organik.platform.products.domain.model.aggregates.Product;
import _5bits.organik.platform.products.infrastructure.persistence.jpa.entities.ProductPersistenceEntity;


/**
 * Assembler that maps between the {@link Product} domain aggregate and its JPA persistence entity.
 * <p>
 * Acts as the anti-corruption layer of the persistence adapter: JPA concerns never leak into the domain.
 * </p>
 */
public final class ProductPersistenceAssembler {

    private ProductPersistenceAssembler() {
    }

    /**
     * Reconstitutes a domain aggregate from its persistence entity.
     *
     * @param entity persistence entity, may be {@code null}
     * @return the reconstituted aggregate, or {@code null} when the entity is {@code null}
     */
    public static Product toDomainFromPersistence(ProductPersistenceEntity entity) {
        if (entity == null) return null;
        return Product.reconstitute(
                entity.getId(),
                entity.getSku(),
                entity.getName(),
                entity.getCategoryId(),
                entity.getSupplierId(),
                entity.getUnit(),
                entity.getMinimumStock());
    }

    /**
     * Maps a domain aggregate to a persistence entity.
     *
     * @param product domain aggregate, may be {@code null}
     * @return the persistence entity, or {@code null} when the aggregate is {@code null}
     */
    public static ProductPersistenceEntity toPersistenceFromDomain(Product product) {
        if (product == null) return null;
        var entity = new ProductPersistenceEntity();
        if (product.getId() != null) {
            entity.setId(product.getId());
        }
        entity.setSku(product.getSku());
        entity.setName(product.getName());
        entity.setCategoryId(product.getCategoryId());
        entity.setSupplierId(product.getSupplierId());
        entity.setUnit(product.getUnit());
        entity.setMinimumStock(product.getMinimumStock());
        return entity;
    }
}
