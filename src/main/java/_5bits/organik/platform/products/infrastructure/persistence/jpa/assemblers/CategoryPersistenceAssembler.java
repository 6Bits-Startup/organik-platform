package _5bits.organik.platform.products.infrastructure.persistence.jpa.assemblers;

import _5bits.organik.platform.products.domain.model.aggregates.Category;
import _5bits.organik.platform.products.infrastructure.persistence.jpa.entities.CategoryPersistenceEntity;


/**
 * Assembler that maps between the {@link Category} domain aggregate and its JPA persistence entity.
 * <p>
 * Acts as the anti-corruption layer of the persistence adapter: JPA concerns never leak into the domain.
 * </p>
 */
public final class CategoryPersistenceAssembler {

    private CategoryPersistenceAssembler() {
    }

    /**
     * Reconstitutes a domain aggregate from its persistence entity.
     *
     * @param entity persistence entity, may be {@code null}
     * @return the reconstituted aggregate, or {@code null} when the entity is {@code null}
     */
    public static Category toDomainFromPersistence(CategoryPersistenceEntity entity) {
        if (entity == null) return null;
        return Category.reconstitute(
                entity.getId(),
                entity.getName(),
                entity.getTone());
    }

    /**
     * Maps a domain aggregate to a persistence entity.
     *
     * @param category domain aggregate, may be {@code null}
     * @return the persistence entity, or {@code null} when the aggregate is {@code null}
     */
    public static CategoryPersistenceEntity toPersistenceFromDomain(Category category) {
        if (category == null) return null;
        var entity = new CategoryPersistenceEntity();
        if (category.getId() != null) {
            entity.setId(category.getId());
        }
        entity.setName(category.getName());
        entity.setTone(category.getTone());
        return entity;
    }
}
