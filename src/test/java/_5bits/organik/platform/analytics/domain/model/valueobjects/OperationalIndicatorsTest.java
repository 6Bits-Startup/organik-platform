package _5bits.organik.platform.analytics.domain.model.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OperationalIndicatorsTest {

    private OperationalIndicators indicatorsWith(long accepted, long rejected) {
        return new OperationalIndicators(new MinimarketId("mm-vida-verde"), 0, 0, 0, 0, 0, accepted, rejected);
    }

    @Test
    void acceptanceRateIsThePercentageOfAcceptedOrders() {
        assertEquals(75.0, indicatorsWith(3, 1).shipmentOrderAcceptanceRate());
    }

    @Test
    void acceptanceRateIsZeroWhenNoOrderWasDecided() {
        assertEquals(0.0, indicatorsWith(0, 0).shipmentOrderAcceptanceRate());
    }
}
