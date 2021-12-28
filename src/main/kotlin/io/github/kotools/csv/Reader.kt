package io.github.kotools.csv

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlin.reflect.KClass

/**
 * Returns the file's records according to the given [configuration], or throws
 * a [CsvException] when the [configuration] is invalid or when the targeted
 * file doesn't exist.
 */
@Throws(CsvException::class)
public suspend fun csvReader(configuration: Reader.() -> Unit):
        List<Map<String, String>> =
    withContext(IO) { delegateCsvReader(configuration) }

/**
 * Returns the file's records as a given list of type [T] according to the given
 * [configuration] or throws a [CsvException] when:
 * - the type [T] is not an internal data class or doesn't match the records
 * - the [configuration] is invalid
 * - the targeted file doesn't exist.
 */
@Suppress("DEPRECATION")
@Throws(CsvException::class)
public suspend inline fun <reified T : Any> csvReaderAs(
    noinline configuration: Reader.() -> Unit
): List<T> = csvReaderAs(T::class, configuration)

@Deprecated(
    message = "Use the `csvReaderAs<T> {}` method instead.",
    ReplaceWith("csvReaderAs<T> {}")
)
@Throws(CsvException::class)
public suspend fun <T : Any> csvReaderAs(
    type: KClass<T>,
    configuration: Reader.() -> Unit
): List<T> = withContext(IO) { delegateCsvReaderAs(type, configuration) }

/**
 * Returns the file's records according to the given [configuration], or returns
 * `null` when the [configuration] is invalid or when the targeted file doesn't
 * exist.
 */
public suspend fun csvReaderOrNull(configuration: Reader.() -> Unit):
        List<Map<String, String>>? =
    withContext(IO) { delegateCsvReaderOrNull(configuration) }

/**
 * Returns the file's records as a given list of type [T] according to the given
 * [configuration].
 * This method returns `null` when the type [T] is not a public or internal
 * data class, or when the [configuration] is invalid, or when the targeted file
 * doesn't exist.
 */
@Suppress("DEPRECATION")
public suspend inline fun <reified T : Any> csvReaderOrNullAs(
    noinline configuration: Reader.() -> Unit
): List<T>? = csvReaderOrNullAs(T::class, configuration)

@Deprecated(
    message = "Use the `csvReaderOrNullAs<T> {}` method instead.",
    ReplaceWith("csvReaderOrNullAs<T> {}")
)
public suspend fun <T : Any> csvReaderOrNullAs(
    type: KClass<T>,
    configuration: Reader.() -> Unit
): List<T>? = withContext(IO) { delegateCsvReaderOrNullAs(type, configuration) }

/**
 * Returns the file's records **asynchronously** according to the given
 * [configuration], or throws a [CsvException] when the [configuration] is
 * invalid or when the targeted file doesn't exist.
 */
@Throws(CsvException::class)
public infix fun CoroutineScope.csvReaderAsync(
    configuration: Reader.() -> Unit
): Deferred<List<Map<String, String>>> =
    async(IO) { delegateCsvReader(configuration) }

/**
 * Returns the file's records as a given list of type [T] **asynchronously**
 * according to the given [configuration] or throws a [CsvException] when:
 * - the type [T] is not an internal data class or doesn't match the records
 * - the [configuration] is invalid
 * - the targeted file doesn't exist.
 */
@Suppress("DEPRECATION")
@Throws(CsvException::class)
public inline infix fun <reified T : Any> CoroutineScope.csvReaderAsAsync(
    noinline configuration: Reader.() -> Unit
): Deferred<List<T>> = csvReaderAsAsync(T::class, configuration)

@Deprecated(
    message = "Use the `csvReaderAsAsync<T> {}` method instead.",
    ReplaceWith("csvReaderAsAsync<T> {}")
)
@Throws(CsvException::class)
public fun <T : Any> CoroutineScope.csvReaderAsAsync(
    type: KClass<T>,
    configuration: Reader.() -> Unit
): Deferred<List<T>> = async(IO) { delegateCsvReaderAs(type, configuration) }

/**
 * Returns the file's records as a given list of type [T] **asynchronously**
 * according to the given [configuration].
 * This method returns `null` when the type [T] is not a public or internal
 * data class, or when the [configuration] is invalid, or when the targeted file
 * doesn't exist.
 */
@Suppress("DEPRECATION")
public inline infix fun <reified T : Any> CoroutineScope.csvReaderOrNullAsAsync(
    noinline configuration: Reader.() -> Unit
): Deferred<List<T>?> = csvReaderOrNullAsAsync(T::class, configuration)

@Deprecated(
    message = "Use the `csvReaderOrNullAsAsync<T> {}` method instead.",
    ReplaceWith("csvReaderOrNullAsAsync<T> {}")
)
public fun <T : Any> CoroutineScope.csvReaderOrNullAsAsync(
    type: KClass<T>,
    configuration: Reader.() -> Unit
): Deferred<List<T>?> =
    async(IO) { delegateCsvReaderOrNullAs(type, configuration) }

/**
 * Returns the file's records **asynchronously** according to the given
 * [configuration], or returns `null` when the [configuration] is invalid or
 * when the targeted file doesn't exist.
 */
public infix fun CoroutineScope.csvReaderOrNullAsync(
    configuration: Reader.() -> Unit
): Deferred<List<Map<String, String>>?> =
    async(IO) { delegateCsvReaderOrNull(configuration) }

/** Configurable object responsible for reading a CSV file. */
public sealed interface Reader : Manager
