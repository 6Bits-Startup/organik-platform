package _5bits.organik.platform.products.domain.model.aggregates;

import _5bits.organik.platform.products.domain.model.commands.CreateCategoryCommand;
import _5bits.organik.platform.products.domain.model.valueobjects.CategoryName;
import _5bits.organik.platform.products.domain.model.valueobjects.CategoryTone;
import _5bits.organik.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.util.Objects;

/**
 * Category aggregate root.
 * <p>
 * Groups the organic products of the catalog (fruits and vegetables, dairy, grains, etc.).
 * </p>
 */
@Getter
public class Category extends AbstractDomainAggregateRoot<Category> {
    private Long id;
    private CategoryName name;
    private CategoryTone tone;

    public Category(CategoryName name, CategoryTone tone) {
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.tone = Objects.requireNonNull(tone, "tone must not be null");
    }

    public Category(CreateCategoryCommand command) {
        this(command.name(), command.tone());
    }

    public static Category reconstitute(Long id, CategoryName name, CategoryTone tone) {
        var category = new Category(name, tone);
        category.id = id;
        return category;
    }
}
