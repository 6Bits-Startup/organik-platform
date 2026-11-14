package _5bits.organik.platform.inventory.domain.model.aggregates;

import _5bits.organik.platform.inventory.domain.model.commands.CreateOfferCommand;
import _5bits.organik.platform.inventory.domain.model.valueobjects.OfferPrice;
import _5bits.organik.platform.inventory.domain.model.valueobjects.StockLotId;
import _5bits.organik.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Offer aggregate root.
 * <p>
 * Represents a special price applied to a stock lot during a period, usually to sell it before it expires.
 * </p>
 */
@Getter
public class Offer extends AbstractDomainAggregateRoot<Offer> {
    private Long id;
    private StockLotId stockLotId;
    private OfferPrice price;
    private LocalDate startsOn;
    private LocalDate endsOn;

    public Offer(StockLotId stockLotId, OfferPrice price, LocalDate startsOn, LocalDate endsOn) {
        this.stockLotId = Objects.requireNonNull(stockLotId, "stockLotId must not be null");
        this.price = Objects.requireNonNull(price, "price must not be null");
        this.startsOn = Objects.requireNonNull(startsOn, "startsOn must not be null");
        this.endsOn = Objects.requireNonNull(endsOn, "endsOn must not be null");
    }

    public Offer(CreateOfferCommand command) {
        this(command.stockLotId(), command.price(), command.startsOn(), command.endsOn());
    }

    public static Offer reconstitute(Long id, StockLotId stockLotId, OfferPrice price, LocalDate startsOn, LocalDate endsOn) {
        var offer = new Offer(stockLotId, price, startsOn, endsOn);
        offer.id = id;
        return offer;
    }

    /**
     * @param date date to evaluate
     * @return {@code true} when the offer is active on the given date
     */
    public boolean isActiveOn(LocalDate date) {
        return !date.isBefore(startsOn) && !date.isAfter(endsOn);
    }
}
