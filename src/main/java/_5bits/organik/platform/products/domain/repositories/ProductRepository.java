package _5bits.organik.platform.products.domain.repositories;

import _5bits.organik.platform.products.domain.model.aggregates.Product;
import _5bits.organik.platform.products.domain.model.valueobjects.CategoryId;
import _5bits.organik.platform.products.domain.model.valueobjects.Sku;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository port for the {@link Product} aggregate.
 * <p>
 * Pure Java interface with no framework dependencies. The infrastructure layer provides the
 * adapter that implements it with Spring Data JPA.
 * </p>
 */
public interface ProductRepository {

    Optional<Product> findById(Long id);

    List<Product> findAll();

    List<Product> findAllByCategoryId(CategoryId categoryId);

    boolean existsBySku(Sku sku);

    Product save(Product product);

    boolean existsById(Long id);
}
