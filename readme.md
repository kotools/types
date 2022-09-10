# Kotools Assert

[![Maven Central](https://img.shields.io/maven-central/v/io.github.kotools/assert)](https://search.maven.org/artifact/io.github.kotools/assert)
[![Kotlin](https://img.shields.io/badge/kotlin-1.5.31-blue.svg?logo=kotlin)][kotlin]

Kotools Assert is a multiplatform library providing lightweight assertions for
[Kotlin].
> This library currently supports the JVM, JS and Native platforms.

```kotlin
1 assertEquals 1
2 assertNotEquals 0

assertNull { null }
null.assertNull()

assertNotNull { 3 }
3.assertNotNull()

assertPass { println("Hello") }
assertFails { throw Exception() }
assertFailsWith<RuntimeException> { throw RuntimeException() }
```

[kotlin]: https://kotlinlang.org

## Installation

### Gradle

#### Kotlin DSL

```kotlin
testImplementation("io.github.kotools:assert:3.0.2")
```

#### Groovy DSL

```groovy
testImplementation 'io.github.kotools:assert:3.0.2'
```

### Maven

```xml
<dependency>
    <groupId>io.github.kotools</groupId>
    <artifactId>assert</artifactId>
    <version>3.0.2</version>
    <scope>test</scope>
</dependency>
```

## Documentation

Latest documentation of Kotools Assert is available
[here](https://kotools.github.io/assert).

## Contributing

Feel free to contribute to the project with
[issues](https://github.com/kotools/assert/issues) and
[pull requests](https://github.com/kotools/assert/pulls).

This project follows the [Conventional Commits][conventional-commits] guidelines
for committing with Git.
Please read [the specifications][conventional-commits] before committing to this
project.

[conventional-commits]: https://www.conventionalcommits.org/en/v1.0.0/

## License

This project is licensed under the
[MIT License](https://choosealicense.com/licenses/mit).
