# Reader

The type `Reader` is a configurable scope for reading CSV files.

## Configuration

- `file` is a **required** property for targeting the file to read

> The `.csv` extension in the `file` property is optional and will be added
> automatically in the process if not present.
> For example, `"my-file.csv"` and `"my-file"` produces the same output.

- `folder` is an **optional** property for targeting the folder containing the
  file target (set to an empty string by default)

> The `/` suffix in the `folder` property is optional and will be added
> automatically in the process if not present.
> For example, `"my-folder/"` and `"my-folder"` produces the same output.

- `separator` is an **optional** property for specifying the file's separator
  (set to comma by default).

## Features

Because the library is built on top of Kotlin coroutines, you will need to
provide a `CoroutineScope` receiver or call the following functions from
a `suspend` one.

> The following features run in the dispatcher `Dispatchers.IO`.
> So no need to provide it in the coroutine's context.

### Read all lines

```kotlin
fun main(): Unit = runBlocking {
    // Reads "my-folder/my-file.csv" file separated with semicolons
    csvReader {
        file = "my-file"
        folder = "my-folder"
        separator = semicolon
    }
}
```

### Read all lines asynchronously

```kotlin
fun main(): Unit = runBlocking {
    // Reads "my-file.csv" file asynchronously and separated with commas
    csvReaderAsync { file = "my-file" }
}
```

> If you need to await the result just after reading the file, use the
> `csvReader` function instead.
