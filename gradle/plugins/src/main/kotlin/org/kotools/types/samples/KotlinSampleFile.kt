package org.kotools.types.samples

import org.gradle.api.file.Directory

internal class KotlinSampleFile(
    private val name: String,
    private val function: KotlinFunction
) {
    init {
        require(name matches nameRegex) {
            "Kotlin sample file name should match '$nameRegex'."
        }
    }

    // ----------------------- Overrides from kotlin.Any -----------------------

    override fun equals(other: Any?): Boolean =
        other is KotlinSampleFile && this.name == other.name

    override fun hashCode(): Int {
        val prime = 31
        return prime + name.hashCode()
    }

    override fun toString(): String = name

    // -------------------------------------------------------------------------

    fun saveIn(directory: Directory): Unit = directory.file(name)
        .asFile
        .writeText(function.bodyText)

    private companion object {
        private val nameRegex = Regex("^[A-Za-z][A-Za-z.]+[A-Za-z]\\.kt$")
    }
}
