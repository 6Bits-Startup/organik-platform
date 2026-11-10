package _5bits.organik.platform.communication.interfaces.rest.transform;

import _5bits.organik.platform.communication.domain.model.valueobjects.NotificationTitle;
import _5bits.organik.platform.communication.domain.model.valueobjects.NotificationType;
import _5bits.organik.platform.communication.domain.model.valueobjects.Severity;

public class CommunicationValueObjectFromStringAssembler {
    public static NotificationType toNotificationTypeFromString(String value) {
        return new NotificationType(value);
    }

    public static Severity toSeverityFromString(String value) {
        return new Severity(value);
    }

    public static NotificationTitle toNotificationTitleFromString(String value) {
        return new NotificationTitle(value);
    }
}