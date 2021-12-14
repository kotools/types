package io.github.kotools.csv.api

import io.github.kotools.csv.core.reader
import io.github.kotools.csv.core.strictReader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

/**
 * Creates a [reader][Reader] with the given [config] and reads the
 * corresponding [file][Csv.file]'s content.
 *
 * Returns `null` if the [config] is invalid or if something goes wrong in the
 * process.
 */
public suspend fun csvReader(config: Reader.() -> Unit):
        List<Map<String, String>>? = withContext(IO) { reader(config) }

/**
 * Creates a [reader][Reader] with the given [config] and reads the
 * corresponding [file][Csv.file]'s content **asynchronously**.
 *
 * Returns `null` if the [config] is invalid or if something goes wrong in the
 * process.
 */
public infix fun CoroutineScope.csvReaderAsync(config: Reader.() -> Unit):
        Deferred<List<Map<String, String>>?> = async(IO) { reader(config) }

/**
 * Creates a [reader][Reader] with the given [config] and reads the
 * corresponding [file][Csv.file]'s content.
 *
 * Throws an [InvalidPropertyError] if the [file][Csv.file] value is not set or
 * is invalid.
 * Throws a [CsvFileNotFoundError] if the [file][Csv.file] doesn't exist on the
 * system.
 */
@Throws(CsvFileNotFoundError::class, InvalidPropertyError::class)
public suspend fun strictCsvReader(config: Reader.() -> Unit):
        List<Map<String, String>> = withContext(IO) { strictReader(config) }

/**
 * Creates a [reader][Reader] with the given [config] and reads the
 * corresponding [file][Csv.file]'s content **asynchronously**.
 *
 * Throws an [InvalidPropertyError] if the [file][Csv.file] value is not set or
 * is invalid.
 * Throws a [CsvFileNotFoundError] if the [file][Csv.file] doesn't exist on the
 * system.
 */
@Throws(CsvFileNotFoundError::class, InvalidPropertyError::class)
public infix fun CoroutineScope.strictCsvReaderAsync(config: Reader.() -> Unit):
        Deferred<List<Map<String, String>>> = async(IO) { strictReader(config) }

/** Scope for reading CSV files. */
public interface Reader : Csv
