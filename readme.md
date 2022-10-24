# Kotools Types

[![Maven Central](https://img.shields.io/maven-central/v/io.github.kotools/types)](https://search.maven.org/artifact/io.github.kotools/types)
[![Kotlin](https://img.shields.io/badge/kotlin-1.5.31-blue.svg?logo=kotlin)][kotlin]

Kotools Types is a lightweight library that provides commonly used types for
[Kotlin].

```kotlin
NonZeroInt(1)
PositiveInt(0)
StrictlyPositiveInt(1)
NegativeInt(0)
StrictlyNegativeInt(-1)

NotBlankString("hello")

notEmptyListOf(1, 2, 3)
notEmptySetOf(4, 5, 6)
notEmptyMapOf("a" to 1, "b" to 2, "c" to 3)
```

[kotlin]: https://kotlinlang.org

## Installation

### Gradle

#### Kotlin DSL

```kotlin
implementation("io.github.kotools:types:$kotoolsTypesVersion")
```

#### Groovy DSL

```groovy
implementation "io.github.kotools:types:$kotools_types_version"
```

### Maven

```xml
<dependency>
    <groupId>io.github.kotools</groupId>
    <artifactId>types</artifactId>
    <version>${kotools.types.version}</version>
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
