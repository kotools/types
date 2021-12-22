package io.github.kotools.csv.old

@Throws(FileNotFoundError::class)
internal fun csvFileNotFoundError(path: String): Nothing =
    throw FileNotFoundError(path)

@Throws(InvalidConfigError::class)
internal fun invalidConfigError(message: String): Nothing =
    throw InvalidConfigError(message)

@Throws(InvalidPropertyError::class)
internal fun invalidPropertyError(property: String): Nothing =
    throw InvalidPropertyError(property)

public sealed class Error(message: String) : RuntimeException(message)

public class FileNotFoundError(filePath: String) :
    Error("The file `$filePath` doesn't exist on the system!")

public open class InvalidConfigError(message: String) : Error(message)

public class InvalidPropertyError(property: String) :
    InvalidConfigError("The property `$property` is invalid!")
