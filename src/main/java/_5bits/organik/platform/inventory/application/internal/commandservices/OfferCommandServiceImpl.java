package _5bits.organik.platform.inventory.application.internal.commandservices;

import _5bits.organik.platform.inventory.application.commandservices.OfferCommandService;
import _5bits.organik.platform.inventory.domain.model.aggregates.Offer;
import _5bits.organik.platform.inventory.domain.model.commands.CreateOfferCommand;
import _5bits.organik.platform.inventory.domain.repositories.OfferRepository;
import _5bits.organik.platform.inventory.domain.repositories.StockLotRepository;
import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Application service that handles Offer commands.
 */
@Service
public class OfferCommandServiceImpl implements OfferCommandService {
    private final OfferRepository offerRepository;
    private final StockLotRepository stockLotRepository;

    public OfferCommandServiceImpl(OfferRepository offerRepository, StockLotRepository stockLotRepository) {
        this.offerRepository = offerRepository;
        this.stockLotRepository = stockLotRepository;
    }

    @Override
    public Result<Long, ApplicationError> handle(CreateOfferCommand command) {
        var stockLot = stockLotRepository.findById(command.stockLotId().value());
        if (stockLot.isEmpty())
            return Result.failure(ApplicationError.notFound("StockLot", command.stockLotId().value().toString()));
        if (command.endsOn().isAfter(stockLot.get().getExpirationDate().value()))
            return Result.failure(ApplicationError.businessRuleViolation("offer-period",
                    "The offer cannot end after the expiration date of the stock lot"));
        var offer = new Offer(command);
        try {
            offer = offerRepository.save(offer);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-offer", e.getMessage()));
        }
        return Result.success(offer.getId());
    }
}
