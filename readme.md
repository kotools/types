# Kotools Assert

[![Maven Central](https://img.shields.io/maven-central/v/io.github.kotools/assert)](https://search.maven.org/artifact/io.github.kotools/assert)
[![Kotlin](https://img.shields.io/badge/kotlin-1.5.31-blue.svg?logo=kotlin)][kotlin]

Kotools Assert is a lightweight assertions library for [Kotlin].

```kotlin
1 assertEquals 1
1.assertEquals(1, "1 should be equal to 1")

2 assertNotEquals 0
2.assertNotEquals(0, "2 shouldn't be equal to 0")

assertNull { null }
assertNull("result should be null") { null }
null.assertNull()
null assertNull "result should be null"

assertNotNull { 3 }
assertNotNull("result shouldn't be null") { 3 }
3.assertNotNull()
3 assertNotNull "result shouldn't be null"

assertFails { throw Exception() }
assertFails("Should fail") { throw Exception() }

assertFailsWith<RuntimeException> { throw RuntimeException() }
assertFailsWith<RuntimeException>("Should fail with RuntimeException") {
    throw RuntimeException()
}
```

[kotlin]: https://kotlinlang.org

## Installation

### Gradle

#### Kotlin DSL

```kotlin
testImplementation("io.github.kotools:assert:1.2.0")
```

#### Groovy DSL

```groovy
testImplementation 'io.github.kotools:assert:1.2.0'
```

### Maven

```xml
<dependency>
    <groupId>io.github.kotools</groupId>
    <artifactId>assert</artifactId>
    <version>1.2.0</version>
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

## License

This project is licensed under the
[MIT License](https://choosealicense.com/licenses/mit).
