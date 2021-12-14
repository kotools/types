package io.github.kotools.csv.core

@Throws(CsvError.FileNotFoundError::class)
internal fun fileNotFoundError(path: String): Nothing =
    throw CsvError.FileNotFoundError(path)

@Throws(CsvError.InvalidPropertyError::class)
internal fun invalidPropertyError(property: String): Nothing =
    throw CsvError.InvalidPropertyError(property)

public sealed class CsvError(message: String) : RuntimeException(message) {
    public class FileNotFoundError(filePath: String) :
        CsvError("The file `$filePath` doesn't exist on the system!")

    public class InvalidPropertyError(property: String) :
        CsvError("The property `$property` is invalid!")
}
