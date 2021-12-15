# Kotools CSV

[![Maven Central](https://img.shields.io/maven-central/v/io.github.kotools/csv)](https://search.maven.org/artifact/io.github.kotools/csv)
[![Kotlin](https://img.shields.io/badge/kotlin-1.5.31-blue.svg?logo=kotlin)][kotlin]

Kotools CSV is a lightweight library for managing CSV files with
elegant [Kotlin] DSLs.

Built on top
of [Kotlin/kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines)
and [doyaaaaaken/kotlin-csv](https://github.com/doyaaaaaken/kotlin-csv), this
library follows the pragmatic design of [Kotlin] and provides type-safe builders
for manipulating CSV files easily. It also solves a lot of issues like finding
_the good way_ to retrieve a file from resources or a file only present at
runtime.

```kotlin
fun main(): Unit = runBlocking {
    csvWriter {
        file = "my-new-file"
        header = setOf("h1", "h2", "h3")
        rows { +listOf("a", "b", "c") }
    }
    csvReader { file = "my-file" }
}
```

## Installation

### Gradle

#### Kotlin DSL

```kotlin
implementation("io.github.kotools:csv:0.1.4")
```

#### Groovy DSL

```groovy
implementation 'io.github.kotools:csv:0.1.4'
```

### Maven

```xml
<dependency>
    <groupId>io.github.kotools</groupId>
    <artifactId>csv</artifactId>
    <version>0.1.4</version>
</dependency>
```

## License

This project is licensed under
the [MIT License](https://choosealicense.com/licenses/mit).

<!-- Links -->

[kotlin]: https://kotlinlang.org
