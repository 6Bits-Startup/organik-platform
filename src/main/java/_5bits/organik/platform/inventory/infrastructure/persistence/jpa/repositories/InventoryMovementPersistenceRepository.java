package _5bits.organik.platform.inventory.infrastructure.persistence.jpa.repositories;

import _5bits.organik.platform.inventory.domain.model.valueobjects.StockLotId;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.entities.InventoryMovementPersistenceEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link InventoryMovementPersistenceEntity}.
 * <p>
 * Infrastructure detail used only by the {@code InventoryMovementRepositoryImpl} adapter.
 * </p>
 */
@Repository
public interface InventoryMovementPersistenceRepository extends JpaRepository<InventoryMovementPersistenceEntity, Long> {

    List<InventoryMovementPersistenceEntity> findAllByStockLotId(StockLotId stockLotId);
}
