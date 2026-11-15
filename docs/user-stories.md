# OrganiK Platform REST API Technical Stories

## Overview
API-focused technical stories for the developers of the OrganiK web application. They are generated from the REST controllers of version 1.0.0, so each story matches an endpoint, its parameters and its responses.

- Base path: `/api/v1`.
- Every endpoint except `POST /api/v1/users` (sign-up) and `POST /api/v1/tokens` (sign-in) requires `Authorization: Bearer <token>`; a missing or invalid token returns `401 Unauthorized`.
- Errors are RFC 9457 `ProblemDetail` payloads with a `code` (for example `VALIDATION_ERROR` or `PROFILE_CONFLICT`), localized in English or Spanish through `Accept-Language`.
- Collections filtered by a minimarket or a supplier require that query parameter and answer `400 Bad Request` when it is missing.

## Technical Stories

### IAM

#### TS001 — Get all roles
**Endpoint:** `GET /api/v1/roles`

As a frontend developer, I want the API to retrieve a list of all available system roles so that the web application can use it.
#### Acceptance criteria:
- Scenario: Roles retrieved successfully
    - Given a valid `GET /api/v1/roles` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Forbidden - Insufficient permissions
    - Given a `GET /api/v1/roles` request is received
    - Then the API responds with `403 Forbidden` and a `ProblemDetail` payload.
---
#### TS002 — Create authentication token (Sign-in)
**Endpoint:** `POST /api/v1/tokens`

As a frontend developer, I want the API to authenticate a user with provided credentials and return a JWT token for subsequent requests so that the web application can use it.
- Request body: `SignInResource` (see the Scalar API Reference for its fields and validations).
#### Acceptance criteria:
- Scenario: Token generated successfully
    - Given a valid `POST /api/v1/tokens` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Invalid credentials or malformed request
    - Given a `POST /api/v1/tokens` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
- Scenario: User not found with provided username
    - Given a `POST /api/v1/tokens` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---
#### TS003 — Create user (Sign-up)
**Endpoint:** `POST /api/v1/users`

As a frontend developer, I want the API to create a new user account with provided credentials and assign specified roles so that the web application can use it.
- Request body: `SignUpResource` (see the Scalar API Reference for its fields and validations).
#### Acceptance criteria:
- Scenario: User created successfully
    - Given a valid `POST /api/v1/users` request is received
    - Then the API responds with `201 Created` and the resource representation.
- Scenario: Invalid input data or username already exists
    - Given a `POST /api/v1/users` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
- Scenario: Conflict - username already taken
    - Given a `POST /api/v1/users` request is received
    - Then the API responds with `409 Conflict` and a `ProblemDetail` payload.
---
#### TS004 — Get all users
**Endpoint:** `GET /api/v1/users`

As a frontend developer, I want the API to retrieve a list of all users in the system with their roles so that the web application can use it.
#### Acceptance criteria:
- Scenario: Users retrieved successfully
    - Given a valid `GET /api/v1/users` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Forbidden - Insufficient permissions
    - Given a `GET /api/v1/users` request is received
    - Then the API responds with `403 Forbidden` and a `ProblemDetail` payload.
---
#### TS005 — Get user by ID
**Endpoint:** `GET /api/v1/users`

As a frontend developer, I want the API to retrieve a specific user's information by unique identifier so that the web application can use it.
#### Acceptance criteria:
- Scenario: User retrieved successfully
    - Given a valid `GET /api/v1/users` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Forbidden - Insufficient permissions
    - Given a `GET /api/v1/users` request is received
    - Then the API responds with `403 Forbidden` and a `ProblemDetail` payload.
- Scenario: User not found
    - Given a `GET /api/v1/users` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---

### Profiles

#### TS006 — Create a profile
**Endpoint:** `POST /api/v1/profiles`

As a frontend developer, I want the API to create the profile of an existing IAM user with the default preferences so that the web application can use it.
- Request body: `CreateProfileResource` (see the Scalar API Reference for its fields and validations).
#### Acceptance criteria:
- Scenario: Profile created successfully
    - Given a valid `POST /api/v1/profiles` request is received
    - Then the API responds with `201 Created` and the resource representation.
- Scenario: Invalid input data
    - Given a `POST /api/v1/profiles` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
- Scenario: Conflict - resource already exists
    - Given a `POST /api/v1/profiles` request is received
    - Then the API responds with `409 Conflict` and a `ProblemDetail` payload.
