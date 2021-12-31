package io.github.kotools.csv.reader

import io.github.kotools.csv.manager.ManagerConfiguration
import java.io.File
import java.io.InputStream
import java.net.URL

private val ClassLoader.baseUrl: URL? get() = getResource("")

private infix fun ClassLoader.getStream(path: String): InputStream? =
    getResourceAsStream(path)

internal class Finder(private val configuration: ManagerConfiguration) {
    private val loader: ClassLoader by lazy {
        ClassLoader.getSystemClassLoader()
    }

    fun findOrNull(): FinderResult? {
        val path = "${configuration.folder}${configuration.file}"
        return streamOrNullOf(path) ?: fileOrNullOf(path)
    }

    private infix fun fileOrNullOf(path: String): FinderResult.File? = loader
        .baseUrl?.let { File("${it.path}$path") }
        ?.takeIf(File::exists)
        ?.let(FinderResult::File)

    private infix fun streamOrNullOf(path: String): FinderResult.Stream? =
        loader.getStream(path)
            ?.let(FinderResult::Stream)
}

internal sealed class FinderResult {
    class File(val file: java.io.File) : FinderResult()
    class Stream(val stream: InputStream) : FinderResult()
}
