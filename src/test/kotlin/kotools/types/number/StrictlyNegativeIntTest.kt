package kotools.types.number

import io.github.kotools.assert.assertEquals
import io.github.kotools.assert.assertNotNull
import io.github.kotools.assert.assertNull
import kotools.types.string.NotBlankString
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class StrictlyNegativeIntTest {
    @Nested
    inner class Constructor {
        @Test
        fun `should pass with -1`() {
            // GIVEN
            val value = -1
            // WHEN
            val result = assertDoesNotThrow { StrictlyNegativeInt(value) }
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with 0`() {
            // GIVEN
            val value = 0
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException> {
                StrictlyNegativeInt(value)
            }
        }

        @Nested
        inner class OrNull {
            @Test
            fun `should pass with -2`() {
                // GIVEN
                val value = -2
                // WHEN
                val result: StrictlyNegativeInt? =
                    StrictlyNegativeInt orNull value
                // THEN
                result.assertNotNull().value assertEquals value
            }

            @Test
            fun `should return null with 1`() {
                // GIVEN
                val value = 1
                // WHEN
                val result: StrictlyNegativeInt? =
                    StrictlyNegativeInt orNull value
                // THEN
                result.assertNull()
            }
        }
    }

    // ---------- Unary operations ----------

    @Nested
    inner class Inc {
        @Test
        fun `should return -1 with -2`() {
            // GIVEN
            var x = StrictlyNegativeInt(-2)
            // WHEN
            x++
            // THEN
            x.value assertEquals -1
        }

        @Test
        fun `should return the minimum value with the maximum value`() {
            // GIVEN
            var x = StrictlyNegativeInt.max
            // WHEN
            x++
            // THEN
            x assertEquals StrictlyNegativeInt.min
        }
    }

    @Nested
    inner class Dec {
        @Test
        fun `should return -2 with -1`() {
            // GIVEN
            var x = StrictlyNegativeInt(-1)
            // WHEN
            x--
            // THEN
            x.value assertEquals -2
        }

        @Test
        fun `should return the maximum value with the minimum value`() {
            // GIVEN
            var x = StrictlyNegativeInt.min
            // WHEN
            x--
            // THEN
            x assertEquals StrictlyNegativeInt.max
        }
    }

    @Nested
    inner class UnaryPlus {
        @Test
        fun `should return the same strictly negative int`() {
            // GIVEN
            val value = -1
            val x = StrictlyNegativeInt(value)
            // WHEN
            val result: StrictlyNegativeInt = +x
            // THEN
            result.value assertEquals value
        }
    }

    @Nested
    inner class UnaryMinus {
        @Test
        fun `should return 1 as a strictly positive int with -1`() {
            // GIVEN
            val value = -1
            val x = StrictlyNegativeInt(value)
            // WHEN
            val result: StrictlyPositiveInt = -x
            // THEN
            result.value assertEquals -value
        }
    }

    // ---------- Binary operations ----------

    @Nested
    inner class Plus {
        @Test
        fun `should return an int with an int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = 1
            // WHEN
            val result: Int = x + y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return an int when adding a strictly negative int to an int`() {
            // GIVEN
            val x = 1
            val y = StrictlyNegativeInt(-1)
            // WHEN
            val result: Int = x + y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return an int with a non zero int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = NonZeroInt(1)
            // WHEN
            val result: Int = x + y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return an int with a positive int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = PositiveInt(1)
            // WHEN
            val result: Int = x + y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return an int with a strictly positive int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = StrictlyPositiveInt(1)
            // WHEN
            val result: Int = x + y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return a strictly negative int with a negative int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = NegativeInt(-2)
            // WHEN
            val result: StrictlyNegativeInt = x + y
            // THEN
            result.value assertEquals -3
        }

        @Test
        fun `should return a strictly negative int with a strictly negative int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = StrictlyNegativeInt(-2)
            // WHEN
            val result: StrictlyNegativeInt = x + y
            // THEN
            result.value assertEquals -3
        }
    }

    @Nested
    inner class Minus {
        @Test
        fun `should return an int with an int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = -1
            // WHEN
            val result: Int = x - y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return an int when subtracting a strictly negative int from an int`() {
            // GIVEN
            val x = -1
            val y = StrictlyNegativeInt(-1)
            // WHEN
            val result: Int = x - y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return an int with a non zero int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = NonZeroInt(-1)
            // WHEN
            val result: Int = x - y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return a strictly negative int with a positive int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = PositiveInt(1)
            // WHEN
            val result: StrictlyNegativeInt = x - y
            // THEN
            result.value assertEquals -2
        }

        @Test
        fun `should return a strictly negative int with a strictly positive int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = StrictlyPositiveInt(1)
            // WHEN
            val result: StrictlyNegativeInt = x - y
            // THEN
            result.value assertEquals -2
        }

        @Test
        fun `should return an int with a negative int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = NegativeInt(-1)
            // WHEN
            val result: Int = x - y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return an int with a strictly negative int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = StrictlyNegativeInt(-1)
            // WHEN
            val result: Int = x - y
            // THEN
            result assertEquals 0
        }
    }

    @Nested
    inner class Times {
        @Test
        fun `should return an int with an int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = 0
            // WHEN
            val result: Int = x * y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return an int when multiplying an int by a strictly negative int`() {
            // GIVEN
            val x = 0
            val y = StrictlyNegativeInt(-1)
            // WHEN
            val result: Int = x * y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return a non zero int with a non zero int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = NonZeroInt(2)
            // WHEN
            val result: NonZeroInt = x * y
            // THEN
            result.value assertEquals -2
        }

        @Test
        fun `should return a negative int with a positive int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = PositiveInt(0)
            // WHEN
            val result: NegativeInt = x * y
            // THEN
            result.value assertEquals 0
        }

        @Test
        fun `should return a strictly negative int with a strictly positive int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = StrictlyPositiveInt(2)
            // WHEN
            val result: StrictlyNegativeInt = x * y
            // THEN
            result.value assertEquals -2
        }

        @Test
        fun `should return a positive int with a negative int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = NegativeInt(0)
            // WHEN
            val result: PositiveInt = x * y
            // THEN
            result.value assertEquals 0
        }

        @Test
        fun `should return a strictly positive int with a strictly negative int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = StrictlyNegativeInt(-2)
            // WHEN
            val result: StrictlyPositiveInt = x * y
            // THEN
            result.value assertEquals 2
        }
    }

    @Nested
    inner class Div {
        @Test
        fun `should return an int with an int other than 0`() {
            // GIVEN
            val x = StrictlyNegativeInt(-2)
            val y = -3
            // WHEN
            val result: Int = assertDoesNotThrow { x / y }
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should throw an error with an int that equals 0`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = 0
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        @Test
        fun `should return an int when dividing an int by a strictly negative int`() {
            // GIVEN
            val x = 0
            val y = StrictlyNegativeInt(-1)
            // WHEN
            val result: Int = x / y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return an int with a non zero int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-2)
            val y = NonZeroInt(3)
            // WHEN
            val result: Int = x / y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return a negative int with a positive int other than 0`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = PositiveInt(1)
            // WHEN
            val result: NegativeInt = assertDoesNotThrow { x / y }
            // THEN
            result.value assertEquals -1
        }

        @Test
        fun `should throw an error with a positive int that equals 0`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = PositiveInt(0)
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        @Test
        fun `should return a negative int with a strictly positive int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = StrictlyPositiveInt(1)
            // WHEN
            val result: NegativeInt = x / y
            // THEN
            result.value assertEquals -1
        }

        @Test
        fun `should return a positive int with a negative int other than 0`() {
            // GIVEN
            val x = StrictlyNegativeInt(-2)
            val y = NegativeInt(-1)
            // WHEN
            val result: PositiveInt = assertDoesNotThrow { x / y }
            // THEN
            result.value assertEquals 2
        }

        @Test
        fun `should throw an error with a negative int that equals 0`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = NegativeInt(0)
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        @Test
        fun `should return a positive int with a strictly negative int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-2)
            val y = StrictlyNegativeInt(-1)
            // WHEN
            val result: PositiveInt = x / y
            // THEN
            result.value assertEquals 2
        }
    }

    // ---------- Comparisons ----------

    @Nested
    inner class CompareTo {
        // ---------- Int ----------

        @Test
        fun `should return 0 with the same int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y: Int = x.value
            // WHEN
            val result: Int = x compareTo y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return a negative number with a greater int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y: Int = -x.value
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result < 0 }
        }

        @Test
        fun `should return a positive number with a less int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = -2
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result > 0 }
        }

        // ---------- NonZeroInt ----------

        @Test
        fun `should return 0 with the same non zero int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y: NonZeroInt = x.toNonZeroInt()
            // WHEN
            val result: Int = x compareTo y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return a negative number with a greater non zero int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = NonZeroInt(1)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result < 0 }
        }

        @Test
        fun `should return a positive number with a less non zero int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = NonZeroInt(-2)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result > 0 }
        }

        // ---------- PositiveInt ----------

        @Test
        fun `should return a negative number with a positive int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = PositiveInt(0)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result < 0 }
        }

        // ---------- StrictlyPositiveInt ----------

        @Test
        fun `should return a negative number with a strictly positive int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = StrictlyPositiveInt(-x.value)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result < 0 }
        }

        // ---------- NegativeInt ----------

        @Test
        fun `should return 0 with the same negative int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y: NegativeInt = x.toNegativeInt()
            // WHEN
            val result: Int = x compareTo y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return a negative number with a greater negative int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = NegativeInt(0)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result < 0 }
        }

        @Test
        fun `should return a positive number with a less negative int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = NegativeInt(-2)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result > 0 }
        }

        // ---------- StrictlyNegativeInt ----------

        @Test
        fun `should return 0 with the same strictly negative int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = StrictlyNegativeInt(x.value)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return a negative number with a greater strictly negative int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-2)
            val y = StrictlyNegativeInt(-1)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result < 0 }
        }

        @Test
        fun `should return a positive number with a less strictly negative int`() {
            // GIVEN
            val x = StrictlyNegativeInt(-1)
            val y = StrictlyNegativeInt(-2)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result > 0 }
        }
    }

    // ---------- Conversions ----------

    @Nested
    inner class ToNonZeroInt {
        @Test
        fun `should return its value as a non zero int`() {
            // GIVEN
            val value = -1
            val x = StrictlyNegativeInt(value)
            // WHEN
            val result: NonZeroInt = x.toNonZeroInt()
            // THEN
            result.value assertEquals value
        }
    }

    @Nested
    inner class ToNegativeInt {
        @Test
        fun `should return its value as a negative int`() {
            // GIVEN
            val value = -1
            val x = StrictlyNegativeInt(value)
            // WHEN
            val result: NegativeInt = x.toNegativeInt()
            // THEN
            result.value assertEquals value
        }
    }

    @Nested
    inner class ToString {
        @Test
        fun `should return its value as a string`() {
            // GIVEN
            val value = -1
            val x = StrictlyNegativeInt(value)
            // WHEN
            val result: String = x.toString()
            // THEN
            result assertEquals value.toString()
        }
    }

    @Nested
    inner class ToNotBlankString {
        @Test
        fun `should return its value as a not blank string`() {
            // GIVEN
            val value = -1
            val x = StrictlyNegativeInt(value)
            // WHEN
            val result: NotBlankString = x.toNotBlankString()
            // THEN
            result.value assertEquals value.toString()
        }
    }
}
