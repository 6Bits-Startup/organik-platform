package _5bits.organik.platform.requisition.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * Request resource used to create a supply request.
 */
@Schema(name = "CreateSupplyRequestRequest", description = "Request payload for creating a supply request")
public record CreateSupplyRequestResource(
    @Schema(description = "Product to restock", example = "ORG-01")
    @NotBlank(message = "{supplyRequest.error.productId.notBlank}")
    @Size(max = 256, message = "{supplyRequest.error.productId.size}")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "{supplyRequest.error.productId.pattern}")
    String productId,

    @Schema(description = "Supplier asked for the restock", example = "sup-bioandes")
    @NotBlank(message = "{supplyRequest.error.supplierId.notBlank}")
    @Size(max = 256, message = "{supplyRequest.error.supplierId.size}")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "{supplyRequest.error.supplierId.pattern}")
    String supplierId,

    @Schema(description = "Minimarket that needs the restock", example = "mm-vida-verde")
    @NotBlank(message = "{supplyRequest.error.minimarketId.notBlank}")
    @Size(max = 256, message = "{supplyRequest.error.minimarketId.size}")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "{supplyRequest.error.minimarketId.pattern}")
    String minimarketId,

    @Schema(description = "Requested quantity", example = "30", minimum = "1")
    @NotNull(message = "{supplyRequest.error.quantity.notNull}")
    @Min(value = 1, message = "{supplyRequest.error.quantity.min}")
    Integer quantity,

    @Schema(description = "Reason of the restock", example = "Weekly demand", maxLength = 512)
    @NotBlank(message = "{supplyRequest.error.requestReason.notBlank}")
    @Size(max = 512, message = "{supplyRequest.error.requestReason.size}")
    String reason,

    @Schema(description = "Date the products are required", example = "2026-11-20")
    @NotNull(message = "{supplyRequest.error.requiredDate.notNull}")
    LocalDate requiredDate,

    @Schema(description = "Priority of the request", example = "HIGH", allowableValues = {"LOW", "MEDIUM", "HIGH"})
    @NotBlank(message = "{supplyRequest.error.priority.notBlank}")
    @Pattern(regexp = "^(LOW|MEDIUM|HIGH)$", message = "{supplyRequest.error.priority.pattern}")
    String priority
) {
}
