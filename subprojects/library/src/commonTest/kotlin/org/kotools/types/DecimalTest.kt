package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@OptIn(ExperimentalKotoolsTypesApi::class)
class DecimalTest {
    // ------------------------------- Creations -------------------------------

    @Test
    fun createsFromLong() {
        // Given
        val number: Long = Long.MAX_VALUE
        // When
        val result: Decimal = Decimal.fromInteger(number)
        // Then
        assertEquals(expected = "$number", "$result")
    }

    @Test
    fun fromDecimalWithUnsignedZeroAsString() {
        // Given
        val text = "000.000"
        // When
        val result: Decimal = Decimal.fromDecimal(text)
        // Then
        assertEquals(expected = "0", "$result")
    }

    @Test
    fun fromDecimalWithPlusSignedZeroAsString() {
        // Given
        val text = "+000.000"
        // When
        val result: Decimal = Decimal.fromDecimal(text)
        // Then
        assertEquals(expected = "0", "$result")
    }

    @Test
    fun fromDecimalWithMinusSignedZeroAsString() {
        // Given
        val text = "-000.000"
        // When
        val result: Decimal = Decimal.fromDecimal(text)
        // Then
        assertEquals(expected = "0", "$result")
    }

    @Test
    fun fromDecimalWithUnsignedPositiveIntegerAsString() {
        // Given
        val text = "123"
        // When
        val result: Decimal = Decimal.fromDecimal(text)
        // Then
        assertEquals(expected = text, "$result")
    }

    @Test
    fun fromDecimalWithSignedPositiveIntegerAsString() {
        // Given
        val integer = 123
        val text = "+$integer"
        // When
        val result: Decimal = Decimal.fromDecimal(text)
        // Then
        assertEquals(expected = "$integer", "$result")
    }

    @Test
    fun fromDecimalWithNegativeIntegerAsString() {
        // Given
        val text = "-123"
        // When
        val result: Decimal = Decimal.fromDecimal(text)
        // Then
        assertEquals(expected = text, "$result")
    }

    @Test
    fun fromDecimalWithIntegerHavingLeadingZerosInString() {
        // Given
        val integer = 123
        val text = "000$integer"
        // When
        val result: Decimal = Decimal.fromDecimal(text)
        // Then
        assertEquals(expected = "$integer", "$result")
    }

    @Test
    fun fromDecimalWithIntegerHavingTrailingZerosInString() {
        // Given
        val integer = 123
        val text = "$integer.000"
        // When
        val result: Decimal = Decimal.fromDecimal(text)
        // Then
        assertEquals(expected = "$integer", "$result")
    }

    @Test
    fun fromDecimalWithUnsignedPositiveFloatingPointNumberAsString() {
        // Given
        val text = "123.456"
        // When
        val result: Decimal = Decimal.fromDecimal(text)
        // Then
        assertEquals(expected = text, "$result")
    }

    @Test
    fun fromDecimalWithSignedPositiveFloatingPointNumberAsString() {
        // Given
        val number = "123.456"
        val text = "+$number"
        // When
        val result: Decimal = Decimal.fromDecimal(text)
        // Then
        assertEquals(expected = number, "$result")
    }

    @Test
    fun fromDecimalWithNegativeFloatingPointNumberAsString() {
        // Given
        val text = "-123.456"
        // When
        val result: Decimal = Decimal.fromDecimal(text)
        // Then
        assertEquals(expected = text, "$result")
    }

    @Test
    fun fromDecimalWithFloatingPointNumberHavingLeadingZerosInString() {
        // Given
        val number = "123.456"
        val text = "000$number"
        // When
        val result: Decimal = Decimal.fromDecimal(text)
        // Then
        assertEquals(expected = number, "$result")
    }

    @Test
    fun fromDecimalWithFloatingPointNumberHavingTrailingZerosInString() {
        // Given
        val number = "123.456"
        val text = "${number}000"
        // When
        val result: Decimal = Decimal.fromDecimal(text)
        // Then
        assertEquals(expected = number, "$result")
    }

    @Test
    fun fromDecimalWithBlankString() {
        // Given
        val text = " "
        // When
        val result: IllegalArgumentException = assertFailsWith {
            Decimal.fromDecimal(text)
        }
        // Then
        val expected = "Decimal floating-point number can't be blank."
        assertEquals(expected, result.message)
    }

    @Test
    fun fromDecimalWithPlusSignAsString() {
        // Given
        val text = "+"
        // When
        val result: IllegalArgumentException = assertFailsWith {
            Decimal.fromDecimal(text)
        }
        // Then
        val expected = "Plus sign (+) is not a decimal floating-point number."
        assertEquals(expected, result.message)
    }

    @Test
    fun fromDecimalWithMinusSignAsString() {
        // Given
        val text = "-"
        // When
        val result: IllegalArgumentException = assertFailsWith {
            Decimal.fromDecimal(text)
        }
        // Then
        val expected = "Minus sign (-) is not a decimal floating-point number."
        assertEquals(expected, result.message)
    }

    @Test
    fun fromDecimalWithRadixPointAsString() {
        // Given
        val text = "."
        // When
        val result: IllegalArgumentException = assertFailsWith {
            Decimal.fromDecimal(text)
        }
        // Then
        val expected = "Radix point (.) is not a decimal floating-point number."
        assertEquals(expected, result.message)
    }

    @Test
    fun fromDecimalWithNonDecimalString() {
        // Given
        val text = "oops"
        // When
        val result: IllegalArgumentException = assertFailsWith {
            Decimal.fromDecimal(text)
        }
        // Then
        val expected = "Only digits, plus sign (+), minus sign (-), and " +
                "radix point (.) characters are allowed in decimal " +
                "floating-point number (was: $text)."
        assertEquals(expected, result.message)
    }

    @Test
    fun fromDecimalWithMultipleRadixPointsInString() {
        // Given
        val text = "1.2.3"
        // When
        val result: IllegalArgumentException = assertFailsWith {
            Decimal.fromDecimal(text)
        }
        // Then
        val expected = "Decimal floating-point number can't have multiple " +
                "radix points (was: $text)."
        assertEquals(expected, result.message)
    }

    @Test
    fun fromDecimalWithMalformedIntegerPartInString() {
        // Given
        val text = ".123"
        // When
        val result: IllegalArgumentException = assertFailsWith {
            Decimal.fromDecimal(text)
        }
        // Then
        val expected = "Integer part of decimal floating-point number is " +
                "malformed (was: $text)."
        assertEquals(expected, result.message)
    }

    @Test
    fun fromDecimalWithMalformedFractionalPartInString() {
        // Given
        val text = "1."
        // When
        val result: IllegalArgumentException = assertFailsWith {
            Decimal.fromDecimal(text)
        }
        // Then
        val expected = "Fractional part of decimal floating-point number is " +
                "malformed (was: $text)."
        assertEquals(expected, result.message)
    }
}
