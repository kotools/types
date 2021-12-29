package io.github.kotools.csv

import com.github.doyaaaaaken.kotlincsv.client.CsvWriter
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.io.File
import java.io.IOException
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.notExists
import kotlin.reflect.KClass

/**
 * Writes records in a CSV file according to the given [configuration], or
 * throws a [CsvException] when the [configuration] is invalid.
 */
@Throws(CsvException::class)
public suspend fun csvWriter(configuration: Writer.() -> Unit): Unit =
    withContext(IO) { processCsvWriter(configuration) }

/**
 * Writes records as a given type [T] in a CSV file according to the given
 * [configuration], or returns `null` when [T] is not an internal data class or
 * when the [configuration] is invalid.
 */
@Suppress("DEPRECATION")
public suspend inline fun <reified T : Any> csvWriterAsOrNull(
    noinline configuration: TypedWriter<T>.() -> Unit
): Unit? = csvWriterAsOrNull(T::class, configuration)

@Deprecated(
    message = "Use the `csvWriterAsOrNull<T> {}` method instead.",
    ReplaceWith("csvWriterAsOrNull<T> {}")
)
public suspend fun <T : Any> csvWriterAsOrNull(
    type: KClass<T>,
    configuration: TypedWriter<T>.() -> Unit
): Unit? = withContext(IO) { processCsvWriterAsOrNull(type, configuration) }

/**
 * Writes records in a CSV file according to the given [configuration], or
 * returns `null` when the [configuration] is invalid.
 */
public suspend fun csvWriterOrNull(configuration: Writer.() -> Unit): Unit? =
    withContext(IO) { processCsvWriterOrNull(configuration) }

/**
 * Writes records as a given type [T] in a CSV file **asynchronously** according
 * to the given [configuration], or returns `null` when [T] is not an internal
 * data class or when the [configuration] is invalid.
 */
@Suppress("DEPRECATION")
public inline infix fun <reified T : Any> CoroutineScope.csvWriterAsOrNullAsync(
    noinline configuration: TypedWriter<T>.() -> Unit
): Deferred<Unit?> = csvWriterAsOrNullAsync(T::class, configuration)

@Deprecated(
    message = "Use the `csvWriterAsOrNullAsync<T> {}` method instead.",
    ReplaceWith("csvWriterAsOrNullAsync<T> {}")
)
public fun <T : Any> CoroutineScope.csvWriterAsOrNullAsync(
    type: KClass<T>,
    configuration: TypedWriter<T>.() -> Unit
): Deferred<Unit?> = async(IO) { processCsvWriterAsOrNull(type, configuration) }

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
 * Writes records in a CSV file **asynchronously** according to the given
 * [configuration], or returns `null` when the [configuration] is invalid.
 */
public infix fun CoroutineScope.csvWriterOrNullAsync(
    configuration: Writer.() -> Unit
): Deferred<Unit?> = async(IO) { processCsvWriterOrNull(configuration) }

private inline fun processCsvWriter(configuration: Writer.() -> Unit): Unit =
    WriterImpl()
        .apply(configuration)
        .takeIf(WriterImpl::isValid)
        ?.writeInFile()
        ?: invalidConfigurationException()

private inline fun <T : Any> processCsvWriterAsOrNull(
    type: KClass<T>,
    configuration: TypedWriter<T>.() -> Unit
): Unit? = type.toDataTypeOrNull()?.let { dataType: DataType<T> ->
    TypedWriterConfiguration<T>()
        .apply(configuration)
        .takeIf(TypedWriterConfiguration<T>::isValid)
        ?.let {
            processCsvWriterOrNull {
                file = it.file
                folder = it.folder
                overwrite = it.overwrite
                separator = it.separator
                header = dataType.properties
                records {
                    (dataType getValuesOf it.records).forEach { +it }
                }
            }
        }
}

private inline fun processCsvWriterOrNull(configuration: Writer.() -> Unit):
        Unit? = WriterImpl()
    .apply(configuration)
    .takeIf(WriterImpl::isValid)
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

/**
 * Configurable object responsible for writing records with a given type [T] in
 * a CSV file.
 */
public sealed interface TypedWriter<in T : Any> : Manager {
    /**
     * **Optional** flag for overwriting the [file]'s content.
     *
     * Set to `true` by default.
     */
    public var overwrite: Boolean

    /** **Required** function that defines the records to write. */
    public infix fun records(configuration: Records<T>.() -> Unit)

    /** Configurable object responsible for defining the records to write. */
    public sealed interface Records<in T : Any> {
        /** Adds the given [Iterable] as a record. */
        public operator fun T.unaryPlus()
    }
}

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

internal class TypedWriterConfiguration<T : Any> : ManagerConfiguration(),
    TypedWriter<T> {
    override var overwrite: Boolean = true
    val records: List<T> get() = mutableRecords
    private val mutableRecords: MutableList<T> = mutableListOf()

    override fun records(configuration: TypedWriter.Records<T>.() -> Unit) {
        mutableRecords += RecordsConfiguration<T>().apply(configuration).records
    }

    fun isValid(): Boolean = file.isNotBlank() && records.isNotEmpty()

    private class RecordsConfiguration<T : Any> : TypedWriter.Records<T> {
        val records: List<T> get() = mutableRecords
        private val mutableRecords: MutableList<T> = mutableListOf()

        override fun T.unaryPlus() {
            mutableRecords += this
        }
    }
}

internal class WriterImpl : ManagerConfiguration(), Writer {
    override var header: Set<String> = emptySet()
    override var overwrite: Boolean = true
    private val csv: CsvWriter get() = csvWriter { delimiter = separator.value }
    private val mutableRecords: MutableList<List<String>> = mutableListOf()
    private val records: List<List<String?>> by lazy {
        mutableRecords.map { List(header.size, it::getOrNull) }
    }
    private val resourceFile: File?
        get() = loader getResourceFile "$folder$file"

    override fun records(configuration: Writer.Records.() -> Unit) {
        mutableRecords += RecordsConfiguration().apply(configuration).records
    }

    fun isValid(): Boolean =
        file.isNotBlank() && header.isNotEmpty() && records.isNotEmpty()

    @Throws(CsvException::class)
    fun writeInFile(): Unit =
        writeInFileOrNull() ?: fileNotFoundException("$folder$file")

    fun writeInFileOrNull(): Unit? = try {
        (resourceFile ?: getOrCreateSystemFile())
            ?.let { if (overwrite) writeWithHeaderIn(it) else writeIn(it) }
    } catch (exception: IOException) {
        exception.printStackTrace()
        null
    }

    private infix fun writeIn(file: File): Unit =
        csv.writeAll(records, file, !overwrite)

    private infix fun writeWithHeaderIn(file: File): Unit =
        mutableListOf<List<String?>>(header.toList())
            .apply { records.forEach(this::add) }
            .let { csv.writeAll(it, file) }

    private class RecordsConfiguration : Writer.Records {
        val records: List<List<String>> get() = mutableRecords
        private val mutableRecords: MutableList<List<String>> = mutableListOf()

        override fun Iterable<String>.unaryPlus() {
            mutableRecords += toList()
        }
    }
}
