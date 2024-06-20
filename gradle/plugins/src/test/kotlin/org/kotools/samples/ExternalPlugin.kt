package org.kotools.samples

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
}
