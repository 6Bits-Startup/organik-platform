package _5bits.organik.platform.products.infrastructure.persistence.jpa.repositories;

import _5bits.organik.platform.products.domain.model.valueobjects.CategoryName;
import _5bits.organik.platform.products.infrastructure.persistence.jpa.entities.CategoryPersistenceEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link CategoryPersistenceEntity}.
 * <p>
 * Infrastructure detail used only by the {@code CategoryRepositoryImpl} adapter.
 * </p>
 */
@Repository
public interface CategoryPersistenceRepository extends JpaRepository<CategoryPersistenceEntity, Long> {

    boolean existsByName(CategoryName name);
}
