# Contributing

Thanks for contributing to OrganiK Platform. This guide describes the project
conventions for proposing, implementing, and reviewing changes.

## Before you start

- Check existing branches to avoid duplicating work: each bounded context has
  an owner on the team.
- For significant changes, agree on the scope and approach with the team
  leader first.
- Keep changes focused. Include tests and documentation for behavior that
  changes.
- Never commit credentials, API keys, database passwords, or other secrets.

## Development setup

See the [README](README.md) for the required Java, Maven, and MySQL versions,
environment variables, and local run instructions.

Before submitting a change, run the relevant tests and build:

```bash
./mvnw test
./mvnw package
```

The application-context test uses MySQL; configure the database and credentials
described in the README before running the complete test suite.

## Architecture and domain-driven design

- Preserve the bounded-context and layer structure documented in
  [`docs/class-diagram.puml`](docs/class-diagram.puml) and
  [`docs/adrs.md`](docs/adrs.md).
- Keep domain concepts, invariants, and value objects in the domain layer.
- Keep use-case contracts and orchestration in the application layer.
- Keep HTTP resources, controllers, validation-to-HTTP translation, and
  response mapping in the REST interface layer.
- Keep framework and database integration in infrastructure. Avoid exposing
  persistence or transport details to the domain model.
- Use the ubiquitous language consistently in types, methods, tests, and
  documentation.
- Follow the hexagonal structure of [ADR-0004](docs/adrs.md#adr-0004-hexagonal-architecture-with-a-pure-domain):
  pure aggregates with `reconstitute(...)`, repository ports with
  `RepositoryImpl` adapters, persistence entities, assemblers and converters.
- Command services return `Result<Long, ApplicationError>`; map errors with
  `ProblemDetailFromApplicationErrorAssembler` and add their messages to
  `messages.properties` and `messages_es.properties`.
- Cross-context reads go through ACL facades and reactions through integration
  events ([ADR-0008](docs/adrs.md#adr-0008-collaboration-through-integration-events-and-acl-facades)).
- Document every endpoint with OpenAPI annotations so that it appears in the
  Scalar API Reference, and regenerate `docs/user-stories.md` and
  `docs/class-diagram.puml` when endpoints or aggregates change.
- When changing a durable architectural decision, add an ADR or superseding ADR
  in `docs/adrs.md`.

## Branching and Git Flow

Use Git Flow-style branches:

- `main` contains releasable code.
- `develop` is the integration branch.
- Create feature branches from `develop` using
  `feature/<short-description>`, for example `feature/stock-lot-update`.
- Use `bugfix/<short-description>` or `hotfix/<short-description>` for fixes,
  based on whether the fix targets integration work or a released version.
- Use `release/<version>` for release stabilization when that workflow is in
  use.

When a feature branch is finished, merge it into `develop` with
`git merge --no-ff` and delete the branch. Keep branches focused and up to date
with `develop`. Do not rewrite shared branch history. Only `main` and `develop`
are long-lived; delete feature, release and hotfix branches once merged.

## Commit messages

Follow the Conventional Commits format:

```text
<type>[optional scope][!]: <imperative summary>

[optional body]

[optional footer(s)]
```

Common types:

- `feat`: introduce user-visible functionality.
- `fix`: correct a defect.
- `docs`: documentation-only changes.
- `test`: add or adjust tests.
- `refactor`: restructure code without changing behavior.
- `perf`: improve performance.
- `build`: change build configuration or dependencies.
- `ci`: change continuous-integration configuration.
- `chore`: maintenance that does not fit another type.

Use a concise imperative summary. Add a scope when it clarifies the affected
area, for example `fix(user): reject duplicate users`. Mark
backward-incompatible changes with `!` and explain them in the commit body.
Reference relevant issues in a footer when applicable.

## Semantic versioning and releases

Release versions use `MAJOR.MINOR.PATCH`:

- Increment **MAJOR** for incompatible public API or behavioral changes.
- Increment **MINOR** for backward-compatible functionality.
- Increment **PATCH** for backward-compatible bug fixes.

Since `1.0.0` the REST API is considered stable: removing or changing an
endpoint, a field or a status code requires a new MAJOR version. Ensure the
Maven project version, `CHANGELOG.md` and the release notes agree with the
chosen version.

### Release process

1. Move the entries of `## [Unreleased]` in `CHANGELOG.md` to a new
   `## [X.Y.Z] - YYYY-MM-DD` section and set `<version>X.Y.Z</version>` in
   `pom.xml` (`chore(release): prepare X.Y.Z`).
2. Merge `develop` into `main` with a pull request.
3. On GitHub, draft a release with the tag `vX.Y.Z` on `main`, use the
   changelog section as its notes and publish it.
4. Merge `main` back into `develop` if the release added commits to `main`. Conventional Commit types can help identify changes, but do not
replace reviewing the actual compatibility impact.

## Before merging

Before merging a branch into `develop`:

- Ensure the change has a clear description, rationale, and any migration or
  compatibility notes.
- Add or update focused tests for the affected behavior.
- Run relevant tests and package/build checks; report any environment-dependent
  tests that could not run.
- Update README, API documentation, diagrams, or ADRs when affected.
- Review the diff for unrelated changes, generated artifacts, debug output, and
  sensitive information.

Address review feedback with follow-up commits where practical. Squashing or
rebasing should follow the repository's contribution workflow and must not
rewrite history others depend on.