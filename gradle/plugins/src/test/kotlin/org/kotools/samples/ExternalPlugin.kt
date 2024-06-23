package org.kotools.samples

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals

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
    fun `structural equality should pass with another ExternalPlugin having the same identifier`() {
        val name = "Base"
        val identifier = "base"
        val first = ExternalPlugin(name, identifier)
        val second = ExternalPlugin(name, identifier)
        assertEquals(first, second)
        val firstHashCode: Int = first.hashCode()
        val secondHashCode: Int = second.hashCode()
        assertEquals(firstHashCode, secondHashCode)
    }

    @Test
    fun `structural equality should fail with another ExternalPlugin having another identifier`() {
        val first = ExternalPlugin(name = "Base", identifier = "base")
        val second = ExternalPlugin(name = "Java", identifier = "java")
        this.structuralEqualityShouldFailWith(first, second)
    }

    @Test
    fun `structural equality should fail with null`() {
        val first = ExternalPlugin(name = "Base", identifier = "base")
        val second: Any? = null
        this.structuralEqualityShouldFailWith(first, second)
    }

    @Test
    fun `structural equality should fail with an object having a type other than ExternalPlugin`() {
        val first = ExternalPlugin(name = "Base", identifier = "base")
        val second: Any = "Oops!"
        this.structuralEqualityShouldFailWith(first, second)
    }

    private fun structuralEqualityShouldFailWith(
        first: ExternalPlugin,
        second: Any?
    ) {
        assertNotEquals(first, second)
        val firstHashCode: Int = first.hashCode()
        val secondHashCode: Int = second.hashCode()
        assertNotEquals(firstHashCode, secondHashCode)
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
        val expected = "$plugin wasn't applied to $project."
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
