package _5bits.organik.platform.procurements.domain.model.entities;

import _5bits.organik.platform.procurements.domain.model.valueobjects.LotCode;
import _5bits.organik.platform.procurements.domain.model.valueobjects.ProductId;
import _5bits.organik.platform.procurements.domain.model.valueobjects.Quantity;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Shipment order line entity.
 * <p>
 * A product lot included in a shipment order. It is only accessed through the {@code ShipmentOrder} aggregate root.
 * </p>
 */
@Getter
public class ShipmentOrderLine {
    private Long id;
    private ProductId productId;
    private Quantity quantity;
    private LotCode lotCode;
    private LocalDate expiresOn;

    public ShipmentOrderLine(ProductId productId, Quantity quantity, LotCode lotCode, LocalDate expiresOn) {
        this.productId = Objects.requireNonNull(productId, "productId must not be null");
        this.quantity = Objects.requireNonNull(quantity, "quantity must not be null");
        this.lotCode = Objects.requireNonNull(lotCode, "lotCode must not be null");
        this.expiresOn = Objects.requireNonNull(expiresOn, "expiresOn must not be null");
    }

    public static ShipmentOrderLine reconstitute(Long id, ProductId productId, Quantity quantity, LotCode lotCode, LocalDate expiresOn) {
        var line = new ShipmentOrderLine(productId, quantity, lotCode, expiresOn);
        line.id = id;
        return line;
    }
}
