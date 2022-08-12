package kotools.types.number

import kotools.assert.*
import kotools.types.string.NotBlankString

class NegativeIntTest {
    @Nested
    inner class Constructor {
        @Test
        fun `should pass with 0`() {
            // GIVEN
            val value = 0
            // WHEN
            val result: NegativeInt = assertPass { NegativeInt(value) }
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should pass with -1`() {
            // GIVEN
            val value = -1
            // WHEN
            val result: NegativeInt = assertPass { NegativeInt(value) }
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with 1`() {
            // GIVEN
            val value = 1
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException> { NegativeInt(value) }
        }

        @Nested
        inner class OrNull {
            @Test
            fun `should pass with 0`() {
                // GIVEN
                val value = 0
                // WHEN
                val result: NegativeInt? = NegativeInt orNull value
                // THEN
                result.assertNotNull().value assertEquals value
            }

            @Test
            fun `should pass with -1`() {
                // GIVEN
                val value = -1
                // WHEN
                val result: NegativeInt? = NegativeInt orNull value
                // THEN
                result.assertNotNull().value assertEquals value
            }

            @Test
            fun `should return null with 1`() {
                // GIVEN
                val value = 1
                // WHEN
                val result: NegativeInt? = NegativeInt orNull value
                // THEN
                result.assertNull()
            }
        }
    }

    // ---------- Unary operations ----------

    @Nested
    inner class Increment {
        @Test
        fun `should return 0 with -1`() {
            // GIVEN
            var x = NegativeInt(-1)
            // WHEN
            x++
            // THEN
            x.value assertEquals 0
        }

        @Test
        fun `should return the minimum value with the maximum value`() {
            // GIVEN
            var x = NegativeInt.max
            // WHEN
            x++
            // THEN
            x assertEquals NegativeInt.min
        }
    }

    @Nested
    inner class Decrement {
        @Test
        fun `should return -1 with 0`() {
            // GIVEN
            var x = NegativeInt(0)
            // WHEN
            x--
            // THEN
            x.value assertEquals -1
        }

        @Test
        fun `should return the maximum value with the minimum value`() {
            // GIVEN
            var x = NegativeInt.min
            // WHEN
            x--
            // THEN
            x assertEquals NegativeInt.max
        }
    }

    @Nested
    inner class UnaryPlus {
        @Test
        fun `should return the same negative int`() {
            // GIVEN
            val x = NegativeInt(-1)
            // WHEN
            val result: NegativeInt = +x
            // THEN
            result assertEquals x
        }
    }

    @Nested
    inner class UnaryMinus {
        @Test
        fun `should return a positive int that equals 1 with -1`() {
            // GIVEN
            val x = NegativeInt(-1)
            // WHEN
            val result: PositiveInt = -x
            // THEN
            result.value assertEquals 1
        }
    }

    // ---------- Binary operations ----------

    @Nested
    inner class Plus {
        @Test
        fun `should return an int with an int`() {
            // GIVEN
            val x = NegativeInt(-1)
            val y = 1
            // WHEN
            val result: Int = x + y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return an int when adding a negative int to an int`() {
            // GIVEN
            val x = 5
            val y = NegativeInt(-2)
            // WHEN
            val result: Int = x + y
            // THEN
            result assertEquals 3
        }

        @Test
        fun `should return an int with a non zero int`() {
            // GIVEN
            val x = NegativeInt(-1)
            val y = NonZeroInt(1)
            // WHEN
            val result: Int = x + y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return an int with a positive int`() {
            // GIVEN
            val x = NegativeInt(-5)
            val y = PositiveInt(2)
            // WHEN
            val result: Int = x + y
            // THEN
            result assertEquals -3
        }

        @Test
        fun `should return an int with a strictly positive int`() {
            // GIVEN
            val x = NegativeInt(-5)
            val y = StrictlyPositiveInt(2)
            // WHEN
            val result: Int = x + y
            // THEN
            result assertEquals -3
        }

        @Test
        fun `should return a negative int with a negative int`() {
            // GIVEN
            val x = NegativeInt(-1)
            val y = NegativeInt(-2)
            // WHEN
            val result: NegativeInt = x + y
            // THEN
            result.value assertEquals -3
        }

        @Test
        fun `should return a strictly negative int with a strictly negative int`() {
            // GIVEN
            val x = NegativeInt(0)
            val y = StrictlyNegativeInt(-1)
            // WHEN
            val result: StrictlyNegativeInt = x + y
            // THEN
            result.value assertEquals -1
        }
    }

    @Nested
    inner class Minus {
        @Test
        fun `should return an int with an int`() {
            // GIVEN
            val x = NegativeInt(-1)
            val y = -1
            // WHEN
            val result: Int = x - y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return an int when subtracting a negative int to an int`() {
            // GIVEN
            val x = -1
            val y = NegativeInt(-1)
            // WHEN
            val result: Int = x - y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return an int with a non zero int`() {
            // GIVEN
            val x = NegativeInt(-1)
            val y = NonZeroInt(-1)
            // WHEN
            val result: Int = x - y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return a negative int with a positive int`() {
            // GIVEN
            val x = NegativeInt(-1)
            val y = PositiveInt(1)
            // WHEN
            val result: NegativeInt = x - y
            // THEN
            result.value assertEquals -2
        }

        @Test
        fun `should return a strictly negative int with a strictly positive int`() {
            // GIVEN
            val x = NegativeInt(-1)
            val y = StrictlyPositiveInt(1)
            // WHEN
            val result: StrictlyNegativeInt = x - y
            // THEN
            result.value assertEquals -2
        }

        @Test
        fun `should return an int with a negative int`() {
            // GIVEN
            val x = NegativeInt(-1)
            val y = NegativeInt(-1)
            // WHEN
            val result: Int = x - y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return an int with a strictly negative int`() {
            // GIVEN
            val x = NegativeInt(-1)
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
            val x = NegativeInt(-1)
            val y = -2
            // WHEN
            val result: Int = x * y
            // THEN
            result assertEquals 2
        }

        @Test
        fun `should return an int when multiplying an int by a negative int`() {
            // GIVEN
            val x = -1
            val y = NegativeInt(-2)
            // WHEN
            val result: Int = x * y
            // THEN
            result assertEquals 2
        }

        @Test
        fun `should return an int with a non zero int`() {
            // GIVEN
            val x = NegativeInt(-1)
            val y = NonZeroInt(-2)
            // WHEN
            val result: Int = x * y
            // THEN
            result assertEquals 2
        }

        @Test
        fun `should return a negative int with a positive int`() {
            // GIVEN
            val x = NegativeInt(-1)
            val y = PositiveInt(2)
            // WHEN
            val result: NegativeInt = x * y
            // THEN
            result.value assertEquals -2
        }

        @Test
        fun `should return a negative int with a strictly positive int`() {
            // GIVEN
            val x = NegativeInt(-1)
            val y = StrictlyPositiveInt(2)
            // WHEN
            val result: NegativeInt = x * y
            // THEN
            result.value assertEquals -2
        }

        @Test
        fun `should return a positive int with a negative int`() {
            // GIVEN
            val x = NegativeInt(-1)
            val y = NegativeInt(-2)
            // WHEN
            val result: PositiveInt = x * y
            // THEN
            result.value assertEquals 2
        }

        @Test
        fun `should return a positive int with a strictly negative int`() {
            // GIVEN
            val x = NegativeInt(0)
            val y = StrictlyNegativeInt(-1)
            // WHEN
            val result: PositiveInt = x * y
            // THEN
            result.value assertEquals 0
        }
    }

    @Nested
    inner class Div {
        @Test
        fun `should return an int with an int other than 0`() {
            // GIVEN
            val x = NegativeInt(-4)
            val y = 2
            // WHEN
            val result: Int = assertPass { x / y }
            // THEN
            result assertEquals -2
        }

        @Test
        fun `should throw an error with an int that equals 0`() {
            // GIVEN
            val x = NegativeInt(-4)
            val y = 0
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        @Test
        fun `should return an int when dividing an int by a negative int other than 0`() {
            // GIVEN
            val x = 4
            val y = NegativeInt(-2)
            // WHEN
            val result: Int = assertPass { x / y }
            // THEN
            result assertEquals -2
        }

        @Test
        fun `should throw an error when dividing an int by a negative int that equals 0`() {
            // GIVEN
            val x = 4
            val y = NegativeInt(0)
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        @Test
        fun `should return an int with a non zero int`() {
            // GIVEN
            val x = NegativeInt(-4)
            val y = NonZeroInt(-2)
            // WHEN
            val result: Int = x / y
            // THEN
            result assertEquals 2
        }

        @Test
        fun `should return a negative int with a positive int other than 0`() {
            // GIVEN
            val x = NegativeInt(-4)
            val y = PositiveInt(2)
            // WHEN
            val result: NegativeInt = assertPass { x / y }
            // THEN
            result.value assertEquals -2
        }

        @Test
        fun `should throw an error with a positive int that equals 0`() {
            // GIVEN
            val x = NegativeInt(-4)
            val y = PositiveInt(0)
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        @Test
        fun `should return a negative int with a strictly positive int`() {
            // GIVEN
            val x = NegativeInt(-4)
            val y = StrictlyPositiveInt(2)
            // WHEN
            val result: NegativeInt = x / y
            // THEN
            result.value assertEquals -2
        }

        @Test
        fun `should return a positive int with a negative int other than 0`() {
            // GIVEN
            val x = NegativeInt(-4)
            val y = NegativeInt(-2)
            // WHEN
            val result: PositiveInt = assertPass { x / y }
            // THEN
            result.value assertEquals 2
        }

        @Test
        fun `should throw an error with a negative int that equals 0`() {
            // GIVEN
            val x = NegativeInt(-4)
            val y = NegativeInt(0)
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        @Test
        fun `should return a positive int with a strictly negative int`() {
            // GIVEN
            val x = NegativeInt(0)
            val y = StrictlyNegativeInt(-1)
            // WHEN
            val result: PositiveInt = x / y
            // THEN
            result.value assertEquals 0
        }
    }

    // ---------- Comparisons ----------

    @Nested
    inner class CompareTo {
        // ---------- Int ----------

        @Test
        fun `should return 0 with the same int`() {
            // GIVEN
            val x = NegativeInt(0)
            val y: Int = x.value
            // WHEN
            val result: Int = x compareTo y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return 0 when comparing an int with the same negative int`() {
            // GIVEN
            val x = 0
            val y = NegativeInt(x)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return a negative number with a greater int`() {
            // GIVEN
            val x = NegativeInt(0)
            val y = 10
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result < 0 }
        }

        @Test
        fun `should return a negative number when comparing an int with a greater negative int`() {
            // GIVEN
            val x = -1
            val y = NegativeInt(0)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result < 0 }
        }

        @Test
        fun `should return a positive number with a less int`() {
            // GIVEN
            val x = NegativeInt(0)
            val y = -10
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result > 0 }
        }

        @Test
        fun `should return a positive number when comparing an int with a less negative int`() {
            // GIVEN
            val x = 0
            val y = NegativeInt(-10)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result > 0 }
        }

        // ---------- NonZeroInt ----------

        @Test
        fun `should return 0 with the same non zero int`() {
            // GIVEN
            val x = NegativeInt(-1)
            val y: NonZeroInt = x.toNonZeroInt()
            // WHEN
            val result: Int = x compareTo y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return a negative number with a greater non zero int`() {
            // GIVEN
            val x = NegativeInt(0)
            val y = NonZeroInt(1)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result < 0 }
        }

        @Test
        fun `should return a positive number with a less non zero int`() {
            // GIVEN
            val x = NegativeInt(0)
            val y = NonZeroInt(-1)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result > 0 }
        }

        // ---------- PositiveInt ----------

        @Test
        fun `should return 0 with the same positive int`() {
            // GIVEN
            val x = NegativeInt(0)
            val y: PositiveInt = x.toPositiveInt()
            // WHEN
            val result: Int = x compareTo y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return a negative number with a greater positive int`() {
            // GIVEN
            val x = NegativeInt(-1)
            val y: PositiveInt = -x
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result < 0 }
        }

        // ---------- StrictlyPositiveInt ----------

        @Test
        fun `should return a negative number with a greater strictly positive int`() {
            // GIVEN
            val x = NegativeInt(0)
            val y = StrictlyPositiveInt(1)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result < 0 }
        }

        // ---------- NegativeInt ----------

        @Test
        fun `should return 0 with the same negative int`() {
            // GIVEN
            val x = NegativeInt(-1)
            val y = NegativeInt(x.value)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return a negative number with a greater negative int`() {
            // GIVEN
            val x = NegativeInt(-1)
            val y = NegativeInt(0)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result < 0 }
        }

        @Test
        fun `should return a positive number with a less negative int`() {
            // GIVEN
            val x = NegativeInt(-1)
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
            val x = NegativeInt(-1)
            val y = StrictlyNegativeInt(x.value)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return a negative number with a greater strictly negative int`() {
            // GIVEN
            val x = NegativeInt(-2)
            val y = StrictlyNegativeInt(-1)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result < 0 }
        }

        @Test
        fun `should return a positive number with a less strictly negative int`() {
            // GIVEN
            val x = NegativeInt(-1)
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
        fun `should return its value as a non zero int with -1`() {
            // GIVEN
            val value = -1
            val x = NegativeInt(value)
            // WHEN
            val result: NonZeroInt = assertPass(x::toNonZeroInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with 0`() {
            // GIVEN
            val x = NegativeInt(0)
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(block = x::toNonZeroInt)
        }

        @Nested
        inner class OrNull {
            @Test
            fun `should return its value as a non zero int with -2`() {
                // GIVEN
                val value = -2
                val x = NegativeInt(value)
                // WHEN
                val result: NonZeroInt? = x.toNonZeroIntOrNull()
                // THEN
                result.assertNotNull().value assertEquals value
            }

            @Test
            fun `should return null with 0`() {
                // GIVEN
                val x = NegativeInt(0)
                // WHEN
                val result: NonZeroInt? = x.toNonZeroIntOrNull()
                // THEN
                result.assertNull()
            }
        }
    }

    @Nested
    inner class ToPositiveInt {
        @Test
        fun `should return its value as a positive int with 0`() {
            // GIVEN
            val value = 0
            val x = NegativeInt(value)
            // WHEN
            val result: PositiveInt = assertPass(x::toPositiveInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with -1`() {
            // GIVEN
            val x = NegativeInt(-1)
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(block = x::toPositiveInt)
        }

        @Nested
        inner class OrNull {
            @Test
            fun `should return its value as a positive int with 0`() {
                // GIVEN
                val value = 0
                val x = NegativeInt(value)
                // WHEN
                val result: PositiveInt? = x.toPositiveIntOrNull()
                // THEN
                result.assertNotNull().value assertEquals value
            }

            @Test
            fun `should return null with -1`() {
                // GIVEN
                val x = NegativeInt(-1)
                // WHEN
                val result: PositiveInt? = x.toPositiveIntOrNull()
                // THEN
                result.assertNull()
            }
        }
    }

    @Nested
    inner class ToStrictlyNegativeInt {
        @Test
        fun `should return its value as a strictly negative int with -1`() {
            // GIVEN
            val value = -1
            val x = NegativeInt(value)
            // WHEN
            val result: StrictlyNegativeInt =
                assertPass(x::toStrictlyNegativeInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with 0`() {
            // GIVEN
            val x = NegativeInt(0)
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(
                block = x::toStrictlyNegativeInt
            )
        }

        @Nested
        inner class OrNull {
            @Test
            fun `should return its value as a strictly negative int with -2`() {
                // GIVEN
                val value = -2
                val x = NegativeInt(value)
                // WHEN
                val result: StrictlyNegativeInt? =
                    x.toStrictlyNegativeIntOrNull()
                // THEN
                result.assertNotNull().value assertEquals value
            }

            @Test
            fun `should return null with 0`() {
                // GIVEN
                val x = NegativeInt(0)
                // WHEN
                val result: StrictlyNegativeInt? =
                    x.toStrictlyNegativeIntOrNull()
                // THEN
                result.assertNull()
            }
        }
    }

    @Nested
    inner class ToString {
        @Test
        fun `should return its value as a string`() {
            // GIVEN
            val value = -1
            val x = NegativeInt(value)
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
            val value = 0
            val x = NegativeInt(value)
            // WHEN
            val result: NotBlankString = x.toNotBlankString()
            // THEN
            result.value assertEquals value.toString()
        }
    }
}