- Scenario: Referenced resource not found
    - Given a `POST /api/v1/profiles` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---
#### TS007 — Get profile by ID
**Endpoint:** `GET /api/v1/profiles/{profileId}`

As a frontend developer, I want the API to retrieve a specific profile by its unique identifier so that the web application can use it.
#### Acceptance criteria:
- Scenario: Profile found
    - Given a valid `GET /api/v1/profiles/{profileId}` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Profile not found
    - Given a `GET /api/v1/profiles/{profileId}` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---
#### TS008 — Get profiles
**Endpoint:** `GET /api/v1/profiles?userId={userId}&minimarketId={minimarketId}`

As a frontend developer, I want the API to retrieve the profiles filtered by userId or minimarketId so that the web application can use it.
- Query parameter `userId`: iam user identifier.
- Query parameter `minimarketId`: minimarket identifier.
#### Acceptance criteria:
- Scenario: Profiles retrieved successfully
    - Given a valid `GET /api/v1/profiles` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Missing or invalid query parameter
    - Given a `GET /api/v1/profiles` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
---
#### TS009 — Update profile
**Endpoint:** `PUT /api/v1/profiles/{profileId}`

As a frontend developer, I want the API to update the personal information of a profile so that the web application can use it.
- Request body: `UpdateProfileResource` (see the Scalar API Reference for its fields and validations).
#### Acceptance criteria:
- Scenario: Profile updated successfully
    - Given a valid `PUT /api/v1/profiles/{profileId}` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Invalid input data
    - Given a `PUT /api/v1/profiles/{profileId}` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
- Scenario: Profile not found
    - Given a `PUT /api/v1/profiles/{profileId}` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---
#### TS010 — Update profile preferences
**Endpoint:** `PUT /api/v1/profiles/{profileId}/preferences`

As a frontend developer, I want the API to update the language of a profile and the expiration alert thresholds of its minimarket, shared by every profile of that minimarket so that the web application can use it.
- Request body: `UpdateProfilePreferencesResource` (see the Scalar API Reference for its fields and validations).
#### Acceptance criteria:
- Scenario: Preferences updated successfully
    - Given a valid `PUT /api/v1/profiles/{profileId}/preferences` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Invalid input data
    - Given a `PUT /api/v1/profiles/{profileId}/preferences` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
- Scenario: Profile not found
    - Given a `PUT /api/v1/profiles/{profileId}/preferences` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---

### Products

#### TS011 — Create a category
**Endpoint:** `POST /api/v1/categories`

As a frontend developer, I want the API to create a product category with a unique name so that the web application can use it.
- Request body: `CreateCategoryResource` (see the Scalar API Reference for its fields and validations).
#### Acceptance criteria:
- Scenario: Category created successfully
    - Given a valid `POST /api/v1/categories` request is received
    - Then the API responds with `201 Created` and the resource representation.
- Scenario: Invalid input data
    - Given a `POST /api/v1/categories` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
- Scenario: Conflict - resource already exists
    - Given a `POST /api/v1/categories` request is received
    - Then the API responds with `409 Conflict` and a `ProblemDetail` payload.
- Scenario: Referenced resource not found
    - Given a `POST /api/v1/categories` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---
#### TS012 — Get category by ID
**Endpoint:** `GET /api/v1/categories/{categoryId}`

As a frontend developer, I want the API to retrieve a specific category by its unique identifier so that the web application can use it.
#### Acceptance criteria:
- Scenario: Category found
    - Given a valid `GET /api/v1/categories/{categoryId}` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Category not found
    - Given a `GET /api/v1/categories/{categoryId}` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---
#### TS013 — Get all categories
**Endpoint:** `GET /api/v1/categories`

As a frontend developer, I want the API to retrieve every category so that the web application can use it.
#### Acceptance criteria:
- Scenario: Categories retrieved successfully
    - Given a valid `GET /api/v1/categories` request is received
    - Then the API responds with `200 OK` and the resource representation.
---
#### TS014 — Create a product
**Endpoint:** `POST /api/v1/products`

As a frontend developer, I want the API to create a product of the catalog with a unique SKU so that the web application can use it.
- Request body: `CreateProductResource` (see the Scalar API Reference for its fields and validations).
#### Acceptance criteria:
- Scenario: Product created successfully
    - Given a valid `POST /api/v1/products` request is received
    - Then the API responds with `201 Created` and the resource representation.
- Scenario: Invalid input data
    - Given a `POST /api/v1/products` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
