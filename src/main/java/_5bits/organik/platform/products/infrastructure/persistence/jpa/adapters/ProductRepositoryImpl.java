package _5bits.organik.platform.products.infrastructure.persistence.jpa.adapters;

import _5bits.organik.platform.products.domain.model.aggregates.Product;
import _5bits.organik.platform.products.domain.model.valueobjects.CategoryId;
import _5bits.organik.platform.products.domain.model.valueobjects.Sku;
import _5bits.organik.platform.products.domain.repositories.ProductRepository;
import _5bits.organik.platform.products.infrastructure.persistence.jpa.assemblers.ProductPersistenceAssembler;
import _5bits.organik.platform.products.infrastructure.persistence.jpa.repositories.ProductPersistenceRepository;
import _5bits.organik.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Infrastructure adapter that implements the {@link ProductRepository} domain port with Spring Data JPA.
 * <p>
 * Maps between domain and persistence models through {@link ProductPersistenceAssembler} and publishes
 * the domain events registered by the aggregate after it is persisted.
 * </p>
 */
@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductPersistenceRepository productPersistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public ProductRepositoryImpl(
            ProductPersistenceRepository productPersistenceRepository,
            DomainEventPublisher domainEventPublisher) {
        this.productPersistenceRepository = productPersistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productPersistenceRepository.findById(id).map(ProductPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Product> findAll() {
        return productPersistenceRepository.findAll().stream()
                .map(ProductPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<Product> findAllByCategoryId(CategoryId categoryId) {
        return productPersistenceRepository.findAllByCategoryId(categoryId).stream()
                .map(ProductPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean existsBySku(Sku sku) {
        return productPersistenceRepository.existsBySku(sku);
    }

    @Override
    public Product save(Product product) {
        var saved = productPersistenceRepository.save(ProductPersistenceAssembler.toPersistenceFromDomain(product));
        var savedProduct = ProductPersistenceAssembler.toDomainFromPersistence(saved);
        domainEventPublisher.publishAndClearEvents(product);
        return savedProduct;
    }

    @Override
    public boolean existsById(Long id) {
        return productPersistenceRepository.existsById(id);
    }
}
