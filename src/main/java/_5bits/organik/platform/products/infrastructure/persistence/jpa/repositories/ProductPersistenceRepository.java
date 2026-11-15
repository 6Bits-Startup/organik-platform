package _5bits.organik.platform.products.infrastructure.persistence.jpa.repositories;

import _5bits.organik.platform.products.domain.model.valueobjects.CategoryId;
import _5bits.organik.platform.products.domain.model.valueobjects.Sku;
import _5bits.organik.platform.products.infrastructure.persistence.jpa.entities.ProductPersistenceEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link ProductPersistenceEntity}.
 * <p>
 * Infrastructure detail used only by the {@code ProductRepositoryImpl} adapter.
 * </p>
 */
@Repository
public interface ProductPersistenceRepository extends JpaRepository<ProductPersistenceEntity, Long> {

    List<ProductPersistenceEntity> findAllByCategoryId(CategoryId categoryId);

    boolean existsBySku(Sku sku);
}
