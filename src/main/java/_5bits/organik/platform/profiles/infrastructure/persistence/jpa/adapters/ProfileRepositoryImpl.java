package _5bits.organik.platform.profiles.infrastructure.persistence.jpa.adapters;

import _5bits.organik.platform.profiles.domain.model.aggregates.Profile;
import _5bits.organik.platform.profiles.domain.model.valueobjects.EmailAddress;
import _5bits.organik.platform.profiles.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.profiles.domain.model.valueobjects.UserId;
import _5bits.organik.platform.profiles.domain.repositories.ProfileRepository;
import _5bits.organik.platform.profiles.infrastructure.persistence.jpa.assemblers.ProfilePersistenceAssembler;
import _5bits.organik.platform.profiles.infrastructure.persistence.jpa.repositories.ProfilePersistenceRepository;
import _5bits.organik.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Infrastructure adapter that implements the {@link ProfileRepository} domain port with Spring Data JPA.
 * <p>
 * Maps between domain and persistence models through {@link ProfilePersistenceAssembler} and publishes
 * the domain events registered by the aggregate after it is persisted.
 * </p>
 */
@Repository
public class ProfileRepositoryImpl implements ProfileRepository {
    private final ProfilePersistenceRepository profilePersistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public ProfileRepositoryImpl(
            ProfilePersistenceRepository profilePersistenceRepository,
            DomainEventPublisher domainEventPublisher) {
        this.profilePersistenceRepository = profilePersistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public Optional<Profile> findById(Long id) {
        return profilePersistenceRepository.findById(id).map(ProfilePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Profile> findAll() {
        return profilePersistenceRepository.findAll().stream()
                .map(ProfilePersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<Profile> findAllByUserId(UserId userId) {
        return profilePersistenceRepository.findAllByUserId(userId).stream()
                .map(ProfilePersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<Profile> findAllByMinimarketId(MinimarketId minimarketId) {
        return profilePersistenceRepository.findAllByMinimarketId(minimarketId).stream()
                .map(ProfilePersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean existsByUserId(UserId userId) {
        return profilePersistenceRepository.existsByUserId(userId);
    }

    @Override
    public boolean existsByEmail(EmailAddress email) {
        return profilePersistenceRepository.existsByEmail(email);
    }

    @Override
    public Profile save(Profile profile) {
        var saved = profilePersistenceRepository.save(ProfilePersistenceAssembler.toPersistenceFromDomain(profile));
        var savedProfile = ProfilePersistenceAssembler.toDomainFromPersistence(saved);
        domainEventPublisher.publishAndClearEvents(profile);
        return savedProfile;
    }

    @Override
    public boolean existsById(Long id) {
        return profilePersistenceRepository.existsById(id);
    }
}
