package _5bits.organik.platform.communication.application.internal.commandservices;

import _5bits.organik.platform.communication.application.commandservices.NotificationCommandService;
import _5bits.organik.platform.communication.domain.model.aggregates.Notification;
import _5bits.organik.platform.communication.domain.model.commands.CreateNotificationCommand;
import _5bits.organik.platform.communication.domain.model.commands.MarkNotificationAsReadCommand;
import _5bits.organik.platform.communication.domain.repositories.NotificationRepository;
import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Application service that handles notification commands.
 */
@Service
public class NotificationCommandServiceImpl implements NotificationCommandService {
    private final NotificationRepository notificationRepository;

    public NotificationCommandServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Result<Long, ApplicationError> handle(CreateNotificationCommand command) {
        var notification = new Notification(command);
        try {
            notification = notificationRepository.save(notification);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-notification", e.getMessage()));
        }
        return Result.success(notification.getId());
    }

    @Override
    public Result<Long, ApplicationError> handle(MarkNotificationAsReadCommand command) {
        return notificationRepository.findById(command.notificationId()).map(notification -> {
            if (notification.isAlreadyRead())
                return Result.<Long, ApplicationError>failure(ApplicationError.conflict("Notification", "Notification is already read"));
            notification.markAsRead();
            return Result.<Long, ApplicationError>success(notificationRepository.save(notification).getId());
        }).orElseGet(() -> Result.failure(ApplicationError.notFound("Notification", command.notificationId().toString())));
    }
}
