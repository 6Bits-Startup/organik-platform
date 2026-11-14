package _5bits.organik.platform.communication.application.queryservices;

import _5bits.organik.platform.communication.domain.model.aggregates.Notification;
import _5bits.organik.platform.communication.domain.model.queries.GetAllNotificationsQuery;
import _5bits.organik.platform.communication.domain.model.queries.GetNotificationByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for notification query operations.
 */
public interface NotificationQueryService {

    /**
     * Retrieves every notification.
     *
     * @param query query object
     * @return list of notifications, possibly empty
     */
    List<Notification> handle(GetAllNotificationsQuery query);

    /**
     * Retrieves a notification by its identifier.
     *
     * @param query query with the notification identifier
     * @return the notification when found, otherwise empty
     */
    Optional<Notification> handle(GetNotificationByIdQuery query);
}
