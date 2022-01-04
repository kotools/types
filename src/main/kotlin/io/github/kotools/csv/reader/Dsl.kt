package io.github.kotools.csv.reader

import io.github.kotools.csv.common.Manager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlin.reflect.KClass

/**
 * Returns the file's records as a given type [T] according to the given
 * [configuration] or throws an [IllegalStateException] when:
 * - the type [T] is not a public or internal data class
 * - the [configuration] is invalid
 * - the targeted file doesn't exist.
 */
public suspend inline fun <reified T : Any> csvReader(
    noinline configuration: Reader.() -> Unit
): List<T> = csvReader(T::class, configuration)

/**
 * Returns the file's records as a given type [T] according to the given
 * [configuration] or returns `null` when:
 * - the type [T] is not a public or internal data class
 * - the [configuration] is invalid
 * - the targeted file doesn't exist.
 */
public suspend inline fun <reified T : Any> csvReaderOrNull(
    noinline configuration: Reader.() -> Unit
): List<T>? = csvReaderOrNull(T::class, configuration)

/**
 * Returns the file's records as a given [type] according to the given
 * [configuration] or throws an [IllegalStateException] when:
 * - the [type] is not a public or internal data class
 * - the [configuration] is invalid
 * - the targeted file doesn't exist.
 */
public suspend fun <T : Any> csvReader(
    type: KClass<T>,
    configuration: Reader.() -> Unit
): List<T> = withContext(IO) { type processReader configuration }

/**
 * Returns the file's records as a given [type] according to the given
 * [configuration] or returns `null` when:
 * - the [type] is not a public or internal data class
 * - the [configuration] is invalid
 * - the targeted file doesn't exist.
 */
public suspend fun <T : Any> csvReaderOrNull(
    type: KClass<T>,
    configuration: Reader.() -> Unit
): List<T>? = withContext(IO) { type processReaderOrNull configuration }

/**
 * Returns the file's records as a given type [T] **asynchronously** according
 * to the given [configuration] or throws [IllegalStateException] when:
 * - the type [T] is not a public or internal data class
 * - the [configuration] is invalid
 * - the targeted file doesn't exist.
 */
public inline infix fun <reified T : Any> CoroutineScope.csvReaderAsync(
    noinline configuration: Reader.() -> Unit
): Deferred<List<T>> = async { csvReader(configuration) }

/**
 * Returns the file's records as a given type [T] **asynchronously** according
 * to the given [configuration] or returns `null` when:
 * - the type [T] is not a public or internal data class
 * - the [configuration] is invalid
 * - the targeted file doesn't exist.
 */
public inline infix fun <reified T : Any> CoroutineScope.csvReaderOrNullAsync(
    noinline configuration: Reader.() -> Unit
): Deferred<List<T>?> = async { csvReaderOrNull(configuration) }

/** Scope for reading a CSV file. */
public sealed interface Reader : Manager
