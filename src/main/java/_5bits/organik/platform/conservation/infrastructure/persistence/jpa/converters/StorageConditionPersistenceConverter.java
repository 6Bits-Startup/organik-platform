package _5bits.organik.platform.conservation.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.conservation.domain.model.valueobjects.StorageCondition;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * JPA attribute converter for the {@link StorageCondition} value object.
 * <p>
 * Keeps the persistence model aligned with the domain model by converting the value object
 * into its database column representation and back.
 * </p>
 */
@Converter(autoApply = false)
public class StorageConditionPersistenceConverter implements AttributeConverter<StorageCondition, String> {

    /**
     * Converts the value object into its database column value.
     *
     * @param attribute value object, may be {@code null}
     * @return the column value, or {@code null} when the attribute is {@code null}
     */
    @Override
    public String convertToDatabaseColumn(StorageCondition attribute) {
        return attribute == null ? null : attribute.value();
    }

    /**
     * Converts the database column value into the value object.
     *
     * @param dbData column value, may be {@code null}
     * @return the value object, or {@code null} when the column value is {@code null}
     */
    @Override
    public StorageCondition convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new StorageCondition(dbData);
    }
}
