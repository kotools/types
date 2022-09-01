package kotools.types.number

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.*

class StrictlyPositiveIntTest {
    // ---------- Constants ----------

    @Test
    fun `the minimum value of StrictlyPositiveInt should be 1`(): Unit =
        StrictlyPositiveInt.min.value assertEquals 1

    @Test
    fun `the maximum value of StrictlyPositiveInt should be the maximum value of PositiveInt`(): Unit =
        StrictlyPositiveInt.max.value assertEquals PositiveInt.max.value

    // ---------- Builders ----------

    @Nested
    inner class Constructor {
        @Test
        fun `should pass with a strictly positive Int`() {
            // GIVEN
            val value: Int = StrictlyPositiveInt.range.random()
            // WHEN
            val result: StrictlyPositiveInt =
                assertPass { StrictlyPositiveInt(value) }
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with a negative Int`() {
            // GIVEN
            val value: Int = NegativeInt.range.random()
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException> {
                StrictlyPositiveInt(value)
            }
        }
    }

    @Nested
    inner class OrNull {
        @Test
        fun `should pass with a strictly positive Int`() {
            // GIVEN
            val value: Int = StrictlyPositiveInt.range.random()
            // WHEN
            val result: StrictlyPositiveInt? = StrictlyPositiveInt orNull value
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with a negative Int`() {
            // GIVEN
            val value: Int = NegativeInt.range.random()
            // WHEN
            val result: StrictlyPositiveInt? = StrictlyPositiveInt orNull value
            // THEN
            result.assertNull()
        }
    }

    // ---------- Unary operations ----------

    @Nested
    inner class Inc {
        @Test
        fun `should return 2 with 1`() {
            // GIVEN
            val value = 1
            var x = StrictlyPositiveInt(value)
            // WHEN
            x++
            // THEN
            x.value assertEquals value + 1
        }

        @Test
        fun `should return the minimum value with the maximum value`() {
            // GIVEN
            var x = StrictlyPositiveInt.max
            // WHEN
            x++
            // THEN
            x assertEquals StrictlyPositiveInt.min
        }
    }

    @Nested
    inner class Dec {
        @Test
        fun `should return 1 with 2`() {
            // GIVEN
            var x = StrictlyPositiveInt(2)
            // WHEN
            x--
            // THEN
            x.value assertEquals 1
        }

        @Test
        fun `should return the maximum value with the minimum value`() {
            // GIVEN
            var x = StrictlyPositiveInt.min
            // WHEN
            x--
            // THEN
            x assertEquals StrictlyPositiveInt.max
        }
    }

    @Nested
    inner class UnaryPlus {
        @Test
        fun `should return the same strictly positive int`() {
            // GIVEN
            val value = 1
            val x = StrictlyPositiveInt(value)
            // WHEN
            val result: StrictlyPositiveInt = +x
            // THEN
            result.value assertEquals value
        }
    }

    @Nested
    inner class UnaryMinus {
        @Test
        fun `should return a strictly negative int`() {
            // GIVEN
            val value = 1
            val x = StrictlyPositiveInt(value)
            // WHEN
            val result: StrictlyNegativeInt = -x
            // THEN
            result.value assertEquals -x.value
        }
    }

    // ---------- Binary operations ----------

    @Nested
    inner class Times {
        @Test
        fun `should return a NonZeroInt with a NonZeroInt`() {
            // GIVEN
            val x: StrictlyPositiveInt = StrictlyPositiveInt.range.random()
                .toStrictlyPositiveInt()
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
            val x: StrictlyPositiveInt = StrictlyPositiveInt.range.random()
                .toStrictlyPositiveInt()
            val y: StrictlyPositiveInt = StrictlyPositiveInt.range.random()
                .toStrictlyPositiveInt()
            // WHEN
            val result: NonZeroInt = x * y
            // THEN
            result.value assertEquals x.value * y.value
        }

        @Test
        fun `should return a NonZeroInt with a StrictlyNegativeInt`() {
            // GIVEN
            val x: StrictlyPositiveInt =
                StrictlyPositiveInt.range.random().toStrictlyPositiveInt()
            val y: StrictlyNegativeInt =
                StrictlyNegativeInt.range.random().toStrictlyNegativeInt()
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
        fun `should return a PositiveInt with a PositiveInt other than 0`() {
            // GIVEN
            val x: StrictlyPositiveInt = StrictlyPositiveInt.range.random()
                .toStrictlyPositiveInt()
            val y: PositiveInt = PositiveInt.range.random().toPositiveInt()
            // WHEN
            val result: PositiveInt = assertPass { x / y }
            // THEN
            result.value assertEquals x.value / y.value
        }

        @Test
        fun `should throw an error with a PositiveInt that equals 0`() {
            // GIVEN
            val x: StrictlyPositiveInt = StrictlyPositiveInt.range.random()
                .toStrictlyPositiveInt()
            val y = PositiveInt(0)
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        // ---------- StrictlyPositiveInt ----------

        @Test
        fun `should return a PositiveInt with a StrictlyPositiveInt`() {
            // GIVEN
            val x: StrictlyPositiveInt = StrictlyPositiveInt.range.random()
                .toStrictlyPositiveInt()
            val y: StrictlyPositiveInt = StrictlyPositiveInt.range.random()
                .toStrictlyPositiveInt()
            // WHEN
            val result: PositiveInt = x / y
            // THEN
            result.value assertEquals x.value / y.value
        }

        // ---------- NegativeInt ----------

        @Test
        fun `should return a NegativeInt with a NegativeInt other than 0`() {
            // GIVEN
            val x: StrictlyPositiveInt = StrictlyPositiveInt.range.random()
                .toStrictlyPositiveInt()
            val y: NegativeInt = NegativeInt.range.random().toNegativeInt()
            // WHEN
            val result: NegativeInt = assertPass { x / y }
            // THEN
            result.value assertEquals x.value / y.value
        }

        @Test
        fun `should throw an error with a NegativeInt that equals 0`() {
            // GIVEN
            val x: StrictlyPositiveInt = StrictlyPositiveInt.range.random()
                .toStrictlyPositiveInt()
            val y = NegativeInt(0)
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        // ---------- StrictlyNegativeInt ----------

        @Test
        fun `should return a NegativeInt with a StrictlyNegativeInt`() {
            // GIVEN
            val x: StrictlyPositiveInt =
                StrictlyPositiveInt.range.random().toStrictlyPositiveInt()
            val y: StrictlyNegativeInt =
                StrictlyNegativeInt.range.random().toStrictlyNegativeInt()
            // WHEN
            val result: NegativeInt = x / y
            // THEN
            result.value assertEquals x.value / y.value
        }
    }

    // ---------- Conversions ----------

    @Nested
    inner class StringToStrictlyPositiveInt {
        @Test
        fun `should pass with a string representation of 1`() {
            // GIVEN
            val value = 1
            val string = "$value"
            // WHEN
            val result: StrictlyPositiveInt =
                assertPass(string::toStrictlyPositiveInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with an invalid string`() {
            // GIVEN
            val string = ""
            // WHEN & THEN
            assertFailsWith<NumberFormatException>(
                string::toStrictlyPositiveInt
            )
        }

        @Test
        fun `should throw an error with a string representation of 0`() {
            // GIVEN
            val string = "0"
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(
                string::toStrictlyPositiveInt
            )
        }
    }

    @Nested
    inner class StringToStrictlyPositiveIntOrNull {
        @Test
        fun `should pass with a string representation of 1`() {
            // GIVEN
            val value = 1
            val string = "$value"
            // WHEN
            val result: StrictlyPositiveInt? =
                string.toStrictlyPositiveIntOrNull()
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with an invalid string`() {
            // GIVEN
            val string = ""
            // WHEN
            val result: StrictlyPositiveInt? =
                string.toStrictlyPositiveIntOrNull()
            // THEN
            result.assertNull()
        }

        @Test
        fun `should return null with a string representation of 0`() {
            // GIVEN
            val string = "0"
            // WHEN
            val result: StrictlyPositiveInt? =
                string.toStrictlyPositiveIntOrNull()
            // THEN
            result.assertNull()
        }
    }

    @Nested
    inner class ToNonZeroInt {
        @Test
        fun `should return its value as a non zero int`() {
            // GIVEN
            val value = 2
            val x = StrictlyPositiveInt(value)
            // WHEN
            val result: NonZeroInt = x.toNonZeroInt()
            // THEN
            result.value assertEquals value
        }
    }

    @Nested
    inner class ToPositiveInt {
        @Test
        fun `should return its value as a positive int`() {
            // GIVEN
            val value = 1
            val x = StrictlyPositiveInt(value)
            // WHEN
            val result: PositiveInt = x.toPositiveInt()
            // THEN
            result.value assertEquals value
        }
    }

    @Nested
    inner class ToString {
        @Test
        fun `should return its value as a string`() {
            // GIVEN
            val value = 1
            val x = StrictlyPositiveInt(value)
            // WHEN
            val result: String = x.toString()
            // THEN
            result assertEquals value.toString()
        }
    }
}

class StrictlyPositiveIntSerializerTest {
    @Test
    fun `should serialize properly the class`() {
        // GIVEN
        val x = StrictlyPositiveInt(1)
        // WHEN
        val result: String = Json.encodeToString(x)
        // THEN
        result assertEquals "$x"
    }

    @Test
    fun `should deserialize properly the class`() {
        // GIVEN
        val x = StrictlyPositiveInt(1)
        val encodedString: String = Json.encodeToString(x)
        // WHEN
        val result: StrictlyPositiveInt = Json.decodeFromString(encodedString)
        // THEN
        result.value assertEquals x.value
    }
}
