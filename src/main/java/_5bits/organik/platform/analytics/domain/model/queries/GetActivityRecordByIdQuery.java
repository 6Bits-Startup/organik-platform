package _5bits.organik.platform.analytics.domain.model.queries;

/**
 * Query object for GetActivityRecordById.
 */
public record GetActivityRecordByIdQuery(Long id) {
    public GetActivityRecordByIdQuery {
        if (id == null || id <= 0) throw new IllegalArgumentException("Activity record id is required.");
    }
}
