package io.github.kotools.csv

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlin.reflect.KClass

/**
 * Writes records in a CSV file according to the given [configuration], or
 * throws a [CsvException] when the [configuration] is invalid.
 */
@Throws(CsvException::class)
public suspend fun csvWriter(configuration: Writer.() -> Unit): Unit =
    withContext(IO) { WriterImpl process configuration }

/**
 * Writes records as a given type [T] in a CSV file according to the given
 * [configuration], or throws a [CsvException] when [T] is not an internal data
 * class or when the [configuration] is invalid.
 */
@Suppress("DEPRECATION")
@Throws(CsvException::class)
public suspend inline fun <reified T : Any> csvWriterAs(
    noinline configuration: TypedWriter<T>.() -> Unit
): Unit = csvWriterAs(T::class, configuration)

@Deprecated(
    message = "Use the `csvWriterAs<T> {}` method instead.",
    ReplaceWith("csvWriterAs<T> {}")
)
@Throws(CsvException::class)
public suspend fun <T : Any> csvWriterAs(
    type: KClass<T>,
    configuration: TypedWriter<T>.() -> Unit
): Unit = withContext(IO) { TypedWriterImpl.process(type, configuration) }

/**
 * Writes records as a given type [T] in a CSV file according to the given
 * [configuration], or returns `null` when [T] is not an internal data class or
 * when the [configuration] is invalid.
 */
@Suppress("DEPRECATION")
public suspend inline fun <reified T : Any> csvWriterAsOrNull(
    noinline configuration: TypedWriter<T>.() -> Unit
): Unit? = csvWriterAsOrNull(T::class, configuration)

@Deprecated(
    message = "Use the `csvWriterAsOrNull<T> {}` method instead.",
    ReplaceWith("csvWriterAsOrNull<T> {}")
)
public suspend fun <T : Any> csvWriterAsOrNull(
    type: KClass<T>,
    configuration: TypedWriter<T>.() -> Unit
): Unit? =
    withContext(IO) { TypedWriterImpl.processOrNull(type, configuration) }

/**
 * Writes records in a CSV file according to the given [configuration], or
 * returns `null` when the [configuration] is invalid.
 */
public suspend fun csvWriterOrNull(configuration: Writer.() -> Unit): Unit? =
    withContext(IO) { WriterImpl processOrNull configuration }

/**
 * Writes records as a given type [T] in a CSV file **asynchronously** according
 * to the given [configuration], or throws a [CsvException] when [T] is not an
 * internal data class or when the [configuration] is invalid.
 */
@Suppress("DEPRECATION")
@Throws(CsvException::class)
public inline infix fun <reified T : Any> CoroutineScope.csvWriterAsAsync(
    noinline configuration: TypedWriter<T>.() -> Unit
): Deferred<Unit> = csvWriterAsAsync(T::class, configuration)

@Deprecated(
    message = "Use the `csvWriterAsAsync<T> {}` method instead.",
    ReplaceWith("csvWriterAsAsync<T> {}")
)
@Throws(CsvException::class)
public fun <T : Any> CoroutineScope.csvWriterAsAsync(
    type: KClass<T>,
    configuration: TypedWriter<T>.() -> Unit
): Deferred<Unit> = async(IO) { TypedWriterImpl.process(type, configuration) }

/**
 * Writes records as a given type [T] in a CSV file **asynchronously** according
 * to the given [configuration], or returns `null` when [T] is not an internal
 * data class or when the [configuration] is invalid.
 */
@Suppress("DEPRECATION")
public inline infix fun <reified T : Any> CoroutineScope.csvWriterAsOrNullAsync(
    noinline configuration: TypedWriter<T>.() -> Unit
): Deferred<Unit?> = csvWriterAsOrNullAsync(T::class, configuration)

@Deprecated(
    message = "Use the `csvWriterAsOrNullAsync<T> {}` method instead.",
    ReplaceWith("csvWriterAsOrNullAsync<T> {}")
)
public fun <T : Any> CoroutineScope.csvWriterAsOrNullAsync(
    type: KClass<T>,
    configuration: TypedWriter<T>.() -> Unit
): Deferred<Unit?> =
    async(IO) { TypedWriterImpl.processOrNull(type, configuration) }

/**
 * Writes records in a CSV file **asynchronously** according to the given
 * [configuration], or throws a [CsvException] when the [configuration] is
 * invalid.
 */
@Throws(CsvException::class)
public infix fun CoroutineScope.csvWriterAsync(
    configuration: Writer.() -> Unit
): Deferred<Unit> = async(IO) { WriterImpl process configuration }

/**
 * Writes records in a CSV file **asynchronously** according to the given
 * [configuration], or returns `null` when the [configuration] is invalid.
 */
public infix fun CoroutineScope.csvWriterOrNullAsync(
    configuration: Writer.() -> Unit
): Deferred<Unit?> = async(IO) { WriterImpl processOrNull configuration }

/**
 * Configurable object responsible for writing records with a given type [T] in
 * a CSV file.
 */
public sealed interface TypedWriter<in T : Any> : Manager {
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
        /** Adds the given [Iterable] as a record. */
        public operator fun T.unaryPlus()
    }
}

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
