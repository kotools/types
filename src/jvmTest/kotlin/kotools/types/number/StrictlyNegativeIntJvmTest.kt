package kotools.types.number

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.*

class StrictlyNegativeIntJvmTest {
    // ---------- Constants & Getters ----------

    @Test
    fun `should the minimum value of StrictlyNegativeInt should be the minimum value of NegativeInt`(): Unit =
        StrictlyNegativeIntJvm.min.value assertEquals NegativeIntJvm.min.value

    @Test
    fun `should the maximum value of StrictlyNegativeInt should be -1`(): Unit =
        StrictlyNegativeIntJvm.max.value assertEquals -1

    @Test
    fun `the random getter should pass`(): Unit =
        StrictlyNegativeIntJvm.random.value
            .assertNotEquals(StrictlyNegativeIntJvm.random.value)

    // ---------- Builders ----------

    @Nested
    inner class Constructor {
        @Test
        fun `should pass with a strictly negative Int`() {
            // GIVEN
            val value: Int = StrictlyNegativeIntJvm.random.value
            // WHEN
            val result: StrictlyNegativeIntJvm =
                assertPass { StrictlyNegativeIntJvm(value) }
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with a positive Int`() {
            // GIVEN
            val value: Int = PositiveIntJvm.random.value
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException> {
                StrictlyNegativeIntJvm(value)
            }
        }
    }

    @Nested
    inner class OrNull {
        @Test
        fun `should pass with a strictly negative Int`() {
            // GIVEN
            val value: Int = StrictlyNegativeIntJvm.random.value
            // WHEN
            val result: StrictlyNegativeIntJvm? =
                StrictlyNegativeIntJvm orNull value
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with a positive Int`() {
            // GIVEN
            val value: Int = PositiveIntJvm.random.value
            // WHEN
            val result: StrictlyNegativeIntJvm? =
                StrictlyNegativeIntJvm orNull value
            // THEN
            result.assertNull()
        }
    }

    @Nested
    inner class StringToStrictlyNegativeInt {
        @Test
        fun `should pass with a string representation of a strictly negative Int`() {
            // GIVEN
            val value: Int = StrictlyNegativeIntJvm.random.value
            val string = "$value"
            // WHEN
            val result: StrictlyNegativeIntJvm =
                assertPass(string::toStrictlyNegativeIntJvm)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with an invalid string`() {
            // GIVEN
            val string = ""
            // WHEN & THEN
            assertFailsWith<NumberFormatException>(
                string::toStrictlyNegativeIntJvm
            )
        }

        @Test
        fun `should throw an error with a string representation of a positive Int`() {
            // GIVEN
            val string = "${PositiveIntJvm.random.value}"
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(
                string::toStrictlyNegativeIntJvm
            )
        }
    }

    @Nested
    inner class StringToStrictlyNegativeIntOrNull {
        @Test
        fun `should pass with a string representation of a strictly negative Int`() {
            // GIVEN
            val value: Int = StrictlyNegativeIntJvm.random.value
            val string = "$value"
            // WHEN
            val result: StrictlyNegativeIntJvm? =
                string.toStrictlyNegativeIntJvmOrNull()
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with an invalid string`() {
            // GIVEN
            val string = ""
            // WHEN
            val result: StrictlyNegativeIntJvm? =
                string.toStrictlyNegativeIntJvmOrNull()
            // THEN
            result.assertNull()
        }

        @Test
        fun `should return null with a string representation of a positive Int`() {
            // GIVEN
            val string = "${PositiveIntJvm.random.value}"
            // WHEN
            val result: StrictlyNegativeIntJvm? =
                string.toStrictlyNegativeIntJvmOrNull()
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
            var x: StrictlyNegativeIntJvm = StrictlyNegativeIntJvm.max
            while (x == StrictlyNegativeIntJvm.max)
                x = StrictlyNegativeIntJvm.random
            val oldValue: Int = x.value
            // WHEN
            x++
            // THEN
            x.value assertEquals oldValue + 1
        }

        @Test
        fun `should return the minimum value with the maximum value`() {
            // GIVEN
            var x = StrictlyNegativeIntJvm.max
            // WHEN
            x++
            // THEN
            x assertEquals StrictlyNegativeIntJvm.min
        }
    }

    @Nested
    inner class Dec {
        @Test
        fun `should decrement the value by 1 with a value other than the minimum value`() {
            // GIVEN
            var x: StrictlyNegativeIntJvm = StrictlyNegativeIntJvm.min
            while (x == StrictlyNegativeIntJvm.min)
                x = StrictlyNegativeIntJvm.random
            val oldValue: Int = x.value
            // WHEN
            x--
            // THEN
            x.value assertEquals oldValue - 1
        }

        @Test
        fun `should return the maximum value with the minimum value`() {
            // GIVEN
            var x = StrictlyNegativeIntJvm.min
            // WHEN
            x--
            // THEN
            x assertEquals StrictlyNegativeIntJvm.max
        }
    }

    @Test
    fun `unaryMinus() should return a StrictlyPositiveInt`() {
        // GIVEN
        val x: StrictlyNegativeIntJvm = StrictlyNegativeIntJvm.random
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
            val x: StrictlyNegativeIntJvm = StrictlyNegativeIntJvm.random
            val y: NonZeroIntJvm = NonZeroIntJvm.random
            // WHEN
            val result: NonZeroIntJvm = x * y
            // THEN
            result.value assertEquals x.value * y.value
        }

        @Test
        fun `should return a NonZeroInt with a StrictlyPositiveInt`() {
            // GIVEN
            val x: StrictlyNegativeIntJvm = StrictlyNegativeIntJvm.random
            val y: StrictlyPositiveIntJvm = StrictlyPositiveIntJvm.random
            // WHEN
            val result: NonZeroIntJvm = x * y
            // THEN
            result.value assertEquals x.value * y.value
        }

        @Test
        fun `should return a NonZeroInt with a StrictlyNegativeInt`() {
            // GIVEN
            val x: StrictlyNegativeIntJvm = StrictlyNegativeIntJvm.random
            val y: StrictlyNegativeIntJvm = StrictlyNegativeIntJvm.random
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
            val x: StrictlyNegativeIntJvm = StrictlyNegativeIntJvm.random
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
            val x: StrictlyNegativeIntJvm = StrictlyNegativeIntJvm.random
            val y = PositiveIntJvm(0)
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        // ---------- StrictlyPositiveInt ----------

        @Test
        fun `should return a NegativeInt with a StrictlyPositiveInt`() {
            // GIVEN
            val x: StrictlyNegativeIntJvm = StrictlyNegativeIntJvm.random
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
            val x: StrictlyNegativeIntJvm = StrictlyNegativeIntJvm.random
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
            val x: StrictlyNegativeIntJvm = StrictlyNegativeIntJvm.random
            val y = NegativeIntJvm(0)
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        // ---------- StrictlyNegativeInt ----------

        @Test
        fun `should return a PositiveInt with a StrictlyNegativeInt`() {
            // GIVEN
            val x: StrictlyNegativeIntJvm = StrictlyNegativeIntJvm.random
            val y: StrictlyNegativeIntJvm = StrictlyNegativeIntJvm.random
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
        val x: StrictlyNegativeIntJvm = StrictlyNegativeIntJvm.random
        // WHEN
        val result: NonZeroIntJvm = x.toNonZeroInt()
        // THEN
        result.value assertEquals x.value
    }

    @Test
    fun `toNegativeInt() should pass`() {
        // GIVEN
        val x: StrictlyNegativeIntJvm = StrictlyNegativeIntJvm.random
        // WHEN
        val result: NegativeIntJvm = x.toNegativeInt()
        // THEN
        result.value assertEquals x.value
    }

    @Test
    fun `toString() should pass`() {
        // GIVEN
        val x: StrictlyNegativeIntJvm = StrictlyNegativeIntJvm.random
        // WHEN
        val result: String = x.toString()
        // THEN
        result assertEquals "${x.value}"
    }
}

class StrictlyNegativeIntJvmSerializerTest {
    @Test
    fun `should serialize properly this class`() {
        // GIVEN
        val x: StrictlyNegativeIntJvm = StrictlyNegativeIntJvm.random
        // WHEN
        val result: String = Json.encodeToString(x)
        // THEN
        result assertEquals "$x"
    }

    @Test
    fun `should deserialize properly this class`() {
        // GIVEN
        val x: StrictlyNegativeIntJvm = StrictlyNegativeIntJvm.random
        val encodedString: String = Json.encodeToString(x)
        // WHEN
        val result: StrictlyNegativeIntJvm =
            Json.decodeFromString(encodedString)
        // THEN
        result.value assertEquals x.value
    }
}
