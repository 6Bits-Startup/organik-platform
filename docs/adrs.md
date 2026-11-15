# Architecture Decision Records

This document records significant architecture decisions for OrganiK Platform.
Each record captures the context, decision, and consequences so that future
changes can revisit decisions with their original trade-offs in view.

## Process

- Records are numbered sequentially and use the `ADR-NNNN` identifier.
- New records are appended; accepted records are not silently rewritten.
- When a decision changes, add a new ADR that supersedes the old one and update
  its status to `Superseded` with a link to the replacement.
- Use `Proposed`, `Accepted`, `Rejected`, `Deprecated`, or `Superseded` as status.
- Keep records focused on durable decisions, not implementation notes or
  temporary tasks.

## Index

| ADR                                                              | Title                                      | Status   |
|------------------------------------------------------------------|--------------------------------------------|----------|
| [ADR-0001](#adr-0001-layered-domain-driven-design-structure)     | Layered Domain-Driven Design structure     | Superseded by ADR-0004 |
| [ADR-0002](#adr-0002-mysql-with-spring-data-jpa-for-persistence) | MySQL with Spring Data JPA for persistence | Superseded by ADR-0005 |
| [ADR-0003](#adr-0003-rest-api-contract-and-error-responses)      | REST API contract and error responses      | Superseded by ADR-0006 |
| [ADR-0004](#adr-0004-hexagonal-architecture-with-a-pure-domain) | Hexagonal architecture with a pure domain | Accepted |
| [ADR-0005](#adr-0005-persistence-through-repository-adapters) | Persistence through repository adapters | Accepted |
| [ADR-0006](#adr-0006-application-results-and-rfc-9457-errors) | Application results and RFC 9457 errors | Accepted |
| [ADR-0007](#adr-0007-jwt-authentication-with-users-roles-and-profiles) | JWT authentication with users, roles and profiles | Accepted |
| [ADR-0008](#adr-0008-collaboration-through-integration-events-and-acl-facades) | Collaboration through integration events and ACL facades | Accepted |
| [ADR-0009](#adr-0009-scalar-api-reference-for-the-api-documentation) | Scalar API Reference for the API documentation | Accepted |
| [ADR-0010](#adr-0010-resources-scoped-to-a-minimarket) | Resources scoped to a minimarket | Accepted |
| [ADR-0011](#adr-0011-reference-data-seeded-on-start-up) | Reference data seeded on start-up | Accepted |

## ADR-0001: Layered Domain-Driven Design structure

- **Status:** Superseded by [ADR-0004](#adr-0004-hexagonal-architecture-with-a-pure-domain)
- **Date:** 2026-10-28

### Context

The service supports the OrganiK web application, which manages inventory,
expiration, supply and conservation for minimarkets of organic products. The
domain has distinct areas such as identity and access (`User`), suppliers,
supply requests, shipment orders and storage conservation. The code also needs
to separate HTTP concerns, use-case orchestration, domain behavior, and
persistence integration.

### Decision

Organize the code into one bounded context per area (`iam`, `inventory`,
`suppliers`, `requisition`, `procurements` and `conservation`), with cross-cutting
components in `shared`. Inside each bounded context, use the `interfaces`,
`application`, `domain`, and `infrastructure` layers. Keep command and query
service contracts in the application layer and their implementations under
`application.internal`. Represent commands and queries as domain-model request
records; application services orchestrate use cases and return domain results.

Each bounded context defines its own value objects, even when another context
uses the same concept (for example, `SupplierId`), so that contexts remain
independent of each other.

### Consequences

- REST controllers depend on application contracts rather than persistence
  implementations.
- Application services coordinate repositories and translate persistence
  outcomes into application results.
- Domain types remain separate from HTTP resources and Spring MVC response
  types.
- The current code has separate command and query services, but shares the
  same persistence model; this does not imply separate read/write databases.
- Bounded contexts can be completed by different people in parallel, because
  each one lives in its own package.
- Some value objects are duplicated across contexts by design.

## ADR-0002: MySQL with Spring Data JPA for persistence

- **Status:** Superseded by [ADR-0005](#adr-0005-persistence-through-repository-adapters)
- **Date:** 2026-10-28

### Context

Users, suppliers, supply requests, shipment orders and storage readings must
persist across service restarts. Users must be unique by email address. The
application uses Spring Boot and models each concept as a JPA aggregate.

### Decision

Use MySQL for the relational database and Spring Data JPA/Hibernate for
persistence. Persist `User` through `UserRepository`, convert value objects
such as `EmailAddress` and `FullName` to strings with JPA attribute converters,
store enumerations such as `Role` and `UserStatus` as strings, and enforce the
unique email with a named database constraint (`uk_email`). Use the shared
Hibernate physical naming strategy to pluralize table names and convert
identifiers to snake case.

### Consequences

- Database-level uniqueness remains the final guard against concurrent
  duplicate creates; the application also performs an existence pre-check for
  a quicker conflict response.
- Persistence tests and application-context tests require a configured,
  available database unless explicitly isolated from MySQL.
- The domain value objects remain independent of their string storage
  representation.
- Schema changes are applied automatically (`spring.jpa.hibernate.ddl-auto=update`),
  which is convenient for development but does not replace controlled
  migrations in a production deployment.

## ADR-0003: REST API contract and error responses

- **Status:** Superseded by [ADR-0006](#adr-0006-application-results-and-rfc-9457-errors)
- **Date:** 2026-10-28

### Context

Clients, such as the OrganiK web application, need stable HTTP semantics for
creating and retrieving users, including actionable validation and lookup
errors. HTTP response types should remain outside the domain and application
models.

### Decision

Expose the service under `/api/v1/users` as a REST API. Return `201 Created`
for successful creation, `409 Conflict` for duplicate creation, `200 OK` for
successful retrievals (including an empty list), `404 Not Found` for missing
single-resource lookups, and `400 Bad Request` for invalid input. Represent
errors as Spring `ProblemDetail` responses and resolve user-facing messages
through `MessageSource`. Keep request/response resources and HTTP response
assemblers in the REST interface layer.

### Consequences

- HTTP status and payload translation is localized to the interface layer.
- Error details can be localized using the request locale.
- Changes to these status codes or response shapes are API contract changes
  and should be reflected in API documentation and compatibility review.

## ADR-0004: Hexagonal architecture with a pure domain

- **Status:** Accepted
- **Date:** 2026-11-14
- **Supersedes:** ADR-0001

### Context

The first layered structure let JPA annotations and framework types reach the
domain model, and every bounded context solved persistence and errors in its
own way. The course reference projects use a hexagonal (ports and adapters)
structure with a pure domain.

### Decision

Keep one package per bounded context (`iam`, `profiles`, `products`,
`inventory`, `suppliers`, `requisition`, `procurements`, `conservation`,
`communication`, `dashboard`, `analytics` and `shared`) with the `domain`,
`application`, `infrastructure` and `interfaces` layers. Aggregates extend
`AbstractDomainAggregateRoot`, carry no framework annotations, are rebuilt with
`reconstitute(...)` and register domain events. Application services follow
CQRS: command services return `Result<Long, ApplicationError>` and query
services return `Optional` or `List`.

### Consequences

- The domain can be tested without Spring or a database.
- Every aggregate needs a persistence entity, an assembler and converters, which
  adds classes but keeps the layers independent.

## ADR-0005: Persistence through repository adapters

- **Status:** Accepted
- **Date:** 2026-11-14
- **Supersedes:** ADR-0002

### Context

The domain must not depend on JPA, but the service still persists its data in
MySQL with Spring Data JPA.

### Decision

Each aggregate has a domain repository port (`XRepository`) and an
infrastructure adapter (`XRepositoryImpl`) that maps the aggregate to a
`XPersistenceEntity` through a `XPersistenceAssembler`. Persistence entities
extend `AuditableAbstractPersistenceEntity` (`created_at`, `updated_at`) and
store value objects with `XPersistenceConverter` classes. Tables are snake_case
and plural. The adapter publishes the domain events of the aggregate after it is
saved.

### Consequences

- Uniqueness is checked by the application service and guarded by named
  database constraints.
- `ddl-auto=update` keeps the schema in development; a schema change that adds
  required columns needs a new database or a migration.

## ADR-0006: Application results and RFC 9457 errors

- **Status:** Accepted
- **Date:** 2026-11-14
- **Supersedes:** ADR-0003

### Context

Each context returned its own failure types, so the same error looked different
depending on the endpoint.

### Decision

Command services return `Result<T, ApplicationError>`. `ResponseEntityAssembler`
turns a result into the HTTP response and
`ProblemDetailFromApplicationErrorAssembler` turns an `ApplicationError` into an
RFC 9457 `ProblemDetail` with a `code` (`VALIDATION_ERROR`, `X_NOT_FOUND`,
`X_CONFLICT`, ...). `GlobalExceptionHandler` covers validation and unexpected
errors. Messages are localized in English (default) and Spanish through
`Accept-Language`.

### Consequences

- Clients rely on the HTTP status and the `code` of the problem, not on the
  message text.

## ADR-0007: JWT authentication with users, roles and profiles

- **Status:** Accepted
- **Date:** 2026-11-14

### Context

The web application needs sign-up, sign-in and the identity of the user in every
request, and keeps the personal data of each user apart from its credentials.

### Decision

`iam` owns users (username and BCrypt password) and the roles
`ROLE_ADMINISTRATOR`, `ROLE_OPERATOR` and `ROLE_SUPPLIER`. `POST /api/v1/users`
signs up and `POST /api/v1/tokens` returns a JWT; every other endpoint requires
`Authorization: Bearer <token>`. `profiles` owns the name, e-mail, minimarket or
supplier and preferences of each user and checks the user through the
`IamContextFacade` ACL.

### Consequences

- The backend authenticates every request; the permissions per role are applied
  by the web application, as in the course reference projects.

## ADR-0008: Collaboration through integration events and ACL facades

- **Status:** Accepted
- **Date:** 2026-11-14

### Context

Accepting an order must register its lots, and low stock and out-of-range
readings must notify the minimarket and feed the analytics, without coupling the
bounded contexts.

### Decision

A context translates its domain events into integration events
(`ShipmentOrderAcceptedIntegrationEvent`, `StockLotLowStockIntegrationEvent`,
`StorageReadingOutOfRangeIntegrationEvent`) that other contexts handle with
`@EventListener` beans. Reads across contexts go through ACL facades
(`interfaces/acl`) consumed by `External*Service` classes.

### Consequences

- `inventory`, `communication` and `analytics` react to the other contexts
  without importing their domain.
- The handlers run in the same process and transaction flow; a failed handler is
  logged and does not undo the original command.

## ADR-0009: Scalar API Reference for the API documentation

- **Status:** Accepted
- **Date:** 2026-11-14

### Context

The frontend team needs an interactive reference of the endpoints.

### Decision

Generate the OpenAPI document with SpringDoc (`/v3/api-docs`) and show it with
the Scalar API Reference (`/scalar`), with the bearer authentication scheme.

### Consequences

- Controllers describe each operation and response with OpenAPI annotations.

## ADR-0010: Resources scoped to a minimarket

- **Status:** Accepted
- **Date:** 2026-11-15

### Context

Several minimarkets share the platform. Each one must see only its stock lots,
storage zones, readings, notifications and users, and a minimarket handles one
expiration policy whoever of its users looks at the stock.

### Decision

Stock lots, storage zones, storage readings, notifications, profiles, supply
requests and shipment orders keep the `minimarketId` they belong to, and their
collections are read with a required `minimarketId` (or `supplierId`) query
parameter. The expiration thresholds are stored in the profiles: updating them
updates every profile of the minimarket and a new profile adopts them.

### Consequences

- The frontend sends the minimarket of the signed-in user in every collection
  request.
- Products and categories remain a catalog shared by every minimarket.

## ADR-0011: Reference data seeded on start-up

- **Status:** Accepted
- **Date:** 2026-11-14

### Context

Roles and the default product categories must exist before anyone signs up or
creates a product.

### Decision

`ApplicationReadyEventHandler` beans seed the IAM roles and the default
categories (`fruits-vegetables`, `dairy`, `grains`, `beverages`, `bakery`,
`frozen`) when the application is ready, only when they do not exist yet.

### Consequences

- A new database is usable right after start-up; the web application translates
  the category names.
