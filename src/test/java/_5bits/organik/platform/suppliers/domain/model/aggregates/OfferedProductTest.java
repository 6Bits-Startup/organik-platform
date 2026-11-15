package _5bits.organik.platform.suppliers.domain.model.aggregates;

import _5bits.organik.platform.suppliers.domain.model.valueobjects.LotCode;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.ProductId;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.Quantity;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.SupplierId;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class OfferedProductTest {

    private OfferedProduct offeredProductWith(int availableQuantity) {
        return OfferedProduct.reconstitute(1L, new SupplierId("2"), new ProductId("ORG-01"), new LotCode("AG-LECHE-1126"),
                new Quantity(availableQuantity), LocalDate.of(2026, 12, 5));
    }

    @Test
    void shippedUnitsDecreaseTheAvailability() {
        var offeredProduct = offeredProductWith(80);

        offeredProduct.decreaseAvailability(new Quantity(30));

        assertThat(offeredProduct.getAvailableQuantity().value()).isEqualTo(50);
    }

    @Test
    void availabilityNeverDropsBelowZero() {
        var offeredProduct = offeredProductWith(20);

        offeredProduct.decreaseAvailability(new Quantity(30));

        assertThat(offeredProduct.getAvailableQuantity().value()).isZero();
    }
}
