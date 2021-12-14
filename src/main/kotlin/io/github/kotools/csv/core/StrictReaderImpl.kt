package io.github.kotools.csv.core

import io.github.kotools.csv.api.Reader

internal inline fun strictReader(config: Reader.() -> Unit):
        List<Map<String, String>> = StrictReaderImpl().apply(config).process()

internal class StrictReaderImpl : BaseReader(),
    StrictProcess<List<Map<String, String>>> {
    override fun process(): List<Map<String, String>> =
        if (file.isBlank()) invalidPropertyError("file")
        else readResource() ?: readFile() ?: fileNotFoundError("$folder/$file")
}
