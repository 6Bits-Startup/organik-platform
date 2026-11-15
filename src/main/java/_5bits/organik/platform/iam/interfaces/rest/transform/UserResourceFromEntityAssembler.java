package _5bits.organik.platform.iam.interfaces.rest.transform;

import _5bits.organik.platform.iam.domain.model.aggregates.User;
import _5bits.organik.platform.iam.domain.model.entities.Role;
import _5bits.organik.platform.iam.interfaces.rest.resources.UserResource;

/**
 * Assembler that converts IAM {@link User} aggregates into REST {@link UserResource} objects.
 */
public class UserResourceFromEntityAssembler {
    /**
     * Converts a user aggregate to its REST representation.
     *
     * @param user user aggregate
     * @return user resource
     */
    public static UserResource toResourceFromEntity(User user) {
        var roles = user.getRoles().stream().map(Role::getStringName).toList();
        return new UserResource(user.getId(), user.getUsername(), roles);
    }
}
