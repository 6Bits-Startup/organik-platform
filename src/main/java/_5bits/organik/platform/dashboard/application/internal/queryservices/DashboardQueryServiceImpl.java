package _5bits.organik.platform.dashboard.application.internal.queryservices;

import _5bits.organik.platform.dashboard.application.internal.outboundservices.acl.ExternalCommunicationService;
import _5bits.organik.platform.dashboard.application.internal.outboundservices.acl.ExternalInventoryService;
import _5bits.organik.platform.dashboard.application.internal.outboundservices.acl.ExternalProcurementsService;
import _5bits.organik.platform.dashboard.application.internal.outboundservices.acl.ExternalProfileService;
import _5bits.organik.platform.dashboard.application.internal.outboundservices.acl.ExternalRequisitionService;
import _5bits.organik.platform.dashboard.application.queryservices.DashboardQueryService;
import _5bits.organik.platform.dashboard.domain.model.queries.GetDashboardSummaryByMinimarketIdQuery;
import _5bits.organik.platform.dashboard.domain.model.valueobjects.DashboardSummary;
import org.springframework.stereotype.Service;

/**
 * Application service that builds the dashboard summary from the other bounded contexts.
 */
@Service
public class DashboardQueryServiceImpl implements DashboardQueryService {
    private static final int DEFAULT_EXPIRATION_WARNING_DAYS = 15;
    private final ExternalInventoryService externalInventoryService;
    private final ExternalProcurementsService externalProcurementsService;
    private final ExternalRequisitionService externalRequisitionService;
    private final ExternalCommunicationService externalCommunicationService;
    private final ExternalProfileService externalProfileService;

    public DashboardQueryServiceImpl(ExternalInventoryService externalInventoryService,
                                     ExternalProcurementsService externalProcurementsService,
                                     ExternalRequisitionService externalRequisitionService,
                                     ExternalCommunicationService externalCommunicationService,
                                     ExternalProfileService externalProfileService) {
        this.externalInventoryService = externalInventoryService;
        this.externalProcurementsService = externalProcurementsService;
        this.externalRequisitionService = externalRequisitionService;
        this.externalCommunicationService = externalCommunicationService;
        this.externalProfileService = externalProfileService;
    }

    @Override
    public DashboardSummary handle(GetDashboardSummaryByMinimarketIdQuery query) {
        var minimarketId = query.minimarketId().value();
        var expirationWarningDays = query.userId() == null
                ? DEFAULT_EXPIRATION_WARNING_DAYS
                : externalProfileService.fetchWarningExpirationDaysByUserId(query.userId()).orElse(DEFAULT_EXPIRATION_WARNING_DAYS);
        return new DashboardSummary(
                query.minimarketId(),
                externalInventoryService.countStockLots(minimarketId),
                externalInventoryService.countLowStockLots(minimarketId),
                expirationWarningDays,
                externalInventoryService.countStockLotsExpiringWithinDays(minimarketId, expirationWarningDays),
                externalProcurementsService.countPendingShipmentOrders(minimarketId),
                externalRequisitionService.countPendingSupplyRequests(minimarketId),
                externalCommunicationService.countUnreadNotifications());
    }
}
