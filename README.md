# Transaction Management Service

This project is a **Spring Boot** application designed for managing transactions within a wallet system. The service dynamically handles transactions, validates transaction limits, auto-approves transactions, and processes events through **Kafka**.

## Features

- **Transaction Management**
    - Create and process transactions (credit/debit).
    - Transactions under the configured limit are automatically approved.
    - Transactions exceeding the limit require user action to be marked as "PENDING".
    - Change transaction statuses (e.g., PENDING, PROCESSING, COMPLETED, FAILED).
    - Retrieve user transactions based on specific filters (e.g., status or type).

- **Reactive Design**
    - Built using **Spring WebFlux**, enabling asynchronous and non-blocking operations.
    - Highly scalable for handling large transaction loads.

- **Kafka Event Handling**
    - Listens to Kafka topics for transaction-related events.
    - Publishes events for downstream processing.

- **Validation**
    - Enforces a configurable transaction limit for auto-approval.
    - Supports custom validation rules for status changes and transaction operations.

## Technologies Used

- **Java 17** (Project Codebase)
- **Spring Boot** (Core Framework)
- **Spring WebFlux** (Reactive programming)
- **Reactive Kafka** (Event-driven transaction handling)
- **Lombok** (For reducing boilerplate code)
- **Jakarta EE** (Imports for modern Java enterprise development)

## Key Features

1. **Auto-Approval for Small Transactions**
    - Transactions below the configured amount (default: 1000) are automatically approved.
    - Saves time and reduces delays for small payments.

2. **Pending Status for Large Transactions**
    - Transactions exceeding the limit are flagged as "PENDING".
    - Requires additional user action to approve or reject.

3. **Event-Based Processing**
    - Produces and consumes transaction-related messages in **Kafka**.
    - Supports event-driven workflows and enables seamless integration with other services.

4. **Validation and Status Management**
    - Prevents invalid status changes with strong validation logic.
    - Provides seamless transaction lifecycle management.

5. **Transactional Repository**
    - Provides easy integration with databases for transactional persistence.

## Configuration

- The transaction limit for automatic approval can be configured as `transaction-limit` in your **application.properties** file.

```properties
transaction-limit=1000
```

- Transactions with an amount below this limit will be automatically approved, while those exceeding it will be marked as "PENDING".

## How to Run

1. Clone the repository:
   ```bash
   git clone <repository-url>
   ```
2. Navigate to the project directory:
   ```bash
   cd <project-folder>
   ```
3. Build the project using **Maven**:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## API Endpoints

### 1. Create Transaction
- **POST** `/transactions/create`
- Request Body:
  ```json
  {
    "walletId": "12345",
    "amount": 100.00,
    "type": "DEBIT"
  }
  ```
- Response if the transaction amount is below the limit:
  ```json
  {
    "transactionId": "67890",
    "status": "COMPLETED"
  }
  ```

- Response if the transaction amount exceeds the limit:
  ```json
  {
    "transactionId": "67891",
    "status": "PENDING"
  }
  ```

### 2. Get User Transactions
- **GET** `/transactions`
- Query Parameters:
    - `walletName`: The name of the wallet
    - `status`: Transaction status (e.g., `PENDING`, `COMPLETED`)
    - `transactionType`: Transaction type (`CREDIT`, `DEBIT`)

- Response:
  ```json
  [
    {
      "transactionId": "12345",
      "amount": 50.0,
      "status": "COMPLETED",
      "type": "CREDIT"
    }
  ]
  ```

### 3. Change Transaction Status
- **PUT** `/transactions/change-status`
- Request Body:
  ```json
  {
    "transactionId": "12345",
    "newStatus": "COMPLETED"
  }
  ```

- Response:
  ```json
  {
    "message": "Transaction status updated successfully."
  }
  ```

## Reactive Programming Explained

This system leverages reactive programming features with **Spring WebFlux** for non-blocking backpressure-based workflows. Key principles:
- Mono: Represents a single, asynchronous response.
- Flux: Handles multiple responses or streams of data asynchronously.

For example:
- **Retrieve Transactions** return a `Flux`: This allows returning zero or more `UserTransactionResponseDto` objects.
- **Create Transaction** returns a `Mono`: This single result represents the creation of one transaction.

## License

This project is licensed under the **MIT License**.