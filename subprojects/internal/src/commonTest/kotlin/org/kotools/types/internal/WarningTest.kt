package org.kotools.types.internal

import kotlin.test.Test
import kotlin.test.assertEquals

class WarningTest {
    @Test
    @Suppress("TestFunctionName")
    fun FINAL_should_return_RedundantModalityModifier() {
        val expected = "RedundantModalityModifier"
        val actual: String = Warning.FINAL
        assertEquals(expected, actual)
    }
}
