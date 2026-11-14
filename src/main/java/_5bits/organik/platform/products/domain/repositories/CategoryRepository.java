package _5bits.organik.platform.products.domain.repositories;

import _5bits.organik.platform.products.domain.model.aggregates.Category;
import _5bits.organik.platform.products.domain.model.valueobjects.CategoryName;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository port for the {@link Category} aggregate.
 * <p>
 * Pure Java interface with no framework dependencies. The infrastructure layer provides the
 * adapter that implements it with Spring Data JPA.
 * </p>
 */
public interface CategoryRepository {

    Optional<Category> findById(Long id);

    List<Category> findAll();

    boolean existsByName(CategoryName name);

    Category save(Category category);

    boolean existsById(Long id);
}
