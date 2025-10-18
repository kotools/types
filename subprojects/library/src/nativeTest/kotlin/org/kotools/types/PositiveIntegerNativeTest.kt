package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@OptIn(ExperimentalKotoolsTypesApi::class)
class PositiveIntegerNativeTest {
    // ------------------------ Int.toPositiveInteger() ------------------------

    @Test
    fun toPositiveIntegerFailsOnInt() {
        val number = 123456
        val error: NotImplementedError =
            assertFailsWith { number.toPositiveInteger() }
        val actual: String? = error.message
        val expected = "Not yet supported on Kotlin/Native."
        assertEquals(expected, actual)
    }

    // ----------------------- Long.toPositiveInteger() ------------------------

    @Test
    fun toPositiveIntegerFailsOnLong() {
        val error: NotImplementedError =
            assertFailsWith { 123456L.toPositiveInteger() }
        val actual: String? = error.message
        val expected = "Not yet supported on Kotlin/Native."
        assertEquals(expected, actual)
    }
}
