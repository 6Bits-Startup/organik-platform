package _5bits.organik.platform.inventory.infrastructure.persistence.jpa.repositories;

import _5bits.organik.platform.inventory.domain.model.valueobjects.LotCode;
import _5bits.organik.platform.inventory.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.entities.StockLotPersistenceEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link StockLotPersistenceEntity}.
 * <p>
 * Infrastructure detail used only by the {@code StockLotRepositoryImpl} adapter.
 * </p>
 */
@Repository
public interface StockLotPersistenceRepository extends JpaRepository<StockLotPersistenceEntity, Long> {

    List<StockLotPersistenceEntity> findAllByMinimarketId(MinimarketId minimarketId);

    boolean existsByMinimarketIdAndLotCode(MinimarketId minimarketId, LotCode lotCode);
}
