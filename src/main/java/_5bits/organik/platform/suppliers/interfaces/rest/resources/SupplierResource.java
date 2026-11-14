package _5bits.organik.platform.suppliers.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Response resource representing a supplier.
 */
@Schema(name = "SupplierResponse", description = "Supplier information response")
public record SupplierResource(
    @Schema(description = "Supplier unique identifier", example = "1")
    Long id,
    @Schema(description = "Business name", example = "BioAndes S.A.C.")
    String businessName,
    @Schema(description = "Contact person", example = "Marco Quispe")
    String contactName,
    @Schema(description = "Phone number", example = "+51 987 654 321")
    String phoneNumber,
    @Schema(description = "Email address", example = "ventas@bioandes.pe")
    String email,
    @Schema(description = "Product categories supplied", example = "[\"DAIRY\"]")
    List<String> categories,
    @Schema(description = "Organic certification", example = "SENASA ORG-2026")
    String organicCertification
) {
}
