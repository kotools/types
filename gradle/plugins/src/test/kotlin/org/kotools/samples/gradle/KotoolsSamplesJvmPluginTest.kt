package org.kotools.samples.gradle

import java.util.Objects
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class KotoolsSamplesJvmPluginTest {
    @Test
    fun `primary constructor should pass`() {
        KotoolsSamplesJvmPlugin()
    }

    // -------------------- Structural equality operations ---------------------

    @Test
    fun `equals should pass with a KotoolsSamplesJvmPlugin type`() {
        val plugin = KotoolsSamplesJvmPlugin()
        val other: Any = KotoolsSamplesJvmPlugin()
        val actual: Boolean = plugin == other
        val message = "Gradle plugins with the same type should be equal."
        assertTrue(actual, message)
    }

    @Test
    fun `equals should fail with another type than KotoolsSamplesJvmPlugin`() {
        val plugin = KotoolsSamplesJvmPlugin()
        val other: Any = "$plugin"
        val actual: Boolean = plugin == other
        val message = "Gradle plugins with different types shouldn't be equal."
        assertFalse(actual, message)
    }

    @Test
    fun `hashCode should pass`() {
        val plugin = KotoolsSamplesJvmPlugin()
        val actual: Int = plugin.hashCode()
        val expected: Int = Objects.hash("$plugin")
        assertEquals(expected, actual)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun `toString should pass`() {
        val plugin = KotoolsSamplesJvmPlugin()
        val actual: String = plugin.toString()
        val expected = "Kotools Samples Gradle plugin for Kotlin/JVM projects"
        assertEquals(expected, actual)
    }
}
