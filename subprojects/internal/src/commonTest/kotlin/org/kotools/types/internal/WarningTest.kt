package org.kotools.types.internal

import kotools.types.internal.InternalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(InternalKotoolsTypesApi::class)
class WarningTest {
    @Suppress(Warning.TEST_FUNCTION_NAME)
    @Test
    fun FINAL_should_return_RedundantModalityModifier() {
        val expected = "RedundantModalityModifier"
        val actual: String = Warning.FINAL
        assertEquals(expected, actual)
    }

    @Suppress(Warning.TEST_FUNCTION_NAME)
    @Test
    fun FUNCTION_NAME_should_return_FunctionName() {
        val expected = "FunctionName"
        val actual: String = Warning.FUNCTION_NAME
        assertEquals(expected, actual)
    }

    @Suppress(Warning.TEST_FUNCTION_NAME)
    @Test
    fun TEST_FUNCTION_NAME_should_return_TestFunctionName() {
        val expected = "TestFunctionName"
        val actual: String = Warning.TEST_FUNCTION_NAME
        assertEquals(expected, actual)
    }
}
