package com.github.kotools.csvfile.core

import com.github.doyaaaaaken.kotlincsv.client.CsvWriter
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import com.github.kotools.csvfile.api.WriterApi
import java.io.File
import java.net.URL
import kotlin.io.path.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.notExists

internal val writer: Writer get() = Writer()

internal class Writer : WriterApi() {
    private val computedRows: List<List<String>>?
        get() = rowsApi?.rows?.map { row: List<String> ->
            List(this@Writer.header.size) {
                row.getOrElse(it) { "" }
            }
        }
    private val csv: CsvWriter get() = csvWriter()
    private val resourceFile: File?
        get() = loader.getResource("$folder$file")
            ?.let { File(it.path) }
    private val systemFile: File?
        get() {
            val url: URL = loader.getResource("") ?: return null
            val path = Path(path = "${url.path}$folder")
            if (path.notExists()) {
                path.createDirectory()
                overwrite = true
            }
            return File("$path/$file")
        }

    inline infix operator fun invoke(config: WriterApi.() -> Unit): Unit? =
        apply(config).execute()

    private fun execute(): Unit? {
        if (file.isBlank() || rowsApi == null) return null
        val f: File = resourceFile ?: systemFile ?: return null
        return if (overwrite) csv writeHeaderAndRows f
        else csv.writeAll(computedRows!!, f, !overwrite)
    }

    private infix fun CsvWriter.writeHeaderAndRows(file: File) {
        val r: MutableList<List<String>> = mutableListOf(header.toList())
        r.addAll(computedRows!!)
        csv.writeAll(r, file)
    }
}
