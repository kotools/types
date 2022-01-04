package io.github.kotools.csv

internal fun ManagerScope.isValid(): Boolean = file.isNotBlank()

/** Scope for manipulating CSV files. */
public sealed interface ManagerScope {
    /**
     * **Required** property for targeting a file.
     *
     * The `.csv` extension is optional and will be added automatically in the
     * process if not present.
     * For example, `"my-file.csv"` and `"my-file"` produces the same output.
     */
    public var file: String

    /**
     * **Optional** property for targeting a folder containing the [file].
     *
     * Set to an empty string by default.
     *
     * The `'/'` suffix is optional and will be added automatically in the
     * process if not present.
     * For example, `"my-folder/"` and `"my-folder"` produces the same output.
     */
    public var folder: String

    /**
     * **Optional** property for setting the [file] content's separator.
     *
     * Set to [Separator.Comma] by default.
     */
    public var separator: Separator
}

internal sealed class Manager : ManagerScope {
    override var file: String by SuffixedString.csvFile()
    override var folder: String by SuffixedString.folder()
    override var separator: Separator = Separator.Comma
}
