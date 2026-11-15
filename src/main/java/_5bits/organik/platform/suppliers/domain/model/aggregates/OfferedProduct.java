package _5bits.organik.platform.suppliers.domain.model.aggregates;

import _5bits.organik.platform.suppliers.domain.model.commands.CreateOfferedProductCommand;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.LotCode;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.ProductId;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.Quantity;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.SupplierId;
import _5bits.organik.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Offered product aggregate root.
 * <p>
 * A lot of a product that a supplier offers in its catalog to the minimarkets.
 * </p>
 */
@Getter
public class OfferedProduct extends AbstractDomainAggregateRoot<OfferedProduct> {
    private Long id;
    private SupplierId supplierId;
    private ProductId productId;
    private LotCode lotCode;
    private Quantity availableQuantity;
    private LocalDate expiresOn;

    public OfferedProduct(SupplierId supplierId, ProductId productId, LotCode lotCode, Quantity availableQuantity, LocalDate expiresOn) {
        this.supplierId = Objects.requireNonNull(supplierId, "supplierId must not be null");
        this.productId = Objects.requireNonNull(productId, "productId must not be null");
        this.lotCode = Objects.requireNonNull(lotCode, "lotCode must not be null");
        this.availableQuantity = Objects.requireNonNull(availableQuantity, "availableQuantity must not be null");
        this.expiresOn = Objects.requireNonNull(expiresOn, "expiresOn must not be null");
    }

    public OfferedProduct(CreateOfferedProductCommand command) {
        this(command.supplierId(), command.productId(), command.lotCode(), command.availableQuantity(), command.expiresOn());
    }

    public static OfferedProduct reconstitute(Long id, SupplierId supplierId, ProductId productId, LotCode lotCode,
                                              Quantity availableQuantity, LocalDate expiresOn) {
        var offeredProduct = new OfferedProduct(supplierId, productId, lotCode, availableQuantity, expiresOn);
        offeredProduct.id = id;
        return offeredProduct;
    }

    /**
     * Updates the lot, the availability and the expiration date of the offered product.
     *
     * @param lotCode new lot code
     * @param availableQuantity new available quantity
     * @param expiresOn new expiration date of the lot
     * @return the updated offered product
     */
    public OfferedProduct updateInformation(LotCode lotCode, Quantity availableQuantity, LocalDate expiresOn) {
        this.lotCode = Objects.requireNonNull(lotCode, "lotCode must not be null");
        this.availableQuantity = Objects.requireNonNull(availableQuantity, "availableQuantity must not be null");
        this.expiresOn = Objects.requireNonNull(expiresOn, "expiresOn must not be null");
        return this;
    }

    /**
     * Decreases the availability of the offered product by the units shipped to a minimarket.
     * The availability never drops below zero.
     *
     * @param shippedQuantity units shipped in an accepted shipment order
     * @return the updated offered product
     */
    public OfferedProduct decreaseAvailability(Quantity shippedQuantity) {
        Objects.requireNonNull(shippedQuantity, "shippedQuantity must not be null");
        this.availableQuantity = new Quantity(Math.max(0, availableQuantity.value() - shippedQuantity.value()));
        return this;
    }
}
