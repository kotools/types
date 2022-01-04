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
import kotlin.reflect.KClass

private val Manager.csv: CsvWriter
    get() = csvWriter { delimiter = separator.value }

private val <T : Any> Writer<T>.newTargetOrNull: FileTarget?
    get() = loader.baseUrlOrNull
        ?.let { Path("${it.path}$folder") }
        ?.also { if (it.notExists()) createDirectoryAt(it) }
        ?.let { File("$it/$file") }
        ?.let(FileTarget::File)

// TODO: Test and document
public suspend inline fun <reified T : Any> csvWriterOrNull(
    noinline configuration: Writer<T>.() -> Unit
): Unit? = csvWriterOrNull(T::class, configuration)

// TODO: Document
public suspend fun <T : Any> csvWriterOrNull(
    type: KClass<T>,
    configuration: Writer<T>.() -> Unit
): Unit? = withContext(IO) {
    val dataType: DataType<T> =
        type.toDataTypeOrNull() ?: return@withContext null
    val writer: WriterImplementation<T> = WriterImplementation<T>()
        .apply(configuration)
        .takeIf(WriterImplementation<T>::isValid)
        ?: return@withContext null
    val file: File = writer.run { targetOrNull ?: newTargetOrNull }
        ?.let { if (it is FileTarget.File) it.file else null }
        ?: return@withContext null
    val records: MutableList<List<String>> =
        if (writer.overwrite) mutableListOf(dataType.properties)
        else mutableListOf()
    records += writer.records.map(dataType::getValuesOf)
    writer.csv.writeAll(records, file, !writer.overwrite)
}

// TODO: Document
public inline infix fun <reified T : Any> CoroutineScope.csvWriterOrNullAsync(
    noinline configuration: Writer<T>.() -> Unit
): Deferred<Unit?> = async { csvWriterOrNull(configuration) }

private infix fun <T : Any> Writer<T>.createDirectoryAt(path: Path) {
    path.createDirectory()
    overwrite = true
}

// TODO: Document
public sealed interface Records<in T : Any> {
    // TODO: Document
    public operator fun T.unaryPlus()
}

// TODO: Document
public sealed interface Writer<in T : Any> : Manager {
    // TODO: Document
    public var overwrite: Boolean

    // TODO: Document
    public infix fun records(configuration: Records<T>.() -> Unit)
}

private class RecordsImplementation<T : Any> : Records<T> {
    val items: List<T> get() = mutableRecords
    private val mutableRecords: MutableList<T> = mutableListOf()

    fun isValid(): Boolean = items.isNotEmpty()

    override fun T.unaryPlus() {
        mutableRecords += this
    }
}

private class WriterImplementation<T : Any> : ManagerImplementation(),
    Writer<T> {
    val records: List<T> get() = recordsHolder!!.items

    override var overwrite: Boolean = true
    private var recordsHolder: RecordsImplementation<T>? = null

    fun isValid(): Boolean = file.isNotBlank() && recordsHolder != null

    override fun records(configuration: Records<T>.() -> Unit) {
        recordsHolder = RecordsImplementation<T>()
            .apply(configuration)
            .takeIf(RecordsImplementation<T>::isValid)
    }
}
