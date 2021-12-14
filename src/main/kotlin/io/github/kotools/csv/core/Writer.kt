package io.github.kotools.csv.core

import com.github.doyaaaaaken.kotlincsv.client.CsvWriter
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import io.github.kotools.csv.api.Writer
import io.github.kotools.csv.api.csvFileNotFoundError
import io.github.kotools.csv.api.invalidConfigError
import io.github.kotools.csv.api.invalidPropertyError
import java.io.File
import java.net.URL
import kotlin.io.path.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.notExists

internal fun strictWriter(config: Writer.() -> Unit): Unit =
    StrictWriterImpl().apply(config).process()

internal fun writer(config: Writer.() -> Unit): Unit? =
    WriterImpl().apply(config).process()

private sealed class BaseWriter : CsvImpl(), Writer, Writer.Rows {
    override var header: Set<String> = emptySet()
    override var overwrite: Boolean = true
    protected val rows: MutableList<List<String>> = mutableListOf()

    protected val csv: CsvWriter
        get() = csvWriter { delimiter = separator.value }
    protected val resourceFile: File?
        get() = systemLoader.getResource("$folder$file")
            ?.let { File(it.path) }
    protected val systemFile: File?
        get() {
            val url: URL = systemLoader.getResource("") ?: return null
            val path = Path(path = "${url.path}$folder")
            if (path.notExists()) {
                path.createDirectory()
                overwrite = true
            }
            return File("$path/$file")
        }
    private val computedRows: List<List<String?>>
        get() = rows.map { List(header.size, it::getOrNull) }

    override fun rows(def: Writer.Rows.() -> Unit): Unit = run(def)

    override fun Iterable<String>.unaryPlus() {
        rows += toList()
    }

    protected infix fun writeIn(file: File): Unit =
        if (overwrite) csv writeHeaderAndRows file
        else csv.writeAll(computedRows, file, !overwrite)

    private infix fun CsvWriter.writeHeaderAndRows(file: File) {
        val r: MutableList<List<String?>> = mutableListOf(header.toList())
        r += computedRows
        csv.writeAll(r, file)
    }
}

private class StrictWriterImpl : BaseWriter(), StrictProcess<Unit> {
    override fun process() = if (file.isBlank()) invalidPropertyError("file")
    else if (header.isEmpty()) invalidPropertyError("header")
    else if (rows.isEmpty()) invalidConfigError("Rows are not defined!")
    else (resourceFile ?: systemFile)?.let { writeIn(it) }
        ?: csvFileNotFoundError("$folder/$file")
}

private class WriterImpl : BaseWriter(), Process<Unit> {
    override fun process(): Unit? =
        if (file.isBlank() || header.isEmpty() || rows.isEmpty()) null
        else (resourceFile ?: systemFile)?.let { writeIn(it) }
}
