package io.github.kotools.csv.api

/** Scope for manipulating CSV files. */
public interface Csv {
    /**
     * **Required** property for targeting a file.
     *
     * The `.csv` extension is optional and will be added automatically in the
     * process.
     * For example, `"my-file.csv"` and `"my-file"` produces the same output.
     */
    public var file: String

    /**
     * **Optional** property for targeting the folder containing the [file]
     * to read.
     *
     * Set to an empty string by default.
     *
     * The `'/'` suffix is optional and will be added automatically in the
     * process.
     * For example, `"my-folder/"` and `"my-folder"` produces the same output.
     */
    public var folder: String

    /**
     * **Optional** property for setting the [file]'s separator.
     *
     * Set to [comma] by default.
     */
    public var separator: Separator
}
