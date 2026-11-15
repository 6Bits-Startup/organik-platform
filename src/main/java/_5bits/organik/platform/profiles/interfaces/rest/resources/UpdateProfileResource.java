package _5bits.organik.platform.profiles.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request resource used to update the personal information of a profile.
 */
@Schema(name = "UpdateProfileRequest", description = "Request payload for updating the personal information of a profile")
public record UpdateProfileResource(
    @Schema(description = "Full name", example = "Albino Rojas", maxLength = 256)
    @NotBlank(message = "{validation.not-blank}")
    @Size(max = 256, message = "{user.error.fullName.size}")
    String fullName
) {
}
