package io.github.kotools.csv.reader

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.reflect.KClass

internal class ReaderProcessor<out T : Any>(
    private val type: KClass<T>,
    private val configuration: CsvReader.() -> Unit
) {
    suspend fun processOrNull(): List<T>? = coroutineScope {
        val dataType: Deferred<DataType<T>?> = async { type.toDataTypeOrNull() }
        val readerConfiguration: ReaderConfiguration = configuration
            .toReaderConfigurationOrNull()
            ?: return@coroutineScope null
        val records: List<Map<String, String>> = Finder(readerConfiguration)
            .findOrNull()
            ?.let { Reader(readerConfiguration.separator, it) }
            ?.read()
            ?: return@coroutineScope null
        dataType.await()
            ?.let { records.mapNotNull(it::createTypeOrNull) }
    }
}
