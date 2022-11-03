package io.github.kotools.csv.writer

import io.github.kotools.csv.common.Manager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlin.reflect.KClass

/**
 * Writes records as a given type [T] in a CSV file according to the given
 * [configuration] or throws an [IllegalStateException] when:
 * - the type [T] is not a public or internal data class
 * - the [configuration] is invalid.
 */
public suspend inline fun <reified T : Any> csvWriter(
    noinline configuration: Writer<T>.() -> Unit
): Unit = csvWriter(T::class, configuration)

/**
 * Writes records as a given [type] in a CSV file according to the given
 * [configuration] or throws an [IllegalStateException] when:
 * - the [type] is not a public or internal data class
 * - the [configuration] is invalid.
 */
public suspend fun <T : Any> csvWriter(
    type: KClass<T>,
    configuration: Writer<T>.() -> Unit
): Unit = withContext(IO) { type processWriter configuration }

/**
 * Writes records as a given type [T] in a CSV file according to the given
 * [configuration] or returns `null` when:
 * - the type [T] is not a public or internal data class
 * - the [configuration] is invalid.
 */
public suspend inline fun <reified T : Any> csvWriterOrNull(
    noinline configuration: Writer<T>.() -> Unit
): Unit? = csvWriterOrNull(T::class, configuration)

/**
 * Writes records as a given [type] in a CSV file according to the given
 * [configuration] or returns `null` when:
 * - the [type] is not a public or internal data class
 * - the [configuration] is invalid.
 */
public suspend fun <T : Any> csvWriterOrNull(
    type: KClass<T>,
    configuration: Writer<T>.() -> Unit
): Unit? = withContext(IO) { type processWriterOrNull configuration }

/**
 * Writes records as a given type [T] in a CSV file **asynchronously** according
 * to the given [configuration] or throws an [IllegalStateException] when:
 * - the type [T] is not a public or internal data class
 * - the [configuration] is invalid.
 */
public inline infix fun <reified T : Any> CoroutineScope.csvWriterAsync(
    noinline configuration: Writer<T>.() -> Unit
): Deferred<Unit> = async { csvWriter(configuration) }

/**
 * Writes records as a given type [T] in a CSV file **asynchronously** according
 * to the given [configuration] or returns `null` when:
 * - the type [T] is not a public or internal data class
 * - the [configuration] is invalid.
 */
public inline infix fun <reified T : Any> CoroutineScope.csvWriterOrNullAsync(
    noinline configuration: Writer<T>.() -> Unit
): Deferred<Unit?> = async { csvWriterOrNull(configuration) }

/** Scope for writing records with a given type [T] in a CSV file. */
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
