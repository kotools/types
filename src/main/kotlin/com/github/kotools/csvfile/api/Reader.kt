package com.github.kotools.csvfile.api

import com.github.kotools.csvfile.core.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

/**
 * Creates a [reader][ReaderApi] with the given [configuration][config] and
 * reads the corresponding [file][ReaderApi.file]'s content.
 *
 * Returns `null` if the [file][ReaderApi.file] doesn't exist.
 */
public suspend fun csvReader(config: ReaderApi.() -> Unit):
        List<Map<String, String>>? = withContext(IO) { reader(config) }

/**
 * Creates a [reader][ReaderApi] with the given [configuration][config] and
 * reads the corresponding [file][ReaderApi.file]'s content **asynchronously**.
 *
 * Returns `null` if the [file][ReaderApi.file] doesn't exist.
 */
public infix fun CoroutineScope.csvReaderAsync(config: ReaderApi.() -> Unit):
        Deferred<List<Map<String, String>>?> = async(IO) { reader(config) }

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
     * **Optional** flag for searching the [file] to read in the `resources`
     * folder.
     *
     * Set to `true` by default.
     *
     * **Please note that files created at runtime will not be stored in the
     * `resources` folder.**
     * So if you try to read a [file] only available at runtime, make sure to
     * set this flag to `false`.
     */
    public var resource: Boolean = true

    /**
     * **Optional** property for setting the [file]'s separator.
     *
     * Set to [comma] by default.
     */
    public var separator: Separator = comma
}
