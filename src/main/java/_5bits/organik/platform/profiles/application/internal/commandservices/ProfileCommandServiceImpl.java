package _5bits.organik.platform.profiles.application.internal.commandservices;

import _5bits.organik.platform.profiles.application.commandservices.ProfileCommandService;
import _5bits.organik.platform.profiles.application.internal.outboundservices.acl.ExternalIamService;
import _5bits.organik.platform.profiles.domain.model.aggregates.Profile;
import _5bits.organik.platform.profiles.domain.model.commands.CreateProfileCommand;
import _5bits.organik.platform.profiles.domain.model.commands.UpdateProfilePreferencesCommand;
import _5bits.organik.platform.profiles.domain.repositories.ProfileRepository;
import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Application service that handles Profile commands.
 */
@Service
public class ProfileCommandServiceImpl implements ProfileCommandService {
    private final ProfileRepository profileRepository;
    private final ExternalIamService externalIamService;

    public ProfileCommandServiceImpl(ProfileRepository profileRepository, ExternalIamService externalIamService) {
        this.profileRepository = profileRepository;
        this.externalIamService = externalIamService;
    }

    @Override
    public Result<Long, ApplicationError> handle(CreateProfileCommand command) {
        if (externalIamService.fetchUsernameByUserId(command.userId().value()).isEmpty())
            return Result.failure(ApplicationError.notFound("User", command.userId().value().toString()));
        if (profileRepository.existsByUserId(command.userId()))
            return Result.failure(ApplicationError.conflict("Profile", "User %d already has a profile".formatted(command.userId().value())));
        if (profileRepository.existsByEmail(command.email()))
            return Result.failure(ApplicationError.conflict("Profile", "Email '%s' already exists".formatted(command.email().value())));
        var profile = new Profile(command);
        try {
            profile = profileRepository.save(profile);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-profile", e.getMessage()));
        }
        return Result.success(profile.getId());
    }

    @Override
    public Result<Long, ApplicationError> handle(UpdateProfilePreferencesCommand command) {
        var result = profileRepository.findById(command.profileId());
        if (result.isEmpty())
            return Result.failure(ApplicationError.notFound("Profile", command.profileId().toString()));
        try {
            var updatedProfile = profileRepository.save(result.get().updatePreferences(command.preferences()));
            return Result.success(updatedProfile.getId());
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("update-profile-preferences", e.getMessage()));
        }
    }
}
