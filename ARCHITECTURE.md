# Software Architecture

This project is built using **Kotlin Multiplatform (KMP)**.
It aims to target **Android**, **iOS** (upcoming), and **Desktop** (JVM).

## Structure
- `shared/` - Contains all cross-platform business logic, view models, database models, and the core UI.
  - `src/commonMain/kotlin` - Primary source set for shared UI (`App.kt`) and business logic.
  - `src/androidMain/kotlin` - Android-specific implementations.
  - `src/iosMain/kotlin` - iOS-specific implementations.
  - `src/desktopMain/kotlin` - JVM-specific implementations.

- `androidApp/` - An empty shell Android app that acts as an entry point for the shared Android Main codebase.
- `iosApp/` - An empty shell Xcode project that embeds the shared module framework.
- `desktopApp/` - An empty shell for Desktop distribution.

## Databases
We use `SqlDelight` to handle local SQLite DBs across platforms.