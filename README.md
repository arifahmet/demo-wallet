# Transaction Management Service

This project is a **Spring Boot** application designed for managing transactions within a wallet system. The service dynamically handles transactions, validates transaction limits, auto-approves transactions, and processes events through **Kafka**.

## Features

- **Transaction Management**
   - Create and process transactions (DEPOSIT/WITHDRAW).
   - Transactions under the configured limit are automatically approved.
   - Transactions exceeding the limit are flagged as "PENDING" and require user action to approve or reject.
   - Retrieve user transactions based on specific filters (e.g., status or type).

- **Reactive Design**
   - Built using **Spring WebFlux**, enabling asynchronous and non-blocking operations.
   - Highly scalable for handling large transaction loads.

- **Kafka Event Handling**
   - Listens to Kafka topics for transaction-related events.
   - Publishes events for downstream processing.

- **Dockerized Kafka Setup**
   - Quickly set up a Kafka instance using Docker Compose for local development and testing.

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
- **Docker & Docker Compose** (For running Kafka)

---

## How to Run Kafka Locally with Docker Compose

This project includes a `docker-compose.yaml` file in the `kafka` folder to quickly set up a local **Kafka**
environment.

1. Navigate to the `kafka` directory in the project:
   ```bash
   cd kafka
   ```

2. Start Kafka using Docker Compose:
   ```bash
   docker-compose up
   ```
   This will start the Kafka broker and ZooKeeper services.

3. Verify Kafka is running:
   - Kafka should now be running locally on port `9092`.
   - Kafka topics will be automatically created as required by the application.

4. Stop Kafka when done:
   ```bash
   docker-compose down
   ```

---

## API Endpoints

### Transaction Management APIs

1. **Create Transaction**
   - **POST** `/transactions/create`
   - Request Body (Example from `TransactionExampleModels.createTransactionRequest`):
     ```json
     {
       "walletId": "12345",
       "amount": 100.00,
       "type": "DEPOSIT"
     }
     ```
   - Response (Example from `TransactionExampleModels.createTransactionResponse`):
     ```json
     {
       "transactionId": "67890",
       "status": "APPROVED"
     }
     ```

2. **Get User Transactions**
   - **GET** `/transactions`
   - Query Parameters:
      - `walletName`: The name of the wallet
      - `status`: Transaction status (e.g., `PENDING`, `APPROVED`, `DENIED`, `FAILED`)
      - `transactionType`: Transaction type (`DEPOSIT`, `WITHDRAW`)
   - Response (Example from `TransactionExampleModels.getTransactionResponse`):
     ```json
     [
       {
         "transactionId": "12345",
         "amount": 50.0,
         "status": "APPROVED",
         "type": "DEPOSIT"
       }
     ]
     ```

3. **Change Transaction Status**
   - **PUT** `/transactions/change-status`
   - Request Body (Example from `TransactionExampleModels.changeTransactionStatusRequest`):
     ```json
     {
       "transactionId": "12345",
       "newStatus": "APPROVED"
     }
     ```
   - Response:
     ```json
     {
       "message": "Transaction status updated successfully."
     }
     ```

### Wallet Management APIs

1. **Get Wallet Balance**
   - **GET** `/wallets/{walletId}/balance`
   - Response (Example from `WalletExampleModels.getWalletBalanceResponse`):
     ```json
     {
       "walletId": "12345",
       "balance": 100.00
     }
     ```

2. **Create Wallet**
   - **POST** `/wallets/create`
   - Request Body (Example from `WalletExampleModels.createWalletRequest`):
     ```json
     {
       "userId": "456"
     }
     ```
   - Response:
     ```json
     {
       "walletId": "12345",
       "balance": 0.00
     }
     ```

### User Management APIs

1. **Get User Details**
   - **GET** `/users/{userId}/details`
   - Response (Example from `UserExampleModels.getUserDetailsResponse`):
     ```json
     {
       "userId": "456",
       "username": "exampleUser"
     }
     ```

### Credential APIs

1. **Login Credential**
   - **POST** `/credentials/login`
   - Request Body (Example from `CredentialExampleModels.loginRequest`):
     ```json
     {
       "username": "exampleUser",
       "password": "examplePassword"
     }
     ```
   - Response:
     ```json
     {
       "token": "Bearer abc.def.ghi"
     }
     ```

---

## License

This project is licensed under the **MIT License**.