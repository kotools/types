package io.github.kotools.csv

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

/**
 * Writes records in a CSV file according to the given [configuration], or
 * throws a [CsvException] when the [configuration] is invalid.
 */
@Throws(CsvException::class)
public suspend fun csvWriter(configuration: Writer.() -> Unit): Unit =
    withContext(IO) { delegateCsvWriter(configuration) }

/**
 * Writes records in a CSV file **asynchronously** according to the given
 * [configuration], or throws a [CsvException] when the [configuration] is
 * invalid.
 */
@Throws(CsvException::class)
public infix fun CoroutineScope.csvWriterAsync(
    configuration: Writer.() -> Unit
): Deferred<Unit> = async(IO) { delegateCsvWriter(configuration) }

/**
 * Writes records in a CSV file according to the given [configuration], or
 * returns `null` when the [configuration] is invalid.
 */
public suspend fun csvWriterOrNull(configuration: Writer.() -> Unit): Unit? =
    withContext(IO) { delegateCsvWriterOrNull(configuration) }

/**
 * Writes records in a CSV file **asynchronously** according to the given
 * [configuration], or returns `null` when the [configuration] is invalid.
 */
public infix fun CoroutineScope.csvWriterOrNullAsync(
    configuration: Writer.() -> Unit
): Deferred<Unit?> = async(IO) { delegateCsvWriterOrNull(configuration) }

/** Configurable object responsible for writing records in a CSV file. */
public sealed interface Writer : Manager {
    /** **Required** property for defining the [file] content's header. */
    public var header: Set<String>

    /**
     * **Optional** flag for overwriting the [file]'s content.
     *
     * Set to `true` by default.
     */
    public var overwrite: Boolean

    /** **Required** function that defines the records to write. */
    public infix fun records(configuration: Records.() -> Unit)

    /** Configurable object responsible for defining the records to write. */
    public sealed interface Records {
        /** Adds the given [Iterable] as a record. */
        public operator fun Iterable<String>.unaryPlus()
    }
}
