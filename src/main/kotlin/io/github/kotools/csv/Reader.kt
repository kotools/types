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
 * [configuration].
 * This method returns `null` when:
 * - the type [T] is not a public or internal data class
 * - the [configuration] is invalid
 * - the targeted file doesn't exist.
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
): List<T>? = withContext(IO) {
    val dataType: DataType<T> =
        type.toDataTypeOrNull() ?: return@withContext null
    ReaderImplementation.apply(configuration)
        .takeIf(Manager::isValid)
        ?.readOrNull()
        ?.mapNotNull(dataType::createTypeOrNull)
}

/**
 * Returns the file's records as a given type [T] **asynchronously** according
 * to the given [configuration].
 * This method returns `null` when:
 * - the type [T] is not a public or internal data class
 * - the [configuration] is invalid
 * - the targeted file doesn't exist.
 */
public inline infix fun <reified T : Any> CoroutineScope.csvReaderOrNullAsync(
    noinline configuration: Reader.() -> Unit
): Deferred<List<T>?> = async { csvReaderOrNull(configuration) }

private fun Manager.isValid(): Boolean = file.isNotBlank()

private fun Manager.readOrNull(): List<Map<String, String>>? {
    val target: FinderResult = finderOrNull() ?: return null
    return when (target) {
        is FinderResult.File -> csv.readAllWithHeader(target.file)
        is FinderResult.Stream -> csv.readAllWithHeader(target.stream)
    }
}

/** Configurable object responsible for reading a CSV file. */
public sealed interface Reader : Manager

private object ReaderImplementation : Reader, ManagerImplementation()
