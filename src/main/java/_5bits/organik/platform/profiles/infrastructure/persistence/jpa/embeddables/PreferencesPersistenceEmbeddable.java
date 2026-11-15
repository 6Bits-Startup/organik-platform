package _5bits.organik.platform.profiles.infrastructure.persistence.jpa.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * JPA embeddable that stores the {@code Preferences} value object of a profile.
 */
@Embeddable
public class PreferencesPersistenceEmbeddable {
    @Column(name = "language")
    private String language;

    @Column(name = "warning_expiration_days")
    private Integer warningExpirationDays;

    @Column(name = "critical_expiration_days")
    private Integer criticalExpirationDays;

    public PreferencesPersistenceEmbeddable() {
    }

    public PreferencesPersistenceEmbeddable(String language, Integer warningExpirationDays, Integer criticalExpirationDays) {
        this.language = language;
        this.warningExpirationDays = warningExpirationDays;
        this.criticalExpirationDays = criticalExpirationDays;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getWarningExpirationDays() {
        return warningExpirationDays;
    }

    public void setWarningExpirationDays(Integer warningExpirationDays) {
        this.warningExpirationDays = warningExpirationDays;
    }

    public Integer getCriticalExpirationDays() {
        return criticalExpirationDays;
    }

    public void setCriticalExpirationDays(Integer criticalExpirationDays) {
        this.criticalExpirationDays = criticalExpirationDays;
    }
}
