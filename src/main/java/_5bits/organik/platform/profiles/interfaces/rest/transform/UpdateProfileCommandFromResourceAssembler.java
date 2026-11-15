package _5bits.organik.platform.profiles.interfaces.rest.transform;

import _5bits.organik.platform.profiles.domain.model.commands.UpdateProfileCommand;
import _5bits.organik.platform.profiles.domain.model.valueobjects.FullName;
import _5bits.organik.platform.profiles.interfaces.rest.resources.UpdateProfileResource;

/**
 * Assembler that converts an {@link UpdateProfileResource} into an {@link UpdateProfileCommand}.
 */
public class UpdateProfileCommandFromResourceAssembler {
    public static UpdateProfileCommand toCommandFromResource(Long profileId, UpdateProfileResource resource) {
        return new UpdateProfileCommand(profileId, new FullName(resource.fullName()));
    }
}
