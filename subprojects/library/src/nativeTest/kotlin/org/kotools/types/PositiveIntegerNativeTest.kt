package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@OptIn(ExperimentalKotoolsTypesApi::class)
class PositiveIntegerNativeTest {
    // ------------------------ Int.toPositiveInteger() ------------------------

    @Test
    fun toPositiveIntegerFailsOnInt() {
        val number: Int = Int.MAX_VALUE
        val error: NotImplementedError =
            assertFailsWith { number.toPositiveInteger() }
        val actual: String? = error.message
        val expected = "Not yet supported on Kotlin/Native."
        assertEquals(expected, actual)
    }

    // ----------------------- Long.toPositiveInteger() ------------------------

    @Test
    fun toPositiveIntegerFailsOnLong() {
        val number: Long = Long.MAX_VALUE
        val error: NotImplementedError =
            assertFailsWith { number.toPositiveInteger() }
        val actual: String? = error.message
        val expected = "Not yet supported on Kotlin/Native."
        assertEquals(expected, actual)
    }
}
