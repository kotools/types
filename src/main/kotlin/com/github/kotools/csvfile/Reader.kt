package com.github.kotools.csvfile

import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

private val reader: CsvReader by lazy {
    csvReader { skipEmptyLine = true }
}

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

@CsvDsl
public class Reader {
    public var file: String? = null
} 
