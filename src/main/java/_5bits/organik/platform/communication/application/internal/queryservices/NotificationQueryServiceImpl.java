package _5bits.organik.platform.communication.application.internal.queryservices;

import _5bits.organik.platform.communication.application.queryservices.NotificationQueryService;
import _5bits.organik.platform.communication.domain.model.aggregates.Notification;
import _5bits.organik.platform.communication.domain.model.queries.GetAllNotificationsByMinimarketIdQuery;
import _5bits.organik.platform.communication.domain.model.queries.GetAllNotificationsQuery;
import _5bits.organik.platform.communication.domain.model.queries.GetNotificationByIdQuery;
import _5bits.organik.platform.communication.domain.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service that handles notification queries.
 */
@Service
public class NotificationQueryServiceImpl implements NotificationQueryService {
    private final NotificationRepository notificationRepository;

    public NotificationQueryServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> handle(GetAllNotificationsQuery query) {
        return notificationRepository.findAll();
    }

    @Override
    public Optional<Notification> handle(GetNotificationByIdQuery query) {
        return notificationRepository.findById(query.id());
    }

    @Override
    public List<Notification> handle(GetAllNotificationsByMinimarketIdQuery query) {
        return notificationRepository.findAllByMinimarketId(query.minimarketId());
    }
}
