package _5bits.organik.platform.profiles.application.internal.commandservices;

import _5bits.organik.platform.profiles.application.internal.outboundservices.acl.ExternalIamService;
import _5bits.organik.platform.profiles.domain.model.aggregates.Profile;
import _5bits.organik.platform.profiles.domain.model.commands.CreateProfileCommand;
import _5bits.organik.platform.profiles.domain.model.commands.UpdateProfilePreferencesCommand;
import _5bits.organik.platform.profiles.domain.model.valueobjects.*;
import _5bits.organik.platform.profiles.domain.repositories.ProfileRepository;
import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileCommandServiceImplTest {

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private ExternalIamService externalIamService;

    private ProfileCommandServiceImpl profileCommandService;

    private final CreateProfileCommand command = new CreateProfileCommand(new UserId(1L), new FullName("Albino Rojas"),
            new EmailAddress("albino@organik.pe"), new MinimarketId("mm-vida-verde"), null);

    @BeforeEach
    void setUp() {
        profileCommandService = new ProfileCommandServiceImpl(profileRepository, externalIamService);
    }

    @Test
    void handleReturnsNotFoundWhenIamUserDoesNotExist() {
        when(externalIamService.fetchUsernameByUserId(1L)).thenReturn(Optional.empty());

        var result = profileCommandService.handle(command);

        assertThat(((Result.Failure<Long, ApplicationError>) result).error().code()).isEqualTo("USER_NOT_FOUND");
        verify(profileRepository, never()).save(any());
    }

    @Test
    void handleReturnsConflictWhenUserAlreadyHasProfile() {
        when(externalIamService.fetchUsernameByUserId(1L)).thenReturn(Optional.of("albino@organik.pe"));
        when(profileRepository.existsByUserId(any(UserId.class))).thenReturn(true);

        var result = profileCommandService.handle(command);

        assertThat(((Result.Failure<Long, ApplicationError>) result).error().code()).isEqualTo("PROFILE_CONFLICT");
    }

    @Test
    void handleCreatesProfileWithDefaultPreferences() {
        when(externalIamService.fetchUsernameByUserId(1L)).thenReturn(Optional.of("albino@organik.pe"));
        when(profileRepository.existsByUserId(any(UserId.class))).thenReturn(false);
        when(profileRepository.existsByEmail(any(EmailAddress.class))).thenReturn(false);
        when(profileRepository.save(any(Profile.class))).thenAnswer(invocation -> {
            Profile profile = invocation.getArgument(0);
            return Profile.reconstitute(5L, profile.getUserId(), profile.getFullName(), profile.getEmail(),
                    profile.getMinimarketId(), profile.getSupplierId(), profile.getPreferences());
        });

        var result = profileCommandService.handle(command);

        assertThat(result.toOptional()).contains(5L);
    }

    @Test
    void handleCreatesProfileWithTheThresholdsOfItsMinimarket() {
        var colleague = Profile.reconstitute(2L, new UserId(2L), new FullName("Rosa Medina"),
                new EmailAddress("rosa@organik.pe"), new MinimarketId("mm-vida-verde"), null,
                new Preferences(new Language("ES"), new ExpirationThreshold(10), new ExpirationThreshold(2)));
        when(externalIamService.fetchUsernameByUserId(1L)).thenReturn(Optional.of("albino@organik.pe"));
        when(profileRepository.existsByUserId(any(UserId.class))).thenReturn(false);
        when(profileRepository.existsByEmail(any(EmailAddress.class))).thenReturn(false);
        when(profileRepository.findAllByMinimarketId(any(MinimarketId.class))).thenReturn(List.of(colleague));
        when(profileRepository.save(any(Profile.class))).thenAnswer(invocation -> invocation.getArgument(0));

        profileCommandService.handle(command);

        verify(profileRepository).save(argThat(profile ->
                profile.getPreferences().warningExpirationDays().days() == 10
                        && profile.getPreferences().criticalExpirationDays().days() == 2
                        && profile.getPreferences().language().equals(new Language("EN"))));
    }

    @Test
    void handleUpdatesTheThresholdsOfEveryProfileOfTheMinimarket() {
        var administrator = Profile.reconstitute(1L, new UserId(1L), new FullName("Rosa Medina"),
                new EmailAddress("rosa@organik.pe"), new MinimarketId("mm-vida-verde"), null, Preferences.defaults());
        var operator = Profile.reconstitute(2L, new UserId(2L), new FullName("Lucia Ramos"),
                new EmailAddress("lucia@organik.pe"), new MinimarketId("mm-vida-verde"), null,
                new Preferences(new Language("ES"), new ExpirationThreshold(15), new ExpirationThreshold(5)));
        var preferences = new Preferences(new Language("EN"), new ExpirationThreshold(10), new ExpirationThreshold(2));
        when(profileRepository.findById(1L)).thenReturn(Optional.of(administrator));
        when(profileRepository.save(any(Profile.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(profileRepository.findAllByMinimarketId(any(MinimarketId.class))).thenReturn(List.of(administrator, operator));

        profileCommandService.handle(new UpdateProfilePreferencesCommand(1L, preferences));

        assertThat(operator.getPreferences().warningExpirationDays().days()).isEqualTo(10);
        assertThat(operator.getPreferences().criticalExpirationDays().days()).isEqualTo(2);
        assertThat(operator.getPreferences().language()).isEqualTo(new Language("ES"));
    }
}
