package com.github.kotools.csvfile.core

import com.github.kotools.csvfile.api.Csv
import com.github.kotools.csvfile.api.Separator
import com.github.kotools.csvfile.api.comma

internal abstract class CsvImpl : Csv {
    override var file: String by file()
    override var folder: String by folder()
    override var separator: Separator = comma

    protected val systemLoader: ClassLoader
        get() = ClassLoader.getSystemClassLoader()
}
