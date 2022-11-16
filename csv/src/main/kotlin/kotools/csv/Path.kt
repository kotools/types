package kotools.csv

import kotools.types.string.NotBlankString
import kotools.types.string.toNotBlankString

/** Returns this string as a [CsvPath] suffixed with a `.csv` extension. */
@SinceKotoolsCsv("2.3")
public fun NotBlankString.csv(): CsvPath {
    val extension = ".csv"
    val path: NotBlankString = takeIf { it.value.endsWith(extension) }
        ?: "$this$extension".toNotBlankString()
    return CsvPath(path)
}

/** Representation of a path pointing to a CSV file. */
@JvmInline
@SinceKotoolsCsv("2.3")
public value class CsvPath
internal constructor(internal val path: NotBlankString)
