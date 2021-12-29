package io.github.kotools.csv

import com.github.doyaaaaaken.kotlincsv.client.CsvWriter
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import java.io.File
import java.io.IOException
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.notExists

internal class WriterImpl : Configurable, ManagerConfiguration(), Writer {
    private val csv: CsvWriter get() = csvWriter { delimiter = separator.value }
    private val records: List<List<String?>>? by lazy {
        recordsHolder?.items?.map { List(header.size, it::getOrNull) }
    }
    private val resourceFile: File?
        get() = loader getResourceFile "$folder$file"

    override var header: Set<String> = emptySet()
    override var overwrite: Boolean = true
    private var recordsHolder: RecordsHolder? = null

    override fun isValid(): Boolean =
        file.isNotBlank() && header.isNotEmpty() && recordsHolder != null

    override fun records(configuration: Writer.Records.() -> Unit) {
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

    private infix fun writeIn(file: File): Unit? =
        records?.let { csv.writeAll(it, file, !overwrite) }

    @Throws(CsvException::class)
    private fun write(): Unit =
        writeOrNull() ?: fileNotFoundException("$folder$file")

    private fun writeOrNull(): Unit? = try {
        (resourceFile ?: getOrCreateSystemFile())
            ?.let { if (overwrite) writeWithHeaderIn(it) else writeIn(it) }
    } catch (exception: IOException) {
        exception.printStackTrace()
        null
    }

    private infix fun writeWithHeaderIn(file: File): Unit =
        mutableListOf<List<String?>>(header.toList())
            .apply { records?.forEach(this::add) }
            .let { csv.writeAll(it, file) }

    companion object {
        @Throws(CsvException::class)
        inline infix fun process(configuration: Writer.() -> Unit): Unit =
            Factory.create<WriterImpl>(configuration)
                .write()

        inline infix fun processOrNull(configuration: Writer.() -> Unit):
                Unit? = Factory.createOrNull<WriterImpl>(configuration)
            ?.writeOrNull()
    }

    class RecordsHolder : ListHolder<List<String>>(), Writer.Records {
        override fun equals(other: Any?): Boolean = this === other

        override fun hashCode(): Int = System.identityHashCode(this)

        override fun Iterable<String>.unaryPlus() {
            mutableList += toList()
        }
    }
}