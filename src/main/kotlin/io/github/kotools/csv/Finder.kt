package io.github.kotools.csv

import java.io.File
import java.io.InputStream
import java.net.URL

private val loader: ClassLoader get() = ClassLoader.getSystemClassLoader()
private val ClassLoader.baseUrl: URL? get() = getResource("")
private val Manager.path: String get() = "$folder$file"

internal fun Manager.finderOrNull(): FinderResult? =
    findFileOrNull() ?: findStreamOrNull()

private infix fun ClassLoader.getStream(path: String): InputStream? =
    getResourceAsStream(path)

private fun Manager.findFileOrNull(): FinderResult.File? = loader.baseUrl
    ?.let { File("${it.path}$path") }
    ?.takeIf(File::exists)
    ?.let(FinderResult::File)

private fun Manager.findStreamOrNull(): FinderResult.Stream? = loader
    .getStream(path)
    ?.let(FinderResult::Stream)

internal sealed class FinderResult {
    class File(val file: java.io.File) : FinderResult()
    class Stream(val stream: InputStream) : FinderResult()
}
