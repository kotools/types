package io.github.kotools.csv.deprecated

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlin.reflect.KClass

/**
 * Returns the file's records as a given type [T] according to the given
 * [configuration] or throws a [CsvException] when:
 * - the type [T] is not an internal data class or doesn't match the records
 * - the [configuration] is invalid
 * - the targeted file doesn't exist.
 */
@Suppress("DEPRECATION")
@Throws(CsvException::class)
public suspend inline fun <reified T : Any> csvReader(
    noinline configuration: Reader.() -> Unit
): List<T> = csvReader(T::class, configuration)

@Deprecated(
    message = "Use the `csvReader<T> {}` method instead.",
    ReplaceWith("csvReader<T> {}")
)
@Throws(CsvException::class)
public suspend fun <T : Any> csvReader(
    type: KClass<T>,
    configuration: Reader.() -> Unit
): List<T> =
    withContext(IO) { ReaderImplementation.process(type, configuration) }

/**
 * Returns the file's records as a given type [T] according to the given
 * [configuration].
 * This method returns `null` when the type [T] is not a public or internal
 * data class, or when the [configuration] is invalid, or when the targeted file
 * doesn't exist.
 */
@Suppress("DEPRECATION")
public suspend inline fun <reified T : Any> csvReaderOrNull(
    noinline configuration: Reader.() -> Unit
): List<T>? = csvReaderOrNull(T::class, configuration)

@Deprecated(
    message = "Use the `csvReaderOrNull<T> {}` method instead.",
    ReplaceWith("csvReaderOrNull<T> {}")
)
public suspend fun <T : Any> csvReaderOrNull(
    type: KClass<T>,
    configuration: Reader.() -> Unit
): List<T>? =
    withContext(IO) { ReaderImplementation.processOrNull(type, configuration) }

/**
 * Returns the file's records as a given type [T] **asynchronously** according
 * to the given [configuration] or throws a [CsvException] when:
 * - the type [T] is not an internal data class or doesn't match the records
 * - the [configuration] is invalid
 * - the targeted file doesn't exist.
 */
@Suppress("DEPRECATION")
@Throws(CsvException::class)
public inline infix fun <reified T : Any> CoroutineScope.csvReaderAsync(
    noinline configuration: Reader.() -> Unit
): Deferred<List<T>> = csvReaderAsync(T::class, configuration)

@Deprecated(
    message = "Use the `csvReaderAsync<T> {}` method instead.",
    ReplaceWith("csvReaderAsync<T> {}")
)
@Throws(CsvException::class)
public fun <T : Any> CoroutineScope.csvReaderAsync(
    type: KClass<T>,
    configuration: Reader.() -> Unit
): Deferred<List<T>> =
    async(IO) { ReaderImplementation.process(type, configuration) }

/**
 * Returns the file's records as a given type [T] **asynchronously** according
 * to the given [configuration].
 * This method returns `null` when the type [T] is not a public or internal
 * data class, or when the [configuration] is invalid, or when the targeted file
 * doesn't exist.
 */
@Suppress("DEPRECATION")
public inline infix fun <reified T : Any> CoroutineScope.csvReaderOrNullAsync(
    noinline configuration: Reader.() -> Unit
): Deferred<List<T>?> = csvReaderOrNullAsync(T::class, configuration)

@Deprecated(
    message = "Use the `csvReaderOrNullAsync<T> {}` method instead.",
    ReplaceWith("csvReaderOrNullAsync<T> {}")
)
public fun <T : Any> CoroutineScope.csvReaderOrNullAsync(
    type: KClass<T>,
    configuration: Reader.() -> Unit
): Deferred<List<T>?> =
    async(IO) { ReaderImplementation.processOrNull(type, configuration) }

/** Configurable object responsible for reading a CSV file. */
public sealed interface Reader : Manager
