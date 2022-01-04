package io.github.kotools.csv.reader

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import kotlin.reflect.KClass

internal class ReaderProcessor<out T : Any>(
    private val type: KClass<T>,
    private val configuration: CsvReader.() -> Unit
) {
    suspend fun processOrNull(): List<T>? = coroutineScope {
        val dataType: Deferred<DataType<T>?> = async { type.toDataTypeOrNull() }
        val records: List<Map<String, String>> = configuration
            .toReaderConfigurationOrNull()
            ?.let { readFileOrNull(it) }
            ?: return@coroutineScope null
        dataType.await()
            ?.let { records.mapNotNull(it::createTypeOrNull) }
    }

    private suspend infix fun readFileOrNull(
        config: ReaderConfiguration
    ): List<Map<String, String>>? = withContext(Dispatchers.IO) {
        Finder(config)
            .findOrNull()
            ?.let { Reader(config, it) }
            ?.read()
    }
}
