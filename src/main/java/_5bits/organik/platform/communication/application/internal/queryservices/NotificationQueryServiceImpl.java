package _5bits.organik.platform.communication.application.internal.queryservices;

import _5bits.organik.platform.communication.application.queryservices.NotificationQueryService;
import _5bits.organik.platform.communication.domain.model.aggregates.Notification;
import _5bits.organik.platform.communication.domain.model.queries.GetAllNotificationsQuery;
import _5bits.organik.platform.communication.domain.model.queries.GetNotificationByIdQuery;
import _5bits.organik.platform.communication.infrastructure.persistence.jpa.adapters.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class NotificationQueryServiceImpl implements NotificationQueryService {
    private final NotificationRepository notificationRepository;

    public NotificationQueryServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> handle(GetAllNotificationsQuery query) {
        log.debug("Querying all notifications");
        var results = notificationRepository.findAll();
        log.debug("Found {} notification(s)", results.size());
        return results;
    }

    @Override
    public Optional<Notification> handle(GetNotificationByIdQuery query) {
        log.debug("Querying notification by id={}", query.id());
        var result = notificationRepository.findById(query.id());
        if (result.isEmpty()) log.debug("No notification found for id={}", query.id());
        return result;
    }
}