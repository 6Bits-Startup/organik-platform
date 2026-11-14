package _5bits.organik.platform.profiles.application.queryservices;

import _5bits.organik.platform.profiles.domain.model.aggregates.Profile;
import _5bits.organik.platform.profiles.domain.model.queries.GetAllProfilesByMinimarketIdQuery;
import _5bits.organik.platform.profiles.domain.model.queries.GetAllProfilesByUserIdQuery;
import _5bits.organik.platform.profiles.domain.model.queries.GetAllProfilesQuery;
import _5bits.organik.platform.profiles.domain.model.queries.GetProfileByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for Profile query operations.
 */
public interface ProfileQueryService {

    /**
     * Handles the {@link GetProfileByIdQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    Optional<Profile> handle(GetProfileByIdQuery query);

    /**
     * Handles the {@link GetAllProfilesQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    List<Profile> handle(GetAllProfilesQuery query);

    /**
     * Handles the {@link GetAllProfilesByUserIdQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    List<Profile> handle(GetAllProfilesByUserIdQuery query);

    /**
     * Handles the {@link GetAllProfilesByMinimarketIdQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    List<Profile> handle(GetAllProfilesByMinimarketIdQuery query);
}
