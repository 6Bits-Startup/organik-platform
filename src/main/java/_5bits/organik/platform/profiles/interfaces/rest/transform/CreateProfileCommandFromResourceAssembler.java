package _5bits.organik.platform.profiles.interfaces.rest.transform;

import _5bits.organik.platform.profiles.domain.model.commands.CreateProfileCommand;
import _5bits.organik.platform.profiles.interfaces.rest.resources.CreateProfileResource;
import _5bits.organik.platform.profiles.domain.model.valueobjects.*;

/**
 * Assembler that converts a {@link CreateProfileResource} into a {@link CreateProfileCommand}.
 */
public class CreateProfileCommandFromResourceAssembler {
    public static CreateProfileCommand toCommandFromResource(CreateProfileResource resource) {
        return new CreateProfileCommand(
                new UserId(resource.userId()),
                new FullName(resource.fullName()),
                new EmailAddress(resource.email()),
                resource.minimarketId() == null || resource.minimarketId().isBlank() ? null : new MinimarketId(resource.minimarketId()),
                resource.supplierId() == null || resource.supplierId().isBlank() ? null : new SupplierId(resource.supplierId()));
    }
}
