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
    inner class Times {
        @Test
        fun `should return a NonZeroInt with a NonZeroInt`() {
            // GIVEN
            val x: StrictlyNegativeInt = (NegativeInt.min.value..-1)
                .random()
                .toStrictlyNegativeInt()
            val y: NonZeroInt = NonZeroInt.ranges.random()
                .random()
                .toNonZeroInt()
            // WHEN
            val result: NonZeroInt = x * y
            // THEN
            result.value assertEquals x.value * y.value
        }

        @Test
        fun `should return a NonZeroInt with a StrictlyPositiveInt`() {
            // GIVEN
            val x: StrictlyNegativeInt = (NegativeInt.min.value..-1)
                .random()
                .toStrictlyNegativeInt()
            val y: StrictlyPositiveInt = (1..PositiveInt.max.value)
                .random()
                .toStrictlyPositiveInt()
            // WHEN
            val result: NonZeroInt = x * y
            // THEN
            result.value assertEquals x.value * y.value
        }

        @Test
        fun `should return a NonZeroInt with a StrictlyNegativeInt`() {
            // GIVEN
            val range: IntRange = NegativeInt.min.value..-1
            val x: StrictlyNegativeInt = range.random().toStrictlyNegativeInt()
            val y: StrictlyNegativeInt = range.random().toStrictlyNegativeInt()
            // WHEN
            val result: NonZeroInt = x * y
            // THEN
            result.value assertEquals x.value * y.value
        }
    }

    @Nested
    inner class Div {
        // ---------- PositiveInt ----------

        @Test
        fun `should return a NegativeInt with a PositiveInt other than 0`() {
            // GIVEN
            val x: StrictlyNegativeInt = (NegativeInt.min.value..-1)
                .random()
                .toStrictlyNegativeInt()
            var value = 0
            while (value == 0) value = PositiveInt.range.random()
            val y = PositiveInt(value)
            // WHEN
            val result: NegativeInt = assertPass { x / y }
            // THEN
            result.value assertEquals x.value / y.value
        }

        @Test
        fun `should throw an error with a PositiveInt that equals 0`() {
            // GIVEN
            val x: StrictlyNegativeInt = (NegativeInt.min.value..-1)
                .random()
                .toStrictlyNegativeInt()
            val y = PositiveInt(0)
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        // ---------- StrictlyPositiveInt ----------

        @Test
        fun `should return a NegativeInt with a StrictlyPositiveInt`() {
            // GIVEN
            val x: StrictlyNegativeInt = (NegativeInt.min.value..-1)
                .random()
                .toStrictlyNegativeInt()
            val y: StrictlyPositiveInt = (1..PositiveInt.max.value)
                .random()
                .toStrictlyPositiveInt()
            // WHEN
            val result: NegativeInt = x / y
            // THEN
            result.value assertEquals x.value / y.value
        }

        // ---------- NegativeInt ----------

        @Test
        fun `should return a PositiveInt with a NegativeInt other than 0`() {
            // GIVEN
            val x: StrictlyNegativeInt = (NegativeInt.min.value..-1)
                .random()
                .toStrictlyNegativeInt()
            var value = 0
            while (value == 0) value = NegativeInt.range.random()
            val y = NegativeInt(value)
            // WHEN
            val result: PositiveInt = assertPass { x / y }
            // THEN
            result.value assertEquals x.value / y.value
        }

        @Test
        fun `should throw an error with a NegativeInt that equals 0`() {
            // GIVEN
            val x: StrictlyNegativeInt = (NegativeInt.min.value..-1)
                .random()
                .toStrictlyNegativeInt()
            val y = NegativeInt(0)
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        // ---------- StrictlyNegativeInt ----------

        @Test
        fun `should return a PositiveInt with a StrictlyNegativeInt`() {
            // GIVEN
            val range: IntRange = NegativeInt.min.value..-1
            val x: StrictlyNegativeInt = range.random().toStrictlyNegativeInt()
            val y: StrictlyNegativeInt = range.random().toStrictlyNegativeInt()
            // WHEN
            val result: PositiveInt = x / y
            // THEN
            result.value assertEquals x.value / y.value
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
