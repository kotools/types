package io.github.kotools.csv

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream
import java.net.URL

/**
 * Returns the file's records according to the given [configuration], or returns
 * `null` when the [configuration] is invalid or when the targeted file
 * doesn't exist.
 *
 * See [csvReaderOrNullAsync] for an async/await implementation style.
 */
public suspend fun csvReaderOrNull(configuration: Reader.() -> Unit):
        List<Map<String, String>>? =
    withContext(IO) { delegateCsvReaderOrNull(configuration) }

/**
 * Returns the file's records **asynchronously** according to the given
 * [configuration], or returns `null` when the [configuration] is invalid or
 * when the targeted file doesn't exist.
 *
 * See [csvReaderOrNull] for a suspending implementation style.
 */
public infix fun CoroutineScope.csvReaderOrNullAsync(
    configuration: Reader.() -> Unit
): Deferred<List<Map<String, String>>?> =
    async(IO) { delegateCsvReaderOrNull(configuration) }

private inline fun delegateCsvReaderOrNull(configuration: Reader.() -> Unit):
        List<Map<String, String>>? {
    val reader = Reader()
    reader.configuration()
    return if (reader.file.isBlank()) null else reader()
}

/** Configurable object responsible for reading a CSV file. */
public class Reader internal constructor() {
    private val csv
        get() = csvReader {
            delimiter = separator.value
            skipEmptyLine = true
        }

    /**
     * **Required** property for targeting a file.
     *
     * The `.csv` extension is optional and will be added automatically in the
     * process if not present.
     * For example, `"my-file.csv"` and `"my-file"` produces the same output.
     */
    public var file: String by csvFile()

    /**
     * **Optional** property for targeting a folder containing the [file].
     *
     * Set to an empty string by default.
     *
     * The `'/'` suffix is optional and will be added automatically in the
     * process if not present.
     * For example, `"my-folder/"` and `"my-folder"` produces the same output.
     */
    public var folder: String by folder()

    /**
     * **Optional** property for setting the [file] content's separator.
     *
     * Set to [comma] by default.
     */
    public var separator: Separator = comma

    internal operator fun invoke(): List<Map<String, String>>? =
        readResource() ?: readSystemFile()

    private fun readResource(): List<Map<String, String>>? {
        val s: InputStream = (loader getStream "$folder$file") ?: return null
        return csv.readAllWithHeader(s)
    }

    private fun readSystemFile(): List<Map<String, String>>? {
        val url: URL = loader.baseUrl ?: return null
        val f = File("${url.path}$folder$file")
        if (!f.exists()) return null
        return csv.readAllWithHeader(f)
    }
}
