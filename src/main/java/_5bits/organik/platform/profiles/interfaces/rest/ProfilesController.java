package _5bits.organik.platform.profiles.interfaces.rest;

import _5bits.organik.platform.profiles.application.commandservices.ProfileCommandService;
import _5bits.organik.platform.profiles.application.queryservices.ProfileQueryService;
import _5bits.organik.platform.profiles.domain.model.aggregates.Profile;
import _5bits.organik.platform.profiles.domain.model.queries.GetAllProfilesByMinimarketIdQuery;
import _5bits.organik.platform.profiles.domain.model.queries.GetAllProfilesByUserIdQuery;
import _5bits.organik.platform.profiles.domain.model.queries.GetAllProfilesQuery;
import _5bits.organik.platform.profiles.domain.model.queries.GetProfileByIdQuery;
import _5bits.organik.platform.profiles.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.profiles.domain.model.valueobjects.UserId;
import _5bits.organik.platform.profiles.interfaces.rest.resources.CreateProfileResource;
import _5bits.organik.platform.profiles.interfaces.rest.resources.ProfileResource;
import _5bits.organik.platform.profiles.interfaces.rest.resources.UpdateProfilePreferencesResource;
import _5bits.organik.platform.profiles.interfaces.rest.transform.CreateProfileCommandFromResourceAssembler;
import _5bits.organik.platform.profiles.interfaces.rest.transform.ProfileResourceFromEntityAssembler;
import _5bits.organik.platform.profiles.interfaces.rest.transform.UpdateProfilePreferencesCommandFromResourceAssembler;
import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;
import _5bits.organik.platform.shared.interfaces.rest.transform.ProblemDetailFromApplicationErrorAssembler;
import _5bits.organik.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes profile endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/profiles", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Profiles", description = "Profile and preferences management endpoints")
public class ProfilesController {
    private final ProfileCommandService profileCommandService;
    private final ProfileQueryService profileQueryService;

    public ProfilesController(ProfileCommandService profileCommandService, ProfileQueryService profileQueryService) {
        this.profileCommandService = profileCommandService;
        this.profileQueryService = profileQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a profile", description = "Creates the profile of an existing IAM user with the default preferences.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Profile created successfully",
                    content = @Content(schema = @Schema(implementation = ProfileResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required"),
            @ApiResponse(responseCode = "409", description = "Conflict - resource already exists"),
            @ApiResponse(responseCode = "404", description = "Referenced resource not found")
    })
    public ResponseEntity<?> createProfile(@Valid @RequestBody CreateProfileResource resource) {
        var command = CreateProfileCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = profileCommandService.handle(command)
                .flatMap(profileId -> profileQueryService.handle(new GetProfileByIdQuery(profileId))
                        .<Result<Profile, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("Profile", profileId.toString()))));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ProfileResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{profileId}")
    @Operation(summary = "Get profile by ID", description = "Retrieves a specific profile by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile found",
                    content = @Content(schema = @Schema(implementation = ProfileResource.class))),
            @ApiResponse(responseCode = "404", description = "Profile not found")
    })
    public ResponseEntity<?> getProfileById(
            @PathVariable
            @Parameter(description = "Profile unique identifier", example = "1", required = true)
            Long profileId
    ) {
        var profile = profileQueryService.handle(new GetProfileByIdQuery(profileId));
        if (profile.isEmpty()) {
            var error = ApplicationError.notFound("Profile", profileId.toString());
            return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(error);
        }
        return ResponseEntity.ok(ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get()));
    }

    @GetMapping
    @Operation(summary = "Get profiles", description = "Retrieves the profiles filtered by userId or minimarketId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profiles retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ProfileResource.class))),
            @ApiResponse(responseCode = "400", description = "Missing or invalid query parameter")
    })
    public ResponseEntity<?> getProfiles(
            @RequestParam(required = false)
            @Parameter(description = "IAM user identifier", example = "1")
            Long userId,
            @RequestParam(required = false)
            @Parameter(description = "Minimarket identifier", example = "mm-vida-verde")
            String minimarketId
    ) {
        if (userId != null) {
            var profiles = profileQueryService.handle(new GetAllProfilesByUserIdQuery(new UserId(userId)));
            return ResponseEntity.ok(profiles.stream().map(ProfileResourceFromEntityAssembler::toResourceFromEntity).toList());
        }
        if (minimarketId != null) {
            var profiles = profileQueryService.handle(new GetAllProfilesByMinimarketIdQuery(new MinimarketId(minimarketId)));
            return ResponseEntity.ok(profiles.stream().map(ProfileResourceFromEntityAssembler::toResourceFromEntity).toList());
        }
        var profiles = profileQueryService.handle(new GetAllProfilesQuery());
        return ResponseEntity.ok(profiles.stream().map(ProfileResourceFromEntityAssembler::toResourceFromEntity).toList());
    }

    @PutMapping("/{profileId}/preferences")
    @Operation(summary = "Update profile preferences", description = "Updates the language and the expiration alert thresholds of a profile.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Preferences updated successfully",
                    content = @Content(schema = @Schema(implementation = ProfileResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Profile not found")
    })
    public ResponseEntity<?> updateProfilePreferences(
            @PathVariable
            @Parameter(description = "Profile unique identifier", example = "1", required = true)
            Long profileId,
            @Valid @RequestBody UpdateProfilePreferencesResource resource
    ) {
        var command = UpdateProfilePreferencesCommandFromResourceAssembler.toCommandFromResource(profileId, resource);
        var result = profileCommandService.handle(command)
                .flatMap(id -> profileQueryService.handle(new GetProfileByIdQuery(id))
                        .<Result<Profile, ApplicationError>>map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("Profile", id.toString()))));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ProfileResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }
}
