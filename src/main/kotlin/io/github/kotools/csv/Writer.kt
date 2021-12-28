package io.github.kotools.csv

import com.github.doyaaaaaken.kotlincsv.client.CsvWriter
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.notExists

private val writer: WriterConfiguration get() = WriterConfiguration()
private val Writer.csv: CsvWriter
    get() = csvWriter { delimiter = separator.value }
private val Writer.resourceFile: File?
    get() = loader getResourceFile "$folder$file"

/**
 * Writes records in a CSV file according to the given [configuration], or
 * throws a [CsvException] when the [configuration] is invalid.
 */
@Throws(CsvException::class)
public suspend fun csvWriter(configuration: Writer.() -> Unit): Unit =
    withContext(IO) { processCsvWriter(configuration) }

/**
 * Writes records in a CSV file **asynchronously** according to the given
 * [configuration], or throws a [CsvException] when the [configuration] is
 * invalid.
 */
@Throws(CsvException::class)
public infix fun CoroutineScope.csvWriterAsync(
    configuration: Writer.() -> Unit
): Deferred<Unit> = async(IO) { processCsvWriter(configuration) }

/**
 * Writes records in a CSV file according to the given [configuration], or
 * returns `null` when the [configuration] is invalid.
 */
public suspend fun csvWriterOrNull(configuration: Writer.() -> Unit): Unit? =
    withContext(IO) { processCsvWriterOrNull(configuration) }

/**
 * Writes records in a CSV file **asynchronously** according to the given
 * [configuration], or returns `null` when the [configuration] is invalid.
 */
public infix fun CoroutineScope.csvWriterOrNullAsync(
    configuration: Writer.() -> Unit
): Deferred<Unit?> = async(IO) { processCsvWriterOrNull(configuration) }

private inline fun processCsvWriter(configuration: Writer.() -> Unit): Unit =
    writer.apply(configuration)
        .takeIf(WriterConfiguration::isValid)
        ?.writeInFile()
        ?: invalidConfigurationException()

private inline fun processCsvWriterOrNull(configuration: Writer.() -> Unit):
        Unit? = writer.apply(configuration)
    .takeIf(WriterConfiguration::isValid)
    ?.writeInFileOrNull()

@Throws(IOException::class)
private fun Writer.createDirectoryAt(path: Path) {
    path.createDirectory()
    overwrite = true
}

@Throws(IOException::class)
private fun Writer.getOrCreateSystemFile(): File? = loader.baseUrl
    ?.let { Path("${it.path}$folder") }
    ?.apply { takeIf(Path::notExists)?.let(::createDirectoryAt) }
    ?.let { File("$it/$file") }

/** Configurable object responsible for writing records in a CSV file. */
public sealed interface Writer : Manager {
    /** **Required** property for defining the [file] content's header. */
    public var header: Set<String>

    /**
     * **Optional** flag for overwriting the [file]'s content.
     *
     * Set to `true` by default.
     */
    public var overwrite: Boolean

    /** **Required** function that defines the records to write. */
    public infix fun records(configuration: Records.() -> Unit)

    /** Configurable object responsible for defining the records to write. */
    public sealed interface Records {
        /** Adds the given [Iterable] as a record. */
        public operator fun Iterable<String>.unaryPlus()
    }
}

internal class WriterConfiguration : ManagerConfiguration(), Writer {
    override var header: Set<String> = emptySet()
    override var overwrite: Boolean = true
    private val mutableRecords: MutableList<List<String>> = mutableListOf()
    private val records: List<List<String?>> by lazy {
        mutableRecords.map { List(header.size, it::getOrNull) }
    }

    override fun records(configuration: Writer.Records.() -> Unit) {
        mutableRecords += RecordsConfiguration().apply(configuration).records
    }

    fun isValid(): Boolean =
        file.isNotBlank() && header.isNotEmpty() && records.isNotEmpty()

    @Throws(CsvException::class)
    fun writeInFile(): Unit =
        writeInFileOrNull() ?: fileNotFoundException("$folder$file")

    fun writeInFileOrNull(): Unit? = try {
        (resourceFile ?: getOrCreateSystemFile())?.let {
            if (overwrite) writeWithHeaderIn(it) else writeIn(it)
        }
    } catch (exception: IOException) {
        exception.printStackTrace()
        null
    }

    private infix fun writeIn(file: File): Unit = csv.writeAll(records, file)

    private infix fun writeWithHeaderIn(file: File): Unit =
        mutableListOf(header.toList(), records).let { csv.writeAll(it, file) }

    class RecordsConfiguration : Writer.Records {
        val records: List<List<String>> get() = mutableRecords
        private val mutableRecords: MutableList<List<String>> = mutableListOf()

        override fun Iterable<String>.unaryPlus() {
            mutableRecords += toList()
        }
    }
}
