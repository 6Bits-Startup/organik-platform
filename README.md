# OrganiK Platform

OrganiK Platform is the RESTful API of OrganiK, the web application that helps minimarkets of organic products control their inventory, expiration dates, supply and conservation conditions, and connects them with their suppliers. It is built on Domain-Driven Design with a hexagonal (ports and adapters) architecture, CQRS application services, and JWT-based security.

- **Current version:** 1.0.0 (see [CHANGELOG.md](CHANGELOG.md)).
- **Web application:** [organik-web-application](https://github.com/6Bits-Startup/organik-web-application) 2.0.0 consumes this API.

## Tech Stack

| Category         | Technology                                      |
|------------------|-------------------------------------------------|
| Language         | **Java 27**                                     |
| Framework        | **Spring Boot 4.1.1**                           |
| Database         | **MySQL 8+**                                    |
| Persistence      | **Spring Data JPA / Hibernate**                 |
| Security         | **Spring Security + JWT (jjwt) + BCrypt**       |
| API Docs         | **SpringDoc OpenAPI + Scalar API Reference**    |
| Build Tool       | **Maven (wrapper included)**                    |
| Containerization | **Docker**                                      |
| Concurrency      | **Project Loom Virtual Threads**                |

## Prerequisites

- OpenJDK 27
- MySQL 8+ running locally (or reachable through environment variables)
- Maven, or the included `mvnw` wrapper
- Docker (optional)

## Project Structure

Every bounded context follows the same four layers:

```
bounded_context/
├── domain/          # Aggregates, entities, value objects, commands, queries, events and repository ports (pure Java)
├── application/     # Command and query services, event handlers and outbound ACL services
├── infrastructure/  # JPA persistence entities, converters, assemblers, Spring Data repositories and repository adapters
└── interfaces/      # REST controllers, resources, transform assemblers, ACL facades and integration events
```

| Bounded context | Responsibility                                                                   |
|-----------------|----------------------------------------------------------------------------------|
| `iam`           | Users, roles, sign-up, sign-in and JWT authorization                             |
| `profiles`      | Personal information of each user and preferences (language, expiration alerts) |
| `products`      | Product catalog and categories                                                   |
| `inventory`     | Stock lots, inventory movements and offers                                       |
| `suppliers`     | Suppliers and the products they offer                                            |
| `requisition`   | Supply requests from a minimarket to a supplier                                  |
| `procurements`  | Shipment orders and their acceptance or rejection                                |
| `conservation`  | Storage zones and temperature and humidity readings                              |
| `communication` | Notifications shown to the minimarket staff                                      |
| `dashboard`     | Operational summary of a minimarket for its home view                            |
| `analytics`     | Operational indicators and activity history                                      |
| `shared`        | Result and error model, base classes, OpenAPI/Scalar, i18n and error handling    |

### Collaboration between bounded contexts

- `profiles` checks that the user exists through the `IamContextFacade` ACL (`ExternalIamService`).
- `procurements` publishes `ShipmentOrderAcceptedIntegrationEvent`: `inventory` registers the stock lots of the order and `communication` creates an `ORDER` notification.
- `inventory` publishes `StockLotLowStockIntegrationEvent` when a lot reaches its minimum stock: `communication` creates a `LOW_STOCK` notification.
- `conservation` publishes `StorageReadingOutOfRangeIntegrationEvent`: `communication` creates a `CONSERVATION` notification.
- `analytics` records the three integration events above in the activity history of the minimarket they belong to.
- `profiles` shares the expiration thresholds among every profile of a minimarket: updating them in one profile updates the others, and a new profile adopts them.
- `dashboard` and `analytics` read the other contexts through their ACL facades (`InventoryContextFacade`, `ProcurementsContextFacade`, `RequisitionContextFacade`, `CommunicationContextFacade`, `ConservationContextFacade`, `ProfilesContextFacade`).

## Configuration

Spring profiles: `dev` (default) and `prod`. The `prod` profile reads every setting from environment variables.

### Environment Variables

Copy `.env.example` to `.env` (never commit it) or export the variables in your shell:

| Variable                 | Description                                       | Default (dev)  |
|--------------------------|---------------------------------------------------|----------------|
| `SPRING_PROFILES_ACTIVE` | Active Spring profile                             | `dev`          |
| `PORT`                   | HTTP port                                         | `8080`         |
| `DATABASE_URL`           | MySQL host                                        | `localhost`    |
| `DATABASE_PORT`          | MySQL port                                        | `3306`         |
| `DATABASE_NAME`          | Database name                                     | `organik-db`   |
| `DATABASE_USER`          | Database user                                     | `root`         |
| `DATABASE_PASSWORD`      | Database password                                 | `password`     |
| `JWT_SECRET`             | Secret used to sign JWT tokens (required in prod) | —              |

## Getting Started

### 1) Database Setup

Start MySQL. In the `dev` profile the database is created automatically if it does not exist.

Without a local MySQL, a container works as well; set `DATABASE_PORT=3308` in the run configuration:

```bash
docker run --name organik-mysql -e MYSQL_ROOT_PASSWORD=password -p 3308:3306 -d mysql:8.4
```

When the schema changes between versions (for example from 0.1.0 to 1.0.0, which adds required columns), recreate the database instead of reusing the old one.

### 2) Run the Application

#### Using Maven

```bash
./mvnw spring-boot:run
```

#### Using Docker

```bash
docker build -t organik-platform .
docker run -p 8080:8080 --env-file .env organik-platform
```

On start-up the application seeds the IAM roles and the default product categories (`fruits-vegetables`, `dairy`, `grains`, `beverages`, `bakery`, `frozen`), whose names the web application uses to translate them.

### 3) API Documentation

- Scalar API Reference: `http://localhost:8080/scalar`
- OpenAPI document: `http://localhost:8080/v3/api-docs`

### 4) Authentication

1. Sign up: `POST /api/v1/users` with `username`, `password` and `roles` (`ROLE_ADMINISTRATOR`, `ROLE_OPERATOR`, `ROLE_SUPPLIER`).
2. Sign in: `POST /api/v1/tokens` with `username` and `password`; the response contains the JWT `token`.
3. Send `Authorization: Bearer <token>` in every other request.

## Scripts and Commands

| Command                  | Description                                                |
|--------------------------|------------------------------------------------------------|
| `./mvnw spring-boot:run` | Runs the application with the `dev` profile                |
| `./mvnw test`            | Runs the test suite (the context test requires MySQL)      |
| `./mvnw clean package`   | Builds the executable JAR                                  |

## API Surface

| Bounded context | Endpoints |
|-----------------|-----------|
| IAM             | `POST /api/v1/users`, `GET /api/v1/users`, `GET /api/v1/users/{userId}`, `POST /api/v1/tokens`, `GET /api/v1/roles` |
| Profiles        | `POST /api/v1/profiles`, `GET /api/v1/profiles` (`userId`, `minimarketId`), `GET/PUT /api/v1/profiles/{profileId}`, `PUT /api/v1/profiles/{profileId}/preferences` |
| Products        | `POST/GET /api/v1/categories`, `GET /api/v1/categories/{categoryId}`, `POST/GET /api/v1/products` (`categoryId`), `GET/PUT /api/v1/products/{productId}` |
| Inventory       | `POST/GET /api/v1/stock-lots` (`minimarketId`), `GET/PUT /api/v1/stock-lots/{stockLotId}`, `POST/GET /api/v1/offers` (`stockLotId`), `GET /api/v1/offers/{offerId}`, `POST/GET /api/v1/inventory-movements` (`stockLotId`), `GET /api/v1/inventory-movements/{inventoryMovementId}` |
| Suppliers       | `POST/GET /api/v1/suppliers`, `GET /api/v1/suppliers/{supplierId}`, `POST/GET /api/v1/offered-products` (`supplierId`), `GET/PUT /api/v1/offered-products/{offeredProductId}` |
| Requisition     | `POST/GET /api/v1/supply-requests` (`minimarketId`, `supplierId`), `GET /api/v1/supply-requests/{supplyRequestId}`, `POST /api/v1/supply-requests/{supplyRequestId}/acceptances`, `POST /api/v1/supply-requests/{supplyRequestId}/rejections` |
| Procurements    | `POST/GET /api/v1/shipment-orders` (`supplierId`, `minimarketId`), `GET /api/v1/shipment-orders/{shipmentOrderId}`, `POST /api/v1/shipment-orders/{shipmentOrderId}/acceptances`, `POST /api/v1/shipment-orders/{shipmentOrderId}/rejections` |
| Conservation    | `POST/GET /api/v1/storage-zones` (`minimarketId`), `GET /api/v1/storage-zones/{storageZoneId}`, `POST/GET /api/v1/storage-readings` (`storageZoneId`), `GET /api/v1/storage-readings/{storageReadingId}` |
| Communication   | `POST/GET /api/v1/notifications` (`minimarketId`), `GET /api/v1/notifications/{notificationId}`, `POST /api/v1/notifications/{notificationId}/readings` |
| Dashboard       | `GET /api/v1/dashboard` (`minimarketId`, optional `userId` to use the expiration warning days of that user profile) |
| Analytics       | `GET /api/v1/operational-indicators` (`minimarketId`), `GET /api/v1/activity-records` (`minimarketId`), `GET /api/v1/activity-records/{activityRecordId}` |

## Architecture & Development Conventions

- **Pure domain**: aggregates extend `AbstractDomainAggregateRoot`, are rebuilt with `reconstitute(...)` and never carry JPA annotations.
- **Repository ports and adapters**: each aggregate has a domain `Repository` port and an infrastructure `RepositoryImpl` adapter that maps through a `PersistenceAssembler` to a `PersistenceEntity`.
- **CQRS**: command services return `Result<Long, ApplicationError>`; query services return `Optional` or `List`.
- **Errors**: failures are returned as RFC 9457 `ProblemDetail` with a `code`, localized in English (default) or Spanish through `Accept-Language`.
- **Naming**: tables are snake_case and plural; value objects are stored with `PersistenceConverter` classes.
- **Minimarket scope**: resources of a minimarket keep its `minimarketId` and their collections are read with a required `minimarketId` (or `supplierId`) query parameter.
- **Updates**: `PUT /{id}` endpoints map the request to an `Update…Command` that calls `updateInformation(...)` on the aggregate.
- **Git**: GitFlow with Conventional Commits; see [CONTRIBUTING.md](CONTRIBUTING.md).

## Documentation

- [Architecture decision records](docs/adrs.md)
- [Class diagram](docs/class-diagram.puml) (PlantUML)
- [REST API technical stories](docs/user-stories.md)
- Scalar API Reference at `http://localhost:8080/scalar` while the application runs

## License

This project is licensed under the MIT License. See [LICENSE.md](LICENSE.md).
