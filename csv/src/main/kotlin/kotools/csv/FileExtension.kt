package kotools.csv

import kotools.types.string.NotBlankString
import kotools.types.string.toNotBlankString

internal val csvExtension: FileExtension.Csv = FileExtension.Csv

internal infix fun NotBlankString.suffixWith(
    extension: FileExtension
): NotBlankString = takeIf { it.value.endsWith(extension.toString()) }
    ?: "$this$extension".toNotBlankString()

internal sealed class FileExtension(private val value: NotBlankString) {
    fun toNotBlankString(): NotBlankString = toString()
        .toNotBlankString()

    override fun toString(): String = ".$value"

    object Csv : FileExtension("csv".toNotBlankString())
}
