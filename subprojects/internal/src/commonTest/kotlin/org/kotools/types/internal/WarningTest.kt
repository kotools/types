package org.kotools.types.internal

import kotools.types.internal.InternalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(InternalKotoolsTypesApi::class)
class WarningTest {
    @Test
    @Suppress("TestFunctionName")
    fun FINAL_should_return_RedundantModalityModifier() {
        val expected = "RedundantModalityModifier"
        val actual: String = Warning.FINAL
        assertEquals(expected, actual)
    }

    @Test
    @Suppress("TestFunctionName")
    fun FUNCTION_NAME_should_return_FunctionName() {
        val expected = "FunctionName"
        val actual: String = Warning.FUNCTION_NAME
        assertEquals(expected, actual)
    }
}
