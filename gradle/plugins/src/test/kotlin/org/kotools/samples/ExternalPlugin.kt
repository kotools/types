package org.kotools.samples

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ExternalPluginTest {
    @Test
    fun `constructor should pass with valid arguments`() {
        val name = "Example"
        val identifier = "org.example-plugin"
        ExternalPlugin(name, identifier)
    }

    @Test
    fun `constructor should fail with a blank name`() {
        val name = "   "
        val identifier = "org.example-plugin"
        val exception: IllegalArgumentException =
            assertFailsWith { ExternalPlugin(name, identifier) }
        val actual: String? = exception.message
        val expected = "External plugin's name shouldn't be blank."
        assertEquals(expected, actual)
    }

    @Test
    fun `constructor should fail with an invalid identifier`() {
        val name = "Example"
        val identifier = "123 org.-example"
        val exception: IllegalArgumentException =
            assertFailsWith { ExternalPlugin(name, identifier) }
        val actual: String? = exception.message
        val expected = "'$identifier' external plugin's identifier is invalid."
        assertEquals(expected, actual)
    }

    @Test
    fun `checkIn should pass if this plugin was applied to project`() {
        val identifier = "base"
        val plugin = ExternalPlugin(name = "Base", identifier)
        val project: Project = ProjectBuilder.builder()
            .build()
        project.pluginManager.apply(identifier)
        plugin.checkIn(project)
    }

    @Test
    fun `checkIn should fail if this plugin wasn't applied to project`() {
        val plugin = ExternalPlugin(name = "Base", identifier = "base")
        val project: Project = ProjectBuilder.builder()
            .withName("test")
            .build()
        val exception: IllegalStateException =
            assertFailsWith { plugin.checkIn(project) }
        val actual: String? = exception.message
        val expected = "$plugin plugin wasn't applied to $project."
        assertEquals(expected, actual)
    }

    @Test
    fun `toString should pass`() {
        val name = "Base"
        val plugin = ExternalPlugin(name, identifier = "base")
        val actual = "$plugin"
        val expected = "$name plugin"
        assertEquals(expected, actual)
    }
}
