package io.github.kotools.csv.core

import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import io.github.kotools.csv.api.Reader
import io.github.kotools.csv.api.csvFileNotFoundError
import io.github.kotools.csv.api.invalidPropertyError
import java.io.File
import java.io.InputStream
import java.net.URL

internal inline fun reader(config: Reader.() -> Unit):
        List<Map<String, String>>? = ReaderImpl().apply(config).process()

internal inline fun strictReader(config: Reader.() -> Unit):
        List<Map<String, String>> = StrictReaderImpl().apply(config).process()

internal sealed class BaseReader : CsvImpl(), Reader {
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

internal class ReaderImpl : BaseReader(), Process<List<Map<String, String>>> {
    override fun process(): List<Map<String, String>>? =
        if (file.isBlank()) null
        else readResource() ?: readFile()
}

internal class StrictReaderImpl : BaseReader(),
    StrictProcess<List<Map<String, String>>> {
    override fun process(): List<Map<String, String>> =
        if (file.isBlank()) invalidPropertyError("file")
        else readResource() ?: readFile()
        ?: csvFileNotFoundError("$folder/$file")
}
