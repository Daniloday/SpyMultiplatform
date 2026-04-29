# AGENTS.md

## Project Snapshot
- Kotlin Multiplatform party game using Compose Multiplatform for shared UI.
- Targets: Android app in `composeApp`; iOS wrapper/project in `iosApp`.
- Architecture is modular: `core/*` contains shared foundations, `feature/*` contains screens and feature state.
- Dependency injection is Koin. App-level module aggregation lives in `composeApp/src/commonMain/kotlin/com/missclick/spy/di/AppModule.kt`.
- Navigation is centralized in `core/navigation/src/commonMain/kotlin/com.missclick.spy.core/navigation/NavGraph.kt`.

## Module Map
- `composeApp`: Android application config, shared app entry UI, iOS framework entry point.
- `iosApp`: native iOS shell, CocoaPods config, iOS-specific ad/app setup, duplicated store content JSON files.
- `core:model`: domain models such as options, sets, words, languages.
- `core:domain`: use cases.
- `core:data`: repository interfaces and implementations.
- `core:database`: Room Multiplatform database, DAOs, entities, migrations, JSON content seeding.
- `core:datastore`: preferences storage.
- `core:ui`: theme, reusable Compose UI kit, localized compose resources.
- `core:navigation`: app nav graph and route wiring.
- `core:advertising`, `core:purchase`, `core:device`, `core:common`: platform/services utilities.
- `feature:*`: screen, view model, navigation, and DI per feature (`rules`, `game_options`, `game`, `sets`, `words`, `guide`, `settings`).
- `build-logic/convention`: custom Gradle convention plugins. Prefer these over repeating Gradle setup in modules.

## Build And Check Commands
- Full Android debug build: `./gradlew :composeApp:assembleDebug`
- Release build: `./gradlew :composeApp:assembleRelease`
- Compile/check all modules: `./gradlew build`
- Run a focused module check when possible: `./gradlew :feature:game:compileKotlinMetadata` or the matching module task.
- iOS dependencies: run CocoaPods from `iosApp` only when iOS project dependencies change.

## Coding Rules
- Keep diffs small and local to the affected module.
- Do not add dependencies unless explicitly requested. Use `gradle/libs.versions.toml` if a dependency is approved.
- Prefer existing convention plugins:
  - Feature modules usually use `alias(libs.plugins.spy.feature)`.
  - Plain KMP modules use `alias(libs.plugins.spy.kotlinMultiplatform)`.
  - Compose support comes from `spy.composeMultiplatform`.
  - Room support comes from `spy.room`.
- Follow existing source-set layout: put shared code in `commonMain`; platform-specific code in `androidMain` or `iosMain`.
- Keep UI in Compose, state in ViewModels, business rules in `core:domain`, persistence behind repos/data sources.
- Use existing `core:ui` theme/components before creating new UI primitives.
- Register new ViewModels/services in the feature `di/*Module.kt`, then include the module from `AppModule.kt` if it is a new feature/module.
- When adding a route, add the feature navigation extension first, then wire it in `NavGraph.kt`.

## Data And Content Notes
- Room schema version is defined in `core/database/.../room/SpyDatabase.kt`; update migrations/schemas when changing entities.
- Content seed files live in `core/database/src/commonMain/resources/spy-content-*.json`.
- Similar JSON files also exist under `iosApp`; keep them in sync if the iOS shell still consumes them.
- Supported Android locale filters are configured in `composeApp/build.gradle.kts`.
- Compose string resources live in `core/ui/src/commonMain/composeResources/values*/strings.xml`.

## Secrets And Generated Files
- `secrets.properties`, `local.properties`, build outputs, APK/AAB files, CocoaPods artifacts, and Gradle caches are not source changes.
- Do not print or modify secret values unless the task explicitly requires it.
- Avoid editing generated files under `build/`, `.gradle/`, and `Pods/`.

## Review Checklist Before Finishing
- Build or compile the smallest affected scope, then mention exactly what was run.
- For UI changes, verify both common shared UI behavior and any Android/iOS expect/actual variants touched.
- For database/content changes, verify migrations or content loading paths.
- For localization changes, update all required `values-*` resource files or state which locales were intentionally left unchanged.
