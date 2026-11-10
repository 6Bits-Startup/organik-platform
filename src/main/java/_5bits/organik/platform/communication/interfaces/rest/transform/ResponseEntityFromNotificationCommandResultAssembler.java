package _5bits.organik.platform.communication.interfaces.rest.transform;

import _5bits.organik.platform.communication.application.commandservices.NotificationCommandFailure;
import _5bits.organik.platform.communication.domain.model.aggregates.Notification;
import _5bits.organik.platform.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

public class ResponseEntityFromNotificationCommandResultAssembler {
    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<Notification, NotificationCommandFailure> result,
            MessageSource messageSource,
            HttpStatus successStatus) {
        return result.fold(
                notification -> new ResponseEntity<>(
                        NotificationResourceFromEntityAssembler.toResourceFromEntity(notification),
                        successStatus),
                failure -> {
                    var status = statusFromFailure(failure);
                    return ResponseEntity.status(status).body(ProblemDetail.forStatusAndDetail(
                            status,
                            localizedMessage(messageSource, failure.messageKey())));
                });
    }

    private static HttpStatus statusFromFailure(NotificationCommandFailure failure) {
        if (failure instanceof NotificationCommandFailure.NotFound) {
            return HttpStatus.NOT_FOUND;
        }
        return HttpStatus.BAD_REQUEST;
    }

    private static String localizedMessage(MessageSource messageSource, String messageKey) {
        return messageSource.getMessage(messageKey, null, messageKey, LocaleContextHolder.getLocale());
    }
}