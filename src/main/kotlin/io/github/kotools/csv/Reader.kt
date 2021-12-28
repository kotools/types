package io.github.kotools.csv

import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.reflect.KClass

private val reader: Reader get() = ReaderConfiguration()
private val Reader.csv: CsvReader
    get() = csvReader {
        delimiter = separator.value
        skipEmptyLine = true
    }

/**
 * Returns the file's records according to the given [configuration], or throws
 * a [CsvException] when the [configuration] is invalid or when the targeted
 * file doesn't exist.
 */
@Throws(CsvException::class)
public suspend fun csvReader(configuration: Reader.() -> Unit):
        List<Map<String, String>> =
    withContext(IO) { processCsvReader(configuration) }

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
): List<T> = withContext(IO) { processCsvReaderAs(type, configuration) }

/**
 * Returns the file's records as a given list of type [T] according to the given
 * [configuration].
 * This method returns `null` when the type [T] is not a public or internal
 * data class, or when the [configuration] is invalid, or when the targeted file
 * doesn't exist.
 */
@Suppress("DEPRECATION")
public suspend inline fun <reified T : Any> csvReaderAsOrNull(
    noinline configuration: Reader.() -> Unit
): List<T>? = csvReaderAsOrNull(T::class, configuration)

@Deprecated(
    message = "Use the `csvReaderAsOrNull<T> {}` method instead.",
    ReplaceWith("csvReaderAsOrNull<T> {}")
)
public suspend fun <T : Any> csvReaderAsOrNull(
    type: KClass<T>,
    configuration: Reader.() -> Unit
): List<T>? = withContext(IO) { processCsvReaderAsOrNull(type, configuration) }

/**
 * Returns the file's records according to the given [configuration], or returns
 * `null` when the [configuration] is invalid or when the targeted file doesn't
 * exist.
 */
public suspend fun csvReaderOrNull(configuration: Reader.() -> Unit):
        List<Map<String, String>>? =
    withContext(IO) { processCsvReaderOrNull(configuration) }

/**
 * Returns the file's records **asynchronously** according to the given
 * [configuration], or throws a [CsvException] when the [configuration] is
 * invalid or when the targeted file doesn't exist.
 */
@Throws(CsvException::class)
public infix fun CoroutineScope.csvReaderAsync(
    configuration: Reader.() -> Unit
): Deferred<List<Map<String, String>>> =
    async(IO) { processCsvReader(configuration) }

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
): Deferred<List<T>> = async(IO) { processCsvReaderAs(type, configuration) }

/**
 * Returns the file's records as a given list of type [T] **asynchronously**
 * according to the given [configuration].
 * This method returns `null` when the type [T] is not a public or internal
 * data class, or when the [configuration] is invalid, or when the targeted file
 * doesn't exist.
 */
@Suppress("DEPRECATION")
public inline infix fun <reified T : Any> CoroutineScope.csvReaderAsOrNullAsync(
    noinline configuration: Reader.() -> Unit
): Deferred<List<T>?> = csvReaderAsOrNullAsync(T::class, configuration)

@Deprecated(
    message = "Use the `csvReaderAsOrNullAsync<T> {}` method instead.",
    ReplaceWith("csvReaderAsOrNullAsync<T> {}")
)
public fun <T : Any> CoroutineScope.csvReaderAsOrNullAsync(
    type: KClass<T>,
    configuration: Reader.() -> Unit
): Deferred<List<T>?> =
    async(IO) { processCsvReaderAsOrNull(type, configuration) }

/**
 * Returns the file's records **asynchronously** according to the given
 * [configuration], or returns `null` when the [configuration] is invalid or
 * when the targeted file doesn't exist.
 */
public infix fun CoroutineScope.csvReaderOrNullAsync(
    configuration: Reader.() -> Unit
): Deferred<List<Map<String, String>>?> =
    async(IO) { processCsvReaderOrNull(configuration) }

@Throws(CsvException::class)
private inline fun processCsvReader(configuration: Reader.() -> Unit):
        List<Map<String, String>> = reader.apply(configuration)
    .takeIf(Manager::isValid)
    ?.readFile()
    ?: invalidConfigurationException()

@Throws(CsvException::class)
private fun <T : Any> processCsvReaderAs(
    type: KClass<T>,
    configuration: Reader.() -> Unit
): List<T> = type.toDataType().let {
    processCsvReader(configuration).map(it::createType)
}

private inline fun <T : Any> processCsvReaderAsOrNull(
    type: KClass<T>,
    configuration: Reader.() -> Unit
): List<T>? = type.toDataTypeOrNull()?.let {
    processCsvReaderOrNull(configuration)?.mapNotNull(it::createTypeOrNull)
}

private inline fun processCsvReaderOrNull(configuration: Reader.() -> Unit):
        List<Map<String, String>>? = reader.apply(configuration)
    .takeIf(Manager::isValid)
    ?.readFileOrNull()

@Throws(CsvException::class)
private fun Reader.readFile(): List<Map<String, String>> =
    readFileOrNull() ?: fileNotFoundException("$folder$file")

private fun Reader.readFileOrNull(): List<Map<String, String>>? =
    readResourceFile() ?: readSystemFile()

private fun Reader.readResourceFile(): List<Map<String, String>>? =
    (loader getStream "$folder$file")?.let(csv::readAllWithHeader)

private fun Reader.readSystemFile(): List<Map<String, String>>? = loader.baseUrl
    ?.let { File("${it.path}$folder$file") }
    ?.takeIf(File::exists)
    ?.let(csv::readAllWithHeader)

/** Configurable object responsible for reading a CSV file. */
public sealed interface Reader : Manager

internal class ReaderConfiguration : ManagerConfiguration(), Reader {
    override fun equals(other: Any?): Boolean = this === other

    override fun hashCode(): Int = System.identityHashCode(this)
}
