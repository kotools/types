package io.github.kotools.csv

import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlin.reflect.KClass

private val Manager.csv: CsvReader
    get() = csvReader {
        delimiter = separator.value
        skipEmptyLine = true
    }

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
 * Returns the file's records as a given [type] according to the given
 * [configuration] or throws an [IllegalStateException] when:
 * - the [type] is not a public or internal data class
 * - the [configuration] is invalid
 * - the targeted file doesn't exist.
 */
public suspend fun <T : Any> csvReader(
    type: KClass<T>,
    configuration: Reader.() -> Unit
): List<T> = withContext(IO) {
    val dataType: DataType<T> = type.toDataType()
    val reader = ReaderImplementation(configuration)
    if (!reader.isValid()) error("Given configuration is invalid")
    reader.run { target readWith csv }
        .map(dataType::createType)
}

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
 * [configuration] or returns `null` when:
 * - the [type] is not a public or internal data class
 * - the [configuration] is invalid
 * - the targeted file doesn't exist.
 */
public suspend fun <T : Any> csvReaderOrNull(
    type: KClass<T>,
    configuration: Reader.() -> Unit
): List<T>? = withContext(IO) {
    val dataType: DataType<T> =
        type.toDataTypeOrNull() ?: return@withContext null
    ReaderImplementation(configuration)
        .takeIf(ReaderImplementation::isValid)
        ?.run { targetOrNull?.readWith(csv) }
        ?.mapNotNull(dataType::createTypeOrNull)
}

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

private infix fun FileTarget.readWith(csv: CsvReader):
        List<Map<String, String>> = when (this) {
    is FileTarget.File -> csv.readAllWithHeader(file)
    is FileTarget.Stream -> csv.readAllWithHeader(stream)
}

/** Configurable object responsible for reading a CSV file. */
public sealed interface Reader : Manager

private class ReaderImplementation(configuration: Reader.() -> Unit) :
    ManagerImplementation(),
    Reader {
    init {
        apply(configuration)
    }
}
