package _5bits.organik.platform.inventory.infrastructure.persistence.jpa.adapters;

import _5bits.organik.platform.inventory.domain.model.aggregates.Offer;
import _5bits.organik.platform.inventory.domain.model.valueobjects.StockLotId;
import _5bits.organik.platform.inventory.domain.repositories.OfferRepository;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.assemblers.OfferPersistenceAssembler;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.repositories.OfferPersistenceRepository;
import _5bits.organik.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Infrastructure adapter that implements the {@link OfferRepository} domain port with Spring Data JPA.
 * <p>
 * Maps between domain and persistence models through {@link OfferPersistenceAssembler} and publishes
 * the domain events registered by the aggregate after it is persisted.
 * </p>
 */
@Repository
public class OfferRepositoryImpl implements OfferRepository {
    private final OfferPersistenceRepository offerPersistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public OfferRepositoryImpl(
            OfferPersistenceRepository offerPersistenceRepository,
            DomainEventPublisher domainEventPublisher) {
        this.offerPersistenceRepository = offerPersistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public Optional<Offer> findById(Long id) {
        return offerPersistenceRepository.findById(id).map(OfferPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Offer> findAll() {
        return offerPersistenceRepository.findAll().stream()
                .map(OfferPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<Offer> findAllByStockLotId(StockLotId stockLotId) {
        return offerPersistenceRepository.findAllByStockLotId(stockLotId).stream()
                .map(OfferPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Offer save(Offer offer) {
        var saved = offerPersistenceRepository.save(OfferPersistenceAssembler.toPersistenceFromDomain(offer));
        var savedOffer = OfferPersistenceAssembler.toDomainFromPersistence(saved);
        domainEventPublisher.publishAndClearEvents(offer);
        return savedOffer;
    }

    @Override
    public boolean existsById(Long id) {
        return offerPersistenceRepository.existsById(id);
    }
}
