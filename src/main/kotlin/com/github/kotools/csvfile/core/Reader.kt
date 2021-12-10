package com.github.kotools.csvfile.core

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.kotools.csvfile.api.ReaderApi

public typealias CsvLine = Map<String, String>

internal val reader: Reader get() = Reader()

internal class Reader : ReaderApi() {
    private val csv: com.github.doyaaaaaken.kotlincsv.client.CsvReader
        get() = csvReader {
            delimiter = separator.value
            skipEmptyLine = true
        }

    fun execute(): List<CsvLine>? = file.takeIf(String::isNotBlank)
        ?.let { "$folder$it" }
        ?.let(ClassLoader.getSystemClassLoader()::getResourceAsStream)
        ?.let(csv::readAllWithHeader)
        ?.apply { if (debug) println(this) }
}
