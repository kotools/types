package io.github.kotools.csv

import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlin.reflect.KClass

private val ManagerScope.csv: CsvReader
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
    noinline configuration: ReaderScope.() -> Unit
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
    configuration: ReaderScope.() -> Unit
): List<T> = withContext(IO) {
    val dataType: DataType<T> = type.toDataType()
    val reader = Reader(configuration)
    if (!reader.isValid()) error("Given configuration is invalid")
    Finder.find(reader.filePath)
        .readWith(reader.csv)
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
    noinline configuration: ReaderScope.() -> Unit
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
    configuration: ReaderScope.() -> Unit
): List<T>? = withContext(IO) {
    val dataType: DataType<T> =
        type.toDataTypeOrNull() ?: return@withContext null
    val reader: Reader = Reader(configuration)
        .takeIf(Reader::isValid)
        ?: return@withContext null
    Finder.findOrNull(reader.filePath)
        ?.readWith(reader.csv)
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
    noinline configuration: ReaderScope.() -> Unit
): Deferred<List<T>> = async { csvReader(configuration) }

/**
 * Returns the file's records as a given type [T] **asynchronously** according
 * to the given [configuration] or returns `null` when:
 * - the type [T] is not a public or internal data class
 * - the [configuration] is invalid
 * - the targeted file doesn't exist.
 */
public inline infix fun <reified T : Any> CoroutineScope.csvReaderOrNullAsync(
    noinline configuration: ReaderScope.() -> Unit
): Deferred<List<T>?> = async { csvReaderOrNull(configuration) }

private infix fun Target.readWith(csv: CsvReader): List<Map<String, String>> =
    when (this) {
        is Target.File -> csv.readAllWithHeader(file)
        is Target.Stream -> csv.readAllWithHeader(stream)
    }

/** Scope for reading a CSV file. */
public sealed interface ReaderScope : ManagerScope

private class Reader(configuration: ReaderScope.() -> Unit) :
    Manager(),
    ReaderScope {
    init {
        apply(configuration)
    }
}
