package _5bits.organik.platform.profiles.infrastructure.persistence.jpa.assemblers;

import _5bits.organik.platform.profiles.domain.model.aggregates.Profile;
import _5bits.organik.platform.profiles.domain.model.valueobjects.ExpirationThreshold;
import _5bits.organik.platform.profiles.domain.model.valueobjects.Language;
import _5bits.organik.platform.profiles.domain.model.valueobjects.Preferences;
import _5bits.organik.platform.profiles.infrastructure.persistence.jpa.embeddables.PreferencesPersistenceEmbeddable;
import _5bits.organik.platform.profiles.infrastructure.persistence.jpa.entities.ProfilePersistenceEntity;

/**
 * Assembler that maps between the {@link Profile} domain aggregate and its JPA persistence entity.
 * <p>
 * Acts as the anti-corruption layer of the persistence adapter: JPA concerns never leak into the domain.
 * </p>
 */
public final class ProfilePersistenceAssembler {

    private ProfilePersistenceAssembler() {
    }

    public static Profile toDomainFromPersistence(ProfilePersistenceEntity entity) {
        if (entity == null) return null;
        return Profile.reconstitute(
                entity.getId(),
                entity.getUserId(),
                entity.getFullName(),
                entity.getEmail(),
                entity.getMinimarketId(),
                entity.getSupplierId(),
                toDomainFromPersistence(entity.getPreferences()));
    }

    public static ProfilePersistenceEntity toPersistenceFromDomain(Profile profile) {
        if (profile == null) return null;
        var entity = new ProfilePersistenceEntity();
        if (profile.getId() != null) {
            entity.setId(profile.getId());
        }
        entity.setUserId(profile.getUserId());
        entity.setFullName(profile.getFullName());
        entity.setEmail(profile.getEmail());
        entity.setMinimarketId(profile.getMinimarketId());
        entity.setSupplierId(profile.getSupplierId());
        entity.setPreferences(toPersistenceFromDomain(profile.getPreferences()));
        return entity;
    }

    private static Preferences toDomainFromPersistence(PreferencesPersistenceEmbeddable value) {
        return value == null ? null : new Preferences(
                new Language(value.getLanguage()),
                new ExpirationThreshold(value.getWarningExpirationDays()),
                new ExpirationThreshold(value.getCriticalExpirationDays()));
    }

    private static PreferencesPersistenceEmbeddable toPersistenceFromDomain(Preferences value) {
        return value == null ? null : new PreferencesPersistenceEmbeddable(
                value.language().value(),
                value.warningExpirationDays().days(),
                value.criticalExpirationDays().days());
    }
}
