package _5bits.organik.platform.conservation.domain.model.queries;

import _5bits.organik.platform.conservation.domain.model.valueobjects.MinimarketId;

/**
 * Query object for GetAllStorageZonesByMinimarketId.
 *
 * @param minimarketId minimarket that owns the storage zones
 */
public record GetAllStorageZonesByMinimarketIdQuery(MinimarketId minimarketId) {
    public GetAllStorageZonesByMinimarketIdQuery {
        if (minimarketId == null) {
            throw new IllegalArgumentException("minimarketId cannot be null");
        }
    }
}
