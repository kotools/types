package kotools.csv

import kotools.types.string.NotBlankString
import kotools.types.string.toNotBlankString

internal const val CSV_EXTENSION: String = ".csv"

/**
 * Returns this string as a [CSV path][CsvPathResult.Success] suffixed with the
 * `.csv` extension, or returns a [CsvPathResult.Exception.CsvExtensionAsPath]
 * if this string equals the `.csv` extension.
 */
@SinceKotoolsCsv("2.3")
public fun NotBlankString.csv(): CsvPathResult.FromNotBlankString =
    if (value == CSV_EXTENSION) CsvPathResult.Exception.CsvExtensionAsPath
    else {
        val path: NotBlankString = takeIf { it.value.endsWith(CSV_EXTENSION) }
            ?: "$this$CSV_EXTENSION".toNotBlankString()
        CsvPathResult.Success(path)
    }

/** Object returned when trying to build a [CSV path][CsvPathResult.Success]. */
@SinceKotoolsCsv("2.3")
public sealed interface CsvPathResult {
    /**
     * Object returned when trying to build a [CSV path][Success] from a
     * [NotBlankString].
     */
    public sealed interface FromNotBlankString : CsvPathResult

    /**
     * Object returned when trying to build a [CSV path][Success] from a
     * [String].
     */
    public sealed interface FromString : CsvPathResult

    /** Representation of a path pointing to a CSV file. */
    @JvmInline
    public value class Success
    internal constructor(internal val path: NotBlankString) :
        FromNotBlankString,
        FromString

    /** Object returned when building a [CSV path][Success] fails. */
    public sealed class Exception(reason: String) :
        IllegalArgumentException("CSV path shouldn't $reason."),
        CsvPathResult {
        /**
         * Exception returned when trying to build a [CSV path][Success] from a
         * blank string.
         */
        public object BlankString : Exception("be blank"),
            FromString

        /**
         * Exception returned when trying to build a [CSV path][Success] from a
         * string that equals the `.csv` extension.
         */
        public object CsvExtensionAsPath :
            Exception("equal the \"$CSV_EXTENSION\" extension"),
            FromNotBlankString,
            FromString
    }
}
