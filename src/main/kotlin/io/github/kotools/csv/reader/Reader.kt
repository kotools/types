package io.github.kotools.csv.reader

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader

internal class Reader(
    private val configuration: ReaderConfiguration,
    private val target: FinderResult
) {
    private val csv
        get() = csvReader {
            delimiter = configuration.separator.value
            skipEmptyLine = true
        }

    fun read(): List<Map<String, String>> = when (target) {
        is FinderResult.File -> csv.readAllWithHeader(target.file)
        is FinderResult.Stream -> csv.readAllWithHeader(target.stream)
    }
}
