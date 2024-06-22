package org.kotools.samples

import org.gradle.api.Project

/**
 * Represents an external plugin that Kotools Samples may depend on.
 *
 * @constructor Creates an external plugin with the specified [name] and
 * [identifier].
 * Throws an [IllegalArgumentException] if the [name] is blank, or if the
 * [identifier] doesn't match the following pattern: `^[a-z]+(?:[-.][a-z]+)*$`.
 *
 * Here's the explanation associated to each symbol used in this pattern:
 * - `^` **Beginning.** Matches the beginning of the string, or the beginning of
 * a line if the multiline flag (**m**) is enabled.
 * - `[]` **Character set.** Match any character in the set.
 * - `a-z` **Range.** Matches a character in the range "a" to "z" (char code 97
 * to 122). Case-sensitive.
 * - `+` **Quantifier.** Match 1 or more of the preceding token.
 * - `(?:)` **Non-capturing group.** Groups multiple tokens together without
 * creating a capture group.
 * - `-` **Character.** Matches a "-" character (char code 45).
 * - `.` **Character.** Matches a "." character (char code 46).
 * - `*` **Quantifier.** Match 0 or more of the preceding token.
 * - `$` **End.** Matches the end of the string, or the end of a line if the
 * multiline flag (**m**) is enabled.
 */
internal class ExternalPlugin internal constructor(
    /** The name of this plugin. */
    internal val name: String,
    private val identifier: String
) {
    init {
        val nameIsNotBlank: Boolean = this.name.isNotBlank()
        require(nameIsNotBlank) { "External plugin's name shouldn't be blank." }
        val regex = Regex("^[a-z]+(?:[-.][a-z]+)*\$")
        val identifierIsValid: Boolean = this.identifier matches regex
        require(identifierIsValid) {
            "'${this.identifier}' external plugin's identifier is invalid."
        }
    }

    /**
     * Returns [Unit] if this plugin was applied to the specified [project], or
     * throws an [IllegalStateException] otherwise.
     */
    internal fun checkIn(project: Project) {
        val projectHasPlugin: Boolean =
            project.pluginManager.hasPlugin(this.identifier)
        check(projectHasPlugin) {
            "${this.name} plugin wasn't applied to '${project.name}' project."
        }
    }

    /** Returns the string representation of this plugin. */
    override fun toString(): String = "${this.name} (${this.identifier})"
}
