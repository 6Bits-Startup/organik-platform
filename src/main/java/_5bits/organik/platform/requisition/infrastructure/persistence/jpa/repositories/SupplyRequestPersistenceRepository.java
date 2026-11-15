package _5bits.organik.platform.requisition.infrastructure.persistence.jpa.repositories;

import _5bits.organik.platform.requisition.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.requisition.domain.model.valueobjects.SupplierId;
import _5bits.organik.platform.requisition.infrastructure.persistence.jpa.entities.SupplyRequestPersistenceEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link SupplyRequestPersistenceEntity}.
 * <p>
 * Infrastructure detail used only by the {@code SupplyRequestRepositoryImpl} adapter.
 * </p>
 */
@Repository
public interface SupplyRequestPersistenceRepository extends JpaRepository<SupplyRequestPersistenceEntity, Long> {

    List<SupplyRequestPersistenceEntity> findAllByMinimarketId(MinimarketId minimarketId);

    List<SupplyRequestPersistenceEntity> findAllBySupplierId(SupplierId supplierId);
}
