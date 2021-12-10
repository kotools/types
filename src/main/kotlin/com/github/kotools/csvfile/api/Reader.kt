package com.github.kotools.csvfile.api

import com.github.kotools.csvfile.core.*
import kotlinx.coroutines.*

/**
 * Creates a [reader][ReaderApi] with the given [configuration] and reads the
 * corresponding [file][ReaderApi.file]'s content.
 *
 * Returns `null` if the [file][ReaderApi.file] doesn't exist.
 */
public suspend fun csvReader(
    configuration: ReaderApi.() -> Unit
): List<CsvLine>? = withContext(Dispatchers.IO) { process(configuration) }

/**
 * Creates a [reader][ReaderApi] with the given [configuration] and reads the
 * corresponding [file][ReaderApi.file]'s content **asynchronously**.
 *
 * Returns `null` if the [file][ReaderApi.file] doesn't exist.
 */
public fun CoroutineScope.csvReaderAsync(
    configuration: ReaderApi.() -> Unit
): Deferred<List<CsvLine>?> = async(Dispatchers.IO) { process(configuration) }

private inline fun process(
    configuration: ReaderApi.() -> Unit
): List<CsvLine>? = reader.apply(configuration)
    .execute()

/** Scope for reading CSV files. */
public abstract class ReaderApi {
    /**
     * **Optional** flag for printing the [file]'s content in the console.
     *
     * Set to `false` by default.
     */
    public var debug: Boolean = false

    /**
     * **Required** property for targeting a file.
     *
     * The `.csv` extension is optional and will be added automatically in the
     * process.
     * For example, `"my-file.csv"` and `"my-file"` produces the same output.
     */
    public var file: String by file()

    /**
     * **Optional** property for targeting the folder containing the [file] to
     * read.
     *
     * Set to an empty string by default.
     *
     * The `'/'` suffix is optional and will be added automatically in the
     * process.
     * For example, `"my-folder/"` and `"my-folder"` produces the same output.
     */
    public var folder: String by folder()

    /**
     * **Optional** property for setting the [file]'s separator.
     *
     * Set to [comma] by default.
     */
    public var separator: Separator = comma
}
