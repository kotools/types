package kotools.csv

import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotools.types.collections.NotEmptyList
import kotools.types.collections.NotEmptyMap
import kotools.types.collections.toNotEmptyListOrNull
import kotools.types.collections.toNotEmptyMapOrThrow
import kotools.types.string.NotBlankString
import kotools.types.string.notBlankStringOrThrow
import kotools.types.string.toNotBlankStringOrNull
import java.io.File
import java.net.URL

// ---------- CSV reader ----------

/**
 * Returns the [records][Record] in the CSV file matching this path, or returns
 * a [CsvReaderResult.Exception.FileNotFound] if no file matches this path, or
 * returns a [CsvReaderResult.Exception.FileHeaderWithBlankField] if the
 * existing file has a header with a blank field, or returns a
 * [CsvReaderResult.Exception.FileWithoutRecord] if the existing file doesn't
 * contain a record.
 */
internal suspend fun CsvPathResult.Success.read(): CsvReaderResult =
    withContext(CoroutineName("CsvReader") + Dispatchers.IO) {
        val url: URL = ClassLoader.getSystemResource(path.value)
            ?: return@withContext csvFileNotFoundException(path)
        val file = File(url.path)
        val csv: CsvReader = csvReader {
            delimiter = ','
            skipEmptyLine = true
        }
        val records: NotEmptyList<Record> = csv.readAllWithHeader(file)
            .map { record: Map<String, String> ->
                val fields: NotEmptyMap<NotBlankString, NotBlankString?> =
                    record.mapKeys {
                        it.key.toNotBlankStringOrNull()
                            ?: return@withContext csvFileHeaderWithBlankField(
                                path
                            )
                    }
                        .mapValues { it.value.toNotBlankStringOrNull() }
                        .toNotEmptyMapOrThrow()
                Record(fields)
            }
            .toNotEmptyListOrNull()
            ?: return@withContext csvFileWithoutRecord(path)
        CsvReaderResult.Success(records)
    }

// ---------- CsvReaderResult ----------

private fun csvFileNotFoundException(
    path: NotBlankString
): CsvReaderResult.Exception.FileNotFound =
    CsvReaderResult.Exception.FileNotFound(path)

private fun csvFileHeaderWithBlankField(
    path: NotBlankString
): CsvReaderResult.Exception.FileHeaderWithBlankField =
    CsvReaderResult.Exception.FileHeaderWithBlankField(path)

private fun csvFileWithoutRecord(
    path: NotBlankString
): CsvReaderResult.Exception.FileWithoutRecord =
    CsvReaderResult.Exception.FileWithoutRecord(path)

internal inline fun CsvReaderResult.onException(
    action: (CsvReaderResult.Exception) -> CsvReaderResult.Success
): CsvReaderResult.Success = when (this) {
    is CsvReaderResult.Success -> this
    is CsvReaderResult.Exception -> action(this)
}

internal sealed interface CsvReaderResult {
    @JvmInline
    value class Success(val records: NotEmptyList<Record>) : CsvReaderResult

    sealed class Exception(reason: NotBlankString) :
        IllegalStateException("The file at ${reason.value}."),
        CsvReaderResult {
        class FileNotFound(path: NotBlankString) : Exception(
            notBlankStringOrThrow("\"$path\" doesn't exist")
        )

        class FileHeaderWithBlankField(path: NotBlankString) : Exception(
            notBlankStringOrThrow("\"$path\" has a header with a blank field")
        )

        class FileWithoutRecord(path: NotBlankString) : Exception(
            notBlankStringOrThrow("\"$path\" doesn't contain a record")
        )
    }
}

// ---------- Record ----------

@JvmInline
internal value class Record(
    val fields: NotEmptyMap<NotBlankString, NotBlankString?>
)
