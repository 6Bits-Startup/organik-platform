package _5bits.organik.platform.profiles.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response resource representing a profile.
 */
@Schema(name = "ProfileResponse", description = "Profile information response")
public record ProfileResource(
    @Schema(description = "Profile unique identifier", example = "1")
    Long id,
    @Schema(description = "IAM user that owns the profile", example = "1")
    Long userId,
    @Schema(description = "Full name", example = "Albino Rojas")
    String fullName,
    @Schema(description = "Email address", example = "albino@organik.pe")
    String email,
    @Schema(description = "Minimarket where the user works", example = "mm-vida-verde")
    String minimarketId,
    @Schema(description = "Supplier the user belongs to", example = "sup-bioandes")
    String supplierId,
    @Schema(description = "Preferred language", example = "EN")
    String language,
    @Schema(description = "Days before expiration that raise a warning alert", example = "15")
    Integer warningExpirationDays,
    @Schema(description = "Days before expiration that raise a critical alert", example = "5")
    Integer criticalExpirationDays
) {
}
