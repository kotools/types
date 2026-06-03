package org.kotools.samples

import kotlinx.ast.common.ast.Ast
import kotlinx.ast.common.ast.AstAttachmentRawAst
import kotlinx.ast.common.ast.AstNode
import kotlinx.ast.common.ast.AstTerminal
import kotlinx.ast.common.flattenTerminal
import kotlinx.ast.common.klass.KlassDeclaration
import kotlinx.ast.common.klass.KlassIdentifier
import kotlinx.ast.common.klass.RawAst
import kotlinx.ast.grammar.kotlin.common.summary.PackageHeader
import org.gradle.api.DefaultTask
import org.gradle.api.file.Directory
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault
import org.jetbrains.kotlin.gradle.internal.ensureParentDirsCreated
import org.kotools.samples.internal.ProgrammingLanguage
import org.kotools.samples.internal.SampleSourceFile
import org.kotools.samples.internal.parseNodes
import java.io.File

/** Gradle task responsible for extracting samples for KDoc. */
@DisableCachingByDefault(because = "Generating files doesn't worth caching.")
public abstract class ExtractSamples : DefaultTask() {
    /** The directory containing sample sources. */
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.NONE)
    public abstract val sourceDirectory: DirectoryProperty

    /** The directory that will contain the extracted KDoc samples. */
    @get:OutputDirectory
    public abstract val outputDirectory: DirectoryProperty

    @TaskAction
    internal fun execute() {
        val destination: Directory = this.outputDirectory.get()
        val sampleSourceFiles: Sequence<SampleSourceFile> = this.sourceDirectory
            .asFileTree
            .asSequence()
            .filterNotNull()
            .mapNotNull { SampleSourceFile.orNull(it) }

        sampleSourceFiles.filter { it.language is ProgrammingLanguage.Java }
            .flatMap(SampleSourceFile::samples)
            .forEach { it.saveFileIn(destination) }

        sampleSourceFiles.filter { it.language is ProgrammingLanguage.Kotlin }
            .map(SampleSourceFile::toFile)
            .flatMap(File::extractSamples)
            .forEach { it.save(destination) }
    }
}

private fun File.extractSamples(): Set<KotlinSample> {
    val nodes: List<Ast> = this.parseNodes()
    val packageIdentifier: String? = nodes.filterIsInstance<PackageHeader>()
        .flatMap(PackageHeader::identifier)
        .joinToString(separator = ".", transform = KlassIdentifier::identifier)
        .ifBlank { null }
    return nodes.filterIsInstance<KlassDeclaration>()
        .flatMap { it.classSamples(packageIdentifier) }
        .toSet()
}

private fun KlassDeclaration.classSamples(
    packageIdentifier: String?
): Set<KotlinSample> {
    if (this.keyword != "class") return emptySet()
    val identifierPrefix: String = packageIdentifier
        ?.let { "${it}.${this.identifier?.identifier}" }
        ?: "${this.identifier?.identifier}"
    return this.expressions.asSequence()
        .filter { it.description == "classBody" }
        .filterIsInstance<AstNode>()
        .flatMap(AstNode::children)
        .filterIsInstance<KlassDeclaration>()
        .mapNotNull { it.toKotlinSampleOrNull(identifierPrefix) }
        .toSet()
}

private fun KlassDeclaration.toKotlinSampleOrNull(
    identifierPrefix: String
): KotlinSample? {
    if (this.keyword != "fun") return null
    val identifier = "${identifierPrefix}.${this.identifier?.identifier}"
    val raw: RawAst = this.attachments.get<RawAst>(AstAttachmentRawAst)
        ?: error("No raw AST available (was: ${this.description}).")
    val text: String = raw.ast.flattenTerminal()
        .joinToString(separator = "", transform = AstTerminal::text)
    val expressionRegex = Regex("""fun [A-Za-z_]+\(\)(?:: [A-Za-z]+)? = .+$""")
    val rawContent: String =
        if (expressionRegex in text) text.substringAfter(" = ")
            .trimIndent()
        else text.substringAfter('{')
            .substringBeforeLast('}')
            .trimIndent()
    val kotlinContent: String = rawContent.ifBlank {
        """TODO("Sample is not yet implemented.")"""
    }
    val content: String = """
        |```kotlin
        |$kotlinContent
        |```
    """.trimMargin()
    return KotlinSample(identifier, content)
}

private data class KotlinSample(val identifier: String, val content: String) {
    override fun equals(other: Any?): Boolean =
        other is KotlinSample && this.identifier == other.identifier

    override fun hashCode(): Int = this.identifier.hashCode()

    override fun toString(): String = "'${this.identifier}' Kotlin sample"
}

private fun KotlinSample.save(destination: Directory) {
    val path: String = this.identifier.replace(oldChar = '.', newChar = '/')
        .plus(".md")
    destination.file(path)
        .asFile
        .also(File::ensureParentDirsCreated)
        .writeText(this.content)
}
