package io.github.kotools.csv.reader

import io.github.kotools.csv.Manager
import java.io.File
import java.io.InputStream
import java.net.URL

private val ClassLoader.baseUrl: URL? get() = getResource("")
private val Manager.path: String get() = "$folder$file"

private infix fun ClassLoader.getStream(path: String): InputStream? =
    getResourceAsStream(path)

internal class Finder(private val manager: Manager) {
    private val loader: ClassLoader by lazy {
        ClassLoader.getSystemClassLoader()
    }

    fun findOrNull(): FinderResult? = streamOrNullOf() ?: fileOrNullOf()

    private fun fileOrNullOf(): FinderResult.File? = loader.baseUrl
        ?.let { File("${it.path}${manager.path}") }
        ?.takeIf(File::exists)
        ?.let(FinderResult::File)

    private fun streamOrNullOf(): FinderResult.Stream? = loader
        .getStream(manager.path)
        ?.let(FinderResult::Stream)
}

internal sealed class FinderResult {
    class File(val file: java.io.File) : FinderResult()
    class Stream(val stream: InputStream) : FinderResult()
}