- Scenario: Conflict - resource already exists
    - Given a `POST /api/v1/products` request is received
    - Then the API responds with `409 Conflict` and a `ProblemDetail` payload.
- Scenario: Referenced resource not found
    - Given a `POST /api/v1/products` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---
#### TS015 — Get product by ID
**Endpoint:** `GET /api/v1/products/{productId}`

As a frontend developer, I want the API to retrieve a specific product by its unique identifier so that the web application can use it.
#### Acceptance criteria:
- Scenario: Product found
    - Given a valid `GET /api/v1/products/{productId}` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Product not found
    - Given a `GET /api/v1/products/{productId}` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---
#### TS016 — Get products
**Endpoint:** `GET /api/v1/products?categoryId={categoryId}`

As a frontend developer, I want the API to retrieve the products filtered by categoryId so that the web application can use it.
- Query parameter `categoryId`: category identifier.
#### Acceptance criteria:
- Scenario: Products retrieved successfully
    - Given a valid `GET /api/v1/products` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Missing or invalid query parameter
    - Given a `GET /api/v1/products` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
---
#### TS017 — Update product
**Endpoint:** `PUT /api/v1/products/{productId}`

As a frontend developer, I want the API to update the name, category, supplier, unit and minimum stock of a product so that the web application can use it.
- Request body: `UpdateProductResource` (see the Scalar API Reference for its fields and validations).
#### Acceptance criteria:
- Scenario: Product updated successfully
    - Given a valid `PUT /api/v1/products/{productId}` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Invalid input data
    - Given a `PUT /api/v1/products/{productId}` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
- Scenario: Product or category not found
    - Given a `PUT /api/v1/products/{productId}` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---

### Inventory

#### TS018 — Register an inventory movement
**Endpoint:** `POST /api/v1/inventory-movements`

As a frontend developer, I want the API to register an entry, exit, adjustment or waste and apply it to the stock of the lot so that the web application can use it.
- Request body: `RegisterInventoryMovementResource` (see the Scalar API Reference for its fields and validations).
#### Acceptance criteria:
- Scenario: Inventory movement created successfully
    - Given a valid `POST /api/v1/inventory-movements` request is received
    - Then the API responds with `201 Created` and the resource representation.
- Scenario: Invalid input data
    - Given a `POST /api/v1/inventory-movements` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
- Scenario: Referenced resource not found
    - Given a `POST /api/v1/inventory-movements` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---
#### TS019 — Get inventory movement by ID
**Endpoint:** `GET /api/v1/inventory-movements/{inventoryMovementId}`

As a frontend developer, I want the API to retrieve a specific inventory movement by its unique identifier so that the web application can use it.
#### Acceptance criteria:
- Scenario: Inventory movement found
    - Given a valid `GET /api/v1/inventory-movements/{inventoryMovementId}` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Inventory movement not found
    - Given a `GET /api/v1/inventory-movements/{inventoryMovementId}` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---
#### TS020 — Get inventory movements
**Endpoint:** `GET /api/v1/inventory-movements?stockLotId={stockLotId}`

As a frontend developer, I want the API to retrieve the inventory movements filtered by stockLotId so that the web application can use it.
- Query parameter `stockLotId`: stock lot identifier.
#### Acceptance criteria:
- Scenario: Inventory movements retrieved successfully
    - Given a valid `GET /api/v1/inventory-movements` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Missing or invalid query parameter
    - Given a `GET /api/v1/inventory-movements` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
---
#### TS021 — Create an offer
**Endpoint:** `POST /api/v1/offers`

As a frontend developer, I want the API to create a special price for a stock lot that ends on or before its expiration date so that the web application can use it.
- Request body: `CreateOfferResource` (see the Scalar API Reference for its fields and validations).
#### Acceptance criteria:
- Scenario: Offer created successfully
    - Given a valid `POST /api/v1/offers` request is received
    - Then the API responds with `201 Created` and the resource representation.
- Scenario: Invalid input data
    - Given a `POST /api/v1/offers` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
- Scenario: Referenced resource not found
    - Given a `POST /api/v1/offers` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---
#### TS022 — Get offer by ID
**Endpoint:** `GET /api/v1/offers/{offerId}`

As a frontend developer, I want the API to retrieve a specific offer by its unique identifier so that the web application can use it.
#### Acceptance criteria:
- Scenario: Offer found
    - Given a valid `GET /api/v1/offers/{offerId}` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Offer not found
    - Given a `GET /api/v1/offers/{offerId}` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---
