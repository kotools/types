# Internals of Kotools Types

This subproject contains internal declarations for Kotools Types.

## Gradle tasks

Here's the recommended tasks to run from the root project for building this
subproject:

- `./gradlew :internal:jsTest` for executing Kotlin/JS tests.
- `./gradlew :internal:apiCheck` for checking the ABI.
- `./gradlew :internal:jvmTest` for executing Kotlin/JVM tests.
- `./gradlew :internal:linuxTest` for executing Kotlin/Native tests for Linux.
- `./gradlew :internal:macosTest` for executing Kotlin/Native tests for macOS.
- `./gradlew :internal:windowsTest` for executing Kotlin/Native tests for
  Windows.
