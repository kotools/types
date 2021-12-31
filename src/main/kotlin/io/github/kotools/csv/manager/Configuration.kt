package io.github.kotools.csv.manager

internal abstract class ManagerConfiguration : CsvManager {
    override var file: String by SuffixedString.csvFile()
    override var folder: String by SuffixedString.folder()
    override var separator: Separator = Separator.Comma
}