#### TS023 — Get offers
**Endpoint:** `GET /api/v1/offers?stockLotId={stockLotId}`

As a frontend developer, I want the API to retrieve the offers filtered by stockLotId so that the web application can use it.
- Query parameter `stockLotId`: stock lot identifier.
#### Acceptance criteria:
- Scenario: Offers retrieved successfully
    - Given a valid `GET /api/v1/offers` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Missing or invalid query parameter
    - Given a `GET /api/v1/offers` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
---
#### TS024 — Register a stock lot
**Endpoint:** `POST /api/v1/stock-lots`

As a frontend developer, I want the API to register a lot of a product received by a minimarket so that the web application can use it.
- Request body: `CreateStockLotResource` (see the Scalar API Reference for its fields and validations).
#### Acceptance criteria:
- Scenario: Stock lot created successfully
    - Given a valid `POST /api/v1/stock-lots` request is received
    - Then the API responds with `201 Created` and the resource representation.
- Scenario: Invalid input data
    - Given a `POST /api/v1/stock-lots` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
- Scenario: Conflict - resource already exists
    - Given a `POST /api/v1/stock-lots` request is received
    - Then the API responds with `409 Conflict` and a `ProblemDetail` payload.
- Scenario: Referenced resource not found
    - Given a `POST /api/v1/stock-lots` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---
#### TS025 — Get stock lot by ID
**Endpoint:** `GET /api/v1/stock-lots/{stockLotId}`

As a frontend developer, I want the API to retrieve a specific stock lot by its unique identifier so that the web application can use it.
#### Acceptance criteria:
- Scenario: Stock lot found
    - Given a valid `GET /api/v1/stock-lots/{stockLotId}` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Stock lot not found
    - Given a `GET /api/v1/stock-lots/{stockLotId}` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---
#### TS026 — Get stock lots
**Endpoint:** `GET /api/v1/stock-lots?minimarketId={minimarketId}`

As a frontend developer, I want the API to retrieve the stock lots filtered by minimarketId so that the web application can use it.
- Query parameter `minimarketId`: minimarket identifier.
#### Acceptance criteria:
- Scenario: Stock lots retrieved successfully
    - Given a valid `GET /api/v1/stock-lots` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Missing or invalid query parameter
    - Given a `GET /api/v1/stock-lots` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
---
#### TS027 — Update stock lot
**Endpoint:** `PUT /api/v1/stock-lots/{stockLotId}`

As a frontend developer, I want the API to update the location, notes and expiration date of a stock lot (its quantity only changes through inventory movements) so that the web application can use it.
- Request body: `UpdateStockLotResource` (see the Scalar API Reference for its fields and validations).
#### Acceptance criteria:
- Scenario: Stock lot updated successfully
    - Given a valid `PUT /api/v1/stock-lots/{stockLotId}` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Invalid input data
    - Given a `PUT /api/v1/stock-lots/{stockLotId}` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
- Scenario: Stock lot not found
    - Given a `PUT /api/v1/stock-lots/{stockLotId}` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---

### Suppliers

#### TS028 — Publish an offered product
**Endpoint:** `POST /api/v1/offered-products`

As a frontend developer, I want the API to publish a product lot in the catalog of a supplier so that the web application can use it.
- Request body: `CreateOfferedProductResource` (see the Scalar API Reference for its fields and validations).
#### Acceptance criteria:
- Scenario: Offered product created successfully
    - Given a valid `POST /api/v1/offered-products` request is received
    - Then the API responds with `201 Created` and the resource representation.
- Scenario: Invalid input data
    - Given a `POST /api/v1/offered-products` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
- Scenario: Conflict - resource already exists
    - Given a `POST /api/v1/offered-products` request is received
    - Then the API responds with `409 Conflict` and a `ProblemDetail` payload.
- Scenario: Referenced resource not found
    - Given a `POST /api/v1/offered-products` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---
#### TS029 — Get offered product by ID
**Endpoint:** `GET /api/v1/offered-products/{offeredProductId}`

As a frontend developer, I want the API to retrieve a specific offered product by its unique identifier so that the web application can use it.
#### Acceptance criteria:
- Scenario: Offered product found
    - Given a valid `GET /api/v1/offered-products/{offeredProductId}` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Offered product not found
    - Given a `GET /api/v1/offered-products/{offeredProductId}` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---
#### TS030 — Get offered products
**Endpoint:** `GET /api/v1/offered-products?supplierId={supplierId}`

