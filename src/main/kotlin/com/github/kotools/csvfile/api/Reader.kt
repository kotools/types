package com.github.kotools.csvfile.api

import com.github.kotools.csvfile.core.*

/**
 * Creates a [reader][CsvReader] with the given [configuration] and reads the
 * corresponding [file][CsvReader.file]'s content.
 * If the [file][CsvReader.file] doesn't exist in **resources** folder, it
 * returns `null`.
 */
public suspend fun csvReader(
    configuration: CsvReader.() -> Unit
): List<CsvLine>? = reader.apply(configuration)
    .execute()

/** Scope for reading CSV files. */
public abstract class CsvReader {
    public var debug: Boolean = false
    public var file: String? by fileName()
    public var separator: Separator = comma
}
