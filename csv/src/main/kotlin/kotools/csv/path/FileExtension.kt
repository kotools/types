package kotools.csv.path

import kotools.types.string.NotBlankString
import kotools.types.string.notBlankStringOrThrow

internal val csvExtension: FileExtension.Csv = FileExtension.Csv

internal infix fun NotBlankString.suffixWith(
    extension: FileExtension
): NotBlankString = takeIf { it.value.endsWith(extension.toString()) }
    ?: notBlankStringOrThrow("$this$extension")

internal sealed class FileExtension(value: NotBlankString) {
    private val extension: NotBlankString = notBlankStringOrThrow(".$value")

    override fun toString(): String = extension.value
    fun toNotBlankString(): NotBlankString = extension

    object Csv : FileExtension(notBlankStringOrThrow("csv"))
}
