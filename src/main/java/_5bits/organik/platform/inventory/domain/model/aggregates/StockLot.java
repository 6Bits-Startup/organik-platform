package _5bits.organik.platform.inventory.domain.model.aggregates;

import _5bits.organik.platform.inventory.domain.model.commands.CreateStockLotCommand;
import _5bits.organik.platform.inventory.domain.model.events.StockLotLowStockDetectedEvent;
import _5bits.organik.platform.inventory.domain.model.valueobjects.*;
import _5bits.organik.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.util.Objects;

/**
 * Stock lot aggregate root.
 * <p>
 * Represents a lot of a product received by a minimarket, with its quantity, minimum stock and expiration date.
 * </p>
 */
@Getter
public class StockLot extends AbstractDomainAggregateRoot<StockLot> {
    private Long id;
    private MinimarketId minimarketId;
    private ProductId productId;
    private LotCode lotCode;
    private Quantity quantity;
    private Quantity minimumStock;
    private ExpirationDate expirationDate;

    public StockLot(MinimarketId minimarketId, ProductId productId, LotCode lotCode,
                    Quantity quantity, Quantity minimumStock, ExpirationDate expirationDate) {
        this.minimarketId = Objects.requireNonNull(minimarketId, "minimarketId must not be null");
        this.productId = Objects.requireNonNull(productId, "productId must not be null");
        this.lotCode = Objects.requireNonNull(lotCode, "lotCode must not be null");
        this.quantity = Objects.requireNonNull(quantity, "quantity must not be null");
        this.minimumStock = Objects.requireNonNull(minimumStock, "minimumStock must not be null");
        this.expirationDate = Objects.requireNonNull(expirationDate, "expirationDate must not be null");
    }

    public StockLot(CreateStockLotCommand command) {
        this(command.minimarketId(), command.productId(), command.lotCode(),
                command.quantity(), command.minimumStock(), command.expirationDate());
    }

    public static StockLot reconstitute(Long id, MinimarketId minimarketId, ProductId productId, LotCode lotCode,
                                        Quantity quantity, Quantity minimumStock, ExpirationDate expirationDate) {
        var stockLot = new StockLot(minimarketId, productId, lotCode, quantity, minimumStock, expirationDate);
        stockLot.id = id;
        return stockLot;
    }

    /**
     * @return {@code true} when the quantity is at or below the minimum stock
     */
    public boolean isLowStock() {
        return quantity.isLessThanOrEqualTo(minimumStock);
    }

    /**
     * Checks whether a movement can be applied without leaving a negative stock.
     *
     * @param type movement type
     * @param movementQuantity quantity of the movement
     * @return {@code true} when the movement can be applied
     */
    public boolean canApplyMovement(MovementType type, Quantity movementQuantity) {
        return !type.decreasesStock() || movementQuantity.isLessThanOrEqualTo(quantity);
    }

    /**
     * Applies an inventory movement to the lot and raises a low stock event when the lot reaches its minimum stock.
     *
     * @param type movement type
     * @param movementQuantity quantity of the movement
     */
    public void applyMovement(MovementType type, Quantity movementQuantity) {
        if (!canApplyMovement(type, movementQuantity)) {
            throw new IllegalArgumentException("Insufficient stock in lot %s".formatted(lotCode.value()));
        }
        this.quantity = switch (type) {
            case ENTRY -> quantity.plus(movementQuantity);
            case EXIT, WASTE -> quantity.minus(movementQuantity);
            case ADJUSTMENT -> movementQuantity;
        };
        if (isLowStock()) {
            registerDomainEvent(new StockLotLowStockDetectedEvent(
                    id, minimarketId.value(), productId.value(), lotCode.value(), quantity.value(), minimumStock.value()));
        }
    }
}
