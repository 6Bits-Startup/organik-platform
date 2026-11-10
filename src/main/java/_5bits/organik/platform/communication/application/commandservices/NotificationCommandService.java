package _5bits.organik.platform.communication.application.commandservices;

import _5bits.organik.platform.communication.domain.model.aggregates.Notification;
import _5bits.organik.platform.communication.domain.model.commands.CreateNotificationCommand;
import _5bits.organik.platform.communication.domain.model.commands.MarkNotificationAsReadCommand;
import _5bits.organik.platform.shared.application.result.Result;

public interface NotificationCommandService {
    Result<Notification, NotificationCommandFailure> handle(CreateNotificationCommand command);
    Result<Notification, NotificationCommandFailure> handle(MarkNotificationAsReadCommand command);
}