package _5bits.organik.platform.dashboard.application.internal.queryservices;

import _5bits.organik.platform.dashboard.application.internal.outboundservices.acl.ExternalCommunicationService;
import _5bits.organik.platform.dashboard.application.internal.outboundservices.acl.ExternalInventoryService;
import _5bits.organik.platform.dashboard.application.internal.outboundservices.acl.ExternalProcurementsService;
import _5bits.organik.platform.dashboard.application.internal.outboundservices.acl.ExternalProfileService;
import _5bits.organik.platform.dashboard.application.internal.outboundservices.acl.ExternalRequisitionService;
import _5bits.organik.platform.dashboard.domain.model.queries.GetDashboardSummaryByMinimarketIdQuery;
import _5bits.organik.platform.dashboard.domain.model.valueobjects.MinimarketId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardQueryServiceImplTest {

    @Mock
    private ExternalInventoryService externalInventoryService;

    @Mock
    private ExternalProcurementsService externalProcurementsService;

    @Mock
    private ExternalRequisitionService externalRequisitionService;

    @Mock
    private ExternalCommunicationService externalCommunicationService;

    @Mock
    private ExternalProfileService externalProfileService;

    private DashboardQueryServiceImpl dashboardQueryService;

    @BeforeEach
    void setUp() {
        dashboardQueryService = new DashboardQueryServiceImpl(externalInventoryService, externalProcurementsService,
                externalRequisitionService, externalCommunicationService, externalProfileService);
    }

    @Test
    void handleBuildsTheSummaryFromTheOtherBoundedContexts() {
        // Arrange
        when(externalInventoryService.countStockLots("mm-vida-verde")).thenReturn(12L);
        when(externalInventoryService.countLowStockLots("mm-vida-verde")).thenReturn(2L);
        when(externalInventoryService.countStockLotsExpiringWithinDays("mm-vida-verde", 15)).thenReturn(3L);
        when(externalProcurementsService.countPendingShipmentOrders("mm-vida-verde")).thenReturn(1L);
        when(externalRequisitionService.countPendingSupplyRequests("mm-vida-verde")).thenReturn(4L);
        when(externalCommunicationService.countUnreadNotifications("mm-vida-verde")).thenReturn(5L);

        // Act
        var summary = dashboardQueryService.handle(new GetDashboardSummaryByMinimarketIdQuery(new MinimarketId("mm-vida-verde"), null));

        // Assert
        assertThat(summary.totalStockLots()).isEqualTo(12L);
        assertThat(summary.lowStockLots()).isEqualTo(2L);
        assertThat(summary.expirationWarningDays()).isEqualTo(15);
        assertThat(summary.expiringSoonLots()).isEqualTo(3L);
        assertThat(summary.pendingShipmentOrders()).isEqualTo(1L);
        assertThat(summary.pendingSupplyRequests()).isEqualTo(4L);
        assertThat(summary.unreadNotifications()).isEqualTo(5L);
        assertThat(summary.requiresAttention()).isTrue();
        verify(externalProfileService, never()).fetchWarningExpirationDaysByUserId(anyLong());
    }

    @Test
    void handleUsesTheWarningDaysOfTheUserProfile() {
        // Arrange
        when(externalProfileService.fetchWarningExpirationDaysByUserId(1L)).thenReturn(Optional.of(7));
        when(externalInventoryService.countStockLotsExpiringWithinDays("mm-vida-verde", 7)).thenReturn(1L);

        // Act
        var summary = dashboardQueryService.handle(new GetDashboardSummaryByMinimarketIdQuery(new MinimarketId("mm-vida-verde"), 1L));

        // Assert
        assertThat(summary.expirationWarningDays()).isEqualTo(7);
        assertThat(summary.expiringSoonLots()).isEqualTo(1L);
    }

    @Test
    void handleUsesTheDefaultWarningDaysWhenTheUserHasNoProfile() {
        // Arrange
        when(externalProfileService.fetchWarningExpirationDaysByUserId(9L)).thenReturn(Optional.empty());

        // Act
        var summary = dashboardQueryService.handle(new GetDashboardSummaryByMinimarketIdQuery(new MinimarketId("mm-vida-verde"), 9L));

        // Assert
        assertThat(summary.expirationWarningDays()).isEqualTo(15);
        verify(externalInventoryService).countStockLotsExpiringWithinDays("mm-vida-verde", 15);
    }
}
