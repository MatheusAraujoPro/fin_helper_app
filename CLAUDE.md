# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK (requires keystore config in local.properties)
./gradlew assembleRelease

# Run unit tests
./gradlew test

# Run instrumented tests (requires connected device/emulator)
./gradlew connectedAndroidTest

# Run a single unit test class
./gradlew test --tests "com.example.fin_helper_app.ExampleUnitTest"

# Lint
./gradlew lint
```

The release build reads keystore properties from `local.properties`: `keystorelocation`, `keystorepass`, `keystorealias`, `keypassword`.

## Architecture

The app follows **Clean Architecture** with **MVI** at the presentation layer, single module.

### Layers

**Presentation** (`presentation/`) — MVI screens. Each screen has:
- `*Screen.kt` — Composable that observes `StateFlow` and dispatches actions
- `*ScreenState.kt` — immutable data class holding all UI state
- `*ScreenAction.kt` — sealed class of user intents
- `*ViewModel.kt` — holds `MutableStateFlow<State>`, routes actions via `dispatcherAction()`

**Domain** (`domain/`) — pure business logic, no Android dependencies:
- `UseCase.kt` — abstract base that runs a `Flow` on `Dispatchers.IO` and delivers results to `Dispatchers.Main` via callbacks (`onSuccess`, `onError`). Each concrete use case defines a `Params` data class and implements `run()`.
- `repository/TransactionRepository.kt` — interface only
- `model/`, `enums/` — domain models (`TransactionModel`, `IncomeType`, `Language`)

**Data** (`data/`) — implements domain interfaces:
- `datasource/local/DataLocalDataSource.kt` — wraps SQLDelight queries
- `repository/TransactionRepositoryImpl.kt` — implements `TransactionRepository`
- `DataLocalMapper.kt` / `model/TransactionsMapper.kt` — map between SQLDelight generated types and domain models

**DI** (`di/Module.kt`) — single Hilt `SingletonComponent` module wiring `SqlDriver → Transactions DB → DataLocalDataSource → TransactionRepositoryImpl`. A singleton `CoroutineScope(SupervisorJob())` is provided for all use cases.

### Database

SQLDelight schema at `app/src/main/sqldelight/data/sqldelight/database/Transactions.sq`. The generated class is `data.sqldelight.database.Transactions`. Columns: `transaction_id`, `name`, `type` (maps to `TransactionType` Int), `value`, `createdAt`, `incomeType` (maps to `IncomeType` Int).

### UI

Reusable Compose components live in `ui/components/`. Screen-specific components are under `presentation/<screen>/components/`. The `ui/screen/Home.kt` hosts the scaffold/top-level layout. Navigation is defined in `navigation/Navigation.kt` using a `NavHost`; currently only `summary_screen` exists.

### IncomeType business logic

`IncomeType.NUBANK` and `IncomeType.GENIAL` are always expenses (credit card totals). `IncomeType.BALANCE` can be either `REVENUE` or `EXPENSE`. The header cards show: real balance (`BALANCE_REVENUE − all expenses`), Nubank total, Genial total.
