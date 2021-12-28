package io.github.kotools.csv

import com.github.doyaaaaaken.kotlincsv.client.CsvWriter
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import java.io.File
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.notExists

@Throws(CsvConfigurationException::class)
internal inline fun delegateCsvWriter(configuration: Writer.() -> Unit): Unit =
    (WriterImpl create configuration).process()

internal inline fun delegateCsvWriterOrNull(configuration: Writer.() -> Unit):
        Unit? = (WriterImpl create configuration).processOrNull()

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
