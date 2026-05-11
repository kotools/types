package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

@OptIn(ExperimentalKotoolsTypesApi::class)
class IntegerTest {
    // -------------------------- Creation from Long ---------------------------

    @Test
    fun creationFromLongPreservesCanonicalRepresentation(): Unit =
        repeat(times = 1_000) {
            val value: Long = Random.nextLong()

            val result: Integer = Integer.of(value)

            val actual: String = result.toString()
            val expected: String = value.toString()
            assertEquals(expected, actual)
        }

    // ------------------------- Conversion to String --------------------------

    @Test
    fun stringRepresentationIsCanonical(): Unit = repeat(times = 1_000) {
        val value: Long = Random.nextLong()
        val integer: Integer = Integer.of(value)

        val actual: String = integer.toString()

        val expected: String = value.toString()
        assertEquals(expected, actual)
        val zero = 0L
        if (value == zero) return@repeat assertEquals(expected = "0", actual)
        if (value > zero) assertFalse("\"$actual\" must not have leading +.") {
            actual.startsWith('+')
        }
    }
}
