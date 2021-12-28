package io.github.kotools.csv

import com.github.doyaaaaaken.kotlincsv.client.CsvWriter
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.File
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.notExists

/**
 * Writes records in a CSV file according to the given [configuration], or
 * throws a [CsvConfigurationException] when the [configuration] is invalid.
 */
@Throws(CsvConfigurationException::class)
public suspend fun csvWriter(configuration: Writer.() -> Unit): Unit =
    withContext(IO) { delegateCsvWriter(configuration) }

/**
 * Writes records in a CSV file **asynchronously** according to the given
 * [configuration], or throws a [CsvConfigurationException] when the
 * [configuration] is invalid.
 */
@Throws(CsvConfigurationException::class)
public infix fun CoroutineScope.csvWriterAsync(
    configuration: Writer.() -> Unit
): Deferred<Unit> = async(IO) { delegateCsvWriter(configuration) }

/**
 * Writes records in a CSV file according to the given [configuration], or
 * returns `null` when the [configuration] is invalid.
 */
public suspend fun csvWriterOrNull(configuration: Writer.() -> Unit): Unit? =
    withContext(IO) { delegateCsvWriterOrNull(configuration) }

/**
 * Writes records in a CSV file **asynchronously** according to the given
 * [configuration], or returns `null` when the [configuration] is invalid.
 */
public infix fun CoroutineScope.csvWriterOrNullAsync(
    configuration: Writer.() -> Unit
): Deferred<Unit?> = async(IO) { delegateCsvWriterOrNull(configuration) }

@Throws(CsvConfigurationException::class)
private inline fun delegateCsvWriter(configuration: Writer.() -> Unit) =
    (WriterImpl create configuration).process()

private inline fun delegateCsvWriterOrNull(configuration: Writer.() -> Unit):
        Unit? = (WriterImpl create configuration).processOrNull()

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

internal class WriterImpl : ManagerImpl(), Processable<Unit>, Writer {
    override var header: Set<String> = emptySet()
    override var overwrite: Boolean = true
    private val csv: CsvWriter get() = csvWriter { delimiter = separator.value }
    private val mutableRecords: MutableList<List<String>> = mutableListOf()
    private val systemFile: File?
        get() = loader.baseUrl?.let { Path("${it.path}$folder") }
            ?.createDirectoryIfNotExists()
            ?.let { File("$it/$file") }

    override fun isValid(): Boolean =
        super.isValid() && header.isNotEmpty() && mutableRecords.isNotEmpty()

    @Throws(CsvConfigurationException::class)
    override fun process() {
        if (isInvalid()) invalidConfigurationException()
        val f: File? = (loader getResourceFile "$folder$file") ?: systemFile
        val records: List<List<String?>> =
            mutableRecords.map { List(header.size, it::getOrNull) }
        f?.let {
            if (overwrite) records writeWithHeaderIn it
            else csv.writeAll(records, it)
        }
    }

    override fun records(configuration: Writer.Records.() -> Unit) {
        mutableRecords += (RecordsImpl create configuration).values
    }

    private infix fun List<List<String?>>.writeWithHeaderIn(file: File) {
        val r: MutableList<List<String?>> = mutableListOf(header.toList())
        r += this
        csv.writeAll(r, file)
    }

    private fun Path.createDirectoryIfNotExists() {
        if (notExists()) {
            createDirectory()
            overwrite = true
        }
    }

    companion object : Factory<WriterImpl>

    class RecordsImpl : Writer.Records {
        internal val values: List<List<String>> get() = records
        private val records: MutableList<List<String>> = mutableListOf()

        override fun Iterable<String>.unaryPlus() {
            records += toList()
        }

        companion object : Factory<RecordsImpl>
    }
}
