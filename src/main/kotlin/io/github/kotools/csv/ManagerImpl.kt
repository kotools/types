package io.github.kotools.csv

internal abstract class ManagerImpl : Configurable, Manager {
    override var file: String by csvFile()
    override var folder: String by folder()
    override var separator: Separator = comma

    override fun isValid(): Boolean = file.isNotBlank()
}
