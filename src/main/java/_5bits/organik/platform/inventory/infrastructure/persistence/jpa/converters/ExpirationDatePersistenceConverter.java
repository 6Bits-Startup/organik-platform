package _5bits.organik.platform.inventory.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.inventory.domain.model.valueobjects.ExpirationDate;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalDate;

/**
 * JPA attribute converter for the {@link ExpirationDate} value object.
 * <p>
 * Keeps the persistence model aligned with the domain model by converting the value object
 * into its database column representation and back.
 * </p>
 */
@Converter(autoApply = false)
public class ExpirationDatePersistenceConverter implements AttributeConverter<ExpirationDate, LocalDate> {

    /**
     * Converts the value object into its database column value.
     *
     * @param attribute value object, may be {@code null}
     * @return the column value, or {@code null} when the attribute is {@code null}
     */
    @Override
    public LocalDate convertToDatabaseColumn(ExpirationDate attribute) {
        return attribute == null ? null : attribute.value();
    }

    /**
     * Converts the database column value into the value object.
     *
     * @param dbData column value, may be {@code null}
     * @return the value object, or {@code null} when the column value is {@code null}
     */
    @Override
    public ExpirationDate convertToEntityAttribute(LocalDate dbData) {
        return dbData == null ? null : new ExpirationDate(dbData);
    }
}
