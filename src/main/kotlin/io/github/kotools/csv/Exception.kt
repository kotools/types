package io.github.kotools.csv

import kotlin.reflect.KProperty

@Throws(FileNotFoundException::class)
internal fun fileNotFoundException(path: String): Nothing =
    throw FileNotFoundException(path)

@Throws(InvalidPropertyException::class)
internal fun <T> invalidPropertyException(property: KProperty<T>): Nothing =
    throw InvalidPropertyException(property.name)

public sealed class CsvConfigurationException(message: String) :
    IllegalArgumentException("$message!")

private class FileNotFoundException(path: String) :
    CsvConfigurationException("The file `$path` doesn't exist")

private class InvalidPropertyException(property: String) :
    CsvConfigurationException("The property `$property` is invalid")
