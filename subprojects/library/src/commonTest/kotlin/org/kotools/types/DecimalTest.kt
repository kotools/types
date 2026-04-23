package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@OptIn(ExperimentalKotoolsTypesApi::class)
class DecimalTest {
    // ------------------------------- Creations -------------------------------

    @Test
    fun fromInteger() {
        val number: Long = Long.MAX_VALUE
        val actual: String = Decimal.fromInteger(number)
            .toString()
        val expected: String = number.toString()
        assertEquals(expected, actual)
    }

    @Test
    fun fromDecimalWithZero(): Unit =
        listOf("0", "+0", "-0", "00", "00.00").forEach {
            val result: Decimal = Decimal.fromDecimal(it)
            assertEquals(expected = "0", "$result")
        }

    @Test
    fun fromDecimalWithPositiveInteger(): Unit =
        listOf("1", "+1", "01", "1.0").forEach {
            val result: Decimal = Decimal.fromDecimal(it)
            assertEquals(expected = "1", "$result")
        }

    @Test
    fun fromDecimalWithNegativeInteger(): Unit =
        listOf("-1", "-01", "-1.0").forEach {
            val result: Decimal = Decimal.fromDecimal(it)
            assertEquals(expected = "-1", "$result")
        }

    @Test
    fun fromDecimalWithPositiveDecimalNumber(): Unit =
        listOf("1.2", "+1.2", "01.2", "1.20").forEach {
            val result: Decimal = Decimal.fromDecimal(it)
            assertEquals(expected = "1.2", "$result")
        }

    @Test
    fun fromDecimalWithNegativeDecimalNumber(): Unit =
        listOf("-1.2", "-01.2", "-1.20").forEach {
            val result: Decimal = Decimal.fromDecimal(it)
            assertEquals(expected = "-1.2", "$result")
        }

    @Test
    fun fromDecimalWithInvalidString(): Unit =
        listOf(" ", "+", "-", ".", "oops", "1.2.3", ".123", "1.").forEach {
            val result: IllegalArgumentException = assertFailsWith {
                Decimal.fromDecimal(it)
            }
            val expected = "\"$it\" is not a valid decimal number."
            assertEquals(expected, result.message)
        }
}
