package org.kotools.samples

import kotlin.test.Test
import kotlin.test.assertEquals

class ExternalPluginCatalogTest {
    @Test
    fun `dokka should pass`() {
        val catalog = ExternalPluginCatalog()
        val actual: ExternalPlugin = catalog.dokka
        val expected =
            ExternalPlugin(name = "Dokka", identifier = "org.jetbrains.dokka")
        assertEquals(expected, actual)
    }

    @Test
    fun `kotlinMultiplatform should pass`() {
        val catalog = ExternalPluginCatalog()
        val actual: ExternalPlugin = catalog.kotlinMultiplatform
        val expected = ExternalPlugin(
            name = "Kotlin Multiplatform",
            identifier = "org.jetbrains.kotlin.multiplatform"
        )
        assertEquals(expected, actual)
    }
}
