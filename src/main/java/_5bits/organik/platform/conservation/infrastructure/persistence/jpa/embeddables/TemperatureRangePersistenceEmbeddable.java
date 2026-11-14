package _5bits.organik.platform.conservation.infrastructure.persistence.jpa.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * JPA embeddable that stores the TemperatureRange value object of a storage zone.
 */
@Embeddable
public class TemperatureRangePersistenceEmbeddable {
    @Column(name = "min_temperature")
    private Double minTemperature;

    @Column(name = "max_temperature")
    private Double maxTemperature;

    public TemperatureRangePersistenceEmbeddable() {
    }

    public TemperatureRangePersistenceEmbeddable(Double minTemperature, Double maxTemperature) {
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
    }

    public Double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }
}
