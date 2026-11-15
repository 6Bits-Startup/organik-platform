package _5bits.organik.platform.profiles.domain.model.valueobjects;

/**
 * Preferences of a profile: language of the interface and expiration thresholds for alerts.
 *
 * @param language preferred language
 * @param warningExpirationDays days before expiration that raise a warning alert
 * @param criticalExpirationDays days before expiration that raise a critical alert
 */
public record Preferences(Language language, ExpirationThreshold warningExpirationDays, ExpirationThreshold criticalExpirationDays) {
    private static final int DEFAULT_WARNING_DAYS = 15;
    private static final int DEFAULT_CRITICAL_DAYS = 5;

    public Preferences {
        if (language == null || warningExpirationDays == null || criticalExpirationDays == null) {
            throw new IllegalArgumentException("All preferences are required");
        }
        if (criticalExpirationDays.days() > warningExpirationDays.days()) {
            throw new IllegalArgumentException("Critical expiration threshold cannot be greater than warning threshold");
        }
    }

    /**
     * @return the default preferences: English, warning at 15 days and critical at 5 days
     */
    public static Preferences defaults() {
        return new Preferences(new Language("EN"),
                new ExpirationThreshold(DEFAULT_WARNING_DAYS),
                new ExpirationThreshold(DEFAULT_CRITICAL_DAYS));
    }
}
