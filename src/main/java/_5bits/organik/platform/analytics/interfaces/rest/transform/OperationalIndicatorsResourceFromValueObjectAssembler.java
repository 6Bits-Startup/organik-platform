package _5bits.organik.platform.analytics.interfaces.rest.transform;

import _5bits.organik.platform.analytics.domain.model.valueobjects.OperationalIndicators;
import _5bits.organik.platform.analytics.interfaces.rest.resources.OperationalIndicatorsResource;

/**
 * Assembler that converts {@link OperationalIndicators} into an {@link OperationalIndicatorsResource}.
 */
public class OperationalIndicatorsResourceFromValueObjectAssembler {
    public static OperationalIndicatorsResource toResourceFromValueObject(OperationalIndicators indicators) {
        return new OperationalIndicatorsResource(
                indicators.minimarketId().value(),
                indicators.expiredLots(),
                indicators.lotsExpiringWithin7Days(),
                indicators.lotsExpiringWithin15Days(),
                indicators.wasteUnits(),
                indicators.outOfRangeReadings(),
                indicators.acceptedShipmentOrders(),
                indicators.rejectedShipmentOrders(),
                indicators.shipmentOrderAcceptanceRate());
    }
}
