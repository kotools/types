package io.github.kotools.csv.core

import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import io.github.kotools.csv.api.Reader
import io.github.kotools.csv.api.csvFileNotFoundError
import io.github.kotools.csv.api.invalidPropertyError
import java.io.File

internal fun reader(config: Reader.() -> Unit): List<Map<String, String>>? =
    ReaderImpl().apply(config).run {
        if (file.isBlank()) null
        else readResource() ?: readFile()
    }

internal fun strictReader(config: Reader.() -> Unit):
        List<Map<String, String>> = ReaderImpl().apply(config).run {
    if (file.isBlank()) invalidPropertyError("file")
    else readResource() ?: readFile() ?: csvFileNotFoundError("$folder/$file")
}

private class ReaderImpl : CsvImpl(), Reader {
    private val csv: CsvReader
        get() = csvReader {
            delimiter = separator.value
            skipEmptyLine = true
        }

    fun readFile(): List<Map<String, String>>? = classLoader.getResource("")
        ?.let { File("${it.path}$folder$file") }
        ?.takeIf(File::exists)
        ?.let(csv::readAllWithHeader)

    fun readResource(): List<Map<String, String>>? =
        classLoader.getResourceAsStream("$folder$file")
            ?.let(csv::readAllWithHeader)
}
