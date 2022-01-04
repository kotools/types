package io.github.kotools.csv

import java.io.File
import java.io.InputStream

internal object Finder {
    private val loader: ClassLoader by lazy {
        ClassLoader.getSystemClassLoader()
    }

    // TODO: Test
    infix fun find(path: String): Target =
        findOrNull(path) ?: error("$path doesn't exist")

    // TODO: Test
    infix fun findOrNull(path: String): Target? =
        findFile(path) ?: findStream(path)

    private infix fun findFile(path: String): Target.File? = loader
        .getResource(path)
        ?.let { File(it.path) }
        ?.takeIf(File::exists)
        ?.let(Target::File)

    private infix fun findStream(path: String): Target.Stream? = loader
        .getResourceAsStream(path)
        ?.let(Target::Stream)
}

internal sealed class Target {
    class File(val file: java.io.File) : Target()
    class Stream(val stream: InputStream) : Target()
}
