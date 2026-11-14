package _5bits.organik.platform.inventory.infrastructure.persistence.jpa.entities;

import _5bits.organik.platform.inventory.domain.model.valueobjects.OfferPrice;
import _5bits.organik.platform.inventory.domain.model.valueobjects.StockLotId;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.converters.OfferPricePersistenceConverter;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.converters.StockLotIdPersistenceConverter;
import _5bits.organik.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * JPA persistence entity for the Offer aggregate.
 * <p>
 * Pure persistence model: it carries no business behavior. Mapping to and from the
 * {@code Offer} domain aggregate is done by {@code OfferPersistenceAssembler}.
 * </p>
 */
@Entity
@Table(name = "offers")
@Getter
@Setter
@NoArgsConstructor
public class OfferPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = StockLotIdPersistenceConverter.class)
    @Column(name = "stock_lot_id", nullable = false)
    private StockLotId stockLotId;

    @Convert(converter = OfferPricePersistenceConverter.class)
    @Column(name = "price", nullable = false)
    private OfferPrice price;

    @Column(name = "starts_on", nullable = false)
    private LocalDate startsOn;

    @Column(name = "ends_on", nullable = false)
    private LocalDate endsOn;
}