As a frontend developer, I want the API to retrieve the offered products filtered by supplierId so that the web application can use it.
- Query parameter `supplierId`: supplier identifier.
#### Acceptance criteria:
- Scenario: Offered products retrieved successfully
    - Given a valid `GET /api/v1/offered-products` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Missing or invalid query parameter
    - Given a `GET /api/v1/offered-products` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
---
#### TS031 — Update offered product
**Endpoint:** `PUT /api/v1/offered-products/{offeredProductId}`

As a frontend developer, I want the API to update the lot, the availability and the expiration date of an offered product so that the web application can use it.
- Request body: `UpdateOfferedProductResource` (see the Scalar API Reference for its fields and validations).
#### Acceptance criteria:
- Scenario: Offered product updated successfully
    - Given a valid `PUT /api/v1/offered-products/{offeredProductId}` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Invalid input data
    - Given a `PUT /api/v1/offered-products/{offeredProductId}` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
- Scenario: Offered product not found
    - Given a `PUT /api/v1/offered-products/{offeredProductId}` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
- Scenario: Conflict - lot code already offered by the supplier
    - Given a `PUT /api/v1/offered-products/{offeredProductId}` request is received
    - Then the API responds with `409 Conflict` and a `ProblemDetail` payload.
---
#### TS032 — Register a supplier
**Endpoint:** `POST /api/v1/suppliers`

As a frontend developer, I want the API to register an organic supplier with a unique email address so that the web application can use it.
- Request body: `CreateSupplierResource` (see the Scalar API Reference for its fields and validations).
#### Acceptance criteria:
- Scenario: Supplier created successfully
    - Given a valid `POST /api/v1/suppliers` request is received
    - Then the API responds with `201 Created` and the resource representation.
- Scenario: Invalid input data
    - Given a `POST /api/v1/suppliers` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
- Scenario: Conflict - resource already exists
    - Given a `POST /api/v1/suppliers` request is received
    - Then the API responds with `409 Conflict` and a `ProblemDetail` payload.
- Scenario: Referenced resource not found
    - Given a `POST /api/v1/suppliers` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---
#### TS033 — Get supplier by ID
**Endpoint:** `GET /api/v1/suppliers/{supplierId}`

As a frontend developer, I want the API to retrieve a specific supplier by its unique identifier so that the web application can use it.
#### Acceptance criteria:
- Scenario: Supplier found
    - Given a valid `GET /api/v1/suppliers/{supplierId}` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Supplier not found
    - Given a `GET /api/v1/suppliers/{supplierId}` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---
#### TS034 — Get all suppliers
**Endpoint:** `GET /api/v1/suppliers`

As a frontend developer, I want the API to retrieve every supplier so that the web application can use it.
#### Acceptance criteria:
- Scenario: Suppliers retrieved successfully
    - Given a valid `GET /api/v1/suppliers` request is received
    - Then the API responds with `200 OK` and the resource representation.
---

### Requisition

#### TS035 — Create a supply request
**Endpoint:** `POST /api/v1/supply-requests`

As a frontend developer, I want the API to create a pending supply request from a minimarket to a supplier so that the web application can use it.
- Request body: `CreateSupplyRequestResource` (see the Scalar API Reference for its fields and validations).
#### Acceptance criteria:
- Scenario: Supply request created successfully
    - Given a valid `POST /api/v1/supply-requests` request is received
    - Then the API responds with `201 Created` and the resource representation.
- Scenario: Invalid input data
    - Given a `POST /api/v1/supply-requests` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
- Scenario: Referenced resource not found
    - Given a `POST /api/v1/supply-requests` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---
#### TS036 — Get supply request by ID
**Endpoint:** `GET /api/v1/supply-requests/{supplyRequestId}`

As a frontend developer, I want the API to retrieve a specific supply request by its unique identifier so that the web application can use it.
#### Acceptance criteria:
- Scenario: Supply request found
    - Given a valid `GET /api/v1/supply-requests/{supplyRequestId}` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Supply request not found
    - Given a `GET /api/v1/supply-requests/{supplyRequestId}` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---
#### TS037 — Get supply requests
**Endpoint:** `GET /api/v1/supply-requests?minimarketId={minimarketId}&supplierId={supplierId}`

