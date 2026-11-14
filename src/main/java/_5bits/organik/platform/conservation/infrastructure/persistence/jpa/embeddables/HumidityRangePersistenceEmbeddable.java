package _5bits.organik.platform.conservation.infrastructure.persistence.jpa.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * JPA embeddable that stores the HumidityRange value object of a storage zone.
 */
@Embeddable
public class HumidityRangePersistenceEmbeddable {
    @Column(name = "min_humidity")
    private Double minHumidity;

    @Column(name = "max_humidity")
    private Double maxHumidity;

    public HumidityRangePersistenceEmbeddable() {
    }

    public HumidityRangePersistenceEmbeddable(Double minHumidity, Double maxHumidity) {
        this.minHumidity = minHumidity;
        this.maxHumidity = maxHumidity;
    }

    public Double getMinHumidity() {
        return minHumidity;
    }

    public void setMinHumidity(Double minHumidity) {
        this.minHumidity = minHumidity;
    }

    public Double getMaxHumidity() {
        return maxHumidity;
    }

    public void setMaxHumidity(Double maxHumidity) {
        this.maxHumidity = maxHumidity;
    }
}
