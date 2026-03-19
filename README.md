# Kotools Types

[![Kotools Types][kotools-types-badge]][kotools-types-project]
[![Embedded Kotlin][kotlin-embedded-badge]][kotlin]
[![Kotlin language][kotlin-language-badge]][kotlin]

[![JVM Platform][jvm-platform-badge]][kotlin/jvm]
[![JS Platform][js-platform-badge]][kotlin/js]
[![iOS x64 Platform][ios-x64-platform-badge]][kotlin-native]
[![iOS Simulator arm64 Platform][ios-simulator-arm64-platform-badge]][kotlin-native]
[![iOS arm64 Platform][ios-arm64-platform-badge]][kotlin-native]
[![Linux x64 Platform][linux-x64-platform-badge]][kotlin-native]
[![macOS x64 Platform][macos-x64-platform-badge]][kotlin-native]
[![macOS arm64 Platform][macos-arm64-platform-badge]][kotlin-native]
[![MinGW x64 Platform][mingw-x64-platform-badge]][kotlin-native]

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
[kotools-types-badge]: https://img.shields.io/maven-central/v/org.kotools/types?label=Latest
[kotools-types-project]: https://github.com/kotools/types
[linux-x64-platform-badge]: https://img.shields.io/badge/Platform-Linux_x64-4b4bff
[macos-x64-platform-badge]: https://img.shields.io/badge/Platform-macOS_x64-4b4bff
[macos-arm64-platform-badge]: https://img.shields.io/badge/Platform-macOS_arm64-4b4bff
[mingw-x64-platform-badge]: https://img.shields.io/badge/Platform-MinGW_x64-4b4bff

## 🚀 Introduction

Kotools Types is a Kotlin Multiplatform library providing types that model
real-world concepts with correct and predictable behavior.

Instead of exposing the limitations of underlying platforms, these types follow
their domain definitions. For example, the [Integer](https://types.kotools.org/types/org.kotools.types/-integer/index.html)
type represents a mathematical integer and does not overflow.

By using Kotools Types, you can build APIs that are more explicit, easier to
reason about, and less error-prone across JVM, JS, and Native platforms.

## ⭐️ Key Features

- **Correct by Design:** Types model real-world concepts and provide predictable
  behavior, avoiding common pitfalls such as integer overflow.
- **Explicit and Safe APIs:** Constraints and invariants are expressed directly
  in types, helping prevent invalid states and making code easier to understand.
- **Complete and Practical:** Each type comes with the operations needed for
  real-world usage, not just constructors or wrappers.
- **Multiplatform Ready:** Use the same reliable types across Kotlin/JVM,
  Kotlin/JS, and Kotlin/Native.

> [!IMPORTANT]
> Support of Kotlin Serialization is experimental and not intended for
> production use. It will be replaced by a stable solution in Kotools Types 6.0.

## 🛠️ Installation

You can add Kotools Types to your project by using Gradle or Maven.
Just replace the `$version` or the `${kotools.types.version}` variables by the
[latest version](#kotools-types) or by another one available in the
[changelog](CHANGELOG.md).

<details open>
<summary>Gradle - Kotlin DSL</summary>

```kotlin
implementation("org.kotools:types:$version")
```
</details>

<details>
<summary>Gradle - Groovy DSL</summary>

```groovy
implementation "org.kotools:types:$version"
```
</details>

<details>
<summary>Maven</summary>

```xml
<dependencies>
    <dependency>
        <groupId>org.kotools</groupId>
        <artifactId>types</artifactId>
        <version>${kotools.types.version}</version>
    </dependency>
</dependencies>
```
</details>

> See [the dedicated documentation](subprojects/kotlinx-serialization/README.md)
> for serializing types from the `org.kotools.types` package using the
> [kotlinx.serialization] library. 

## 📝 Documentation

Here's additional documentation for learning more about this project:

- [API reference](https://types.kotools.org)
- [Design goals](documentation/design-goals.md)
- [Versioning strategy](documentation/versioning-strategy.md)
- [Dependency compatibility](documentation/dependencies.md)
- [Declarations lifecycle](documentation/declarations-lifecycle.md)
- [Security Policy](SECURITY.md)

## 🤝 Community

Join our thriving community! Connect, share insights, and collaborate with
fellow developers to make Kotools Types even more powerful.

- [GitHub Discussions](https://github.com/kotools/types/discussions)
- [#kotools on Kotlin Slack](https://kotlinlang.slack.com/archives/C05H0L1LD25)

## 📣 Show Your Support

If you find this project valuable, show your support by giving us a ⭐️ on
GitHub.
Your feedback and engagement mean the world to us!

## 🚧 Contributing

As an Open-Source project, Kotools Types is in need of new contributors!
We have issues suited for all levels, from entry to advanced.
All are welcome in this project.

If you are looking to contribute, check out our
[contribution guidelines](CONTRIBUTING.md) for more details on how to get
started.

## 🙏 Acknowledgements

Thanks to [Loïc Lamarque](https://github.com/LVMVRQUXL) for creating and sharing 
this project with the open source community.

Thanks to all the [people that ever contributed](https://github.com/kotools/types/graphs/contributors)
through code or other means such as bug reports, feature suggestions and so on.

## 📄 License

This project is licensed under the [MIT License](LICENSE.txt).

---

Happy coding with Kotools Types! 🎉
