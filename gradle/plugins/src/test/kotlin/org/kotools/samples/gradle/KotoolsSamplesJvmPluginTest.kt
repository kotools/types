package org.kotools.samples.gradle

import kotlin.test.Test
import kotlin.test.assertEquals

class KotoolsSamplesJvmPluginTest {
    @Test
    fun `primary constructor should pass`() {
        KotoolsSamplesJvmPlugin()
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
