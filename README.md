# Product Service - Ecommerce BackEnd

## Overview
The **Product Service** is a critical component of the **Ecommerce BackEnd** application, responsible for handling product-related data and operations. This service manages the addition, retrieval, updating, and deletion of product information and integrates with the **Inventory Service** to maintain accurate stock levels.

## Purpose
The **Product Service** enables:
- **Product Management**: Allows administrators to add, update, and delete products.
- **Product Retrieval**: Provides open access to retrieve product details for all users, ensuring customers can view product information without authentication.
- **Stock Synchronization**: Communicates with the **Inventory Service** to manage stock levels when products are created or modified.

## Key Features
- **Admin Controls**: Restricts product creation, updating, and deletion to users with the admin role.
- **Open Product View**: Allows unauthenticated access to view product information, enabling easy browsing for customers.
- **Inventory Sync**: Synchronizes stock data with the **Inventory Service** whenever product stock is modified.

## API Endpoints
Here are the primary endpoints available in the **Product Service**:

| Endpoint                  | Method | Description                                  | Access       |
|---------------------------|--------|----------------------------------------------|--------------|
| `/products`               | POST   | Add a new product                            | Admin Only   |
| `/products/quantity`      | POST   | Add a new product with specified quantity    | Admin Only   |
| `/product/all`            | GET    | Retrieve all products                        | Public       |
| `/product/{id}`           | GET    | Retrieve product details by ID               | Public       |
| `/product/admin/update`   | PUT    | Update an existing product                   | Admin Only   |
| `/product/admin/delete/{id}` | DELETE | Delete a product by ID                   | Admin Only   |

## Dependencies
- **Java 17** (or compatible version)
- **Spring Boot**: Provides the foundational framework for the service.
- **Elasticsearch**: Stores product data, enabling quick search and retrieval.
- **FeignClient**: For inter-service communication with the **Inventory Service**.

## Configuration
Configuration for the **Product Service** includes settings for:
- **Elasticsearch Connection**: The service connects to an Elasticsearch index to store and retrieve product data.
- **Feign Client**: Used to communicate with the **Inventory Service** to manage stock levels.

Key configuration properties are located in the `application.yml` file.

## Integration with Inventory Service
The **Product Service** uses **OpenFeign** to communicate with the **Inventory Service** for stock updates:
- When a new product is added, stock information is updated in the Inventory Service.
- Similarly, any changes in product stock will reflect in the inventory data through synchronous API calls.

## Security
- **Role-Based Access Control**: Ensures only administrators can modify product data.
- **Open Access Endpoints**: Product retrieval endpoints are open to all users, allowing browsing without authentication.

The **Product Service** is essential for managing and delivering product information within the **Ecommerce BackEnd** application.
