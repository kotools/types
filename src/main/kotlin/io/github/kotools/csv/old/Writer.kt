package io.github.kotools.csv.old

import com.github.doyaaaaaken.kotlincsv.client.CsvWriter
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import io.github.kotools.csv.loader
import java.io.File
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.notExists

internal fun strictWriter(config: WriterDsl.() -> Unit): Unit =
    Writer().apply(config).run {
        if (file.isBlank()) invalidPropertyError("file")
        if (header.isEmpty()) invalidPropertyError("header")
        if (rows.isEmpty()) invalidConfigError("Rows are not defined!")
        write() ?: csvFileNotFoundError("$folder/$file")
    }

internal fun writer(config: WriterDsl.() -> Unit): Unit? =
    Writer().apply(config).run {
        if (file.isBlank() || header.isEmpty() || rows.isEmpty()) null
        else write()
    }

private class Writer : RowsDsl, WriterDsl {
    override var file: String by file()
    override var folder: String by folder()
    override var header: Set<String> = emptySet()
    override var overwrite: Boolean = true
    override var separator: Separator = comma

    val rows: MutableList<List<String>> = mutableListOf()
    private val csv: CsvWriter get() = csvWriter { delimiter = separator.value }
    private val computedRows: List<List<String?>>
        get() = rows.map { List(header.size, it::getOrNull) }
    private val resourceFile: File?
        get() = loader.getResource("$folder$file")?.let { File(it.path) }
    private val systemFile: File?
        get() = loader.getResource("")
            ?.let { Path("${it.path}$folder") }
            ?.apply { createFolderIfNotExists() }
            ?.let { File("$it/$file") }

    override fun rows(config: RowsDsl.() -> Unit): Unit = run(config)

    override fun Iterable<String>.unaryPlus() {
        rows += toList()
    }

    fun write(): Unit? = (resourceFile ?: systemFile)?.let {
        if (overwrite) writeHeaderAndRows(it)
        else csv.writeAll(computedRows, it, !overwrite)
    }

    private infix fun writeHeaderAndRows(file: File) {
        val r: MutableList<List<String?>> = mutableListOf(header.toList())
        r += computedRows
        csv.writeAll(r, file)
    }

    private fun Path.createFolderIfNotExists() {
        if (notExists()) {
            createDirectory()
            overwrite = true
        }
    }
}
