package _5bits.organik.platform.profiles.domain.model.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PreferencesTest {

    @Test
    void defaultsAreEnglishWithFifteenAndFiveDays() {
        var preferences = Preferences.defaults();

        assertEquals("EN", preferences.language().value());
        assertEquals(15, preferences.warningExpirationDays().days());
        assertEquals(5, preferences.criticalExpirationDays().days());
    }

    @Test
    void criticalThresholdCannotBeGreaterThanWarningThreshold() {
        var exception = assertThrows(IllegalArgumentException.class,
                () -> new Preferences(new Language("ES"), new ExpirationThreshold(3), new ExpirationThreshold(10)));
        assertEquals("Critical expiration threshold cannot be greater than warning threshold", exception.getMessage());
    }
}
