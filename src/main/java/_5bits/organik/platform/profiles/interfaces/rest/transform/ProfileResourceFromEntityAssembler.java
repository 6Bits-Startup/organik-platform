package _5bits.organik.platform.profiles.interfaces.rest.transform;

import _5bits.organik.platform.profiles.domain.model.aggregates.Profile;
import _5bits.organik.platform.profiles.interfaces.rest.resources.ProfileResource;


/**
 * Assembler that converts a {@link Profile} aggregate into a {@link ProfileResource}.
 */
public class ProfileResourceFromEntityAssembler {
    public static ProfileResource toResourceFromEntity(Profile entity) {
        return new ProfileResource(
                entity.getId(),
                entity.getUserId().value(),
                entity.getFullName().value(),
                entity.getEmail().value(),
                entity.getMinimarketId() == null ? null : entity.getMinimarketId().value(),
                entity.getSupplierId() == null ? null : entity.getSupplierId().value(),
                entity.getPreferences().language().value(),
                entity.getPreferences().warningExpirationDays().days(),
                entity.getPreferences().criticalExpirationDays().days());
    }
}
