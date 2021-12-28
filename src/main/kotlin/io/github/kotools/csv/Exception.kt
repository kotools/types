package io.github.kotools.csv

import kotlin.reflect.KClass

@Throws(InvalidConfigurationException::class)
internal fun invalidConfigurationException(): Nothing =
    throw InvalidConfigurationException("Given configuration is invalid!")

@Throws(FileNotFoundException::class)
internal fun fileNotFoundException(path: String): Nothing =
    throw FileNotFoundException(path)

@Throws(InvalidTypeException::class)
internal fun <T : Any> invalidTypeException(type: KClass<T>): Nothing =
    throw InvalidTypeException(type)

public sealed class CsvConfigurationException(message: String) :
    IllegalArgumentException("$message!")

private class FileNotFoundException(path: String) :
    CsvConfigurationException("The file `$path` doesn't exist")

private class InvalidConfigurationException(message: String) :
    CsvConfigurationException(message)

private class InvalidTypeException(type: KClass<*>) :
    CsvConfigurationException("The type `$type` is invalid")
