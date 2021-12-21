package io.github.kotools.csv.core

import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import io.github.kotools.csv.api.Reader
import io.github.kotools.csv.api.csvFileNotFoundError
import io.github.kotools.csv.api.invalidPropertyError
import java.io.File

private val ReaderImpl.csv: CsvReader
    get() = csvReader {
        delimiter = separator.value
        skipEmptyLine = true
    }

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

private fun ReaderImpl.readFile(): List<Map<String, String>>? =
    classLoader.getResource("")?.let { File("${it.path}$folder$file") }
        ?.takeIf(File::exists)
        ?.let(csv::readAllWithHeader)

private fun ReaderImpl.readResource(): List<Map<String, String>>? =
    classLoader.getResourceAsStream("$folder$file")?.let(csv::readAllWithHeader)

private class ReaderImpl : CsvImpl(), Reader
