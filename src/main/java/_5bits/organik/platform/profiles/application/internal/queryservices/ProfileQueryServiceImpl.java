package _5bits.organik.platform.profiles.application.internal.queryservices;

import _5bits.organik.platform.profiles.application.queryservices.ProfileQueryService;
import _5bits.organik.platform.profiles.domain.model.aggregates.Profile;
import _5bits.organik.platform.profiles.domain.model.queries.GetAllProfilesByMinimarketIdQuery;
import _5bits.organik.platform.profiles.domain.model.queries.GetAllProfilesByUserIdQuery;
import _5bits.organik.platform.profiles.domain.model.queries.GetAllProfilesQuery;
import _5bits.organik.platform.profiles.domain.model.queries.GetProfileByIdQuery;
import _5bits.organik.platform.profiles.domain.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service that handles Profile queries.
 */
@Service
public class ProfileQueryServiceImpl implements ProfileQueryService {
    private final ProfileRepository profileRepository;

    public ProfileQueryServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Optional<Profile> handle(GetProfileByIdQuery query) {
        return profileRepository.findById(query.id());
    }

    @Override
    public List<Profile> handle(GetAllProfilesQuery query) {
        return profileRepository.findAll();
    }

    @Override
    public List<Profile> handle(GetAllProfilesByUserIdQuery query) {
        return profileRepository.findAllByUserId(query.userId());
    }

    @Override
    public List<Profile> handle(GetAllProfilesByMinimarketIdQuery query) {
        return profileRepository.findAllByMinimarketId(query.minimarketId());
    }
}
