package com.github.kotools.csv.core

import com.github.kotools.csv.api.Csv
import com.github.kotools.csv.api.Separator
import com.github.kotools.csv.api.comma

internal abstract class CsvImpl : Csv {
    override var file: String by file()
    override var folder: String by folder()
    override var separator: Separator = comma

    protected val systemLoader: ClassLoader
        get() = ClassLoader.getSystemClassLoader()
}
