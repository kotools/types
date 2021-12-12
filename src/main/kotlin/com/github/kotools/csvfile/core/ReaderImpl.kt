package com.github.kotools.csvfile.core

import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.kotools.csvfile.api.Reader
import java.io.File
import java.io.InputStream
import java.net.URL

internal inline fun reader(config: Reader.() -> Unit):
        List<Map<String, String>>? = ReaderImpl().apply(config).process()

internal class ReaderImpl : CsvImpl(), Process<List<Map<String, String>>>,
    Reader {
    private val csv: CsvReader
        get() = csvReader {
            delimiter = separator.value
            skipEmptyLine = true
        }

    override fun process(): List<Map<String, String>>? =
        if (file.isBlank()) null
        else readResource() ?: readFile()

    private fun readFile(): List<Map<String, String>>? {
        val url: URL = systemLoader.getResource("") ?: return null
        val f = File("${url.path}$folder$file")
        if (!f.exists()) return null
        return csv.readAllWithHeader(f)
    }

    private fun readResource(): List<Map<String, String>>? {
        val stream: InputStream =
            systemLoader.getResourceAsStream("$folder$file") ?: return null
        return csv.readAllWithHeader(stream)
    }
}
