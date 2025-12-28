# Lemfi QA Test Project

This project implements a comprehensive E2E test suite for the Lemfi API, covering the complete user journey:

1. User Registration - Create new user account and extract JSESSIONID cookie
2. Personal Data Update - Update user profile (authenticated)
3. Balance Top-Up - Add funds to account (authenticated)
4. Balance Verification - Verify correct balance (authenticated)
5. Payment History - Validate transaction history (authenticated)


## Quick Start

### 1. Clone/Navigate to Project

```bash
cd lemfi-qa-tests
```

### 2. Run All Tests

```bash
./gradlew test
```

### 3. View Test Results

Test results will be displayed in the console and saved to:
```
build/reports/tests/test/index.html
```

---

## Running Tests

### Run All Tests
```bash
./gradlew test
```

### Run Only E2E Tests
```bash
./gradlew e2eTest
```

### Run with Specific Base URL
```bash
./gradlew test -Dapi.base.url=http://example.com:8080
```

### Run with Custom Timeout
```bash
./gradlew test -Dapi.timeout=60000
```

### Run with Info Logging
```bash
./gradlew test --info
```

### Clean and Rebuild
```bash
./gradlew clean test
```