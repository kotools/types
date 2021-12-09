package com.github.kotools.csvfile

@DslMarker
internal annotation class CsvDsl

internal typealias CsvLine = Map<String, String>

internal const val EXTENSION: String = ".csv"

internal val csv: Csv get() = Csv()

internal fun String.formatFileName(): String =
    takeIf { it.endsWith(EXTENSION) } ?: "$this$EXTENSION"

/** Parent scope of all CSV file's related operations. */
@CsvDsl
public class Csv
