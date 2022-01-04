package io.github.kotools.csv

import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
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
    noinline configuration: Reader.() -> Unit
): List<T>? = csvReaderOrNull(T::class, configuration)

@Deprecated(
    message = "Use the `csvReaderOrNull<T> {}` method instead.",
    ReplaceWith("csvReaderOrNull<T> {}")
)
public suspend fun <T : Any> csvReaderOrNull(
    type: KClass<T>,
    configuration: Reader.() -> Unit
): List<T>? = withContext(Dispatchers.IO) {
    processOrNull { ReaderImplementation.createOrNull(type, configuration) }
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
    noinline configuration: Reader.() -> Unit
): Deferred<List<T>?> = csvReaderOrNullAsync(T::class, configuration)

@Deprecated(
    message = "Use the `csvReaderOrNullAsync<T> {}` method instead.",
    ReplaceWith("csvReaderOrNullAsync<T> {}")
)
public fun <T : Any> CoroutineScope.csvReaderOrNullAsync(
    type: KClass<T>,
    configuration: Reader.() -> Unit
): Deferred<List<T>?> = async(Dispatchers.IO) {
    processOrNull { ReaderImplementation.createOrNull(type, configuration) }
}

/** Configurable object responsible for reading a CSV file. */
public sealed interface Reader : Manager

internal class ReaderImplementation<out T : Any>
private constructor(private val type: DataType<T>) : Reader,
    ManagerImplementation(),
    Processor<List<T>> {
    private val csv: CsvReader
        get() = csvReader {
            delimiter = separator.value
            skipEmptyLine = true
        }

    override fun process(): List<T> = TODO()

    override fun processOrNull(): List<T>? = findOrNull { this }
        ?.let(::read)
        ?.mapNotNull(type::createTypeOrNull)

    private infix fun read(target: FinderResult): List<Map<String, String>> =
        when (target) {
            is FinderResult.File -> csv.readAllWithHeader(target.file)
            is FinderResult.Stream -> csv.readAllWithHeader(target.stream)
        }

    companion object {
        inline fun <T : Any> createOrNull(
            type: KClass<T>,
            configuration: Reader.() -> Unit
        ): ReaderImplementation<T>? = type.toDataTypeOrNull()
            ?.let(::ReaderImplementation)
            ?.apply(configuration)
            ?.takeIf(ReaderImplementation<T>::isValid)
    }
}
