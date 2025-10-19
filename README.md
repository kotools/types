# Kotools Types

[![Kotools Types][kotools-types-badge]][kotools-types-project]
[![Embedded Kotlin][kotlin-embedded-badge]][kotlin]
[![Kotlin language][kotlin-language-badge]][kotlin]

[![JVM Platform][jvm-platform-badge]][kotlin/jvm]
[![JS Platform][js-platform-badge]][kotlin/js]
[![iOS x64 Platform][ios-x64-platform-badge]][kotlin-native]
[![iOS Simulator arm64 Platform][ios-simulator-arm64-platform-badge]][kotlin-native]
[![Linux x64 Platform][linux-x64-platform-badge]][kotlin-native]
[![macOS x64 Platform][macos-x64-platform-badge]][kotlin-native]
[![macOS arm64 Platform][macos-arm64-platform-badge]][kotlin-native]
[![MinGW x64 Platform][mingw-x64-platform-badge]][kotlin-native]

> "Kool Types for Kotlin Multiplatform." -
> [@jmfayard](https://github.com/jmfayard)

Unlock the true potential of Kotlin's type system across Kotlin/JVM, Kotlin/JS,
and Kotlin/Native platforms with Kotools Types – your comprehensive toolkit for
explicit type handling!

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

Kotools Types is not just a library; it's your gateway to seamless and
expressive type manipulation in Kotlin projects.
Dive into a world where types are your allies, providing clarity, safety, and
flexibility across diverse Kotlin platforms.

## ⭐️ Key Features

- **Unified Type Handling:** Embrace a unified approach to handling types across
  platforms, ensuring consistency in your Kotlin/JVM, Kotlin/JS and
  Kotlin/Native projects.
- **Enhanced Type Safety:** Fortify your code with explicit types, catching
  errors at compile time to create robust and reliable applications.
- **Automatic Serialization:** Seamlessly serialize and deserialize types using
  [kotlinx.serialization], making data interchange between platforms, APIs, 
  databases and libraries a breeze.

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

## 🎨 Included types

Explore some of the types offered by this library:

- [NotBlankString][kotools.types.text.NotBlankString] ensuring that your strings
  have at least one character excluding whitespaces.
- [PositiveInt][kotools.types.number.PositiveInt] representing an integer number
  of type [Int][kotlin.Int] that is greater than or equals zero.
- [NotEmptyList][kotools.types.collection.NotEmptyList] for grouping your data
  in a list with at least one element.

See the [API reference](https://types.kotools.org) for more types!

[kotlin.Int]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int
[kotools.types.collection.NotEmptyList]: https://types.kotools.org/types/kotools.types.collection/-not-empty-list/index.html
[kotools.types.number.PositiveInt]: https://types.kotools.org/types/kotools.types.number/-positive-int/index.html
[kotools.types.text.NotBlankString]: https://types.kotools.org/types/kotools.types.text/-not-blank-string/index.html

## 📝 Documentation

Here's additional documentation for learning more about this project:

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
