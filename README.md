# Rewards Service

Spring Boot REST API that calculates retailer reward points for customers over a user-provided date range.

## Business Rule

For each transaction:

- 2 points for every whole dollar spent above `$100`
- 1 point for every whole dollar spent between `$50` and `$100`

Examples:

- `$120.00` -> `90` points
- `$76.45` -> `26` points
- `$49.99` -> `0` points

Fractional dollar values are not rewarded until they reach the next whole dollar, which matches the common interpretation of "every dollar spent".

## Technical Stack

- Java 8
- Spring Boot 2.7.18
- Spring Web
- Bean Validation
- JUnit 5 / MockMvc

## API

### 1. Get rewards for one customer

`GET /api/rewards/customers/{customerId}?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD`

Example:

```http
GET /api/rewards/customers/1?startDate=2026-01-01&endDate=2026-03-31
```

If `startDate` and `endDate` are omitted, the API defaults to the last 3 months ending on the current date.

Example:

```http
GET /api/rewards/customers/1
```

Sample response:

```json
{
  "customerId": 1,
  "customerCode": "CUST-1001",
  "customerName": "Ava Thompson",
  "email": "ava.thompson@example.com",
  "periodStartDate": "2026-01-01",
  "periodEndDate": "2026-03-31",
  "totalTransactions": 4,
  "totalRewardPoints": 386,
  "monthlyRewards": [
    {
      "month": "January 2026",
      "transactionCount": 2,
      "rewardPoints": 116,
      "totalSpend": "196.45"
    }
  ],
  "transactions": [
    {
      "transactionId": 10001,
      "transactionDate": "2026-01-05",
      "description": "Groceries",
      "amount": "120.00",
      "rewardPoints": 90
    }
  ]
}
```

## Validation and Error Handling

- `startDate` and `endDate` are optional
- when both are omitted, the API uses the last 3 months ending on the current date
- `startDate` must be before or equal to `endDate`
- maximum supported range is `12` months
- unknown customer id returns `404`

Sample error response:

```json
{
  "timestamp": "2026-04-09T18:33:00.000",
  "status": 400,
  "error": "Bad Request",
  "message": "Start date must be before or equal to end date",
  "path": "/api/rewards/customers/1"
}
```

## Running the Application

Using Maven:

```bash
mvn spring-boot:run
```

## Running Tests

Using Maven:

```bash
mvn test
```
