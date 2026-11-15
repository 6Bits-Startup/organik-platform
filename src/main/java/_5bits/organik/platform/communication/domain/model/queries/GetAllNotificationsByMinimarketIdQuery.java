package _5bits.organik.platform.communication.domain.model.queries;

import _5bits.organik.platform.communication.domain.model.valueobjects.MinimarketId;

/**
 * Query object for GetAllNotificationsByMinimarketId.
 *
 * @param minimarketId minimarket whose staff receives the notifications
 */
public record GetAllNotificationsByMinimarketIdQuery(MinimarketId minimarketId) {
    public GetAllNotificationsByMinimarketIdQuery {
        if (minimarketId == null) {
            throw new IllegalArgumentException("minimarketId cannot be null");
        }
    }
}
