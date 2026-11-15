package _5bits.organik.platform.analytics.application.internal.queryservices;

import _5bits.organik.platform.analytics.application.internal.outboundservices.acl.ExternalConservationService;
import _5bits.organik.platform.analytics.application.internal.outboundservices.acl.ExternalInventoryService;
import _5bits.organik.platform.analytics.application.internal.outboundservices.acl.ExternalProcurementsService;
import _5bits.organik.platform.analytics.application.queryservices.OperationalIndicatorsQueryService;
import _5bits.organik.platform.analytics.domain.model.queries.GetOperationalIndicatorsByMinimarketIdQuery;
import _5bits.organik.platform.analytics.domain.model.valueobjects.OperationalIndicators;
import org.springframework.stereotype.Service;

/**
 * Application service that computes the operational indicators from the other bounded contexts.
 */
@Service
public class OperationalIndicatorsQueryServiceImpl implements OperationalIndicatorsQueryService {
    private static final int SHORT_EXPIRATION_DAYS = 7;
    private static final int LONG_EXPIRATION_DAYS = 15;
    private final ExternalInventoryService externalInventoryService;
    private final ExternalProcurementsService externalProcurementsService;
    private final ExternalConservationService externalConservationService;

    public OperationalIndicatorsQueryServiceImpl(ExternalInventoryService externalInventoryService,
                                                 ExternalProcurementsService externalProcurementsService,
                                                 ExternalConservationService externalConservationService) {
        this.externalInventoryService = externalInventoryService;
        this.externalProcurementsService = externalProcurementsService;
        this.externalConservationService = externalConservationService;
    }

    @Override
    public OperationalIndicators handle(GetOperationalIndicatorsByMinimarketIdQuery query) {
        var minimarketId = query.minimarketId().value();
        return new OperationalIndicators(
                query.minimarketId(),
                externalInventoryService.countExpiredStockLots(minimarketId),
                externalInventoryService.countStockLotsExpiringWithinDays(minimarketId, SHORT_EXPIRATION_DAYS),
                externalInventoryService.countStockLotsExpiringWithinDays(minimarketId, LONG_EXPIRATION_DAYS),
                externalInventoryService.sumWasteUnits(minimarketId),
                externalConservationService.countOutOfRangeReadings(),
                externalProcurementsService.countAcceptedShipmentOrders(minimarketId),
                externalProcurementsService.countRejectedShipmentOrders(minimarketId));
    }
}
