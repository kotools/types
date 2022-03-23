# Kotools CSV

[![Maven Central](https://img.shields.io/maven-central/v/io.github.kotools/csv)](https://search.maven.org/artifact/io.github.kotools/csv)
[![Kotlin](https://img.shields.io/badge/kotlin-1.5.31-blue.svg?logo=kotlin)][kotlin]

Kotools CSV is a lightweight library for managing CSV files with
elegant [Kotlin] DSLs.

```kotlin
data class Person(val name: String, val age: Int, val isAdmin: Boolean = false)

suspend fun main() {
    csvWriter<Person> {
        file = "people"
        records { +Person("Nobody", 25) }
    }
    val people: List<Person> = csvReader { file = "people" }
    println(people)
}
```

[kotlin]: https://kotlinlang.org

## Design goals

### Lightweight

Kotools CSV just ship with what you need for manipulating CSV files, and has
only 3 direct dependencies:

- [doyaaaaaken/kotlin-csv](https://github.com/doyaaaaaken/kotlin-csv) for
  reading and writing in CSV files
- [kotlin-reflect](https://kotlinlang.org/docs/reflection.html) for parsing
  custom types to or from CSV records
- [Kotlin/kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) for
  running processes asynchronously.

### Explicit error handling

Kotools CSV lets you choose explicitly how to handle errors following the design
of [Kotlin Standard Library](https://kotlinlang.org/api/latest/jvm/stdlib): if
something goes wrong, functions suffixed by `OrNull` will return `null` and
other functions will throw an `IllegalStateException`. With this explicit
design, you can orchestrate easily your application state in production.

### Simply elegant

This library provides intuitive type-safe builders (also known as DSLs) for
readability and maintainability purpose. So you can focus on what's important in
your code, instead of trying to guess what is _the good way_
to retrieve a file from resources or a file only present at runtime. Simplicity
is key.

## Installation

### Gradle

#### Kotlin DSL

```kotlin
implementation("io.github.kotools:csv:2.1.1")
```

#### Groovy DSL

```groovy
implementation 'io.github.kotools:csv:2.1.1'
```

### Maven

```xml
<dependency>
    <groupId>io.github.kotools</groupId>
    <artifactId>csv</artifactId>
    <version>2.1.1</version>
</dependency>
```

## Documentation

Latest documentation of Kotools CSV is available
[here](https://kotools.github.io/csv). Also, you can find more information about
CSV format in the [RFC 4180](https://datatracker.ietf.org/doc/html/rfc4180).

## Contributing

Feel free to contribute to the project with
[issues](https://github.com/kotools/csv/issues) and
[pull requests](https://github.com/kotools/csv/pulls).

## License

This project is licensed under the
[MIT License](https://choosealicense.com/licenses/mit).
