package _5bits.organik.platform.communication.application.acl;

import _5bits.organik.platform.communication.application.queryservices.NotificationQueryService;
import _5bits.organik.platform.communication.domain.model.queries.GetAllNotificationsQuery;
import _5bits.organik.platform.communication.interfaces.acl.CommunicationContextFacade;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link CommunicationContextFacade} ACL facade based on the query services of the bounded context.
 */
@Service
public class CommunicationContextFacadeImpl implements CommunicationContextFacade {
    private final NotificationQueryService notificationQueryService;

    public CommunicationContextFacadeImpl(NotificationQueryService notificationQueryService) {
        this.notificationQueryService = notificationQueryService;
    }

    @Override
    public long countUnreadNotifications() {
        return notificationQueryService.handle(new GetAllNotificationsQuery()).stream()
                .filter(notification -> !notification.isAlreadyRead())
                .count();
    }
}
