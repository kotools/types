package org.kotools.types

import org.kotools.types.internal.ExceptionMessage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class PositiveIntegerTest {
    @Test
    fun equalsShouldPassWithPositiveIntegerHavingSameStringRepresentation() {
        val number: Int = (1..Int.MAX_VALUE).random()
        val integer: PositiveInteger = PositiveInteger.orThrow(number)
        val other: PositiveInteger = PositiveInteger.orThrow(number)
        val actual: Boolean = integer.equals(other)
        val message = "Positive integers with the same value are equal."
        assertTrue(actual, message)
    }

    @Test
    fun equalsShouldFailWithPositiveIntegerHavingAnotherStringRepresentation() {
        val integer: PositiveInteger = (1..Int.MAX_VALUE).random()
            .let(PositiveInteger.Companion::orThrow)
        val other: PositiveInteger = (1..Int.MAX_VALUE).random()
            .let(PositiveInteger.Companion::orThrow)
        val actual: Boolean = integer.equals(other)
        val message = "Positive integers with different values are not equal."
        assertFalse(actual, message)
    }

    @Test
    fun equalsShouldFailWithAnotherTypeThanPositiveInteger() {
        val number: Int = (1..Int.MAX_VALUE).random()
        val integer: PositiveInteger = PositiveInteger.orThrow(number)
        val actual: Boolean = integer.equals(other = number)
        val message = "PositiveInteger doesn't equal objects with another type."
        assertFalse(actual, message)
    }

    @Test
    fun equalsShouldFailWithNull() {
        val integer: PositiveInteger = (1..Int.MAX_VALUE).random()
            .let(PositiveInteger.Companion::orThrow)
        val other: Any? = null
        val actual: Boolean = integer.equals(other)
        val message = "PositiveInteger never equals '$other'."
        assertFalse(actual, message)
    }

    @Test
    fun toStringShouldPass() {
        val number: Int = (1..Int.MAX_VALUE).random()
        val actual: String = PositiveInteger.orThrow(number)
            .toString()
        assertEquals(expected = "$number", actual)
    }
}

@OptIn(ExperimentalKotoolsTypesApi::class)
class PositiveIntegerCompanionTest {
    @Test
    fun orNullShouldPassWithIntNumberGreaterThanZero() {
        val number: Int = (1..Int.MAX_VALUE).random()
        val actual: PositiveInteger? = PositiveInteger.orNull(number)
        val message = "$number is greater than zero."
        assertNotNull(actual, message)
    }

    @Test
    fun orNullShouldFailWithIntNumberThatEqualsZero() {
        val number: Int = Zero()
            .toInt()
        PositiveInteger.orNull(number)
            .assertNull(number)
    }

    @Test
    fun orNullShouldFailWithIntNumberLessThanZero() {
        val number: Int = (Int.MIN_VALUE..-1).random()
        PositiveInteger.orNull(number)
            .assertNull(number)
    }

    @Test
    fun orNullShouldPassWithLongNumberGreaterThanZero() {
        val number: Long = (1..Long.MAX_VALUE).random()
        val actual: PositiveInteger? = PositiveInteger.orNull(number)
        val message = "$number is greater than zero."
        assertNotNull(actual, message)
    }

    @Test
    fun orNullShouldFailWithLongNumberThatEqualsZero() {
        val number: Long = Zero()
            .toLong()
        PositiveInteger.orNull(number)
            .assertNull(number)
    }

    @Test
    fun orNullShouldFailWithLongNumberLessThanZero() {
        val number: Long = (Long.MIN_VALUE..-1).random()
        PositiveInteger.orNull(number)
            .assertNull(number)
    }

    @Test
    fun orNullShouldPassWithStringIntegerGreaterThanZero() {
        val text: String = (1..Long.MAX_VALUE).random()
            .toString()
        val actual: PositiveInteger? = PositiveInteger.orNull(text)
        val message = "'$text' is an integer greater than zero."
        assertNotNull(actual, message)
    }

