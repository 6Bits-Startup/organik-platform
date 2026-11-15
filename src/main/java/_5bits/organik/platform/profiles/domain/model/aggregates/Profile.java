package _5bits.organik.platform.profiles.domain.model.aggregates;

import _5bits.organik.platform.profiles.domain.model.commands.CreateProfileCommand;
import _5bits.organik.platform.profiles.domain.model.valueobjects.*;
import _5bits.organik.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.util.Objects;

/**
 * Profile aggregate root.
 * <p>
 * Personal information of an IAM user (name, email, minimarket or supplier) and the preferences
 * used by the web application (language and expiration alert thresholds). The expiration
 * thresholds are the same for every profile of a minimarket.
 * </p>
 */
@Getter
public class Profile extends AbstractDomainAggregateRoot<Profile> {
    private Long id;
    private UserId userId;
    private FullName fullName;
    private EmailAddress email;
    private MinimarketId minimarketId;
    private SupplierId supplierId;
    private Preferences preferences;

    public Profile(UserId userId, FullName fullName, EmailAddress email, MinimarketId minimarketId,
                   SupplierId supplierId, Preferences preferences) {
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.fullName = Objects.requireNonNull(fullName, "fullName must not be null");
        this.email = Objects.requireNonNull(email, "email must not be null");
        this.minimarketId = minimarketId;
        this.supplierId = supplierId;
        this.preferences = preferences != null ? preferences : Preferences.defaults();
    }

    /**
     * Creates a profile with the default preferences.
     *
     * @param command command with the profile data
     */
    public Profile(CreateProfileCommand command) {
        this(command.userId(), command.fullName(), command.email(), command.minimarketId(), command.supplierId(),
                Preferences.defaults());
    }

    public static Profile reconstitute(Long id, UserId userId, FullName fullName, EmailAddress email,
                                       MinimarketId minimarketId, SupplierId supplierId, Preferences preferences) {
        var profile = new Profile(userId, fullName, email, minimarketId, supplierId, preferences);
        profile.id = id;
        return profile;
    }

    /**
     * Updates the preferences of the profile.
     *
     * @param preferences new preferences
     * @return the updated profile
     */
    public Profile updatePreferences(Preferences preferences) {
        this.preferences = Objects.requireNonNull(preferences, "preferences must not be null");
        return this;
    }

    /**
     * Adopts the expiration thresholds of its minimarket and keeps its own language.
     * A minimarket handles a single expiration policy, whoever of its users looks at the stock.
     *
     * @param minimarketPreferences preferences that hold the thresholds of the minimarket
     * @return the updated profile
     */
    public Profile adoptExpirationThresholds(Preferences minimarketPreferences) {
        Objects.requireNonNull(minimarketPreferences, "minimarketPreferences must not be null");
        this.preferences = new Preferences(preferences.language(),
                minimarketPreferences.warningExpirationDays(), minimarketPreferences.criticalExpirationDays());
        return this;
    }
}
