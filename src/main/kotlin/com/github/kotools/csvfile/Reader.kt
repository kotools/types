package com.github.kotools.csvfile

import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

private val reader: CsvReader by lazy {
    csvReader { skipEmptyLine = true }
}

/**
 * Creates a new [reader][Reader] with the given [configuration][config] and
 * reads the [corresponding file][Reader.file]'s content.
 *
 * If no [configuration][config] is defined, or if the [file][Reader.file]
 * doesn't exist in **resources** folder, this function returns `null`.
 *
 * The `.csv` extension in the [file's name][Reader.file] is optional.
 */
public suspend infix fun Csv.reader(
    config: Reader.() -> Unit
): List<Map<String, String>>? = Reader()
    .apply(config)
    .execute()

private suspend fun Reader.execute(): List<CsvLine>? = withContext(IO) {
    file?.formatFileName()
        ?.let(ClassLoader.getSystemClassLoader()::getResourceAsStream)
        ?.let(reader::readAllWithHeader)
}

/** Scope for reading CSV files content. */
@CsvDsl
public class Reader {
    public var file: String? = null
} 
