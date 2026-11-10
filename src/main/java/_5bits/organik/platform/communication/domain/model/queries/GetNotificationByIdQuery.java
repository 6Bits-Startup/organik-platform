package _5bits.organik.platform.communication.domain.model.queries;

public record GetNotificationByIdQuery(Long id) {
    public GetNotificationByIdQuery {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("notification.error.id.invalid");
        }
    }
}