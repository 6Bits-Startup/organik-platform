package _5bits.organik.platform.inventory.application.internal.queryservices;

import _5bits.organik.platform.inventory.application.queryservices.OfferQueryService;
import _5bits.organik.platform.inventory.domain.model.aggregates.Offer;
import _5bits.organik.platform.inventory.domain.model.queries.GetAllOffersByStockLotIdQuery;
import _5bits.organik.platform.inventory.domain.model.queries.GetOfferByIdQuery;
import _5bits.organik.platform.inventory.domain.repositories.OfferRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service that handles Offer queries.
 */
@Service
public class OfferQueryServiceImpl implements OfferQueryService {
    private final OfferRepository offerRepository;

    public OfferQueryServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public Optional<Offer> handle(GetOfferByIdQuery query) {
        return offerRepository.findById(query.id());
    }

    @Override
    public List<Offer> handle(GetAllOffersByStockLotIdQuery query) {
        return offerRepository.findAllByStockLotId(query.stockLotId());
    }
}
