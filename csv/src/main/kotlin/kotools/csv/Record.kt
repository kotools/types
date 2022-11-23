package kotools.csv

import kotools.types.collections.NotEmptyMap
import kotools.types.collections.toNotEmptyMapOrThrow
import kotools.types.string.NotBlankString
import kotools.types.string.toNotBlankStringOrNull
import kotools.types.string.toNotBlankStringOrThrow

private fun NotEmptyMap<NotBlankString, NotBlankString?>.toRecord(): Record =
    Record(this)

private fun Map<String, String>.toRecordOrNull(): Record? =
    takeIf(Map<String, String>::hasNotBlankKeys)
        ?.mapKeys { it.key.toNotBlankStringOrThrow() }
        ?.mapValues { it.value.toNotBlankStringOrNull() }
        ?.toNotEmptyMapOrThrow()
        ?.toRecord()

internal inline fun List<Map<String, String>>.toRecordsOrElse(
    action: (Map<String, String>) -> Record
): List<Record> = map { it.toRecordOrNull() ?: action(it) }

private fun Map<String, String>.hasNotBlankKeys(): Boolean =
    all { it.key.isNotBlank() }

@JvmInline
internal value class Record(
    val fields: NotEmptyMap<NotBlankString, NotBlankString?>
)
