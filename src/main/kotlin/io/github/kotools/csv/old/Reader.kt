package io.github.kotools.csv.old

import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import io.github.kotools.csv.loader
import java.io.File

internal fun reader(config: ReaderDsl.() -> Unit): List<Map<String, String>>? =
    Reader().apply(config).run { if (file.isBlank()) null else read() }

internal fun strictReader(config: ReaderDsl.() -> Unit):
        List<Map<String, String>> = Reader().apply(config).run {
    if (file.isBlank()) invalidPropertyError("file")
    read() ?: csvFileNotFoundError("$folder$file")
}

private class Reader : ReaderDsl {
    override var file: String by file()
    override var folder: String by folder()
    override var separator: Separator = comma

    private val csv: CsvReader
        get() = csvReader {
            delimiter = separator.value
            skipEmptyLine = true
        }

    fun read(): List<Map<String, String>>? = readResource() ?: readFile()

    private fun readFile(): List<Map<String, String>>? = loader
        .getResource("")
        ?.let { File("${it.path}$folder$file") }
        ?.takeIf(File::exists)
        ?.let(csv::readAllWithHeader)

    private fun readResource(): List<Map<String, String>>? = loader
        .getResourceAsStream("$folder$file")
        ?.let(csv::readAllWithHeader)
}
