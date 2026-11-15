package _5bits.organik.platform.procurements.infrastructure.persistence.jpa.repositories;

import _5bits.organik.platform.procurements.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.procurements.domain.model.valueobjects.SupplierId;
import _5bits.organik.platform.procurements.infrastructure.persistence.jpa.entities.ShipmentOrderPersistenceEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link ShipmentOrderPersistenceEntity}.
 * <p>
 * Infrastructure detail used only by the {@code ShipmentOrderRepositoryImpl} adapter.
 * </p>
 */
@Repository
public interface ShipmentOrderPersistenceRepository extends JpaRepository<ShipmentOrderPersistenceEntity, Long> {

    List<ShipmentOrderPersistenceEntity> findAllBySupplierId(SupplierId supplierId);

    List<ShipmentOrderPersistenceEntity> findAllByMinimarketId(MinimarketId minimarketId);
}
