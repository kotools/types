package kotools.types.number

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.*

class StrictlyNegativeIntTest {
    // ---------- Constants & Getters ----------

    @Test
    fun `should the minimum value of StrictlyNegativeInt should be the minimum value of NegativeInt`(): Unit =
        StrictlyNegativeInt.min.value assertEquals NegativeIntJvm.min.value

    @Test
    fun `should the maximum value of StrictlyNegativeInt should be -1`(): Unit =
        StrictlyNegativeInt.max.value assertEquals -1

    @Test
    fun `the random getter should pass`(): Unit =
        StrictlyNegativeInt.random.value assertNotEquals StrictlyNegativeInt.random.value

    // ---------- Builders ----------

    @Nested
    inner class Constructor {
        @Test
        fun `should pass with a strictly negative Int`() {
            // GIVEN
            val value: Int = StrictlyNegativeInt.random.value
            // WHEN
            val result: StrictlyNegativeInt =
                assertPass { StrictlyNegativeInt(value) }
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with a positive Int`() {
            // GIVEN
            val value: Int = PositiveIntJvm.random.value
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException> {
                StrictlyNegativeInt(value)
            }
        }
    }

    @Nested
    inner class OrNull {
        @Test
        fun `should pass with a strictly negative Int`() {
            // GIVEN
            val value: Int = StrictlyNegativeInt.random.value
            // WHEN
            val result: StrictlyNegativeInt? =
                StrictlyNegativeInt orNull value
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with a positive Int`() {
            // GIVEN
            val value: Int = PositiveIntJvm.random.value
            // WHEN
            val result: StrictlyNegativeInt? =
                StrictlyNegativeInt orNull value
            // THEN
            result.assertNull()
        }
    }

    @Nested
    inner class StringToStrictlyNegativeInt {
        @Test
        fun `should pass with a string representation of a strictly negative Int`() {
            // GIVEN
            val value: Int = StrictlyNegativeInt.random.value
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
        fun `should throw an error with a string representation of a positive Int`() {
            // GIVEN
            val string = "${PositiveIntJvm.random.value}"
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(
                string::toStrictlyNegativeInt
            )
        }
    }

    @Nested
    inner class StringToStrictlyNegativeIntOrNull {
        @Test
        fun `should pass with a string representation of a strictly negative Int`() {
            // GIVEN
            val value: Int = StrictlyNegativeInt.random.value
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
        fun `should return null with a string representation of a positive Int`() {
            // GIVEN
            val string = "${PositiveIntJvm.random.value}"
            // WHEN
            val result: StrictlyNegativeInt? =
                string.toStrictlyNegativeIntOrNull()
            // THEN
            result.assertNull()
        }
    }

    // ---------- Unary operations ----------

    @Nested
    inner class Inc {
        @Test
        fun `should increment the value by 1 with a value other than the maximum value`() {
            // GIVEN
            var x: StrictlyNegativeInt = StrictlyNegativeInt.max
            while (x == StrictlyNegativeInt.max) x = StrictlyNegativeInt.random
            val oldValue: Int = x.value
            // WHEN
            x++
            // THEN
            x.value assertEquals oldValue + 1
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
        fun `should decrement the value by 1 with a value other than the minimum value`() {
            // GIVEN
            var x: StrictlyNegativeInt = StrictlyNegativeInt.min
            while (x == StrictlyNegativeInt.min) x = StrictlyNegativeInt.random
            val oldValue: Int = x.value
            // WHEN
            x--
            // THEN
            x.value assertEquals oldValue - 1
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

    @Test
    fun `unaryMinus() should return a StrictlyPositiveInt`() {
        // GIVEN
        val x: StrictlyNegativeInt = StrictlyNegativeInt.random
        // WHEN
        val result: StrictlyPositiveIntJvm = -x
        // THEN
        result.value assertEquals -x.value
    }

    // ---------- Binary operations ----------

    @Nested
    inner class Times {
        @Test
        fun `should return a NonZeroInt with a NonZeroInt`() {
            // GIVEN
            val x: StrictlyNegativeInt = StrictlyNegativeInt.random
            val y: NonZeroIntJvm = NonZeroIntJvm.random
            // WHEN
            val result: NonZeroIntJvm = x * y
            // THEN
            result.value assertEquals x.value * y.value
        }

        @Test
        fun `should return a NonZeroInt with a StrictlyPositiveInt`() {
            // GIVEN
            val x: StrictlyNegativeInt = StrictlyNegativeInt.random
            val y: StrictlyPositiveIntJvm = StrictlyPositiveIntJvm.random
            // WHEN
            val result: NonZeroIntJvm = x * y
            // THEN
            result.value assertEquals x.value * y.value
        }

        @Test
        fun `should return a NonZeroInt with a StrictlyNegativeInt`() {
            // GIVEN
            val x: StrictlyNegativeInt = StrictlyNegativeInt.random
            val y: StrictlyNegativeInt = StrictlyNegativeInt.random
            // WHEN
            val result: NonZeroIntJvm = x * y
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
            val x: StrictlyNegativeInt = StrictlyNegativeInt.random
            var value = 0
            while (value == 0) value = PositiveIntJvm.random.value
            val y = PositiveIntJvm(value)
            // WHEN
            val result: NegativeIntJvm = assertPass { x / y }
            // THEN
            result.value assertEquals x.value / y.value
        }

        @Test
        fun `should throw an error with a PositiveInt that equals 0`() {
            // GIVEN
            val x: StrictlyNegativeInt = StrictlyNegativeInt.random
            val y = PositiveIntJvm(0)
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        // ---------- StrictlyPositiveInt ----------

        @Test
        fun `should return a NegativeInt with a StrictlyPositiveInt`() {
            // GIVEN
            val x: StrictlyNegativeInt = StrictlyNegativeInt.random
            val y: StrictlyPositiveIntJvm = StrictlyPositiveIntJvm.random
            // WHEN
            val result: NegativeIntJvm = x / y
            // THEN
            result.value assertEquals x.value / y.value
        }

        // ---------- NegativeInt ----------

        @Test
        fun `should return a PositiveInt with a NegativeInt other than 0`() {
            // GIVEN
            val x: StrictlyNegativeInt = StrictlyNegativeInt.random
            var value = 0
            while (value == 0) value = NegativeIntJvm.random.value
            val y = NegativeIntJvm(value)
            // WHEN
            val result: PositiveIntJvm = assertPass { x / y }
            // THEN
            result.value assertEquals x.value / y.value
        }

        @Test
        fun `should throw an error with a NegativeInt that equals 0`() {
            // GIVEN
            val x: StrictlyNegativeInt = StrictlyNegativeInt.random
            val y = NegativeIntJvm(0)
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        // ---------- StrictlyNegativeInt ----------

        @Test
        fun `should return a PositiveInt with a StrictlyNegativeInt`() {
            // GIVEN
            val x: StrictlyNegativeInt = StrictlyNegativeInt.random
            val y: StrictlyNegativeInt = StrictlyNegativeInt.random
            // WHEN
            val result: PositiveIntJvm = x / y
            // THEN
            result.value assertEquals x.value / y.value
        }
    }

    // ---------- Conversions ----------

    @Test
    fun `toNonZeroInt() should pass`() {
        // GIVEN
        val x: StrictlyNegativeInt = StrictlyNegativeInt.random
        // WHEN
        val result: NonZeroIntJvm = x.toNonZeroInt()
        // THEN
        result.value assertEquals x.value
    }

    @Test
    fun `toNegativeInt() should pass`() {
        // GIVEN
        val x: StrictlyNegativeInt = StrictlyNegativeInt.random
        // WHEN
        val result: NegativeIntJvm = x.toNegativeInt()
        // THEN
        result.value assertEquals x.value
    }

    @Test
    fun `toString() should pass`() {
        // GIVEN
        val x: StrictlyNegativeInt = StrictlyNegativeInt.random
        // WHEN
        val result: String = x.toString()
        // THEN
        result assertEquals "${x.value}"
    }
}

class StrictlyNegativeIntSerializerTest {
    @Test
    fun `should serialize properly this class`() {
        // GIVEN
        val x: StrictlyNegativeInt = StrictlyNegativeInt.random
        // WHEN
        val result: String = Json.encodeToString(x)
        // THEN
        result assertEquals "$x"
    }

    @Test
    fun `should deserialize properly this class`() {
        // GIVEN
        val x: StrictlyNegativeInt = StrictlyNegativeInt.random
        val encodedString: String = Json.encodeToString(x)
        // WHEN
        val result: StrictlyNegativeInt = Json.decodeFromString(encodedString)
        // THEN
        result.value assertEquals x.value
    }
}
