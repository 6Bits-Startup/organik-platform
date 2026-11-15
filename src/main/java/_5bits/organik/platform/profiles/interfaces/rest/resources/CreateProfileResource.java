package _5bits.organik.platform.profiles.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

/**
 * Request resource used to create a profile.
 */
@Schema(name = "CreateProfileRequest", description = "Request payload for creating the profile of an IAM user",
        example = "{\"userId\": 1, \"fullName\": \"Albino Rojas\", \"email\": \"albino@organik.pe\", \"minimarketId\": \"mm-vida-verde\"}")
public record CreateProfileResource(
    @Schema(description = "IAM user that owns the profile", example = "1")
    @NotNull(message = "{validation.not-blank}")
    @Positive(message = "{profile.error.userId.min}")
    Long userId,

    @Schema(description = "Full name", example = "Albino Rojas", maxLength = 256)
    @NotBlank(message = "{validation.not-blank}")
    @Size(max = 256, message = "{user.error.fullName.size}")
    String fullName,

    @Schema(description = "Email address", example = "albino@organik.pe", maxLength = 256)
    @NotBlank(message = "{validation.not-blank}")
    @Email(message = "{validation.email}")
    @Size(max = 256, message = "{user.error.email.size}")
    String email,

    @Schema(description = "Minimarket where the user works", example = "mm-vida-verde")
    @Size(max = 256, message = "{user.error.minimarketId.size}")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "{user.error.minimarketId.pattern}")
    String minimarketId,

    @Schema(description = "Supplier the user belongs to", example = "sup-bioandes")
    @Size(max = 256, message = "{user.error.supplierId.size}")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "{user.error.supplierId.pattern}")
    String supplierId
) {
}
