package com.github.kotools.csvfile

@DslMarker
internal annotation class CsvDsl

internal const val EXTENSION: String = ".csv"

internal val csv: Csv get() = Csv()

internal fun String.formatFileName(): String =
    takeIf { it.endsWith(EXTENSION) } ?: "$this$EXTENSION"

@CsvDsl
public class Csv
