package io.github.kotools.csv.core

import com.github.doyaaaaaken.kotlincsv.client.CsvWriter
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import io.github.kotools.csv.api.Writer
import java.io.File
import java.net.URL
import kotlin.io.path.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.notExists

internal inline fun writer(config: Writer.() -> Unit): Unit? =
    WriterImpl().apply(config).process()

internal class WriterImpl : CsvImpl(), Process<Unit>, Writer, Writer.Rows {
    override var header: Set<String> = emptySet()
    override var overwrite: Boolean = true
    private val rows: MutableList<List<String>> = mutableListOf()

    private val computedRows: List<List<String?>>
        get() = rows.map { List(header.size, it::getOrNull) }
    private val csv: CsvWriter get() = csvWriter { delimiter = separator.value }
    private val resourceFile: File?
        get() = systemLoader.getResource("$folder$file")
            ?.let { File(it.path) }
    private val systemFile: File?
        get() {
            val url: URL = systemLoader.getResource("") ?: return null
            val path = Path(path = "${url.path}$folder")
            if (path.notExists()) {
                path.createDirectory()
                overwrite = true
            }
            return File("$path/$file")
        }

    override fun process(): Unit? {
        if (file.isBlank() || rows.isEmpty()) return null
        val f: File = resourceFile ?: systemFile ?: return null
        return if (overwrite) csv writeHeaderAndRows f
        else csv.writeAll(computedRows, f, !overwrite)
    }

    override fun rows(def: Writer.Rows.() -> Unit): Unit = run(def)

    override fun Iterable<String>.unaryPlus() {
        rows += toList()
    }

    private infix fun CsvWriter.writeHeaderAndRows(file: File) {
        val r: MutableList<List<String?>> = mutableListOf(header.toList())
        r += computedRows
        csv.writeAll(r, file)
    }
}
