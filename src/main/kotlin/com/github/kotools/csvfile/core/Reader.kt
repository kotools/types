package com.github.kotools.csvfile.core

import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.kotools.csvfile.api.ReaderApi
import java.io.File
import java.io.InputStream
import java.net.URL

internal val reader: Reader get() = Reader()

internal class Reader : ReaderApi() {
    private val csv: CsvReader
        get() = csvReader {
            delimiter = separator.value
            skipEmptyLine = true
        }

    inline infix operator fun invoke(config: ReaderApi.() -> Unit):
            List<Map<String, String>>? = apply(config).execute()

    private fun execute(): List<Map<String, String>>? = if (file.isBlank()) null
    else if (resource) readResource()
    else readFile()

    private fun readFile(): List<Map<String, String>>? {
        val url: URL = loader.getResource("") ?: return null
        val f = File("${url.path}$folder$file")
        if (!f.exists()) return null
        return csv.readAllWithHeader(f)
    }

    private fun readResource(): List<Map<String, String>>? {
        val stream: InputStream =
            loader.getResourceAsStream("$folder$file") ?: return null
        return csv.readAllWithHeader(stream)
    }
}
