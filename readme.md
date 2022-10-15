# Kotools Types

[![Maven Central](https://img.shields.io/maven-central/v/io.github.kotools/types)](https://search.maven.org/artifact/io.github.kotools/types)
[![Kotlin](https://img.shields.io/badge/kotlin-1.5.31-blue.svg?logo=kotlin)][kotlin]

Kotools Types is a lightweight library that provides commonly used types for
[Kotlin].

```kotlin
NonZeroInt(1)
NonZeroIntOrNull(-1)

PositiveInt(0)
PositiveIntOrNull(1)

StrictlyNegativeInt(-1)
StrictlyNegativeIntOrNull(-2)

NotBlankString("hello")
NotBlankStringOrNull("world")

notEmptyListOf(1, 2, 3)
notEmptySetOf(4, 5, 6)
```

[kotlin]: https://kotlinlang.org

## Installation

### Gradle

#### Kotlin DSL

```kotlin
implementation("io.github.kotools:types:3.0.0")
```

#### Groovy DSL

```groovy
implementation 'io.github.kotools:types:3.0.0'
```

### Maven

```xml
<dependency>
    <groupId>io.github.kotools</groupId>
    <artifactId>types</artifactId>
    <version>3.0.0</version>
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