As a frontend developer, I want the API to retrieve the supply requests filtered by minimarketId or supplierId so that the web application can use it.
- Query parameter `minimarketId`: minimarket identifier.
- Query parameter `supplierId`: supplier identifier.
#### Acceptance criteria:
- Scenario: Supply requests retrieved successfully
    - Given a valid `GET /api/v1/supply-requests` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Missing or invalid query parameter
    - Given a `GET /api/v1/supply-requests` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
---
#### TS038 — Accept supply request
**Endpoint:** `POST /api/v1/supply-requests/{supplyRequestId}/acceptances`

As a frontend developer, I want the API to let the supplier accept a pending supply request so that the web application can use it.
#### Acceptance criteria:
- Scenario: Supply request accepts
    - Given a valid `POST /api/v1/supply-requests/{supplyRequestId}/acceptances` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Supply request not found
    - Given a `POST /api/v1/supply-requests/{supplyRequestId}/acceptances` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
- Scenario: Conflict - supply request already answered
    - Given a `POST /api/v1/supply-requests/{supplyRequestId}/acceptances` request is received
    - Then the API responds with `409 Conflict` and a `ProblemDetail` payload.
---
#### TS039 — Reject supply request
**Endpoint:** `POST /api/v1/supply-requests/{supplyRequestId}/rejections`

As a frontend developer, I want the API to let the supplier reject a pending supply request so that the web application can use it.
#### Acceptance criteria:
- Scenario: Supply request rejects
    - Given a valid `POST /api/v1/supply-requests/{supplyRequestId}/rejections` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Supply request not found
    - Given a `POST /api/v1/supply-requests/{supplyRequestId}/rejections` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
- Scenario: Conflict - supply request already answered
    - Given a `POST /api/v1/supply-requests/{supplyRequestId}/rejections` request is received
    - Then the API responds with `409 Conflict` and a `ProblemDetail` payload.
---

### Procurements

#### TS040 — Create a shipment order
**Endpoint:** `POST /api/v1/shipment-orders`

As a frontend developer, I want the API to create a pending shipment order sent by a supplier to a minimarket so that the web application can use it.
- Request body: `CreateShipmentOrderResource` (see the Scalar API Reference for its fields and validations).
#### Acceptance criteria:
- Scenario: Shipment order created successfully
    - Given a valid `POST /api/v1/shipment-orders` request is received
    - Then the API responds with `201 Created` and the resource representation.
- Scenario: Invalid input data
    - Given a `POST /api/v1/shipment-orders` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
- Scenario: Referenced resource not found
    - Given a `POST /api/v1/shipment-orders` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---
#### TS041 — Get shipment order by ID
**Endpoint:** `GET /api/v1/shipment-orders/{shipmentOrderId}`

As a frontend developer, I want the API to retrieve a specific shipment order by its unique identifier so that the web application can use it.
#### Acceptance criteria:
- Scenario: Shipment order found
    - Given a valid `GET /api/v1/shipment-orders/{shipmentOrderId}` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Shipment order not found
    - Given a `GET /api/v1/shipment-orders/{shipmentOrderId}` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---
#### TS042 — Get shipment orders
**Endpoint:** `GET /api/v1/shipment-orders?supplierId={supplierId}&minimarketId={minimarketId}`

As a frontend developer, I want the API to retrieve the shipment orders filtered by supplierId or minimarketId so that the web application can use it.
- Query parameter `supplierId`: supplier identifier.
- Query parameter `minimarketId`: minimarket identifier.
#### Acceptance criteria:
- Scenario: Shipment orders retrieved successfully
    - Given a valid `GET /api/v1/shipment-orders` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Missing or invalid query parameter
    - Given a `GET /api/v1/shipment-orders` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
---
#### TS043 — Accept shipment order
**Endpoint:** `POST /api/v1/shipment-orders/{shipmentOrderId}/acceptances`

As a frontend developer, I want the API to accept a pending shipment order; its lots are registered in the minimarket inventory so that the web application can use it.
- Request body: `AcceptShipmentOrderResource` (see the Scalar API Reference for its fields and validations).
#### Acceptance criteria:
- Scenario: Shipment order accepted
    - Given a valid `POST /api/v1/shipment-orders/{shipmentOrderId}/acceptances` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Shipment order not found
    - Given a `POST /api/v1/shipment-orders/{shipmentOrderId}/acceptances` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
- Scenario: Conflict - shipment order already decided
    - Given a `POST /api/v1/shipment-orders/{shipmentOrderId}/acceptances` request is received
    - Then the API responds with `409 Conflict` and a `ProblemDetail` payload.
