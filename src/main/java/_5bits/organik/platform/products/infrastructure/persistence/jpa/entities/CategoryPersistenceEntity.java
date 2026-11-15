package _5bits.organik.platform.products.infrastructure.persistence.jpa.entities;

import _5bits.organik.platform.products.domain.model.valueobjects.CategoryName;
import _5bits.organik.platform.products.domain.model.valueobjects.CategoryTone;
import _5bits.organik.platform.products.infrastructure.persistence.jpa.converters.CategoryNamePersistenceConverter;
import _5bits.organik.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity for the Category aggregate.
 * <p>
 * Pure persistence model: it carries no business behavior. Mapping to and from the
 * {@code Category} domain aggregate is done by {@code CategoryPersistenceAssembler}.
 * </p>
 */
@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class CategoryPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = CategoryNamePersistenceConverter.class)
    @Column(name = "name", nullable = false, unique = true, length = 128)
    private CategoryName name;

    @Enumerated(EnumType.STRING)
    @Column(name = "tone", nullable = false, length = 20)
    private CategoryTone tone;
}
