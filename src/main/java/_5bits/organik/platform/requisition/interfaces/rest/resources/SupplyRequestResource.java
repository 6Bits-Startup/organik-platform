package _5bits.organik.platform.requisition.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

/**
 * Response resource representing a supply request.
 */
@Schema(name = "SupplyRequestResponse", description = "Supply request information response")
public record SupplyRequestResource(
    @Schema(description = "Supply request unique identifier", example = "1")
    Long id,
    @Schema(description = "Product to restock", example = "ORG-01")
    String productId,
    @Schema(description = "Supplier asked for the restock", example = "sup-bioandes")
    String supplierId,
    @Schema(description = "Minimarket that needs the restock", example = "mm-vida-verde")
    String minimarketId,
    @Schema(description = "Requested quantity", example = "30")
    Integer quantity,
    @Schema(description = "Reason of the restock", example = "Weekly demand")
    String reason,
    @Schema(description = "Date the products are required", example = "2026-11-20")
    LocalDate requiredDate,
    @Schema(description = "Priority of the request", example = "HIGH")
    String priority,
    @Schema(description = "Status of the request", example = "PENDING", allowableValues = {"PENDING", "ACCEPTED", "REJECTED"})
    String status
) {
}
