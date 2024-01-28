# Kotools Types

[![Kotools Types][kotools-types-badge]][kotools-types-project]
[![Embedded Kotlin][kotlin-embedded-badge]][kotlin]
[![Kotlin language][kotlin-language-badge]][kotlin]
[![kotlinx.serialization][kotlinx.serialization-badge]][kotlinx.serialization]

[![JVM Platform][jvm-platform-badge]][kotlin/jvm]
[![JS Platform][js-platform-badge]][kotlin/js]
[![Linux x64 Platform][linux-x64-platform-badge]][kotlin-native]
[![macOS x64 Platform][macos-x64-platform-badge]][kotlin-native]
[![MinGW x64 Platform][mingw-x64-platform-badge]][kotlin-native]

Type safety is a must-have nowadays and reducing runtime errors to compile-time
errors feels like magic!
But even with the [Kotlin] type system, we still have runtime issues that can
fail our logic or break our software, like dividing a number by zero or
receiving a negative index...

How can we solve that? Defining more explicit types!
Luckily for you, this is basically what this library does: providing types for
improving the preciseness of your code.
Here's an example dividing an integer by an integer other than zero, for
avoiding an [`ArithmeticException`][kotlin.ArithmeticException] to be thrown:

```kotlin
val x = 42
val y: NonZeroInt = 6.toNonZeroInt().getOrThrow()
println(x / y) // 7
```

Using explicit types in your code is perfect for:

- ensuring that your data is valid through all your application
- striving for [total functions][total-functions] by reducing the possible
  inputs or outputs (like the `div` function used in the example above)
- testing your code using the compiler effectively without writing tests (this
  is how we reduce runtime checks to compile-time ones).

Cherry on top: Kotools Types is a multiplatform library, so you can use it in
all your [Kotlin] projects!
Supported platforms are available in
[badges at the top of this file](#kotools-types).

Here's a non-exhaustive list of types provided by this library:
- [NotBlankString][kotools.types.text.NotBlankString] representing a string that
  has at least one character excluding whitespaces.
- [PositiveInt][kotools.types.number.PositiveInt] representing an integer number
  of type [Int][kotlin.Int] that is greater than or equals zero.
- [NotEmptyList][kotools.types.collection.NotEmptyList] representing a list with
  at least one element.

See the [API reference][api-reference] for more types!

[js-platform-badge]: https://img.shields.io/badge/Platform-JS-ff9b00
[jvm-platform-badge]: https://img.shields.io/badge/Platform-JVM-6bac25
[kotools-types-badge]: https://img.shields.io/maven-central/v/org.kotools/types?label=Latest
[kotools-types-project]: https://github.com/kotools/types
[kotlin]: https://kotlinlang.org
[kotlin-embedded-badge]: https://img.shields.io/badge/Embedded_Kotlin-1.8.10-blue?logo=kotlin
[kotlin-language-badge]: https://img.shields.io/badge/Kotlin_language-1.5-blue?logo=kotlin
[kotlin-native]: https://kotlinlang.org/docs/native-overview.html
[kotlin/js]: https://kotlinlang.org/docs/js-overview.html
[kotlin/jvm]: https://kotlinlang.org/docs/jvm-get-started.html
[kotlin.ArithmeticException]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-arithmetic-exception
[kotlin.Int]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int
[kotlinx.serialization]: https://github.com/Kotlin/kotlinx.serialization
[kotlinx.serialization-badge]: https://img.shields.io/badge/kotlinx.serialization-1.5.0-blue
[kotools.types.collection.NotEmptyList]: https://types.kotools.org/-kotools%20-types/kotools.types.collection/-not-empty-list/index.html
[kotools.types.number.PositiveInt]: https://types.kotools.org/-kotools%20-types/kotools.types.number/-positive-int/index.html
[kotools.types.text.NotBlankString]: https://types.kotools.org/-kotools%20-types/kotools.types.text/-not-blank-string/index.html
[linux-x64-platform-badge]: https://img.shields.io/badge/Platform-Linux_x64-4b4bff
[macos-x64-platform-badge]: https://img.shields.io/badge/Platform-macOS_x64-4b4bff
[mingw-x64-platform-badge]: https://img.shields.io/badge/Platform-MinGW_x64-4b4bff
[total-functions]: https://xlinux.nist.gov/dads/HTML/totalfunc.html

## Installation

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

## Documentation

Here's additional documentation for learning more about this project:

- [API reference][api-reference]
- [Design goals](documentation/design-goals.md)
- [Dependency compatibility](documentation/dependencies.md)
- [Versioning strategy](documentation/versioning-strategy.md)
- [Declarations lifecycle](documentation/declarations-lifecycle.md)
- [Security Policy](SECURITY.md)

## Community

As an Open-Source project, Kotools Types is in need of new contributors!
We have issues suited for all levels, from entry to advanced.
All are welcome in this project.

If you are looking to contribute, have questions, or want to keep up-to-date
about what's happening, please follow us here and say hi!

- [GitHub Discussions]
- [#kotools-types on Kotlin Slack]

See the [contributing guidelines](CONTRIBUTING.md) for more details.

[#kotools-types on Kotlin Slack]: https://kotlinlang.slack.com/archives/C05H0L1LD25
[GitHub Discussions]: https://github.com/kotools/types/discussions

## Show Your Support

If you find this project useful, and you'd like to support our work, please
consider giving it a ⭐️ on GitHub!
Your support means a lot to us and helps us continue improving the library.

## Acknowledgements

Thanks to [Loïc Lamarque] for creating and sharing this project with the open
source community.

Thanks to [all the people that ever contributed] through code or other means such
as bug reports, feature suggestions and so on.

[all the people that ever contributed]: https://github.com/kotools/types/graphs/contributors
[Loïc Lamarque]: https://github.com/LVMVRQUXL

## License

This project is licensed under the [MIT License](LICENSE.txt).

[api-reference]: https://types.kotools.org
