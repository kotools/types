package io.github.kotools.csv.reader

import io.github.kotools.csv.manager.ManagerConfiguration
import io.github.kotools.csv.manager.isValid

internal fun (CsvReader.() -> Unit).toReaderConfigurationOrNull():
        ReaderConfiguration? = ReaderConfiguration createOrNull this

internal class ReaderConfiguration
private constructor() : CsvReader, ManagerConfiguration() {
    companion object {
        inline infix fun createOrNull(configuration: CsvReader.() -> Unit):
                ReaderConfiguration? = ReaderConfiguration()
            .apply(configuration)
            .takeIf(ReaderConfiguration::isValid)
    }
}
