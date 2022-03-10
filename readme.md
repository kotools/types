# Kotools Types

[![Maven Central](https://img.shields.io/maven-central/v/io.github.kotools/types)](https://search.maven.org/artifact/io.github.kotools/types)
[![Kotlin](https://img.shields.io/badge/kotlin-1.5.31-blue.svg?logo=kotlin)][kotlin]

Kotools Types is a lightweight library that provides commonly used types for
[Kotlin].

```kotlin
1.nonZero
1.nonZeroOrNull

(-1).strictlyNegative
(-1).strictlyNegativeOrNull

1.strictlyPositive
1.strictlyPositiveOrNull

"hello".notBlank
"hello".notBlankOrNull
```

[kotlin]: https://kotlinlang.org

## Installation

### Gradle

#### Kotlin DSL

```kotlin
implementation("io.github.kotools:types:1.0.0")
```

#### Groovy DSL

```groovy
implementation 'io.github.kotools:types:1.0.0'
```

### Maven

```xml
<dependency>
    <groupId>io.github.kotools</groupId>
    <artifactId>types</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Documentation

Latest documentation of Kotools Types is available
[here](https://kotools.github.io/types).

## Contributing

Feel free to contribute to the project with
[issues](https://github.com/kotools/types/issues) and
[pull requests](https://github.com/kotools/types/pulls).

## License

This project is licensed under the
[MIT License](https://choosealicense.com/licenses/mit).
