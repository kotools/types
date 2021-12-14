package io.github.kotools.csv.api

@Throws(CsvFileNotFoundError::class)
internal fun csvFileNotFoundError(path: String): Nothing =
    throw CsvFileNotFoundError(path)

@Throws(InvalidPropertyError::class)
internal fun invalidPropertyError(property: String): Nothing =
    throw InvalidPropertyError(property)

public sealed class CsvError(message: String) : RuntimeException(message)

public class CsvFileNotFoundError(filePath: String) :
    CsvError("The file `$filePath` doesn't exist on the system!")

public class InvalidPropertyError(property: String) :
    CsvError("The property `$property` is invalid!")
