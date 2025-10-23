# 🔌 Support of [kotlinx.serialization]

[![Latest version][kotools-types-kotlinx-serialization-badge]][kotools-types]
[![Embedded Kotlin][kotlin-embedded-badge]][kotlin]
[![Kotlin language][kotlin-language-badge]][kotlin]
[![kotlinx.serialization][kotlinx.serialization-badge]][kotlinx.serialization]

[![JVM Platform][jvm-platform-badge]][kotlin/jvm]
[![JS Platform][js-platform-badge]][kotlin/js]
[![iOS x64 Platform][ios-x64-platform-badge]][kotlin-native]
[![iOS Simulator arm64 Platform][ios-simulator-arm64-platform-badge]][kotlin-native]
[![iOS arm64 Platform][ios-arm64-platform-badge]][kotlin-native]
[![Linux x64 Platform][linux-x64-platform-badge]][kotlin-native]
[![macOS x64 Platform][macos-x64-platform-badge]][kotlin-native]
[![macOS arm64 Platform][macos-arm64-platform-badge]][kotlin-native]
[![MinGW x64 Platform][mingw-x64-platform-badge]][kotlin-native]

This module provides support for serializing types from Kotools Types using the
[kotlinx.serialization] library.

## 🛠️ Installation

You can add this module to your project by using Gradle or Maven.
Just replace the `$version` or the `${kotools.types.version}` variables by the
[latest version](#-support-of-kotlinxserialization) or by another one available
in the [changelog](../../CHANGELOG.md).

> [!IMPORTANT]
> Note that this module doesn't exist for versions prior Kotools Types 4.5.1.

<details open>
<summary>Gradle - Kotlin DSL</summary>

```kotlin
implementation("org.kotools:types-kotlinx-serialization:$version")
```
</details>

<details>
<summary>Gradle - Groovy DSL</summary>

```groovy
implementation "org.kotools:types-kotlinx-serialization:$version"
```
</details>

<details>
<summary>Maven</summary>

```xml
<dependencies>
    <dependency>
        <groupId>org.kotools</groupId>
        <artifactId>types-kotlinx-serialization</artifactId>
        <version>${kotools.types.version}</version>
    </dependency>
</dependencies>
```
</details>

## 👨‍💻 Usage

For using this module, you need to import the
[serializers module][kotlinx.serialization.modules.SerializersModule] needed
when configuring the serialization format.

Here's an example using default serializers from Kotools Types with the
[JavaScript Object Notation (JSON)][kotlinx.serialization.json] format:

```kotlin
val format = Json { serializersModule = KotoolsTypesSerializersModule() }
val address: EmailAddress = EmailAddress.orThrow("contact@kotools.org")
val encoded: String = format.encodeToString(address)
assertEquals(expected = "\"$address\"", encoded)
val decoded: EmailAddress = format.decodeFromString(encoded)
assertEquals(expected = address, decoded)
```

See the [API reference] of the `KotoolsTypesSerializersModule()` function for
more details.

[api reference]: https://types.kotools.org
[ios-arm64-platform-badge]: https://img.shields.io/badge/Platform-iOS_arm64-4b4bff
[ios-simulator-arm64-platform-badge]: https://img.shields.io/badge/Platform-iOS_Simulator_arm64-4b4bff
[ios-x64-platform-badge]: https://img.shields.io/badge/Platform-iOS_x64-4b4bff
[js-platform-badge]: https://img.shields.io/badge/Platform-JS-ff9b00
[jvm-platform-badge]: https://img.shields.io/badge/Platform-JVM-6bac25
[kotlin]: https://kotlinlang.org
[kotlin-embedded-badge]: https://img.shields.io/badge/Embedded_Kotlin-1.9.25-blue?logo=kotlin
[kotlin-language-badge]: https://img.shields.io/badge/Kotlin_language-1.9-blue?logo=kotlin
[kotlin-native]: https://kotlinlang.org/docs/native-overview.html
[kotlin/js]: https://kotlinlang.org/docs/js-overview.html
[kotlin/jvm]: https://kotlinlang.org/docs/jvm-get-started.html
[kotlinx.serialization]: https://github.com/Kotlin/kotlinx.serialization
[kotlinx.serialization-badge]: https://img.shields.io/badge/kotlinx.serialization-1.6.3-blue
[kotlinx.serialization.json]: https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-json/kotlinx.serialization.json/-json
[kotlinx.serialization.modules.SerializersModule]: https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-core/kotlinx.serialization.modules/-serializers-module
[kotools-types]: https://github.com/kotools/types
[kotools-types-kotlinx-serialization-badge]: https://img.shields.io/maven-central/v/org.kotools/types-kotlinx-serialization?label=Latest
[linux-x64-platform-badge]: https://img.shields.io/badge/Platform-Linux_x64-4b4bff
[macos-x64-platform-badge]: https://img.shields.io/badge/Platform-macOS_x64-4b4bff
[macos-arm64-platform-badge]: https://img.shields.io/badge/Platform-macOS_arm64-4b4bff
[mingw-x64-platform-badge]: https://img.shields.io/badge/Platform-MinGW_x64-4b4bff
