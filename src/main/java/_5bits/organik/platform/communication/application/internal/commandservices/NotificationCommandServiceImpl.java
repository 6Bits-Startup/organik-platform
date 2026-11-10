package _5bits.organik.platform.communication.application.internal.commandservices;

import _5bits.organik.platform.communication.application.commandservices.NotificationCommandFailure;
import _5bits.organik.platform.communication.application.commandservices.NotificationCommandService;
import _5bits.organik.platform.communication.domain.model.aggregates.Notification;
import _5bits.organik.platform.communication.domain.model.commands.CreateNotificationCommand;
import _5bits.organik.platform.communication.domain.model.commands.MarkNotificationAsReadCommand;
import _5bits.organik.platform.communication.infrastructure.persistence.jpa.adapters.NotificationRepository;
import _5bits.organik.platform.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class NotificationCommandServiceImpl implements NotificationCommandService {
    private final NotificationRepository notificationRepository;

    public NotificationCommandServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    @Transactional
    public Result<Notification, NotificationCommandFailure> handle(CreateNotificationCommand command) {
        var notification = new Notification(command);
        var createdNotification = notificationRepository.save(notification);
        log.info("Notification created: type={}, severity={}, id={}, createdAt={}",
                command.type().value(),
                command.severity().value(),
                createdNotification.getId(),
                createdNotification.getCreatedAt());
        return Result.success(createdNotification);
    }

    @Override
    @Transactional
    public Result<Notification, NotificationCommandFailure> handle(MarkNotificationAsReadCommand command) {
        var notificationOptional = notificationRepository.findById(command.notificationId());
        if (notificationOptional.isEmpty()) {
            log.warn("Notification not found to mark as read: id={}", command.notificationId());
            return Result.failure(new NotificationCommandFailure.NotFound());
        }
        var notification = notificationOptional.get();
        notification.markAsRead();
        var updatedNotification = notificationRepository.save(notification);
        log.info("Notification marked as read: id={}, updatedAt={}",
                updatedNotification.getId(),
                updatedNotification.getUpdatedAt());
        return Result.success(updatedNotification);
    }
}