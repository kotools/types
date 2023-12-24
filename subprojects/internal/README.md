<!--
    Copyright 2023 Kotools S.A.S.
    Use of this source code is governed by the MIT license.
-->

# Internals of Kotools Types

This subproject contains internal declarations for Kotools Types.

## Gradle tasks

Here's the recommended tasks to run from the root project for building this
subproject:

- `./gradlew :internal:jsTest` for executing Kotlin/JS tests.
- `./gradlew :internal:jvmApiCheck` for checking the public API binaries.
- `./gradlew :internal:jvmTest` for executing Kotlin/JVM tests.
- `./gradlew :internal:linuxTest` for executing Kotlin/Native tests for Linux.
- `./gradlew :internal:macosTest` for executing Kotlin/Native tests for macOS.
- `./gradlew :internal:windowsTest` for executing Kotlin/Native tests for
  Windows.
