package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.random.nextLong
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertSame
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class IntegerTest {
    // ------------------------------- Creations -------------------------------

    @Test
    fun ofWithLongRepresentingZero() {
        // Given
        val value = 0L
        // When
        val actual: Integer = Integer.of(value)
        // Then
        assertEquals(expected = "$value", "$actual")
    }

    @Test
    fun ofWithLongMaxValue() {
        // Given
        val value: Long = Long.MAX_VALUE
        // When
        val actual: Integer = Integer.of(value)
        // Then
        assertEquals(expected = "$value", "$actual")
    }

    @Test
    fun ofWithLongMinValue() {
        // Given
        val value: Long = Long.MIN_VALUE
        // When
        val actual: Integer = Integer.of(value)
        // Then
        assertEquals(expected = "$value", "$actual")
    }

    @Test
    fun ofWithRandomLong(): Unit = repeatTest {
        // Given
        val value: Long = Random.nextLong()
        // When
        val actual: Integer = Integer.of(value)
        // Then
        assertEquals(expected = "$value", "$actual")
    }

    @Test
    fun parseHasUniqueRepresentationOfZero(): Unit = repeatTest {
        // Given
        val sign: String = listOf("", "+", "-").random()
        val digits: String = buildString {
            val times: Int = Random.nextInt(1..2_000)
            repeat(times) { this.append(0) }
        }
        val value = "$sign$digits"
        // When
        val actual: Integer = Integer.parse(value)
        // Then
        val expected: Integer = Integer.of(0)
        assertEquals(expected, actual, "Input: $value")
    }

    @Test
    fun parseRemovesPlusSignFromPositiveInteger(): Unit = repeatTest {
        // Given
        val digits: String = buildString {
            val firstDigit: Int = Random.nextInt(1..9)
            this.append(firstDigit)
            val times: Int = Random.nextInt(1..32)
            repeat(times) {
                val otherDigit: Int = Random.nextInt(0..9)
                this.append(otherDigit)
            }
        }
        val value = "+$digits"
        // When
        val actual: Integer = Integer.parse(value)
        // Then
        assertEquals(expected = digits, "$actual", "Input: $value")
    }

    @Test
    fun parsePreservesRepresentationOfNegativeInteger(): Unit = repeatTest {
        // Given
        val digits: String = buildString {
            val firstDigit: Int = Random.nextInt(1..9)
            this.append(firstDigit)
            val times: Int = Random.nextInt(1..32)
            repeat(times) {
                val otherDigit: Int = Random.nextInt(0..9)
                this.append(otherDigit)
            }
        }
        val value = "-$digits"
        // When
        val actual: Integer = Integer.parse(value)
        // Then
        assertEquals(expected = value, "$actual", "Input: $value")
    }

    @Test
    fun parseRemovesInsignificantLeadingZerosFromInteger(): Unit = repeatTest {
        // Given
        val sign: String = listOf("", "+", "-").random()
        val zeros: String = buildString {
            val times: Int = Random.nextInt(1..16)
            repeat(times) { this.append(0) }
        }
        val digits: String = buildString {
            val firstDigit: Int = Random.nextInt(1..9)
            this.append(firstDigit)
            val times: Int = Random.nextInt(1..16)
            repeat(times) {
                val otherDigit: Int = Random.nextInt(0..9)
                this.append(otherDigit)
            }
        }
        val value = "$sign$zeros$digits"
        // When
        val actual: Integer = Integer.parse(value)
        // Then
        val expected: String = buildString {
            if (sign != "+") this.append(sign)
            this.append(digits)
        }
        assertEquals(expected, "$actual", "Input: $value")
    }

    @Test
    fun parseThrowsNumberFormatExceptionWithEmptyString() {
        // Given
        val value = ""
        // When & Then
        val exception: NumberFormatException = assertFailsWith {
            Integer.parse(value)
        }
        val actual: String? = exception.message
        assertEquals(expected = "\"$value\" is not a valid integer.", actual)
    }

    @Test
    fun parseThrowsNumberFormatExceptionWithSignOnly(): Unit = listOf("+", "-")
        .forEach {
            // Given, When & Then
            val exception: NumberFormatException = assertFailsWith {
                Integer.parse(it)
            }
            val actual: String? = exception.message
            assertEquals(expected = "\"$it\" is not a valid integer.", actual)
        }

    @Test
    fun parseThrowsNumberFormatExceptionWithMultipleSigns(): Unit = repeatTest {
        // Given
        val signs: String = buildString {
            val signs: List<Char> = listOf('+', '-')
            val times: Int = Random.nextInt(2..16)
            repeat(times) {
                val sign: Char = signs.random()
                this.append(sign)
            }
        }
        val digits: String = buildString {
            val firstDigit: Int = Random.nextInt(1..9)
            this.append(firstDigit)
            val times: Int = Random.nextInt(1..16)
            repeat(times) {
                val otherDigit: Int = Random.nextInt(0..9)
                this.append(otherDigit)
            }
        }
        val value = "$signs$digits"
        // When & Then
        val exception: NumberFormatException = assertFailsWith {
            Integer.parse(value)
        }
        val actual: String? = exception.message
        assertEquals(expected = "\"$value\" is not a valid integer.", actual)
    }

    @Test
    fun parseThrowsNumberFormatExceptionWithMalformedIntegerString(): Unit =
        repeatTest {
            // Given
            val signs: List<Char> = listOf('+', '-')
            val characters: List<Char> = ('0'..'9') + signs
            val value: String = buildString {
                var signCount = 0
                while (signCount < 2) {
                    val character: Char = characters.random()
                    this.append(character)
                    if (character in signs) signCount++
                }
            }
            // When & Then
            val exception: NumberFormatException = assertFailsWith {
                Integer.parse(value)
            }
            val actual: String? = exception.message
            val expected = "\"$value\" is not a valid integer."
            assertEquals(expected, actual)
        }

    @Test
    fun parseThrowsNumberFormatExceptionWithIllegalCharactersInString(): Unit =
        repeatTest {
            // Given
            val allowedCharacters: List<Char> = ('0'..'9') + listOf('+', '-')
            val characters: List<Char> =
                allowedCharacters + ('a'..'z') + ('A'..'Z')
            val value = buildString {
                var illegalCharacterCount = 0
                while (illegalCharacterCount == 0) {
                    val character: Char = characters.random()
                    this.append(character)
                    if (character !in allowedCharacters) illegalCharacterCount++
                }
            }
            // When & Then
            val exception: NumberFormatException = assertFailsWith {
                Integer.parse(value)
            }
            val actual: String? = exception.message
            val expected = "\"$value\" is not a valid integer."
            assertEquals(expected, actual)
        }

    @Test
    fun parseOrNullHasUniqueRepresentationOfZero(): Unit = repeatTest {
        // Given
        val sign: String = listOf("", "+", "-").random()
        val digits: String = buildString {
            val times: Int = Random.nextInt(1..2_000)
            repeat(times) { this.append(0) }
        }
        val value = "$sign$digits"
        // When
        val actual: Integer? = Integer.parseOrNull(value)
        // Then
        val expected: Integer = Integer.of(0)
        assertEquals(expected, actual, "Input: $value")
    }

    @Test
    fun parseOrNullRemovesPlusSignFromPositiveInteger(): Unit = repeatTest {
        // Given
        val digits: String = buildString {
            val firstDigit: Int = Random.nextInt(1..9)
            this.append(firstDigit)
            val times: Int = Random.nextInt(1..32)
            repeat(times) {
                val otherDigit: Int = Random.nextInt(0..9)
                this.append(otherDigit)
            }
        }
        val value = "+$digits"
        // When
        val actual: Integer? = Integer.parseOrNull(value)
        // Then
        assertEquals(expected = digits, "$actual", "Input: $value")
    }

    @Test
    fun parseOrNullPreservesRepresentationOfNegativeInteger(): Unit =
        repeatTest {
            // Given
            val digits: String = buildString {
                val firstDigit: Int = Random.nextInt(1..9)
                this.append(firstDigit)
                val times: Int = Random.nextInt(1..32)
                repeat(times) {
                    val otherDigit: Int = Random.nextInt(0..9)
                    this.append(otherDigit)
                }
            }
            val value = "-$digits"
            // When
            val actual: Integer? = Integer.parseOrNull(value)
            // Then
            assertEquals(expected = value, "$actual", "Input: $value")
        }

    @Test
    fun parseOrNullRemovesInsignificantLeadingZerosFromInteger(): Unit =
        repeatTest {
            // Given
            val sign: String = listOf("", "+", "-").random()
            val zeros: String = buildString {
                val times: Int = Random.nextInt(1..16)
                repeat(times) { this.append(0) }
            }
            val digits: String = buildString {
                val firstDigit: Int = Random.nextInt(1..9)
                this.append(firstDigit)
                val times: Int = Random.nextInt(1..16)
                repeat(times) {
                    val otherDigit: Int = Random.nextInt(0..9)
                    this.append(otherDigit)
                }
            }
            val value = "$sign$zeros$digits"
            // When
            val actual: Integer? = Integer.parseOrNull(value)
            // Then
            val expected: String = buildString {
                if (sign != "+") this.append(sign)
                this.append(digits)
            }
            assertEquals(expected, "$actual", "Input: $value")
        }

    @Test
    fun parseOrNullReturnsNullWithEmptyString() {
        // Given
        val value = ""
        // When
        val actual: Integer? = Integer.parseOrNull(value)
        // Then
        assertNull(actual)
    }

    @Test
    fun parseOrNullReturnsNullWithSignOnly(): Unit = listOf("+", "-").forEach {
        // When
        val actual: Integer? = Integer.parseOrNull(it)
        // Then
        assertNull(actual, "Input: $it")
    }

    @Test
    fun parseOrNullReturnsNullWithMultipleSigns(): Unit = repeatTest {
        // Given
        val signs: String = buildString {
            val signs: List<Char> = listOf('+', '-')
            val times: Int = Random.nextInt(2..16)
            repeat(times) {
                val sign: Char = signs.random()
                this.append(sign)
            }
        }
        val digits: String = buildString {
            val firstDigit: Int = Random.nextInt(1..9)
            this.append(firstDigit)
            val times: Int = Random.nextInt(1..16)
            repeat(times) {
                val otherDigit: Int = Random.nextInt(0..9)
                this.append(otherDigit)
            }
        }
        val value = "$signs$digits"
        // When
        val actual: Integer? = Integer.parseOrNull(value)
        // Then
        assertNull(actual, "Input: $value")
    }

    @Test
    fun parseOrNullReturnsNullWithMalformedIntegerString(): Unit = repeatTest {
        // Given
        val signs: List<Char> = listOf('+', '-')
        val characters: List<Char> = ('0'..'9') + signs
        val value: String = buildString {
            var signCount = 0
            while (signCount < 2) {
                val character: Char = characters.random()
                this.append(character)
                if (character in signs) signCount++
            }
        }
        // When
        val actual: Integer? = Integer.parseOrNull(value)
        // Then
        assertNull(actual, "Input: $value")
    }

    @Test
    fun parseOrNullReturnsNullWithIllegalCharactersInString(): Unit =
        repeatTest {
            // Given
            val allowedCharacters: List<Char> = ('0'..'9') + listOf('+', '-')
            val characters: List<Char> =
                allowedCharacters + ('a'..'z') + ('A'..'Z')
            val value = buildString {
                var illegalCharacterCount = 0
                while (illegalCharacterCount == 0) {
                    val character: Char = characters.random()
                    this.append(character)
                    if (character !in allowedCharacters) illegalCharacterCount++
                }
            }
            // When
            val actual: Integer? = Integer.parseOrNull(value)
            // Then
            assertNull(actual)
        }

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun structuralEqualityReturnsTrueWithSameIntegerValue(): Unit = repeatTest {
        // Given
        val value: Long = Random.nextLong()
        val x: Integer = Integer.of(value)
        val y: Integer = Integer.of(value)
        // When
        val equality: Boolean = x == y
        val hashConformity: Boolean = x.hashCode() == y.hashCode()
        // Then
        assertTrue(equality, "$x and $y must be structurally equal.")
        assertTrue(hashConformity, "$x and $y must have the same hash code.")
    }

    @Test
    fun structuralEqualityReturnsFalseWithAnotherTypeThanInteger(): Unit =
        repeatTest {
            // Given
            val value: Long = Random.nextLong()
            val x: Integer = Integer.of(value)
            // When
            val equality: Boolean = x.equals(value)
            val hashConformity: Boolean = x.hashCode() == value.hashCode()
            // Then
            assertFalse(equality, "Integer can't be equal to another type.")
            assertFalse(
                hashConformity,
                "Integer must produce a unique hash code."
            )
        }

    @Test
    fun structuralEqualityReturnsFalseWithAnotherIntegerValue(): Unit =
        repeatTest {
            // Given
            val random = Random.Default
            val x: Integer = Integer.of(random.nextLong())
            val y: Integer = Integer.of(random.nextLong())
            // When
            val equality: Boolean = x == y
            val hashConformity: Boolean = x.hashCode() == y.hashCode()
            // Then
            assertFalse(equality, "$x and $y must be different.")
            assertFalse(
                hashConformity,
                "$x and $y must have different hash codes."
            )
        }

    @Test
    fun compareToReturnsZeroWithSameIntegerValue(): Unit = repeatTest {
        // Given
        val value: Long = Random.nextLong()
        val x: Integer = Integer.of(value)
        val y: Integer = Integer.of(value)
        // When
        val actual: Int = x.compareTo(y)
        // Then
        assertEquals(expected = 0, actual, "$x and $y must be equal.")
    }

    @Test
    fun compareToReturnsPositiveNumberWithLesserIntegerValue(): Unit =
        repeatTest {
            // Given
            val value: Long = Random.nextLong(
                (Long.MIN_VALUE + 1)..Long.MAX_VALUE
            )
            val x: Integer = Integer.of(value)
            val y: Integer = Integer.of(Random.nextLong(Long.MIN_VALUE..<value))
            // When
            val actual: Int = x.compareTo(y)
            // Then
            assertTrue("$x must be greater than ${y}.") { actual > 0 }
        }

    @Test
    fun compareToReturnsNegativeNumberWithGreaterIntegerValue(): Unit =
        repeatTest {
            // Given
            val value: Long = Random.nextLong(Long.MIN_VALUE..<Long.MAX_VALUE)
            val x: Integer = Integer.of(value)
            val y: Integer = Integer.of(
                Random.nextLong((value + 1)..Long.MAX_VALUE)
            )
            // When
            val actual: Int = x.compareTo(y)
            // Then
            assertTrue("$x must be less than ${y}.") { actual < 0 }
        }

    @Test
    fun compareToIsAlignedWithLongComparisons() {
        // Given
        val random = Random.Default
        val xValue: Long = random.nextLong()
        val x: Integer = Integer.of(xValue)
        val yValue: Long = random.nextLong()
        val y: Integer = Integer.of(yValue)
        // When
        val actual: Int = x.compareTo(y)
        // Then
        val expected: Int = xValue.compareTo(yValue)
        val message = "Integer must align with Long comparisons."
        assertEquals(expected, actual, message)
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    fun unaryMinusReturnsSameInstanceOnZero() {
        // Given
        val x: Integer = Integer.of(0)
        // When
        val actual: Integer = -x
        // Then
        assertSame(expected = x, actual)
    }

    @Test
    fun unaryMinusReturnsNegativeIntegerOnPositiveInteger(): Unit = repeatTest {
        // Given
        val value: Long = Random.nextLong(1..Long.MAX_VALUE)
        val x: Integer = Integer.of(value)
        // When
        val actual: Integer = -x
        // Then
        val expected: Integer = Integer.of(-value)
        assertEquals(expected, actual, "Input: $value")
    }

    @Test
    fun unaryMinusReturnsPositiveIntegerOnNegativeInteger(): Unit = repeatTest {
        // Given
        val value: Long = Random.nextLong((Long.MIN_VALUE + 1)..-1)
        val x: Integer = Integer.of(value)
        // When
        val actual: Integer = -x
        // Then
        val expected: Integer = Integer.of(-value)
        assertEquals(expected, actual, "Input: $value")
    }

    @Test
    fun plusDoesNotOverflowWithPositiveIntegers(): Unit = repeatTest {
        // Given
        val range: LongRange = 1..Long.MAX_VALUE
        val x: Integer = Integer.of(Random.nextLong(range))
        val y: Integer = Integer.of(Random.nextLong(range))
        // When
        val sum: Integer = x + y
        // Then
        assertTrue("$sum must be greater than ${x}.") { sum > x }
        assertTrue("$sum must be greater than ${y}.") { sum > y }
    }

    @Test
    fun plusDoesNotOverflowWithNegativeIntegers(): Unit = repeatTest {
        // Given
        val range: LongRange = Long.MIN_VALUE..-1
        val x: Integer = Integer.of(Random.nextLong(range))
        val y: Integer = Integer.of(Random.nextLong(range))
        // When
        val sum: Integer = x + y
        // Then
        assertTrue("$sum must be less than ${x}.") { sum < x }
        assertTrue("$sum must be less than ${y}.") { sum < y }
    }

    @Test
    fun plusIsAssociative(): Unit = repeatTest {
        // Given
        val x: Integer = Integer.of(Random.nextLong())
        val y: Integer = Integer.of(Random.nextLong())
        val z: Integer = Integer.of(Random.nextLong())
        // When
        val sum: Integer = (x + y) + z
        // Then
        val expected: Integer = x + (y + z)
        val message = "Inputs: x = $x, y = $y, z = $z"
        assertEquals(expected, actual = sum, message)
    }

    @Test
    fun plusIsCommutative(): Unit = repeatTest {
        // Given
        val x: Integer = Integer.of(Random.nextLong())
        val y: Integer = Integer.of(Random.nextLong())
        // When
        val sum: Integer = x + y
        // Then
        val expected: Integer = y + x
        assertEquals(expected, actual = sum, message = "Inputs: x = $x, y = $y")
    }

    @Test
    fun plusHasIdentityElement(): Unit = repeatTest {
        // Given
        val x: Integer = Integer.of(Random.nextLong())
        val zero: Integer = Integer.of(0)
        // When & Then
        val message = "Input: $x"
        assertSame(expected = x, actual = x + zero, message)
        assertSame(expected = x, actual = zero + x, message)
    }

    @Test
    fun plusHasInverseElement(): Unit = repeatTest {
        // Given
        val x: Integer = Integer.of(Random.nextLong())
        // When
        val actual: Integer = x + (-x)
        // Then
        val expected: Integer = Integer.of(0)
        assertEquals(expected, actual, message = "Input: $x")
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringHasUniqueRepresentationOnZero() {
        // Given
        val zero = Integer.of(0)
        // When
        val actual: String = zero.toString()
        // Then
        assertEquals(expected = "0", actual)
    }

    @Test
    fun toStringIgnoresPlusSignOnPositiveInteger(): Unit = repeatTest {
        // Given
        val value: Long = Random.nextLong(1..Long.MAX_VALUE)
        val integer: Integer = Integer.of(value)
        // When
        val actual: String = integer.toString()
        // Then
        assertFalse(message = "$actual must not start with '+'.") {
            actual.startsWith('+')
        }
        assertEquals(expected = "$value", actual)
    }

    @Test
    fun toStringKeepsMinusSignOnNegativeInteger(): Unit = repeatTest {
        // Given
        val value: Long = Random.nextLong(Long.MIN_VALUE..-1)
        val integer: Integer = Integer.of(value)
        // When
        val actual: String = integer.toString()
        // Then
        assertTrue(message = "$actual must start with '-'.") {
            actual.startsWith('-')
        }
        assertEquals(expected = "$value", actual)
    }
}

private inline fun repeatTest(block: () -> Unit): Unit = repeat(times = 1_000) {
    block()
}
