# Kotools CSV

[![Maven Central][badge-maven]][maven-artifacts]
[![Kotlin][badge-kotlin]][kotlin]

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

## Design goals

### Lightweight

Kotools CSV just ship with what you need for manipulating CSV files, and has
only 3 direct dependencies:
- [doyaaaaaken/kotlin-csv][kotlin-csv] for reading and writing in CSV files
- [kotlin-reflect] for parsing custom types to or from CSV records
- [Kotlin/kotlinx.coroutines][kotlin-coroutines] for running processes
  asynchronously on [IO dispatcher][kotlin-coroutines-io].

### Explicit error handling

Kotools CSV lets you choose explicitly how to handle errors following
the [Kotlin Standard Library][kotlin-stdlib]'s design: if something goes wrong,
functions suffixed by `OrNull` will return `null` and other functions will throw
an `IllegalStateException`. With this explicit design, you can orchestrate
easily your application state in production.

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
implementation("io.github.kotools:csv:2.0.1")
```

#### Groovy DSL

```groovy
implementation 'io.github.kotools:csv:2.0.1'
```

### Maven

```xml
<dependency>
    <groupId>io.github.kotools</groupId>
    <artifactId>csv</artifactId>
    <version>2.0.1</version>
</dependency>
```

## Documentation

Latest documentation of Kotools CSV is available [here][documentation].

## Contributing

Feel free to contribute to the project with [issues]
and [pull requests][pull-requests]. Also, if you have any idea or question, you
can join the community in the GitHub [discussions] side.

## License

This project is licensed under the [MIT License][mit-license].

<!-- Links -->

[badge-kotlin]: https://img.shields.io/badge/kotlin-1.5.31-blue.svg?logo=kotlin
[badge-maven]: https://img.shields.io/maven-central/v/io.github.kotools/csv
[discussions]: https://github.com/kotools/csv/discussions
[documentation]: https://kotools.github.io/csv/csv/io.github.kotools.csv.api/index.html
[issues]: https://github.com/kotools/csv/issues
[kotlin]: https://kotlinlang.org
[kotlin-coroutines]: https://github.com/Kotlin/kotlinx.coroutines
[kotlin-coroutines-io]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-i-o.html
[kotlin-csv]: https://github.com/doyaaaaaken/kotlin-csv
[kotlin-reflect]: https://kotlinlang.org/docs/reflection.html
[kotlin-stdlib]: https://kotlinlang.org/api/latest/jvm/stdlib
[maven-artifacts]: https://search.maven.org/artifact/io.github.kotools/csv
[mit-license]: https://choosealicense.com/licenses/mit
[pull-requests]: https://github.com/kotools/csv/pulls
