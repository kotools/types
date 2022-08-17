package kotools.types.number

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.*
import kotools.types.string.NotBlankString

class PositiveIntTest {
    @Nested
    inner class Constructor {
        @Test
        fun `should pass with 0`() {
            // GIVEN
            val x = 0
            // WHEN
            val result: PositiveInt = assertPass { PositiveInt(x) }
            // THEN
            result.value assertEquals x
        }

        @Test
        fun `should pass with 1`() {
            // GIVEN
            val x = 1
            // WHEN
            val result: PositiveInt = assertPass { PositiveInt(x) }
            // THEN
            result.value assertEquals x
        }

        @Test
        fun `should throw an error with -1`() {
            // GIVEN
            val x = -1
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException> { PositiveInt(x) }
        }

        @Nested
        inner class OrNull {
            @Test
            fun `should pass with 0`() {
                // GIVEN
                val x = 0
                // WHEN
                val result: PositiveInt? = PositiveInt orNull x
                // THEN
                result.assertNotNull().value assertEquals x
            }

            @Test
            fun `should pass with 1`() {
                // GIVEN
                val x = 1
                // WHEN
                val result: PositiveInt? = PositiveInt orNull x
                // THEN
                result.assertNotNull().value assertEquals x
            }

            @Test
            fun `should return null with -1`() {
                // GIVEN
                val x = -1
                // WHEN
                val result: PositiveInt? = PositiveInt orNull x
                // THEN
                result.assertNull()
            }
        }
    }

    // ---------- Unary operations ----------

    @Nested
    inner class Inc {
        @Test
        fun `should return 1 with 0`() {
            // GIVEN
            var x = PositiveInt(0)
            // WHEN
            x++
            // THEN
            x.value assertEquals 1
        }

        @Test
        fun `should return the minimum value with the maximum value`() {
            // GIVEN
            var x = PositiveInt.max
            // WHEN
            x++
            // THEN
            x assertEquals PositiveInt.min
        }
    }

    @Nested
    inner class Dec {
        @Test
        fun `should return 1 with 2`() {
            // GIVEN
            var x = PositiveInt(2)
            // WHEN
            x--
            // THEN
            x.value assertEquals 1
        }

        @Test
        fun `should return the maximum value with the minimum value`() {
            // GIVEN
            var x = PositiveInt.min
            // WHEN
            x--
            // THEN
            x assertEquals PositiveInt.max
        }
    }

    @Nested
    inner class UnaryPlus {
        @Test
        fun `should return the same positive int`() {
            // GIVEN
            val x = PositiveInt(1)
            // WHEN
            val result: PositiveInt = +x
            // THEN
            result assertEquals x
        }
    }

    @Nested
    inner class UnaryMinus {
        @Test
        fun `should return a negative int`() {
            // GIVEN
            val x = PositiveInt(1)
            // WHEN
            val result: NegativeInt = -x
            // THEN
            result.value assertEquals -1
        }
    }

    // ---------- Binary operations ----------

