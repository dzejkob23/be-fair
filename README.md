# Be-Fair

Be-Fair is application that allows you to track cost-effectiveness of your waredrobe.

## Key Features (Planned)

- **User account:** Register & Login user account.
- **Track clothes wearing & washing:** Add your clothes and track its usage.
- **Real-time Sync:** All data is synced across devices via a centralized backend.

## Tech Stack

- **Frontend:** [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) (Android, iOS)
- **Backend:** [Ktor](https://ktor.io/) (Kotlin-based server)
- **Shared Logic:** Kotlin Multiplatform (KMP)
- **Database:** (Planned) Exposed DB for local caching, and backend database

## Project Structure

- `app/androidApp`: The entry point for the Android application.
- `app/iosApp`: The entry point for the iOS application.
- `app/shared`: Shared UI, navigation, and mobile domain/data logic using Compose Multiplatform.
- `core`: Shared business logic, models, and networking (all platforms).
- `server`: The Ktor backend server.

## Getting Started

### Prerequisites

- Android Studio (for Android and KMP)
- Xcode (for iOS development)
- JDK 17 or higher

### Running the App

#### Android
1. Open the project in Android Studio.
2. Select the `androidApp` run configuration.
3. Click "Run".

#### iOS
1. Open `app/iosApp/iosApp.xcodeproj` in Xcode.
2. Select a simulator or physical device.
3. Click "Run".

#### Backend
1. In Android Studio, select the `server` run configuration.
2. Click "Run".

## Development Rules

For AI agents and developers, please refer to [AGENTS.md](./AGENTS.md) for detailed architecture, module ownership, and safe-edit guidelines.
