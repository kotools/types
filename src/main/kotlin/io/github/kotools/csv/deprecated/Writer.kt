package io.github.kotools.csv.deprecated

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlin.reflect.KClass

/**
 * Writes records as a given type [T] in a CSV file according to the given
 * [configuration], or throws a [CsvException] when [T] is not an internal data
 * class or when the [configuration] is invalid.
 */
@Suppress("DEPRECATION")
@Throws(CsvException::class)
public suspend inline fun <reified T : Any> csvWriter(
    noinline configuration: Writer<T>.() -> Unit
): Unit = csvWriter(T::class, configuration)

@Deprecated(
    message = "Use the `csvWriter<T> {}` method instead.",
    ReplaceWith("csvWriter<T> {}")
)
@Throws(CsvException::class)
public suspend fun <T : Any> csvWriter(
    type: KClass<T>,
    configuration: Writer<T>.() -> Unit
): Unit = withContext(IO) { WriterImplementation.process(type, configuration) }

/**
 * Writes records as a given type [T] in a CSV file according to the given
 * [configuration], or returns `null` when [T] is not an internal data class or
 * when the [configuration] is invalid.
 */
@Suppress("DEPRECATION")
public suspend inline fun <reified T : Any> csvWriterOrNull(
    noinline configuration: Writer<T>.() -> Unit
): Unit? = csvWriterOrNull(T::class, configuration)

@Deprecated(
    message = "Use the `csvWriterOrNull<T> {}` method instead.",
    ReplaceWith("csvWriterOrNull<T> {}")
)
public suspend fun <T : Any> csvWriterOrNull(
    type: KClass<T>,
    configuration: Writer<T>.() -> Unit
): Unit? =
    withContext(IO) { WriterImplementation.processOrNull(type, configuration) }

/**
 * Writes records as a given type [T] in a CSV file **asynchronously** according
 * to the given [configuration], or throws a [CsvException] when [T] is not an
 * internal data class or when the [configuration] is invalid.
 */
@Suppress("DEPRECATION")
@Throws(CsvException::class)
public inline infix fun <reified T : Any> CoroutineScope.csvWriterAsync(
    noinline configuration: Writer<T>.() -> Unit
): Deferred<Unit> = csvWriterAsync(T::class, configuration)

@Deprecated(
    message = "Use the `csvWriterAsync<T> {}` method instead.",
    ReplaceWith("csvWriterAsync<T> {}")
)
@Throws(CsvException::class)
public fun <T : Any> CoroutineScope.csvWriterAsync(
    type: KClass<T>,
    configuration: Writer<T>.() -> Unit
): Deferred<Unit> =
    async(IO) { WriterImplementation.process(type, configuration) }

/**
 * Writes records as a given type [T] in a CSV file **asynchronously** according
 * to the given [configuration], or returns `null` when [T] is not an internal
 * data class or when the [configuration] is invalid.
 */
@Suppress("DEPRECATION")
public inline infix fun <reified T : Any> CoroutineScope.csvWriterOrNullAsync(
    noinline configuration: Writer<T>.() -> Unit
): Deferred<Unit?> = csvWriterOrNullAsync(T::class, configuration)

@Deprecated(
    message = "Use the `csvWriterOrNullAsync<T> {}` method instead.",
    ReplaceWith("csvWriterOrNullAsync<T> {}")
)
public fun <T : Any> CoroutineScope.csvWriterOrNullAsync(
    type: KClass<T>,
    configuration: Writer<T>.() -> Unit
): Deferred<Unit?> =
    async(IO) { WriterImplementation.processOrNull(type, configuration) }

/**
 * Configurable object responsible for writing records with a given type [T] in
 * a CSV file.
 */
public sealed interface Writer<in T : Any> : Manager {
    /**
     * **Optional** flag for overwriting the [file]'s content.
     *
     * Set to `true` by default.
     */
    public var overwrite: Boolean

    /** **Required** function that defines the records to write. */
    public infix fun records(configuration: Records<T>.() -> Unit)

    /** Configurable object responsible for defining the records to write. */
    public sealed interface Records<in T : Any> {
        /** Adds the given type [T] as a record. */
        public operator fun T.unaryPlus()
    }
}