---
#### TS044 — Reject shipment order
**Endpoint:** `POST /api/v1/shipment-orders/{shipmentOrderId}/rejections`

As a frontend developer, I want the API to reject a pending shipment order with a reason so that the web application can use it.
- Request body: `RejectShipmentOrderResource` (see the Scalar API Reference for its fields and validations).
#### Acceptance criteria:
- Scenario: Shipment order rejected
    - Given a valid `POST /api/v1/shipment-orders/{shipmentOrderId}/rejections` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Shipment order not found
    - Given a `POST /api/v1/shipment-orders/{shipmentOrderId}/rejections` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
- Scenario: Conflict - shipment order already decided
    - Given a `POST /api/v1/shipment-orders/{shipmentOrderId}/rejections` request is received
    - Then the API responds with `409 Conflict` and a `ProblemDetail` payload.
---

### Conservation

#### TS045 — Record a storage reading
**Endpoint:** `POST /api/v1/storage-readings`

As a frontend developer, I want the API to record a temperature and humidity reading and check it against the ranges of its zone so that the web application can use it.
- Request body: `RecordStorageReadingResource` (see the Scalar API Reference for its fields and validations).
#### Acceptance criteria:
- Scenario: Storage reading created successfully
    - Given a valid `POST /api/v1/storage-readings` request is received
    - Then the API responds with `201 Created` and the resource representation.
- Scenario: Invalid input data
    - Given a `POST /api/v1/storage-readings` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
- Scenario: Referenced resource not found
    - Given a `POST /api/v1/storage-readings` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---
#### TS046 — Get storage reading by ID
**Endpoint:** `GET /api/v1/storage-readings/{storageReadingId}`

As a frontend developer, I want the API to retrieve a specific storage reading by its unique identifier so that the web application can use it.
#### Acceptance criteria:
- Scenario: Storage reading found
    - Given a valid `GET /api/v1/storage-readings/{storageReadingId}` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Storage reading not found
    - Given a `GET /api/v1/storage-readings/{storageReadingId}` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---
#### TS047 — Get storage readings
**Endpoint:** `GET /api/v1/storage-readings?storageZoneId={storageZoneId}`

As a frontend developer, I want the API to retrieve the storage readings filtered by storageZoneId so that the web application can use it.
- Query parameter `storageZoneId`: storage zone identifier.
#### Acceptance criteria:
- Scenario: Storage readings retrieved successfully
    - Given a valid `GET /api/v1/storage-readings` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Missing or invalid query parameter
    - Given a `GET /api/v1/storage-readings` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
---
#### TS048 — Create a storage zone
**Endpoint:** `POST /api/v1/storage-zones`

As a frontend developer, I want the API to create a storage zone with its allowed temperature and humidity ranges so that the web application can use it.
- Request body: `CreateStorageZoneResource` (see the Scalar API Reference for its fields and validations).
#### Acceptance criteria:
- Scenario: Storage zone created successfully
    - Given a valid `POST /api/v1/storage-zones` request is received
    - Then the API responds with `201 Created` and the resource representation.
- Scenario: Invalid input data
    - Given a `POST /api/v1/storage-zones` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
- Scenario: Conflict - resource already exists
    - Given a `POST /api/v1/storage-zones` request is received
    - Then the API responds with `409 Conflict` and a `ProblemDetail` payload.
- Scenario: Referenced resource not found
    - Given a `POST /api/v1/storage-zones` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---
#### TS049 — Get storage zone by ID
**Endpoint:** `GET /api/v1/storage-zones/{storageZoneId}`

As a frontend developer, I want the API to retrieve a specific storage zone by its unique identifier so that the web application can use it.
#### Acceptance criteria:
- Scenario: Storage zone found
    - Given a valid `GET /api/v1/storage-zones/{storageZoneId}` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Storage zone not found
    - Given a `GET /api/v1/storage-zones/{storageZoneId}` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---
#### TS050 — Get storage zones
**Endpoint:** `GET /api/v1/storage-zones?minimarketId={minimarketId}`

As a frontend developer, I want the API to retrieve the storage zones filtered by minimarketId so that the web application can use it.
- Query parameter `minimarketId`: minimarket identifier.
#### Acceptance criteria:
- Scenario: Storage zones retrieved successfully
    - Given a valid `GET /api/v1/storage-zones` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Missing or invalid query parameter
    - Given a `GET /api/v1/storage-zones` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
---

### Communication

