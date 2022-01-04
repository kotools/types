package io.github.kotools.csv.common

import java.io.File
import java.io.InputStream

private val loader: ClassLoader by lazy { ClassLoader.getSystemClassLoader() }

internal fun findTarget(path: String): Target =
    findTargetOrNull(path) ?: error("$path doesn't exist")

internal fun findTargetOrNull(path: String): Target? =
    findFile(path) ?: findStream(path)

private fun findFile(path: String): Target.File? = loader.getResource(path)
    ?.let { File(it.path) }
    ?.takeIf(File::exists)
    ?.let(Target::File)

private fun findStream(path: String): Target.Stream? = loader
    .getResourceAsStream(path)
    ?.let(Target::Stream)

internal sealed class Target {
    class File(val file: java.io.File) : Target()
    class Stream(val stream: InputStream) : Target()
}
