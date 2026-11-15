package _5bits.organik.platform.iam.interfaces.rest;

import _5bits.organik.platform.iam.application.commandservices.UserCommandService;
import _5bits.organik.platform.iam.application.queryservices.UserQueryService;
import _5bits.organik.platform.iam.domain.model.aggregates.User;
import _5bits.organik.platform.iam.domain.model.commands.SignUpCommand;
import _5bits.organik.platform.iam.domain.model.queries.GetAllUsersQuery;
import _5bits.organik.platform.iam.domain.model.queries.GetUserByIdQuery;
import _5bits.organik.platform.iam.interfaces.rest.resources.SignUpResource;
import _5bits.organik.platform.iam.interfaces.rest.resources.UserResource;
import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsersControllerTest {

    @Mock
    private UserQueryService userQueryService;

    @Mock
    private UserCommandService userCommandService;

    private UsersController usersController;

    @BeforeEach
    void setUp() {
        usersController = new UsersController(userQueryService, userCommandService);
    }

    @Test
    void createUserReturnsCreatedWhenSuccessful() {
        var user = User.reconstitute(2L, "jane_doe", "hashedPassword", new HashSet<>());

        when(userCommandService.handle(any(SignUpCommand.class)))
                .thenReturn(Result.success(user));

        var resource = new SignUpResource("jane_doe", "password123", List.of("ROLE_OPERATOR"));
        ResponseEntity<?> response = usersController.createUser(resource);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        var userResource = (UserResource) response.getBody();
        assertEquals(2L, userResource.id());
        assertEquals("jane_doe", userResource.username());
        verify(userCommandService).handle(any(SignUpCommand.class));
    }

    @Test
    void createUserReturnsConflictWhenUsernameExists() {
        when(userCommandService.handle(any(SignUpCommand.class)))
                .thenReturn(Result.failure(ApplicationError.conflict("USER_CONFLICT", "Username already exists")));

        var resource = new SignUpResource("jane_doe", "password123", List.of("ROLE_OPERATOR"));
        ResponseEntity<?> response = usersController.createUser(resource);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        var problem = (ProblemDetail) response.getBody();
        assertEquals(409, problem.getStatus());
        verify(userCommandService).handle(any(SignUpCommand.class));
    }

    @Test
    void getAllUsersReturnsListOfUsers() {
        var user = new User("alice", "hashedPassword");

        when(userQueryService.handle(any(GetAllUsersQuery.class))).thenReturn(List.of(user));

        ResponseEntity<List<UserResource>> response = usersController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("alice", response.getBody().getFirst().username());
    }

    @Test
    void getUserByIdReturnsUserWhenFound() {
        var user = User.reconstitute(4L, "bob", "hashedPassword", new HashSet<>());

        when(userQueryService.handle(any(GetUserByIdQuery.class))).thenReturn(Optional.of(user));

        ResponseEntity<UserResource> response = usersController.getUserById(4L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(4L, response.getBody().id());
        assertEquals("bob", response.getBody().username());
    }

    @Test
    void getUserByIdReturnsNotFoundWhenUserDoesNotExist() {
        when(userQueryService.handle(any(GetUserByIdQuery.class))).thenReturn(Optional.empty());

        ResponseEntity<UserResource> response = usersController.getUserById(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