#### TS051 — Create a notification
**Endpoint:** `POST /api/v1/notifications`

As a frontend developer, I want the API to create a new unread notification for the minimarket staff so that the web application can use it.
- Request body: `CreateNotificationResource` (see the Scalar API Reference for its fields and validations).
#### Acceptance criteria:
- Scenario: Notification created successfully
    - Given a valid `POST /api/v1/notifications` request is received
    - Then the API responds with `201 Created` and the resource representation.
- Scenario: Invalid input data
    - Given a `POST /api/v1/notifications` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
---
#### TS052 — Get notification by ID
**Endpoint:** `GET /api/v1/notifications/{notificationId}`

As a frontend developer, I want the API to retrieve a specific notification by its unique identifier so that the web application can use it.
#### Acceptance criteria:
- Scenario: Notification found
    - Given a valid `GET /api/v1/notifications/{notificationId}` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Notification not found
    - Given a `GET /api/v1/notifications/{notificationId}` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---
#### TS053 — Get notifications
**Endpoint:** `GET /api/v1/notifications?minimarketId={minimarketId}`

As a frontend developer, I want the API to retrieve the notifications of a minimarket filtered by minimarketId so that the web application can use it.
- Query parameter `minimarketId`: minimarket identifier.
#### Acceptance criteria:
- Scenario: Notifications retrieved successfully
    - Given a valid `GET /api/v1/notifications` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Missing or invalid query parameter
    - Given a `GET /api/v1/notifications` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
---
#### TS054 — Mark notification as read
**Endpoint:** `POST /api/v1/notifications/{notificationId}/readings`

As a frontend developer, I want the API to mark an unread notification as read so that the web application can use it.
#### Acceptance criteria:
- Scenario: Notification marked as read
    - Given a valid `POST /api/v1/notifications/{notificationId}/readings` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Notification not found
    - Given a `POST /api/v1/notifications/{notificationId}/readings` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
- Scenario: Conflict - notification already read
    - Given a `POST /api/v1/notifications/{notificationId}/readings` request is received
    - Then the API responds with `409 Conflict` and a `ProblemDetail` payload.
---

### Dashboard

#### TS055 — Get dashboard summary
**Endpoint:** `GET /api/v1/dashboard?minimarketId={minimarketId}&userId={userId}`

As a frontend developer, I want the API to retrieve the operational summary of a minimarket: stock, expirations, pending orders, pending requests and unread notifications. When userId is sent, the lots about to expire are counted with the warning days of that user profile so that the web application can use it.
- Query parameter `minimarketId`: minimarket identifier.
- Query parameter `userId`: iam user that views the dashboard.
#### Acceptance criteria:
- Scenario: Dashboard summary retrieved successfully
    - Given a valid `GET /api/v1/dashboard` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Missing or invalid minimarketId
    - Given a `GET /api/v1/dashboard` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
---

### Analytics

#### TS056 — Get activity record by ID
**Endpoint:** `GET /api/v1/activity-records/{activityRecordId}`

As a frontend developer, I want the API to retrieve a specific entry of the activity history so that the web application can use it.
#### Acceptance criteria:
- Scenario: Activity record found
    - Given a valid `GET /api/v1/activity-records/{activityRecordId}` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Activity record not found
    - Given a `GET /api/v1/activity-records/{activityRecordId}` request is received
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload.
---
#### TS057 — Get activity history
**Endpoint:** `GET /api/v1/activity-records?minimarketId={minimarketId}`

As a frontend developer, I want the API to retrieve the activity history, optionally filtered by minimarket so that the web application can use it.
- Query parameter `minimarketId`: minimarket identifier.
#### Acceptance criteria:
- Scenario: Activity history retrieved successfully
    - Given a valid `GET /api/v1/activity-records` request is received
    - Then the API responds with `200 OK` and the resource representation.
---
#### TS058 — Get operational indicators
**Endpoint:** `GET /api/v1/operational-indicators?minimarketId={minimarketId}`

As a frontend developer, I want the API to compute expirations, waste, conservation alerts and supplier order indicators of a minimarket so that the web application can use it.
- Query parameter `minimarketId`: minimarket identifier.
#### Acceptance criteria:
- Scenario: Operational indicators retrieved successfully
    - Given a valid `GET /api/v1/operational-indicators` request is received
    - Then the API responds with `200 OK` and the resource representation.
- Scenario: Missing or invalid minimarketId
    - Given a `GET /api/v1/operational-indicators` request is received
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload.
