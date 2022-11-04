package io.github.kotools.csv.common

import io.github.kotools.csv.writer.Writer
import kotools.types.string.NotBlankString
import java.io.File
import java.io.InputStream
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.notExists
import kotlin.io.path.toPath

private val loader: ClassLoader by lazy { ClassLoader.getSystemClassLoader() }

internal fun findTarget(path: NotBlankString): Target =
    findTargetOrNull(path) ?: error("$path doesn't exist")

internal fun findTargetOrNull(path: NotBlankString): Target? =
    findFile(path) ?: findStream(path)

internal fun <T : Any> Writer<T>.createTarget(): Target = loader.getResource("")
    ?.toURI()
    ?.let { Path("${it.toPath()}$folder") }
    ?.also { if (it.notExists()) createDirectoryAt(it) }
    ?.let { File("$it/$file") }
    ?.let(Target::File)
    ?: error("Unable to create file at $filePath")

private fun findFile(path: NotBlankString): Target.File? = loader
    .getResource(path.value)
    ?.let { File(it.path) }
    ?.takeIf(File::exists)
    ?.let(Target::File)

private fun findStream(path: NotBlankString): Target.Stream? = loader
    .getResourceAsStream(path.value)
    ?.let(Target::Stream)

private infix fun <T : Any> Writer<T>.createDirectoryAt(path: Path) {
    path.createDirectory()
    overwrite = true
}

internal sealed class Target {
    class File(val file: java.io.File) : Target()
    class Stream(val stream: InputStream) : Target()
}
