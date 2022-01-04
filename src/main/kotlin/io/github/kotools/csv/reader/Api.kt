package io.github.kotools.csv.reader

import io.github.kotools.csv.manager.CsvManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlin.reflect.KClass

/**
 * Returns the file's records as a given type [T] according to the given
 * [configuration].
 * This method returns `null` when:
 * - the type [T] is not a public or internal data class
 * - the [configuration] is invalid
 * - the targeted file doesn't exist.
 */
@Suppress("DEPRECATION")
public suspend inline fun <reified T : Any> csvReaderOrNull(
    noinline configuration: CsvReader.() -> Unit
): List<T>? = csvReaderOrNull(T::class, configuration)

@Deprecated(
    message = "Use the `csvReaderOrNull<T> {}` method instead.",
    ReplaceWith("csvReaderOrNull<T> {}")
)
public suspend fun <T : Any> csvReaderOrNull(
    type: KClass<T>,
    configuration: CsvReader.() -> Unit
): List<T>? = withContext(Dispatchers.Default) {
    ReaderProcessor(type, configuration)
        .processOrNull()
}

/**
 * Returns the file's records as a given type [T] **asynchronously** according
 * to the given [configuration].
 * This method returns `null` when:
 * - the type [T] is not a public or internal data class
 * - the [configuration] is invalid
 * - the targeted file doesn't exist.
 */
@Suppress("DEPRECATION")
public inline infix fun <reified T : Any> CoroutineScope.csvReaderOrNullAsync(
    noinline configuration: CsvReader.() -> Unit
): Deferred<List<T>?> = csvReaderOrNullAsync(T::class, configuration)

@Deprecated(
    message = "Use the `csvReaderOrNullAsync<T> {}` method instead.",
    ReplaceWith("csvReaderOrNullAsync<T> {}")
)
public fun <T : Any> CoroutineScope.csvReaderOrNullAsync(
    type: KClass<T>,
    configuration: CsvReader.() -> Unit
): Deferred<List<T>?> = async(Dispatchers.Default) {
    ReaderProcessor(type, configuration)
        .processOrNull()
}

/** Configurable object responsible for reading a CSV file. */
public sealed interface CsvReader : CsvManager
