package _5bits.organik.platform.profiles.application.acl;

import _5bits.organik.platform.profiles.application.queryservices.ProfileQueryService;
import _5bits.organik.platform.profiles.domain.model.aggregates.Profile;
import _5bits.organik.platform.profiles.domain.model.queries.GetAllProfilesByUserIdQuery;
import _5bits.organik.platform.profiles.domain.model.valueobjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfilesContextFacadeImplTest {

    @Mock
    private ProfileQueryService profileQueryService;

    private ProfilesContextFacadeImpl profilesContextFacade;

    @BeforeEach
    void setUp() {
        profilesContextFacade = new ProfilesContextFacadeImpl(profileQueryService);
    }

    @Test
    void fetchWarningExpirationDaysReturnsThePreferenceOfTheUser() {
        var preferences = new Preferences(new Language("ES"), new ExpirationThreshold(7), new ExpirationThreshold(3));
        var profile = Profile.reconstitute(1L, new UserId(1L), new FullName("Albino Rojas"),
                new EmailAddress("albino@organik.pe"), new MinimarketId("mm-vida-verde"), null, preferences);
        when(profileQueryService.handle(any(GetAllProfilesByUserIdQuery.class))).thenReturn(List.of(profile));

        assertEquals(7, profilesContextFacade.fetchWarningExpirationDaysByUserId(1L));
    }

    @Test
    void fetchWarningExpirationDaysReturnsZeroWhenTheUserHasNoProfile() {
        when(profileQueryService.handle(any(GetAllProfilesByUserIdQuery.class))).thenReturn(List.of());

        assertEquals(0, profilesContextFacade.fetchWarningExpirationDaysByUserId(9L));
    }
}
