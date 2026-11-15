package _5bits.organik.platform.profiles.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

/**
 * Request resource used to update the preferences of a profile.
 */
@Schema(name = "UpdateProfilePreferencesRequest", description = "Request payload for updating the preferences of a profile")
public record UpdateProfilePreferencesResource(
    @Schema(description = "Preferred language", example = "ES", allowableValues = {"EN", "ES"})
    @NotBlank(message = "{profile.error.language.notBlank}")
    @Pattern(regexp = "^(EN|ES)$", message = "{profile.error.language.pattern}")
    String language,

    @Schema(description = "Days before expiration that raise a warning alert", example = "15", minimum = "1", maximum = "365")
    @NotNull(message = "{profile.error.expirationDays.notNull}")
    @Min(value = 1, message = "{profile.error.expirationDays.range}")
    @Max(value = 365, message = "{profile.error.expirationDays.range}")
    Integer warningExpirationDays,

    @Schema(description = "Days before expiration that raise a critical alert", example = "5", minimum = "1", maximum = "365")
    @NotNull(message = "{profile.error.expirationDays.notNull}")
    @Min(value = 1, message = "{profile.error.expirationDays.range}")
    @Max(value = 365, message = "{profile.error.expirationDays.range}")
    Integer criticalExpirationDays
) {
}
