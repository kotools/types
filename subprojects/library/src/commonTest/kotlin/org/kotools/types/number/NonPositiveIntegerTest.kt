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
import kotlin.test.assertNull

@OptIn(ExperimentalKotoolsTypesApi::class)
class NonPositiveIntegerTest {
    // --------------------------- Factory functions ---------------------------

    @Test
    fun fromIntegerPreservesValue(): Unit = repeatTest {
        val integer: Integer = Random.nonPositiveInteger()
            .toInteger()

        val nonPositiveInteger: NonPositiveInteger =
            NonPositiveInteger.fromInteger(integer)
        val safeNonPositiveInteger: NonPositiveInteger? =
            NonPositiveInteger.fromIntegerOrNull(integer)

        val message = "Input: $integer"
        assertEquals(integer, actual = nonPositiveInteger.toInteger(), message)
        assertEquals(
            integer,
            actual = safeNonPositiveInteger?.toInteger(),
            message
        )
    }

    @Test
    fun fromIntegerFailsWithPositiveValue(): Unit = repeatTest {
        val integer: Integer = Random.positiveInteger()

        val exception: IllegalArgumentException = assertFailsWith(
            message = "Input: $integer"
        ) { NonPositiveInteger.fromInteger(integer) }
        val expected: String = errorMessage("Positive integer", integer)
        assertEquals(expected, actual = exception.message)

        val safeNonPositiveInteger: NonPositiveInteger? =
            NonPositiveInteger.fromIntegerOrNull(integer)
        assertNull(safeNonPositiveInteger, message = "Input: $integer")
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
