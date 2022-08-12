# Kotools Types

[![Maven Central](https://img.shields.io/maven-central/v/io.github.kotools/types)](https://search.maven.org/artifact/io.github.kotools/types)
[![Kotlin](https://img.shields.io/badge/kotlin-1.5.31-blue.svg?logo=kotlin)][kotlin]

Kotools Types is a lightweight library that provides commonly used types for
[Kotlin].

```kotlin
NonZeroInt(1)
NonZeroInt orNull -1

PositiveInt(0)
PositiveInt orNull 1

StrictlyPositiveInt(1)
StrictlyPositiveInt orNull 2

NegativeInt(0)
NegativeInt orNull -1

StrictlyNegativeInt(-1)
StrictlyNegativeInt orNull -2

NotBlankString("hello")
NotBlankString orNull "world"

NotEmptyList(0, 1)
NotEmptyMutableList(2, 3)
NotEmptySet(0, 1)
NotEmptyMutableSet(2, 3)
```

[kotlin]: https://kotlinlang.org

## Installation

### Gradle

#### Kotlin DSL

```kotlin
implementation("io.github.kotools:types:2.0.0")
```

#### Groovy DSL

```groovy
implementation 'io.github.kotools:types:2.0.0'
```

### Maven

```xml
<dependency>
    <groupId>io.github.kotools</groupId>
    <artifactId>types</artifactId>
    <version>2.0.0</version>
</dependency>
```

## Documentation

Latest documentation of Kotools Types is available
[here](https://kotools.github.io/types).

## Contributing

Feel free to contribute to the project with
[issues](https://github.com/kotools/types/issues) and
[pull requests](https://github.com/kotools/types/pulls).

This project follows the [Conventional Commits][conventional-commits] guidelines
for committing with Git.
Please read [the specifications][conventional-commits] before committing to this
project.

[conventional-commits]: https://www.conventionalcommits.org/en/v1.0.0/

## License

This project is licensed under the
[MIT License](https://choosealicense.com/licenses/mit).
