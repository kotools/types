package io.github.kotools.csv

import java.io.File
import java.io.InputStream
import java.net.URL

internal val loader: ClassLoader get() = ClassLoader.getSystemClassLoader()
internal val ClassLoader.baseUrlOrNull: URL? get() = getResource("")
internal val ManagerScope.target: FileTarget
    get() = targetOrNull ?: error("$path doesn't exist")
internal val ManagerScope.targetOrNull: FileTarget?
    get() = fileOrNull ?: resourceFileOrNull ?: streamOrNull
private val ManagerScope.fileOrNull: FileTarget.File?
    get() = loader.baseUrlOrNull
        ?.let { File("${it.path}$path") }
        ?.takeIf(File::exists)
        ?.let(FileTarget::File)
private val ManagerScope.path: String get() = "$folder$file"
private val ManagerScope.resourceFileOrNull: FileTarget.File?
    get() = loader.getResourceFileOrNull(path)
        ?.let(FileTarget::File)
private val ManagerScope.streamOrNull: FileTarget.Stream?
    get() = loader.getStreamOrNull(path)
        ?.let(FileTarget::Stream)

private infix fun ClassLoader.getResourceFileOrNull(path: String): File? =
    getResource(path)?.let { File(it.path) }

private infix fun ClassLoader.getStreamOrNull(path: String): InputStream? =
    getResourceAsStream(path)

internal sealed class FileTarget {
    class File(val file: java.io.File) : FileTarget()
    class Stream(val stream: InputStream) : FileTarget()
}
