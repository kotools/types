package com.github.kotools.csvfile.core

import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.kotools.csvfile.api.ReaderApi
import java.io.File

public typealias CsvLine = Map<String, String>

internal val reader: Reader get() = Reader()

internal class Reader : ReaderApi() {
    private val csv: CsvReader
        get() = csvReader {
            delimiter = separator.value
            skipEmptyLine = true
        }

    private val loader: ClassLoader get() = ClassLoader.getSystemClassLoader()

    fun execute(): List<CsvLine>? = file.takeIf(String::isNotBlank)
        ?.let { if (resource) readResource() else readFile() }
        ?.apply { if (debug) println(this) }

    private fun readFile(): List<CsvLine>? = loader.getResource("")
        ?.let { File("${it.path}$folder$file") }
        ?.takeIf(File::exists)
        ?.let(csv::readAllWithHeader)

    private fun readResource(): List<CsvLine>? =
        loader.getResourceAsStream("$folder$file")
            ?.let(csv::readAllWithHeader)
}
