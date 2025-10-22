package org.kotools.types.numbers

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@OptIn(ExperimentalKotoolsTypesApi::class)
class IntegerTest {
    @Test
    fun constructorWithMaximumLong() {
        val number: Long = Long.MAX_VALUE
        val integer = Integer(number)
        assertEquals(expected = "$number", actual = "$integer")
    }

    @Test
    fun constructorWithMinimumLong() {
        val number: Long = Long.MIN_VALUE
        val integer = Integer(number)
        assertEquals(expected = "$number", actual = "$integer")
    }

    @Test
    fun constructorWithDecimalText() {
        val text = "${Long.MAX_VALUE}"
        val integer = Integer(text)
        assertEquals(expected = text, actual = "$integer")
    }

    @Test
    fun constructorWithPlusSignedDecimalText() {
        val number: Long = Long.MAX_VALUE
        val integer = Integer("+$number")
        assertEquals(expected = "$number", actual = "$integer")
    }

    @Test
    fun constructorWithMinusSignedDecimalText() {
        val text = "${Long.MIN_VALUE}"
        val integer = Integer(text)
        assertEquals(expected = text, actual = "$integer")
    }

    @Test
    fun constructorWithBlankTest() {
        val exception: IllegalArgumentException = assertFailsWith {
            Integer("")
        }
        val expected = "Integer should not be blank"
        assertEquals(expected, actual = exception.message)
    }

    @Test
    fun constructorWithNonDecimalText() {
        val text = "hello"
        val exception: IllegalArgumentException = assertFailsWith {
            Integer(text)
        }
        val expected = "Integer can only contain an optional + or - sign, " +
                "followed by a sequence of digits, was: $text"
        assertEquals(expected, actual = exception.message)
    }
}
