package kotools.csv

import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotools.types.collections.NotEmptyList
import kotools.types.collections.toNotEmptyListOrNull
import kotools.types.string.NotBlankString
import kotools.types.string.notBlankStringOrThrow
import java.io.File
import java.net.URL

// ---------- Reading operations ----------

/**
 * Returns the [records][Record] in the CSV file matching this path, or returns
 * a [CsvFileNotFoundException] if no file matches this path, or returns a
 * [CsvFileHeaderWithBlankFieldException] if the existing file has a header with
 * a blank field, or returns a [CsvFileWithoutRecordException] if the existing
 * file doesn't contain a record.
 */
internal suspend fun CsvPathResult.Success.read(): CsvReaderResult =
    withContext(CoroutineName("CsvReader") + Dispatchers.IO) { path.read() }

private fun NotBlankString.read(): CsvReaderResult {
    val url: URL = ClassLoader.getSystemResource(value)
        ?: return fileNotFoundException
    val file = File(url.path)
    val csv: CsvReader = csvReader {
        delimiter = ','
        skipEmptyLine = true
    }
    val records: NotEmptyList<Record> = csv.readAllWithHeader(file)
        .toRecordsOrElse { return fileHeaderWithBlankFieldException }
        .toNotEmptyListOrNull()
        ?: return fileWithoutRecordException
    return CsvReaderResult.Success(records)
}

// ---------- Result ----------

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
        CsvReaderResult
}

// ---------- Exceptions ----------

private val NotBlankString.fileHeaderWithBlankFieldException:
        CsvFileHeaderWithBlankFieldException
    get() = CsvFileHeaderWithBlankFieldException(this)

internal class CsvFileHeaderWithBlankFieldException(path: NotBlankString) :
    CsvReaderResult.Exception(
        notBlankStringOrThrow("\"$path\" has a header with a blank field")
    )

private val NotBlankString.fileNotFoundException: CsvFileNotFoundException
    get() = CsvFileNotFoundException(this)

internal class CsvFileNotFoundException(path: NotBlankString) :
    CsvReaderResult.Exception(
        notBlankStringOrThrow("\"$path\" doesn't exist")
    )

private val NotBlankString.fileWithoutRecordException:
        CsvFileWithoutRecordException
    get() = CsvFileWithoutRecordException(this)

internal class CsvFileWithoutRecordException(path: NotBlankString) :
    CsvReaderResult.Exception(
        notBlankStringOrThrow("\"$path\" doesn't contain a record")
    )
