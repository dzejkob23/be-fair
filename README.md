# Be-Fair

Be-Fair is a cross-platform application for fair expense sharing among groups, like roommates, friends on a trip, or couples. It helps track who paid for what and calculates who owes whom, ensuring transparency and fairness.

## Key Features (Planned)

- **Group Management:** Create and join groups with a simple code or invite.
- **Expense Logging:** Quickly add expenses with a description, amount, and payer.
- **Split Customization:** Split expenses equally or by specific amounts/percentages.
- **Real-time Sync:** All data is synced across devices via a centralized backend.
- **Settlement Suggestions:** Automated advice on the most efficient way to settle debts.

## Tech Stack

- **Frontend:** [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) (Android, iOS)
- **Backend:** [Ktor](https://ktor.io/) (Kotlin-based server)
- **Shared Logic:** Kotlin Multiplatform (KMP)
- **Database:** (Planned) SQLDelight for local caching, PostgreSQL for backend.

## Project Structure

- `androidApp`: The entry point for the Android application.
- `iosApp`: The entry point for the iOS application.
- `composeApp`: Shared UI and navigation logic using Compose Multiplatform.
- `shared`: Shared business logic, models, and networking.
- `server`: The Ktor backend server.

## Getting Started

### Prerequisites

- Android Studio (latest stable or Ladybug+)
- Xcode (for iOS development)
- JDK 17 or higher

### Running the App

#### Android
1. Open the project in Android Studio.
2. Select the `androidApp` run configuration.
3. Click "Run".

#### iOS
1. Open `iosApp/iosApp.xcodeproj` in Xcode.
2. Select a simulator or physical device.
3. Click "Run".

#### Backend
1. In Android Studio, select the `server` run configuration.
2. Click "Run".

## Development Rules

For AI agents and developers, please refer to [AGENTS.md](./AGENTS.md) for detailed architecture, module ownership, and safe-edit guidelines.

---

### Current Status & Versions

- **Kotlin:** `2.3.20`
- **Compose Multiplatform:** `1.9.3`
- **Ktor:** `3.3.3`
- **Target Android SDK:** 36