    @Test
    fun orNullShouldPassWithStringSignedIntegerGreaterThanZero() {
        val text: String = (1..Long.MAX_VALUE).random()
            .toString()
            .let { "+$it" }
        val actual: PositiveInteger? = PositiveInteger.orNull(text)
        val message = "'$text' is an integer greater than zero."
        assertNotNull(actual, message)
    }

    @Test
    fun orNullShouldFailWithStringIntegerRepresentingZero() {
        val text: String = Zero()
            .toString()
        val actual: PositiveInteger? = PositiveInteger.orNull(text)
        val message = "'$text' is zero."
        assertNull(actual, message)
    }

    @Test
    fun orNullShouldFailWithStringIntegerLessThanZero() {
        val text: String = (Long.MIN_VALUE..-1).random()
            .toString()
        val actual: PositiveInteger? = PositiveInteger.orNull(text)
        val message = "'$text' is an integer less than zero."
        assertNull(actual, message)
    }

    @Test
    fun orThrowShouldPassWithIntNumberGreaterThanZero() {
        val number: Int = (1..Int.MAX_VALUE).random()
        PositiveInteger.orThrow(number)
    }

    @Test
    fun orThrowShouldFailWithIntNumberThatEqualsZero() {
        val number: Int = Zero()
            .toInt()
        assertThrowsIllegalArgumentException { PositiveInteger.orThrow(number) }
            .assertEquals { ExceptionMessage.nonPositive(number) }
    }

    @Test
    fun orThrowShouldFailWithIntNumberLessThanZero() {
        val number: Int = (Int.MIN_VALUE..-1).random()
        assertThrowsIllegalArgumentException { PositiveInteger.orThrow(number) }
            .assertEquals { ExceptionMessage.nonPositive(number) }
    }

    @Test
    fun orThrowShouldPassWithLongNumberGreaterThanZero() {
        val number: Long = (1..Long.MAX_VALUE).random()
        PositiveInteger.orThrow(number)
    }

    @Test
    fun orThrowShouldFailWithLongNumberThatEqualsZero() {
        val number: Long = Zero()
            .toLong()
        assertThrowsIllegalArgumentException { PositiveInteger.orThrow(number) }
            .assertEquals { ExceptionMessage.nonPositive(number) }
    }

    @Test
    fun orThrowShouldFailWithLongNumberLessThanZero() {
        val number: Long = (Long.MIN_VALUE..-1).random()
        assertThrowsIllegalArgumentException { PositiveInteger.orThrow(number) }
            .assertEquals { ExceptionMessage.nonPositive(number) }
    }

    @Test
    fun orThrowShouldPassWithStringIntegerGreaterThanZero() {
        val text: String = (1..Long.MAX_VALUE).random()
            .toString()
        PositiveInteger.orThrow(text)
    }

    @Test
    fun orThrowShouldPassWithStringSignedIntegerGreaterThanZero() {
        val text: String = (1..Long.MAX_VALUE).random()
            .toString()
            .let { "+$it" }
        PositiveInteger.orThrow(text)
    }

    @Test
    fun orThrowShouldFailWithStringIntegerRepresentingZero() {
        val text: String = Zero()
            .toString()
        assertThrowsIllegalArgumentException { PositiveInteger.orThrow(text) }
            .assertEquals { ExceptionMessage.nonPositiveInteger(text) }
    }

    @Test
    fun orThrowShouldFailWithStringIntegerLessThanZero() {
        val text: String = (Long.MIN_VALUE..-1).random()
            .toString()
        assertThrowsIllegalArgumentException { PositiveInteger.orThrow(text) }
            .assertEquals { ExceptionMessage.nonPositiveInteger(text) }
    }
}

// -------------------------------- Assertions ---------------------------------

@OptIn(ExperimentalKotoolsTypesApi::class)
private fun PositiveInteger?.assertNull(number: Number) {
    val message: String = ExceptionMessage.nonPositive(number)
        .toString()
    assertNull(this, message)
}
