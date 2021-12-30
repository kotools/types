package io.github.kotools.csv

import com.github.doyaaaaaken.kotlincsv.client.CsvWriter
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import java.io.File
import java.io.IOException
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.notExists
import kotlin.reflect.KClass

internal class WriterImpl<T : Any> : ManagerImpl(), Writer<T> {
    private val csv: CsvWriter get() = csvWriter { delimiter = separator.value }
    private val resourceFile: File?
        get() = loader getResourceFile "$folder$file"

    override var overwrite: Boolean = true
    private var recordsHolder: RecordsHolder<T>? = null

    override fun isValid(): Boolean = file.isNotBlank() && recordsHolder != null

    override fun records(configuration: Writer.Records<T>.() -> Unit) {
        recordsHolder = Factory createOrNull configuration
    }

    @Throws(IOException::class)
    private infix fun createDirectoryAt(path: Path) {
        path.createDirectory()
        overwrite = true
    }

    @Throws(IOException::class)
    private fun getOrCreateSystemFile(): File? = loader.baseUrl
        ?.let { Path("${it.path}$folder") }
        ?.apply { takeIf(Path::notExists)?.let(::createDirectoryAt) }
        ?.let { File("$it/$file") }

    @Throws(CsvException::class)
    private infix fun write(dataType: DataType<T>): Unit =
        writeOrNull(dataType) ?: fileNotFoundException("$folder$file")

    private fun writeIn(file: File, records: List<List<String>>): Unit =
        csv.writeAll(records, file, !overwrite)

    private infix fun writeOrNull(dataType: DataType<T>): Unit? = try {
        (resourceFile ?: getOrCreateSystemFile())?.let { f: File ->
            val header: List<String> = dataType.properties.toList()
            recordsHolder?.items?.let {
                val records: List<List<String>> = dataType getValuesOf it
                if (overwrite) writeWithHeaderIn(f, header, records)
                else writeIn(f, records)
            }
        }
    } catch (exception: IOException) {
        exception.printStackTrace()
        null
    }

    private fun writeWithHeaderIn(
        file: File,
        header: List<String>,
        records: List<List<String>>
    ): Unit = mutableListOf(header)
        .apply { records.forEach(::add) }
        .let { csv.writeAll(it, file) }

    companion object {
        @Throws(CsvException::class)
        inline fun <T : Any> process(
            type: KClass<T>,
            configuration: Writer<T>.() -> Unit
        ): Unit = Factory.create<WriterImpl<T>>(configuration)
            .write(type.toDataType())

        inline fun <T : Any> processOrNull(
            type: KClass<T>,
            configuration: Writer<T>.() -> Unit
        ): Unit? = type.toDataTypeOrNull()?.let {
            Factory.createOrNull<WriterImpl<T>>(configuration)
                ?.writeOrNull(it)
        }
    }

    class RecordsHolder<T : Any> : ListHolder<T>(), Writer.Records<T>
}
