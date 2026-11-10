package _5bits.organik.platform.communication.interfaces.rest.transform;

import _5bits.organik.platform.communication.domain.model.commands.CreateNotificationCommand;
import _5bits.organik.platform.communication.interfaces.rest.resources.CreateNotificationResource;

public class CreateNotificationCommandFromResourceAssembler {
    public static CreateNotificationCommand toCommandFromResource(CreateNotificationResource resource) {
        return new CreateNotificationCommand(
                CommunicationValueObjectFromStringAssembler.toNotificationTypeFromString(resource.type()),
                CommunicationValueObjectFromStringAssembler.toSeverityFromString(resource.severity()),
                CommunicationValueObjectFromStringAssembler.toNotificationTitleFromString(resource.title()));
    }
}