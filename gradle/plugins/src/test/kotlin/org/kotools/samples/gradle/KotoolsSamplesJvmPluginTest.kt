package org.kotools.samples.gradle

import kotlin.test.Test
import kotlin.test.assertEquals

class KotoolsSamplesJvmPluginTest {
    @Test
    fun primaryConstructorShouldPass() {
        KotoolsSamplesJvmPlugin()
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringShouldPass() {
        val plugin = KotoolsSamplesJvmPlugin()
        val actual: String = plugin.toString()
        val expected = "Kotools Samples Gradle plugin for Kotlin/JVM projects"
        assertEquals(expected, actual)
    }
}
