package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.errorMessage
import org.kotools.types.nonPositiveInteger
import org.kotools.types.positiveInteger
import org.kotools.types.repeatTest
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@OptIn(ExperimentalKotoolsTypesApi::class)
class NonPositiveIntegerTest {
    // --------------------------- Factory functions ---------------------------

    @Test
    fun fromIntegerFailsWithPositiveValue(): Unit = repeatTest {
        val integer: Integer = Random.positiveInteger()

        val exception: IllegalArgumentException = assertFailsWith(
            message = "Input: $integer"
        ) { NonPositiveInteger.fromInteger(integer) }
        val expected: String = errorMessage("Positive integer", integer)
        assertEquals(expected, actual = exception.message)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toIntegerRoundTripsWithFromInteger(): Unit = repeatTest {
        val integer: Integer = Random.nonPositiveInteger()
            .toInteger()

        val nonPositiveInteger: NonPositiveInteger =
            NonPositiveInteger.fromInteger(integer)
        val actual: Integer = nonPositiveInteger.toInteger()

        assertEquals(integer, actual, message = "Input: $integer")
    }
}
