package _5bits.organik.platform.profiles.infrastructure.persistence.jpa.repositories;

import _5bits.organik.platform.profiles.domain.model.valueobjects.EmailAddress;
import _5bits.organik.platform.profiles.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.profiles.domain.model.valueobjects.UserId;
import _5bits.organik.platform.profiles.infrastructure.persistence.jpa.entities.ProfilePersistenceEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link ProfilePersistenceEntity}.
 * <p>
 * Infrastructure detail used only by the {@code ProfileRepositoryImpl} adapter.
 * </p>
 */
@Repository
public interface ProfilePersistenceRepository extends JpaRepository<ProfilePersistenceEntity, Long> {

    List<ProfilePersistenceEntity> findAllByUserId(UserId userId);

    List<ProfilePersistenceEntity> findAllByMinimarketId(MinimarketId minimarketId);

    boolean existsByUserId(UserId userId);

    boolean existsByEmail(EmailAddress email);
}
