package io.github.kotools.csv.api

@Throws(CsvFileNotFoundError::class)
internal fun csvFileNotFoundError(path: String): Nothing =
    throw CsvFileNotFoundError(path)

@Throws(InvalidConfigError::class)
internal fun invalidConfigError(message: String): Nothing =
    throw InvalidConfigError(message)

@Throws(InvalidPropertyError::class)
internal fun invalidPropertyError(property: String): Nothing =
    throw InvalidPropertyError(property)

public sealed class CsvError(message: String) : RuntimeException(message)

public class CsvFileNotFoundError(filePath: String) :
    CsvError("The file `$filePath` doesn't exist on the system!")

public open class InvalidConfigError(message: String) : CsvError(message)

public class InvalidPropertyError(property: String) :
    InvalidConfigError("The property `$property` is invalid!")
