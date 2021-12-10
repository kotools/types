package com.github.kotools.csvfile.core

import com.github.doyaaaaaken.kotlincsv.client.CsvWriter
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import com.github.kotools.csvfile.api.WriterApi
import com.github.kotools.csvfile.api.WriterRowsApi
import java.io.File
import java.net.URL
import kotlin.io.path.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.notExists

internal val writer: Writer get() = Writer()

internal class Writer : WriterApi() {
    private val csv: CsvWriter get() = csvWriter()
    private val WriterRowsApi.computedRows: List<List<String>>
        get() = rows.map { row: List<String> ->
            List(this@Writer.header.size) {
                row.getOrElse(it) { "" }
            }
        }

    inline infix operator fun invoke(config: WriterApi.() -> Unit): Unit? =
        apply(config).execute()

    private fun execute(): Unit? {
        if (file.isBlank() || rowsApi == null) return null
        val url: URL = loader.getResource("") ?: return null
        val path = Path(path = "${url.path}$folder")
        val f = File("$path/$file")
        return if (path.notExists()) {
            path.createDirectory()
            csv writeAllWithHeader f
        } else if (overwrite) csv writeAllWithHeader f
        else csv.writeAll(rowsApi!!.computedRows, f, !overwrite)
    }

    private infix fun CsvWriter.writeAllWithHeader(file: File) {
        val r: MutableList<List<String>> = mutableListOf(header.toList())
        r.addAll(rowsApi!!.computedRows)
        csv.writeAll(r, file)
    }
}
