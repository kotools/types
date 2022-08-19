package kotools.types.number

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.*

class StrictlyNegativeIntTest {
    @Nested
    inner class Constructor {
        @Test
        fun `should pass with -1`() {
            // GIVEN
            val value = -1
            // WHEN
            val result = assertPass { StrictlyNegativeInt(value) }
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
            val result: Int = assertPass { x / y }
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
            val result: NegativeInt = assertPass { x / y }
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
            val result: PositiveInt = assertPass { x / y }
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

    // ---------- Conversions ----------

    @Nested
    inner class StringToStrictlyNegativeInt {
        @Test
        fun `should pass with a string representation of -1`() {
            // GIVEN
            val value = -1
            val string = "$value"
            // WHEN
            val result: StrictlyNegativeInt =
                assertPass(string::toStrictlyNegativeInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with an invalid string`() {
            // GIVEN
            val string = ""
            // WHEN & THEN
            assertFailsWith<NumberFormatException>(
                string::toStrictlyNegativeInt
            )
        }

        @Test
        fun `should throw an error with a string representation of 0`() {
            // GIVEN
            val string = "0"
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(
                string::toStrictlyNegativeInt
            )
        }
    }

    @Nested
    inner class StringToStrictlyNegativeIntOrNull {
        @Test
        fun `should pass with a string representation of -1`() {
            // GIVEN
            val value = -1
            val string = "$value"
            // WHEN
            val result: StrictlyNegativeInt? =
                string.toStrictlyNegativeIntOrNull()
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with an invalid string`() {
            // GIVEN
            val string = ""
            // WHEN
            val result: StrictlyNegativeInt? =
                string.toStrictlyNegativeIntOrNull()
            // THEN
            result.assertNull()
        }

        @Test
        fun `should return null with a string representation of 0`() {
            // GIVEN
            val string = "0"
            // WHEN
            val result: StrictlyNegativeInt? =
                string.toStrictlyNegativeIntOrNull()
            // THEN
            result.assertNull()
        }
    }

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
}

class StrictlyNegativeIntSerializerTest {
    @Test
    fun `should serialize properly this class`() {
        // GIVEN
        val x = StrictlyNegativeInt(-1)
        // WHEN
        val result: String = Json.encodeToString(x)
        // THEN
        result assertEquals "$x"
    }

    @Test
    fun `should deserialize properly this class`() {
        // GIVEN
        val x = StrictlyNegativeInt(-1)
        val encodedString: String = Json.encodeToString(x)
        // WHEN
        val result: StrictlyNegativeInt = Json.decodeFromString(encodedString)
        // THEN
        result.value assertEquals x.value
    }
}
