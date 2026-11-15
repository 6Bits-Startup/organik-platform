package _5bits.organik.platform.inventory.infrastructure.persistence.jpa.entities;

import _5bits.organik.platform.inventory.domain.model.valueobjects.ExpirationDate;
import _5bits.organik.platform.inventory.domain.model.valueobjects.LotCode;
import _5bits.organik.platform.inventory.domain.model.valueobjects.LotLocation;
import _5bits.organik.platform.inventory.domain.model.valueobjects.LotNotes;
import _5bits.organik.platform.inventory.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.inventory.domain.model.valueobjects.ProductId;
import _5bits.organik.platform.inventory.domain.model.valueobjects.Quantity;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.converters.ExpirationDatePersistenceConverter;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.converters.LotCodePersistenceConverter;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.converters.LotLocationPersistenceConverter;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.converters.LotNotesPersistenceConverter;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.converters.MinimarketIdPersistenceConverter;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.converters.ProductIdPersistenceConverter;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.converters.QuantityPersistenceConverter;
import _5bits.organik.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity for the StockLot aggregate.
 * <p>
 * Pure persistence model: it carries no business behavior. Mapping to and from the
 * {@code StockLot} domain aggregate is done by {@code StockLotPersistenceAssembler}.
 * </p>
 */
@Entity
@Table(name = "stock_lots", uniqueConstraints = {
        @UniqueConstraint(name = "uk_stock_lot_minimarket_lot_code", columnNames = {"minimarket_id", "lot_code"})
})
@Getter
@Setter
@NoArgsConstructor
public class StockLotPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = MinimarketIdPersistenceConverter.class)
    @Column(name = "minimarket_id", nullable = false)
    private MinimarketId minimarketId;

    @Convert(converter = ProductIdPersistenceConverter.class)
    @Column(name = "product_id", nullable = false)
    private ProductId productId;

    @Convert(converter = LotCodePersistenceConverter.class)
    @Column(name = "lot_code", nullable = false, length = 32)
    private LotCode lotCode;

    @Convert(converter = QuantityPersistenceConverter.class)
    @Column(name = "quantity", nullable = false)
    private Quantity quantity;

    @Convert(converter = QuantityPersistenceConverter.class)
    @Column(name = "minimum_stock", nullable = false)
    private Quantity minimumStock;

    @Convert(converter = ExpirationDatePersistenceConverter.class)
    @Column(name = "expiration_date", nullable = false)
    private ExpirationDate expirationDate;

    @Convert(converter = LotLocationPersistenceConverter.class)
    @Column(name = "location", nullable = false, length = 100)
    private LotLocation location;

    @Convert(converter = LotNotesPersistenceConverter.class)
    @Column(name = "notes", nullable = false, length = 500)
    private LotNotes notes;
}
