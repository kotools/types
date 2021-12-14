package io.github.kotools.csv.core

import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import io.github.kotools.csv.api.Reader
import java.io.File
import java.io.InputStream
import java.net.URL

internal abstract class BaseReader : CsvImpl(), Reader {
    protected val csv: CsvReader
        get() = csvReader {
            delimiter = separator.value
            skipEmptyLine = true
        }

    protected fun readFile(): List<Map<String, String>>? {
        val url: URL = systemLoader.getResource("") ?: return null
        val f = File("${url.path}$folder$file")
        if (!f.exists()) return null
        return csv.readAllWithHeader(f)
    }

    protected fun readResource(): List<Map<String, String>>? {
        val stream: InputStream =
            systemLoader.getResourceAsStream("$folder$file") ?: return null
        return csv.readAllWithHeader(stream)
    }
}
