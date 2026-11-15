package _5bits.organik.platform.inventory.infrastructure.persistence.jpa.repositories;

import _5bits.organik.platform.inventory.domain.model.valueobjects.StockLotId;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.entities.OfferPersistenceEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link OfferPersistenceEntity}.
 * <p>
 * Infrastructure detail used only by the {@code OfferRepositoryImpl} adapter.
 * </p>
 */
@Repository
public interface OfferPersistenceRepository extends JpaRepository<OfferPersistenceEntity, Long> {

    List<OfferPersistenceEntity> findAllByStockLotId(StockLotId stockLotId);
}
