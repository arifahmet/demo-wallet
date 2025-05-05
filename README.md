# Transaction Management Service

This project is a **Spring Boot** application designed for managing transactions within a wallet system. The service dynamically handles transactions, validates transaction limits, auto-approves transactions, and processes events through **Kafka**.

## Features

- **Wallet Management**: Create and manage wallets with various currencies, track balances.
- **Transaction Management**:
  - Handle DEPOSIT and WITHDRAW operations.
  - Transactions below the configured limit are automatically approved.
  - Transactions above the limit are flagged as "PENDING" for further action.
- **User Management**: Provides user-related details and operations.
- **Credential Management**: Authentication operations using username/password.
- **Reactive Design**: Built using **Spring WebFlux**, enabling asynchronous and non-blocking operations.
- **Kafka Integration**: Producess and consumes Kafka topics for event-based workflows.

### OpenAPI & Swagger Integration

Swagger is integrated for API documentation and testing.

**Swagger URL**: [Swagger UI](http://localhost:8044/webjars/swagger-ui/index.html)

---

## Technologies Used

- **Java 17** (Project Codebase)
- **Spring Boot** (Framework)
- **Spring WebFlux** (Reactive programming)
- **Reactive Kafka** (Kafka event-handling)
- **Lombok** (Reduce boilerplate code)
- **Docker Compose** (Setup Kafka and Postgres environments)
- **Liquibase** (Db migration/initialization)

---

## How to Run Kafka Locally with Docker Compose

Kafka and Postgres is setup with a `docker-compose.yaml` file in the `kafka` directory.

1. Navigate to the `kafka` folder.
   ```bash
   cd kafka
   ```
2. Start Kafka.
   ```bash
   docker-compose up
   ```
3. Verify Kafka locally on port `9092`.
4. Stop Kafka when done.
   ```bash
   docker-compose down
   ```

---

## Using the APIs

### Wallet Management

#### Get Wallets

- **Endpoint**: `/api/v1/wallet`
- **Method**: `GET`
- **Summary**: Fetches wallets for a user.
- **Example Response**:
  ```json
  [
    {
        "walletName": "test_wallet",
        "currency": "USD",
        "usableBalance": "0.00",
        "blockedBalance": "0.00",
        "activeForWithdraw": true
    },
    {
        "walletName": "eur_wallet",
        "currency": "USD",
        "usableBalance": "0.00",
        "blockedBalance": "0.00",
        "activeForWithdraw": true
    },
    {
        "walletName": "eurr_wallet",
        "currency": "EUR",
        "usableBalance": "0.00",
        "blockedBalance": "0.00",
        "activeForWithdraw": true
    }
  ]
  ```

#### Create Wallet

- **Endpoint**: `/api/v1/wallet`
- **Method**: `POST`
- **Summary**: Creates a new wallet with zero balance.
- **Request Body Example**:
  ```json
  {
    "walletName": "eur_wallet",
    "currency": "EUR",
    "activeForWithdraw": false,
    "activeForShopping": true
  }
  ```
- **Response**:
  ```json
  {
    "walletId": "12345",
    "usableBalance": 0.00,
    "blockedBalance": 0.00
  }
  ```

#### Change Wallet Withdraw Status

- **Endpoint**: `/api/v1/wallet/withdraw-status`
- **Method**: `POST`
- **Summary**: Wallet withdraw status changer.
- **Request Body Example**:
  ```json
  {
    "walletName": "test_wallet",
    "activeForWithdraw": false
  }
  ```

#### Change Wallet Shopping Active Status

- **Endpoint**: `/api/v1/wallet/shopping-status`
- **Method**: `POST`
- **Summary**: Wallet shopping active status changer.
- **Request Body Example**:
  ```json
  {
    "walletName": "test_wallet",
    "activeForShopping": true
  }
  ```

---

### Transaction Management

#### Get Transactions

- **Endpoint**: `/api/v1/transaction`
- **Method**: `GET`
- **Summary**: Retrieve transactions based on filters.
- **Query Parameters**:
  - `walletName` (Optional): User's wallet name.
  - `status` (Optional): `PENDING`, `APPROVED`, `DENIED`, `FAILED`.
  - `transactionType` (Optional): `DEPOSIT`, `WITHDRAW`.
- **Example Response**:
  ```json
  [
    {
        "walletName": "test_wallet",
        "currency": "USD",
        "usableBalance": "2997.00",
        "blockedBalance": "0.00",
        "activeForWithdraw": false,
        "activeForShopping": true
    },
    {
        "walletName": "eur_wallet",
        "currency": "EUR",
        "usableBalance": "0.00",
        "blockedBalance": "0.00",
        "activeForWithdraw": false,
        "activeForShopping": true
    }
  ]
  ```

#### Create Transaction

- **Endpoint**: `/api/v1/transaction`
- **Method**: `POST`
- **Summary**: Initiates a transaction.
- **transactionType**: `DEPOSIT`, `WITHDRAW`
- **oppositePartyType**: `IBAN`, `BANK_ACCOUNT`
- **Request Body Example**:
  ```json
  {
    "walletName": "test_wallet",
    "transactionType": "WITHDRAW",
    "oppositePartyType": "BANK_ACCOUNT",
    "amount": 1001
  }
  ```
- **Response**:
  ```json
  {
    "transactionKey": "09681f9d-d708-4791-bc7a-34e711aeb7f1",
    "transactionStatus": "PENDING"
  }
  ```

#### Approve/Deny Transaction

- **Endpoint**: `/api/v1/transaction/status`
- **Method**: `POST`
- **Summary**: Approve or Deny a transaction.
- **Request Body Example For Approve**:
  ```json
  {
    "transactionKey": "09681f9d-d708-4791-bc7a-34e711aeb7f1",
    "transactionStatus": "APPROVED"
  }
  ```
- **Request Body Example For Deny**:
  ```json
  {
    "transactionKey": "09681f9d-d708-4791-bc7a-34e711aeb7f1",
    "transactionStatus": "DENIED"
  }
  ```

---

### User Management

#### Get User Details

- **Endpoint**: `/api/v1/user`
- **Method**: `GET`
- **Summary**: Fetches logged in user.
- **Example Response**:
  ```json
  {
    "userKey": "1992c9fb-b0e8-416f-a1d9-ad5355507ed0",
    "username": "test_user",
    "name": "Arif",
    "surname": "Halıcı",
    "tckn": "11111111111"
  }
  ```

---

### Credential Management

#### Sign In

- **Endpoint**: `/api/v1/credential/sign-in`
- **Method**: `POST`
- **Summary**: Authorizes users with credentials.
- **Request Body Example**:
  ```json
  {
    "username":"test_user",
    "password":"123456"
  }
  ```
- **Example Response**:
  ```json
  {
    "accessToken": "Bearer abc.def.ghi"
  }
  ```

#### Sign Up

- **Endpoint**: `/api/v1/credential/sign-up`
- **Method**: `POST`
- **Summary**: Sign up a new user.
- **Request Body Example**:
  ```json
  {
    "username":"test_user",
    "password":"123456",
    "name": "Arif",
    "surname": "Halıcı",
    "tckn": "11111111111"
  }
  ```
---

## Authorization

APIs require the `Authorization` header with a Bearer token. Unauthorized requests will return a `401 Unauthorized`
error.

---
This project is licensed under the **MIT License**.
