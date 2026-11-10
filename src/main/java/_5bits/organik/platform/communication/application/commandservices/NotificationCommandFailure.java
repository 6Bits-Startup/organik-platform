package _5bits.organik.platform.communication.application.commandservices;

public sealed interface NotificationCommandFailure permits NotificationCommandFailure.NotFound {
    String messageKey();

    record NotFound() implements NotificationCommandFailure {
        @Override
        public String messageKey() {
            return "notification.error.notFound";
        }
    }
}