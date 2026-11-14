package _5bits.organik.platform.communication.application.commandservices;

import _5bits.organik.platform.communication.domain.model.commands.CreateNotificationCommand;
import _5bits.organik.platform.communication.domain.model.commands.MarkNotificationAsReadCommand;
import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;

/**
 * Application service contract for notification command operations.
 */
public interface NotificationCommandService {

    /**
     * Handles the creation of a notification.
     *
     * @param command command with the notification data
     * @return the identifier of the created notification, or an application error
     */
    Result<Long, ApplicationError> handle(CreateNotificationCommand command);

    /**
     * Handles marking a notification as read.
     *
     * @param command command with the notification identifier
     * @return the identifier of the updated notification, or an application error
     */
    Result<Long, ApplicationError> handle(MarkNotificationAsReadCommand command);
}
