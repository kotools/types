package com.github.kotools.csvfile.api

import com.github.kotools.csvfile.core.Separator
import com.github.kotools.csvfile.core.comma
import com.github.kotools.csvfile.core.file
import com.github.kotools.csvfile.core.folder

@DslMarker
internal annotation class CsvDsl

/** Scope for manipulating CSV files. */
@CsvDsl
public abstract class CsvApi {
    /**
     * **Required** property for targeting a file.
     *
     * The `.csv` extension is optional and will be added automatically in the
     * process.
     * For example, `"my-file.csv"` and `"my-file"` produces the same output.
     */
    public var file: String by file()

    /**
     * **Optional** property for targeting the folder containing the [file] to
     * read.
     *
     * Set to an empty string by default.
     *
     * The `'/'` suffix is optional and will be added automatically in the
     * process.
     * For example, `"my-folder/"` and `"my-folder"` produces the same output.
     */
    public var folder: String by folder()

    /**
     * **Optional** property for setting the [file]'s separator.
     *
     * Set to [comma] by default.
     */
    public var separator: Separator = comma

    protected val loader: ClassLoader get() = ClassLoader.getSystemClassLoader()
}
