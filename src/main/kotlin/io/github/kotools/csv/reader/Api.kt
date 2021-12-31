package io.github.kotools.csv.reader

import io.github.kotools.csv.manager.CsvManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlin.reflect.KClass

// TODO: Test and document
@Suppress("DEPRECATION")
public suspend inline fun <reified T : Any> csvReader(
    noinline configuration: CsvReader.() -> Unit
): List<T> = csvReader(T::class, configuration)

@Deprecated(
    message = "Use the `csvReader<T> {}` method instead.",
    ReplaceWith("csvReader<T> {}"),
)
public suspend fun <T : Any> csvReader(
    type: KClass<T>,
    configuration: CsvReader.() -> Unit
): List<T> = withContext(IO) {
    ReaderProcessor(type, configuration)
        .process()
}

// TODO: Test and document
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
): List<T>? = withContext(IO) {
    ReaderProcessor(type, configuration)
        .processOrNull()
}

// TODO: Test and document
@Suppress("DEPRECATION")
public inline infix fun <reified T : Any> CoroutineScope.csvReaderAsync(
    noinline configuration: CsvReader.() -> Unit
): Deferred<List<T>> = csvReaderAsync(T::class, configuration)

@Deprecated(
    message = "Use the `csvReaderAsync<T> {}` method instead.",
    ReplaceWith("csvReaderAsync<T> {}")
)
public fun <T : Any> CoroutineScope.csvReaderAsync(
    type: KClass<T>,
    configuration: CsvReader.() -> Unit
): Deferred<List<T>> = async(IO) {
    ReaderProcessor(type, configuration)
        .process()
}

// TODO: Test and document
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
): Deferred<List<T>?> = async(IO) {
    ReaderProcessor(type, configuration)
        .processOrNull()
}

/** Configurable object responsible for reading a CSV file. */
public sealed interface CsvReader : CsvManager
