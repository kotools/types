package io.github.kotools.csv.core

import io.github.kotools.csv.api.Reader

internal inline fun reader(config: Reader.() -> Unit):
        List<Map<String, String>>? = ReaderImpl().apply(config).process()

internal class ReaderImpl : BaseReader(), Process<List<Map<String, String>>> {
    override fun process(): List<Map<String, String>>? =
        if (file.isBlank()) null
        else readResource() ?: readFile()
}
