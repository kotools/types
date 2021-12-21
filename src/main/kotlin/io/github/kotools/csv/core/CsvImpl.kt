package io.github.kotools.csv.core

import io.github.kotools.csv.api.Csv
import io.github.kotools.csv.api.Separator
import io.github.kotools.csv.api.comma

internal abstract class CsvImpl : Csv {
    override var file: String by file()
    override var folder: String by folder()
    override var separator: Separator = comma
}
