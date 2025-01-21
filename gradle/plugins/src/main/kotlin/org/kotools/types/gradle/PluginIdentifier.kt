package org.kotools.types.gradle

internal enum class PluginIdentifier(private val value: String) {
    KotlinMultiplatform("org.jetbrains.kotlin.multiplatform");

    init {
        val regex = Regex("^[a-z]([a-z.-]+)*[a-z]\$")
        require(this.value matches regex) {
            val message = "'${this.value}' is an invalid plugin's identifier."
            val reason = "It should match the following pattern: ${regex}."
            "$message\n$reason"
        }
    }

    override fun toString(): String = this.value
}
