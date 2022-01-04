package io.github.kotools.csv.reader

import io.github.kotools.csv.ManagerImpl

internal fun (CsvReader.() -> Unit).toReaderConfigurationOrNull():
        ReaderConfiguration? = ReaderConfiguration createOrNull this

internal class ReaderConfiguration
private constructor() : CsvReader, ManagerImpl() {
    companion object {
        inline infix fun createOrNull(configuration: CsvReader.() -> Unit):
                ReaderConfiguration? = ReaderConfiguration()
            .apply(configuration)
            .takeIf { it.isValid() }
    }
}
