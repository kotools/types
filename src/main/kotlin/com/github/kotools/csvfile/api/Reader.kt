package com.github.kotools.csvfile.api

import com.github.kotools.csvfile.core.*

/**
 * Creates a [reader][ReaderApi] with the given [configuration] and reads the
 * corresponding [file][ReaderApi.file]'s content.
 * If the [file][ReaderApi.file] doesn't exist in **resources** folder, it
 * returns `null`.
 */
public suspend fun csvReader(
    configuration: ReaderApi.() -> Unit
): List<CsvLine>? = reader.apply(configuration)
    .execute()

/** Scope for reading CSV files. */
public abstract class ReaderApi {
    public var debug: Boolean = false
    public var file: String by file()
    public var folder: String by folder()
    public var separator: Separator = comma
}
