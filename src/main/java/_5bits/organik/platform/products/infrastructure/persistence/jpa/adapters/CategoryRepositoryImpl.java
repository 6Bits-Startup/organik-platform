package _5bits.organik.platform.products.infrastructure.persistence.jpa.adapters;

import _5bits.organik.platform.products.domain.model.aggregates.Category;
import _5bits.organik.platform.products.domain.model.valueobjects.CategoryName;
import _5bits.organik.platform.products.domain.repositories.CategoryRepository;
import _5bits.organik.platform.products.infrastructure.persistence.jpa.assemblers.CategoryPersistenceAssembler;
import _5bits.organik.platform.products.infrastructure.persistence.jpa.repositories.CategoryPersistenceRepository;
import _5bits.organik.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Infrastructure adapter that implements the {@link CategoryRepository} domain port with Spring Data JPA.
 * <p>
 * Maps between domain and persistence models through {@link CategoryPersistenceAssembler} and publishes
 * the domain events registered by the aggregate after it is persisted.
 * </p>
 */
@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    private final CategoryPersistenceRepository categoryPersistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public CategoryRepositoryImpl(
            CategoryPersistenceRepository categoryPersistenceRepository,
            DomainEventPublisher domainEventPublisher) {
        this.categoryPersistenceRepository = categoryPersistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryPersistenceRepository.findById(id).map(CategoryPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Category> findAll() {
        return categoryPersistenceRepository.findAll().stream()
                .map(CategoryPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean existsByName(CategoryName name) {
        return categoryPersistenceRepository.existsByName(name);
    }

    @Override
    public Category save(Category category) {
        var saved = categoryPersistenceRepository.save(CategoryPersistenceAssembler.toPersistenceFromDomain(category));
        var savedCategory = CategoryPersistenceAssembler.toDomainFromPersistence(saved);
        domainEventPublisher.publishAndClearEvents(category);
        return savedCategory;
    }

    @Override
    public boolean existsById(Long id) {
        return categoryPersistenceRepository.existsById(id);
    }
}
