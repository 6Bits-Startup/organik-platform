package _5bits.organik.platform.profiles.domain.repositories;

import _5bits.organik.platform.profiles.domain.model.aggregates.Profile;
import _5bits.organik.platform.profiles.domain.model.valueobjects.EmailAddress;
import _5bits.organik.platform.profiles.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.profiles.domain.model.valueobjects.UserId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository port for the {@link Profile} aggregate.
 * <p>
 * Pure Java interface with no framework dependencies. The infrastructure layer provides the
 * adapter that implements it with Spring Data JPA.
 * </p>
 */
public interface ProfileRepository {

    Optional<Profile> findById(Long id);

    List<Profile> findAll();

    List<Profile> findAllByUserId(UserId userId);

    List<Profile> findAllByMinimarketId(MinimarketId minimarketId);

    boolean existsByUserId(UserId userId);

    boolean existsByEmail(EmailAddress email);

    Profile save(Profile profile);

    boolean existsById(Long id);
}
