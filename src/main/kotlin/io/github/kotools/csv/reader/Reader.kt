package io.github.kotools.csv.reader

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import io.github.kotools.csv.manager.Separator

internal class Reader(
    private val separator: Separator,
    private val target: FinderResult
) {
    private val csv
        get() = csvReader {
            delimiter = separator.value
            skipEmptyLine = true
        }

    fun read(): List<Map<String, String>> = when (target) {
        is FinderResult.File -> csv.readAllWithHeader(target.file)
        is FinderResult.Stream -> csv.readAllWithHeader(target.stream)
    }
}
