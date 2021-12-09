package com.github.kotools.csvfile.core

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.kotools.csvfile.api.CsvReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

public typealias CsvLine = Map<String, String>

internal val reader: Reader get() = Reader()

internal class Reader : CsvReader() {
    private val csv: com.github.doyaaaaaken.kotlincsv.client.CsvReader
        get() = csvReader {
            delimiter = separator.value
            skipEmptyLine = true
        }

    suspend fun execute(): List<CsvLine>? = withContext(Dispatchers.IO) {
        file?.let(ClassLoader.getSystemClassLoader()::getResourceAsStream)
            ?.let(csv::readAllWithHeader)
            ?.apply { if (debug) println(this) }
    }
}
