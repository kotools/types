@file:Suppress("unused")

package io.github.kotools.csv

import io.github.kotools.csv.api.CsvFileNotFoundError
import io.github.kotools.csv.api.InvalidPropertyError
import io.github.kotools.csv.api.Separator
import io.github.kotools.csv.api.comma
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

/**
 * Creates a reader with the given [config] and reads the corresponding
 * [file][ManagerDsl.file]'s content.
 *
 * Returns `null` if the [config] is invalid or if something else goes wrong in
 * the process.
 */
public suspend fun csvReader(config: ReaderDsl.() -> Unit):
        List<Map<String, String>>? = withContext(IO) { reader(config) }

/**
 * Creates a writer with the given [config] and writes new
 * [rows][WriterDsl.rows] in the corresponding [file][ManagerDsl.file].
 *
 * Returns `null` if the [config] is invalid or if something goes wrong in the
 * process.
 */
public suspend fun csvWriter(config: WriterDsl.() -> Unit): Unit? =
    withContext(IO) { writer(config) }

/**
 * Creates a reader with the given [config] and reads the corresponding
 * [file][ManagerDsl.file]'s content.
 *
 * Throws an [InvalidPropertyError] if the [file][ManagerDsl.file] value is not
 * set or is invalid.
 * Throws a [CsvFileNotFoundError] if the [file][ManagerDsl.file] doesn't exist
 * on the system.
 */
@Throws(CsvFileNotFoundError::class, InvalidPropertyError::class)
public suspend fun strictCsvReader(config: ReaderDsl.() -> Unit):
        List<Map<String, String>> = withContext(IO) { strictReader(config) }

/**
 * Creates a reader with the given [config] and reads the corresponding
 * [file][ManagerDsl.file]'s content **asynchronously**.
 *
 * Returns `null` if the [config] is invalid or if something else goes wrong in
 * the process.
 */
public infix fun CoroutineScope.csvReaderAsync(config: ReaderDsl.() -> Unit):
        Deferred<List<Map<String, String>>?> = async(IO) { reader(config) }

/**
 * Creates a writer with the given [config] and writes new
 * [rows][WriterDsl.rows] in the corresponding [file][ManagerDsl.file]
 * **asynchronously**.
 *
 * Returns `null` if the [config] is invalid or if something goes wrong in the
 * process.
 */
public infix fun CoroutineScope.csvWriterAsync(config: WriterDsl.() -> Unit):
        Deferred<Unit?> = async(IO) { writer(config) }

/**
 * Creates a reader with the given [config] and reads the corresponding
 * [file][ManagerDsl.file]'s content **asynchronously**.
 *
 * Throws an [InvalidPropertyError] if the [file][ManagerDsl.file] value is not
 * set or is invalid.
 * Throws a [CsvFileNotFoundError] if the [file][ManagerDsl.file] doesn't exist
 * on the system.
 */
@Throws(CsvFileNotFoundError::class, InvalidPropertyError::class)
public infix fun CoroutineScope.strictCsvReaderAsync(
    config: ReaderDsl.() -> Unit
): Deferred<List<Map<String, String>>> = async(IO) { strictReader(config) }

public sealed interface Dsl

/** Scope for manipulating CSV files. */
public sealed interface ManagerDsl : Dsl {
    /**
     * **Required** property for targeting a file.
     *
     * The `.csv` extension is optional and will be added automatically in the
     * process if not present.
     * For example, `"my-file.csv"` and `"my-file"` produces the same output.
     */
    public var file: String

    /**
     * **Optional** property for targeting the folder containing the [file] to
     * read.
     *
     * Set to an empty string by default.
     *
     * The `'/'` suffix is optional and will be added automatically in the
     * process if not present.
     * For example, `"my-folder/"` and `"my-folder"` produces the same output.
     */
    public var folder: String

    /**
     * **Optional** property for setting the [file]'s separator.
     *
     * Set to [comma] by default.
     */
    public var separator: Separator
}

/** Scope for reading CSV files. */
public interface ReaderDsl : ManagerDsl

public fun interface RowsDsl : Dsl {
    public operator fun Iterable<String>.unaryPlus()
}

public interface WriterDsl : ManagerDsl {
    public var header: Set<String>
    public var overwrite: Boolean

    public infix fun rows(config: RowsDsl.() -> Unit)
}
