package kotools.csv.path

import kotools.shared.Project.Csv
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel
import kotools.types.string.NotBlankString

/**
 * Returns this string as a [CsvPath] suffixed with the `.csv` extension, or
 * returns a [CsvExtensionAsPathException] if this string equals the `.csv`
 * extension.
 */
@SinceKotools(Csv, "2.3", StabilityLevel.Alpha)
public val NotBlankString.csv: Result<CsvPath>
    get() = takeIf { value != csvExtension.toString() }
        ?.suffix(csvExtension)
        ?.toCsvPath()
        ?.toSuccessfulResult()
        ?: Result.failure(CsvExtensionAsPathException)

private fun NotBlankString.toCsvPath(): CsvPath = CsvPath(this)

private fun CsvPath.toSuccessfulResult(): Result<CsvPath> =
    Result.success(this)

/** Representation of a path pointing to a CSV file. */
@JvmInline
@SinceKotools(Csv, "2.3", StabilityLevel.Alpha)
public value class CsvPath
internal constructor(private val path: NotBlankString) {
    override fun toString(): String = path.value
}

/**
 * Exception returned when trying to build a [CsvPath] from a string that equals
 * the `.csv` extension.
 */
@SinceKotools(Csv, "2.3", StabilityLevel.Alpha)
public object CsvExtensionAsPathException : IllegalArgumentException(
    "The path shouldn't equal the \"$csvExtension\" extension."
)
