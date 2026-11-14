package _5bits.organik.platform.conservation.infrastructure.persistence.jpa.entities;

import _5bits.organik.platform.conservation.domain.model.valueobjects.Humidity;
import _5bits.organik.platform.conservation.domain.model.valueobjects.ReadingSource;
import _5bits.organik.platform.conservation.domain.model.valueobjects.StorageZoneId;
import _5bits.organik.platform.conservation.domain.model.valueobjects.Temperature;
import _5bits.organik.platform.conservation.infrastructure.persistence.jpa.converters.HumidityPersistenceConverter;
import _5bits.organik.platform.conservation.infrastructure.persistence.jpa.converters.ReadingSourcePersistenceConverter;
import _5bits.organik.platform.conservation.infrastructure.persistence.jpa.converters.StorageZoneIdPersistenceConverter;
import _5bits.organik.platform.conservation.infrastructure.persistence.jpa.converters.TemperaturePersistenceConverter;
import _5bits.organik.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * JPA persistence entity for the StorageReading aggregate.
 * <p>
 * Pure persistence model: it carries no business behavior. Mapping to and from the
 * {@code StorageReading} domain aggregate is done by {@code StorageReadingPersistenceAssembler}.
 * </p>
 */
@Entity
@Table(name = "storage_readings")
@Getter
@Setter
@NoArgsConstructor
public class StorageReadingPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = StorageZoneIdPersistenceConverter.class)
    @Column(name = "storage_zone_id", nullable = false)
    private StorageZoneId storageZoneId;

    @Convert(converter = TemperaturePersistenceConverter.class)
    @Column(name = "temperature", nullable = false)
    private Temperature temperature;

    @Convert(converter = HumidityPersistenceConverter.class)
    @Column(name = "humidity", nullable = false)
    private Humidity humidity;

    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt;

    @Convert(converter = ReadingSourcePersistenceConverter.class)
    @Column(name = "source", nullable = false, length = 20)
    private ReadingSource source;

    @Column(name = "within_range", nullable = false)
    private Boolean withinRange;
}
