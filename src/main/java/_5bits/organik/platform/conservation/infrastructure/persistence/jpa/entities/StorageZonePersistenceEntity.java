package _5bits.organik.platform.conservation.infrastructure.persistence.jpa.entities;

import _5bits.organik.platform.conservation.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.conservation.domain.model.valueobjects.StorageCondition;
import _5bits.organik.platform.conservation.domain.model.valueobjects.ZoneName;
import _5bits.organik.platform.conservation.infrastructure.persistence.jpa.converters.MinimarketIdPersistenceConverter;
import _5bits.organik.platform.conservation.infrastructure.persistence.jpa.converters.StorageConditionPersistenceConverter;
import _5bits.organik.platform.conservation.infrastructure.persistence.jpa.converters.ZoneNamePersistenceConverter;
import _5bits.organik.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import _5bits.organik.platform.conservation.infrastructure.persistence.jpa.embeddables.HumidityRangePersistenceEmbeddable;
import _5bits.organik.platform.conservation.infrastructure.persistence.jpa.embeddables.TemperatureRangePersistenceEmbeddable;

/**
 * JPA persistence entity for the StorageZone aggregate.
 * <p>
 * Pure persistence model: it carries no business behavior. Mapping to and from the
 * {@code StorageZone} domain aggregate is done by {@code StorageZonePersistenceAssembler}.
 * </p>
 */
@Entity
@Table(name = "storage_zones", uniqueConstraints = {
        @UniqueConstraint(name = "uk_storage_zone_minimarket_name", columnNames = {"minimarket_id", "name"})
})
@Getter
@Setter
@NoArgsConstructor
public class StorageZonePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = MinimarketIdPersistenceConverter.class)

    @Column(name = "minimarket_id", nullable = false)

    private MinimarketId minimarketId;


    @Convert(converter = ZoneNamePersistenceConverter.class)

    @Column(name = "name", nullable = false, length = 100)
    private ZoneName name;

    @Convert(converter = StorageConditionPersistenceConverter.class)
    @Column(name = "storage_condition", nullable = false, length = 30)
    private StorageCondition storageCondition;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "minTemperature", column = @Column(name = "min_temperature", nullable = false)),
            @AttributeOverride(name = "maxTemperature", column = @Column(name = "max_temperature", nullable = false))})
    private TemperatureRangePersistenceEmbeddable temperatureRange;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "minHumidity", column = @Column(name = "min_humidity", nullable = false)),
            @AttributeOverride(name = "maxHumidity", column = @Column(name = "max_humidity", nullable = false))})
    private HumidityRangePersistenceEmbeddable humidityRange;
}