    @Nested
    inner class Plus {
        @Test
        fun `should return an int with an int`() {
            // GIVEN
            val x = PositiveInt(1)
            val y = -1
            // WHEN
            val result: Int = x + y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return an int when adding a positive int to an int`() {
            // GIVEN
            val x = -1
            val y = PositiveInt(1)
            // WHEN
            val result: Int = x + y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return an int with a non zero int`() {
            // GIVEN
            val x = PositiveInt(1)
            val y = NonZeroInt(-1)
            // WHEN
            val result: Int = x + y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return a positive int with a positive int`() {
            // GIVEN
            val x = PositiveInt(1)
            val y = PositiveInt(2)
            // WHEN
            val result: PositiveInt = x + y
            // THEN
            result.value assertEquals 3
        }

        @Test
        fun `should return a strictly positive int with a strictly positive int`() {
            // GIVEN
            val x = PositiveInt(1)
            val y = StrictlyPositiveInt(2)
            // WHEN
            val result: StrictlyPositiveInt = x + y
            // THEN
            result.value assertEquals 3
        }

        @Test
        fun `should return an int with a negative int`() {
            // GIVEN
            val x = PositiveInt(1)
            val y = NegativeInt(-2)
            // WHEN
            val result: Int = x + y
            // THEN
            result assertEquals -1
        }

        @Test
        fun `should return an int with strictly negative int`() {
            // GIVEN
            val x = PositiveInt(1)
            val y = StrictlyNegativeInt(-1)
            // WHEN
            val result: Int = x + y
            // THEN
            result assertEquals 0
        }
    }

    @Nested
    inner class Minus {
        @Test
        fun `should return an int with an int`() {
            // GIVEN
            val x = PositiveInt(1)
            val y = 1
            // WHEN
            val result: Int = x - y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return an int when subtracting a positive int to an int`() {
            // GIVEN
            val x = 1
            val y = PositiveInt(2)
            // WHEN
            val result: Int = x - y
            // THEN
            result assertEquals -1
        }

        @Test
        fun `should return an int with a non zero int`() {
            // GIVEN
            val x = PositiveInt(1)
            val y = NonZeroInt(3)
            // WHEN
            val result: Int = x - y
            // THEN
            result assertEquals -2
        }

        @Test
        fun `should return an int with a positive int`() {
            // GIVEN
            val x = PositiveInt(1)
            val y = PositiveInt(2)
            // WHEN
            val result: Int = x - y
            // THEN
            result assertEquals -1
        }

        @Test
        fun `should return an int with a strictly positive int`() {
            // GIVEN
            val x = PositiveInt(1)
            val y = StrictlyPositiveInt(1)
            // WHEN
            val result: Int = x - y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return a positive int with a negative int`() {
            // GIVEN
            val x = PositiveInt(1)
            val y = NegativeInt(-1)
            // WHEN
            val result: PositiveInt = x - y
            // THEN
            result.value assertEquals 2
        }

        @Test
        fun `should return a strictly positive int with a strictly negative int`() {
            // GIVEN
            val x = PositiveInt(0)
            val y = StrictlyNegativeInt(-1)
            // WHEN
            val result: StrictlyPositiveInt = x - y
            // THEN
            result.value assertEquals 1
        }
    }

    @Nested
    inner class Times {
        @Test
        fun `should return an int with an int`() {
            // GIVEN
            val x = PositiveInt(2)
            val y = -3
            // WHEN
            val result: Int = x * y
            // THEN
            result assertEquals -6
        }

        @Test
        fun `should return an int when multiplying an int by a positive int`() {
            // GIVEN
            val x = -2
            val y = PositiveInt(0)
            // WHEN
            val result: Int = x * y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return an int with a non zero int`() {
            // GIVEN
            val x = PositiveInt(0)
            val y = NonZeroInt(3)
            // WHEN
            val result: Int = x * y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return a positive int with a positive int`() {
            // GIVEN
            val x = PositiveInt(2)
            val y = PositiveInt(1)
            // WHEN
            val result: PositiveInt = x * y
            // THEN
            result.value assertEquals 2
        }

        @Test
        fun `should return a positive int with a strictly positive int`() {
            // GIVEN
            val x = PositiveInt(0)
            val y = StrictlyPositiveInt(1)
            // WHEN
            val result: PositiveInt = x * y
            // THEN
            result.value assertEquals 0
        }

        @Test
        fun `should return a negative int with a negative int`() {
            // GIVEN
            val x = PositiveInt(2)
            val y = NegativeInt(-3)
            // WHEN
            val result: NegativeInt = x * y
            // THEN
            result.value assertEquals -6
        }

        @Test
        fun `should return a negative int with a strictly negative int`() {
            // GIVEN
            val x = PositiveInt(0)
            val y = StrictlyNegativeInt(-1)
            // WHEN
            val result: NegativeInt = x * y
            // THEN
            result.value assertEquals 0
        }
    }

    @Nested
    inner class Div {
        @Test
        fun `should return an int with an int other than 0`() {
            // GIVEN
            val x = PositiveInt(2)
            val y = -2
            // WHEN
            val result: Int = assertPass { x / y }
            // THEN
            result assertEquals -1
        }

        @Test
        fun `should throw an error with an int that equals 0`() {
            // GIVEN
            val x = PositiveInt(2)
            val y = 0
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        @Test
        fun `should return an int when dividing an int by a positive int other than 0`() {
            // GIVEN
            val x = 0
            val y = PositiveInt(2)
            // WHEN
            val result: Int = assertPass { x / y }
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should throw an error when dividing an int by a positive int that equals 0`() {
            // GIVEN
            val x = 0
            val y = PositiveInt(0)
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        @Test
        fun `should return an int with a non zero int`() {
            // GIVEN
            val x = PositiveInt(0)
            val y = NonZeroInt(-1)
            // WHEN
            val result: Int = x / y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return a positive int with a positive int other than 0`() {
            // GIVEN
            val x = PositiveInt(0)
            val y = PositiveInt(2)
            // WHEN
            val result: PositiveInt = assertPass { x / y }
            // THEN
            result.value assertEquals 0
        }

        @Test
        fun `should throw an error with a positive int that equals 0`() {
            // GIVEN
            val x = PositiveInt(2)
            val y = PositiveInt(0)
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        @Test
        fun `should return a positive int with a strictly positive int`() {
            // GIVEN
            val x = PositiveInt(0)
            val y = StrictlyPositiveInt(2)
            // WHEN
            val result: PositiveInt = x / y
            // THEN
            result.value assertEquals 0
        }

        @Test
        fun `should return a negative int with a negative int other than 0`() {
            // GIVEN
            val x = PositiveInt(4)
            val y = NegativeInt(-2)
            // WHEN
            val result: NegativeInt = assertPass { x / y }
            // THEN
            result.value assertEquals -2
        }

        @Test
        fun `should throw an error with a negative int that equals 0`() {
            // GIVEN
            val x = PositiveInt(4)
            val y = NegativeInt(0)
            // WHEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        @Test
        fun `should return a negative int with a strictly negative int`() {
            // GIVEN
            val x = PositiveInt(0)
            val y = StrictlyNegativeInt(-1)
            // WHEN
            val result: NegativeInt = x / y
            // THEN
            result.value assertEquals 0
        }
    }

    // ---------- Comparisons ----------

    @Nested
    inner class CompareTo {
        // ---------- NonZeroInt ----------

        @Test
        fun `should return 0 with the same non zero int`() {
            // GIVEN
            val value = 1
            val x = PositiveInt(value)
            val y = NonZeroInt(value)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return a negative number with a greater non zero int`() {
            // GIVEN
            val value = 0
            val x = PositiveInt(value)
            val y = NonZeroInt(value + 1)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result < 0 }
        }

        @Test
        fun `should return a positive number with a less non zero int`() {
            // GIVEN
            val value = 0
            val x = PositiveInt(value)
            val y = NonZeroInt(value - 1)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result > 0 }
        }

        // ---------- PositiveInt ----------

        @Test
        fun `should return 0 with the same positive int`() {
            // GIVEN
            val x = PositiveInt(1)
            val y = PositiveInt(1)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return a negative number with a greater positive int`() {
            // GIVEN
            val x = PositiveInt(1)
            val y = PositiveInt(2)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result < 0 }
        }

        @Test
        fun `should return a positive number with a less positive int`() {
            // GIVEN
            val x = PositiveInt(2)
            val y = PositiveInt(1)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result > 0 }
        }

        // ---------- StrictlyPositiveInt ----------

        @Test
        fun `should return 0 with the same strictly positive int`() {
            // GIVEN
            val value = 1
            val x = PositiveInt(value)
            val y = StrictlyPositiveInt(value)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return a negative number with a greater strictly positive int`() {
            // GIVEN
            val value = 0
            val x = PositiveInt(value)
            val y = StrictlyPositiveInt(value + 1)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result < 0 }
        }

        @Test
        fun `should return a positive number with a less strictly positive int`() {
            // GIVEN
            val value = 2
            val x = PositiveInt(value)
            val y = StrictlyPositiveInt(value - 1)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result > 0 }
        }

        // ---------- NegativeInt ----------

        @Test
        fun `should return 0 with the same negative int`() {
            // GIVEN
            val value = 0
            val x = PositiveInt(value)
            val y = NegativeInt(value)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return a positive number with a less negative int`() {
            // GIVEN
            val value = 0
            val x = PositiveInt(value)
            val y = NegativeInt(value - 1)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result > 0 }
        }

        // ---------- StrictlyNegativeInt ----------

        @Test
        fun `should return a positive number with a less strictly negative int`() {
            // GIVEN
            val value = 0
            val x = PositiveInt(value)
            val y = StrictlyNegativeInt(value - 1)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result > 0 }
        }
    }

    // ---------- Conversions ----------

    @Nested
    inner class StringToPositiveInt {
        @Test
        fun `should pass with a string representation of 0`() {
            // GIVEN
            val value = 0
            val string = "$value"
            // WHEN
            val result: PositiveInt = assertPass(string::toPositiveInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should pass with a string representation of 1`() {
            // GIVEN
            val value = 1
            val string = "$value"
            // WHEN
            val result: PositiveInt = assertPass(string::toPositiveInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with an invalid string`() {
            // GIVEN
            val string = ""
            // WHEN & THEN
            assertFailsWith<NumberFormatException>(string::toPositiveInt)
        }

        @Test
        fun `should throw an error with a string representation of -1`() {
            // GIVEN
            val string = "-1"
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(string::toPositiveInt)
        }
    }

    @Nested
    inner class StringToPositiveIntOrNull {
        @Test
        fun `should pass with a string representation of 0`() {
            // GIVEN
            val value = 0
            val string = "$value"
            // WHEN
            val result: PositiveInt? = string.toPositiveIntOrNull()
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should pass with a string representation of 1`() {
            // GIVEN
            val value = 1
            val string = "$value"
            // WHEN
            val result: PositiveInt? = string.toPositiveIntOrNull()
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with an invalid string`() {
            // GIVEN
            val string = ""
            // WHEN
            val result: PositiveInt? = string.toPositiveIntOrNull()
            // THEN
            result.assertNull()
        }

        @Test
        fun `should return null with a string representation of -1`() {
            // GIVEN
            val string = "-1"
            // WHEN
            val result: PositiveInt? = string.toPositiveIntOrNull()
            // THEN
            result.assertNull()
        }
    }

    @Nested
    inner class ToNonZeroInt {
        @Test
        fun `should return its value as a non zero int with 1`() {
            // GIVEN
            val value = 1
            val x = PositiveInt(value)
            // WHEN
            val result: NonZeroInt = assertPass(x::toNonZeroInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with 0`() {
            // GIVEN
            val x = PositiveInt(0)
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(block = x::toNonZeroInt)
        }

        @Nested
        inner class OrNull {
            @Test
            fun `should return its value as a non zero int with 2`() {
                // GIVEN
                val value = 2
                val x = PositiveInt(value)
                // WHEN
                val result: NonZeroInt? = x.toNonZeroIntOrNull()
                // THEN
                result.assertNotNull().value assertEquals value
            }

            @Test
            fun `should return null with 0`() {
                // GIVEN
                val x = PositiveInt(0)
                // WHEN
                val result: NonZeroInt? = x.toNonZeroIntOrNull()
                // THEN
                result.assertNull()
            }
        }
    }

    @Nested
    inner class ToStrictlyPositiveInt {
        @Test
        fun `should return its value as a strictly positive int with 1`() {
            // GIVEN
            val value = 1
            val x = PositiveInt(value)
            // WHEN
            val result: StrictlyPositiveInt =
                assertPass(x::toStrictlyPositiveInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with 0`() {
            // GIVEN
            val x = PositiveInt(0)
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(
                block = x::toStrictlyPositiveInt
            )
        }

        @Nested
        inner class OrNull {
            @Test
            fun `should return its value as a strictly positive int with 2`() {
                // GIVEN
                val value = 2
                val x = PositiveInt(value)
                // WHEN
                val result: StrictlyPositiveInt? =
                    x.toStrictlyPositiveIntOrNull()
                // THEN
                result.assertNotNull().value assertEquals value
            }

            @Test
            fun `should return null with 0`() {
                // GIVEN
                val x = PositiveInt(0)
                // WHEN
                val result: StrictlyPositiveInt? =
                    x.toStrictlyPositiveIntOrNull()
                // THEN
                result.assertNull()
            }
        }
    }

    @Nested
    inner class ToNegativeInt {
        @Test
        fun `should return its value as a negative int with 0`() {
            // GIVEN
            val value = 0
            val x = PositiveInt(value)
            // WHEN
            val result: NegativeInt = assertPass(x::toNegativeInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with 1`() {
            // GIVEN
            val x = PositiveInt(1)
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(block = x::toNegativeInt)
        }

        @Nested
        inner class OrNull {
            @Test
            fun `should return its value as a negative int with 0`() {
                // GIVEN
                val value = 0
                val x = PositiveInt(value)
                // WHEN
                val result: NegativeInt? = x.toNegativeIntOrNull()
                // THEN
                result.assertNotNull().value assertEquals value
            }

            @Test
            fun `should return null with 2`() {
                // GIVEN
                val x = PositiveInt(2)
                // WHEN
                val result: NegativeInt? = x.toNegativeIntOrNull()
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
            val value = 1
            val x = PositiveInt(value)
            // WHEN
            val result: String = x.toString()
            // THEN
            result assertEquals "$value"
        }
    }

    @Nested
    inner class ToNotBlankString {
        @Test
        fun `should return its value as a not blank string`() {
            // GIVEN
            val value = 0
            val x = PositiveInt(value)
            // WHEN
            val result: NotBlankString = x.toNotBlankString()
            // THEN
            result.value assertEquals value.toString()
        }
    }
}

class PositiveIntSerializerTest {
    @Test
    fun `should serialize properly the class`() {
        // GIVEN
        val x = PositiveInt(1)
        // WHEN
        val result: String = Json.encodeToString(x)
        // THEN
        result assertEquals "$x"
    }

    @Test
    fun `should deserialize properly the class`() {
        // GIVEN
        val x = PositiveInt(0)
        val encodedString: String = Json.encodeToString(x)
        // WHEN
        val result: PositiveInt = Json.decodeFromString(encodedString)
        // THEN
        result.value assertEquals x.value
    }
}
