package kotools.types.number

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.*

class StrictlyPositiveIntJvmTest {
    // ---------- Constants & Getters ----------

    @Test
    fun `the minimum value of StrictlyPositiveInt should be 1`(): Unit =
        StrictlyPositiveIntJvm.min.value assertEquals 1

    @Test
    fun `the maximum value of StrictlyPositiveInt should be the maximum value of PositiveInt`(): Unit =
        StrictlyPositiveIntJvm.max.value assertEquals PositiveIntJvm.max.value

    @Test
    fun `the random getter should pass`(): Unit =
        StrictlyPositiveIntJvm.random.value assertNotEquals StrictlyPositiveIntJvm.random.value

    // ---------- Builders ----------

    @Nested
    inner class Constructor {
        @Test
        fun `should pass with a strictly positive Int`() {
            // GIVEN
            val value: Int = StrictlyPositiveIntJvm.random.value
            // WHEN
            val result: StrictlyPositiveIntJvm =
                assertPass { StrictlyPositiveIntJvm(value) }
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with a negative Int`() {
            // GIVEN
            val value: Int = NegativeInt.random.value
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException> {
                StrictlyPositiveIntJvm(value)
            }
        }
    }

    @Nested
    inner class OrNull {
        @Test
        fun `should pass with a strictly positive Int`() {
            // GIVEN
            val value: Int = StrictlyPositiveIntJvm.random.value
            // WHEN
            val result: StrictlyPositiveIntJvm? =
                StrictlyPositiveIntJvm orNull value
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with a negative Int`() {
            // GIVEN
            val value: Int = NegativeInt.random.value
            // WHEN
            val result: StrictlyPositiveIntJvm? =
                StrictlyPositiveIntJvm orNull value
            // THEN
            result.assertNull()
        }
    }

    @Nested
    inner class StringToStrictlyPositiveInt {
        @Test
        fun `should pass with a string representation of a strictly positive Int`() {
            // GIVEN
            val value: Int = StrictlyPositiveIntJvm.random.value
            val string = "$value"
            // WHEN
            val result: StrictlyPositiveIntJvm =
                assertPass(string::toStrictlyPositiveIntJvm)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with an invalid string`() {
            // GIVEN
            val string = ""
            // WHEN & THEN
            assertFailsWith<NumberFormatException>(
                string::toStrictlyPositiveIntJvm
            )
        }

        @Test
        fun `should throw an error with a string representation of a negative Int`() {
            // GIVEN
            val string = "${NegativeInt.random.value}"
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(
                string::toStrictlyPositiveIntJvm
            )
        }
    }

    @Nested
    inner class StringToStrictlyPositiveIntOrNull {
        @Test
        fun `should pass with a string representation of a strictly positive Int`() {
            // GIVEN
            val value: Int = StrictlyPositiveIntJvm.random.value
            val string = "$value"
            // WHEN
            val result: StrictlyPositiveIntJvm? =
                string.toStrictlyPositiveIntJvmOrNull()
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with an invalid string`() {
            // GIVEN
            val string = ""
            // WHEN
            val result: StrictlyPositiveIntJvm? =
                string.toStrictlyPositiveIntJvmOrNull()
            // THEN
            result.assertNull()
        }

        @Test
        fun `should return null with a string representation of a negative Int`() {
            // GIVEN
            val string = "${NegativeInt.random.value}"
            // WHEN
            val result: StrictlyPositiveIntJvm? =
                string.toStrictlyPositiveIntJvmOrNull()
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
            var x: StrictlyPositiveIntJvm = StrictlyPositiveIntJvm.max
            while (x == StrictlyPositiveIntJvm.max) x =
                StrictlyPositiveIntJvm.random
            val oldValue: Int = x.value
            // WHEN
            x++
            // THEN
            x.value assertEquals oldValue + 1
        }

        @Test
        fun `should return the minimum value with the maximum value`() {
            // GIVEN
            var x = StrictlyPositiveIntJvm.max
            // WHEN
            x++
            // THEN
            x assertEquals StrictlyPositiveIntJvm.min
        }
    }

    @Nested
    inner class Dec {
        @Test
        fun `should decrement the value by 1 with a value other than the minimum value`() {
            // GIVEN
            var x: StrictlyPositiveIntJvm = StrictlyPositiveIntJvm.min
            while (x == StrictlyPositiveIntJvm.min) x =
                StrictlyPositiveIntJvm.random
            val oldValue: Int = x.value
            // WHEN
            x--
            // THEN
            x.value assertEquals oldValue - 1
        }

        @Test
        fun `should return the maximum value with the minimum value`() {
            // GIVEN
            var x = StrictlyPositiveIntJvm.min
            // WHEN
            x--
            // THEN
            x assertEquals StrictlyPositiveIntJvm.max
        }
    }

    @Test
    fun `unaryMinus() should return a StrictlyNegativeInt`() {
        // GIVEN
        val x: StrictlyPositiveIntJvm = StrictlyPositiveIntJvm.random
        // WHEN
        val result: StrictlyNegativeInt = -x
        // THEN
        result.value assertEquals -x.value
    }

    // ---------- Binary operations ----------

    @Nested
    inner class Times {
        @Test
        fun `should return a NonZeroInt with a NonZeroInt`() {
            // GIVEN
            val x: StrictlyPositiveIntJvm = StrictlyPositiveIntJvm.random
            val y: NonZeroIntJvm = NonZeroIntJvm.random
            // WHEN
            val result: NonZeroIntJvm = x * y
            // THEN
            result.value assertEquals x.value * y.value
        }

        @Test
        fun `should return a NonZeroInt with a StrictlyPositiveInt`() {
            // GIVEN
            val x: StrictlyPositiveIntJvm = StrictlyPositiveIntJvm.random
            val y: StrictlyPositiveIntJvm = StrictlyPositiveIntJvm.random
            // WHEN
            val result: NonZeroIntJvm = x * y
            // THEN
            result.value assertEquals x.value * y.value
        }

        @Test
        fun `should return a NonZeroInt with a StrictlyNegativeInt`() {
            // GIVEN
            val x: StrictlyPositiveIntJvm = StrictlyPositiveIntJvm.random
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
        fun `should return a PositiveInt with a PositiveInt other than 0`() {
            // GIVEN
            val x: StrictlyPositiveIntJvm = StrictlyPositiveIntJvm.random
            val y: PositiveIntJvm = PositiveIntJvm.random
            // WHEN
            val result: PositiveIntJvm = assertPass { x / y }
            // THEN
            result.value assertEquals x.value / y.value
        }

        @Test
        fun `should throw an error with a PositiveInt that equals 0`() {
            // GIVEN
            val x: StrictlyPositiveIntJvm = StrictlyPositiveIntJvm.random
            val y = PositiveIntJvm(0)
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        // ---------- StrictlyPositiveInt ----------

        @Test
        fun `should return a PositiveInt with a StrictlyPositiveInt`() {
            // GIVEN
            val x: StrictlyPositiveIntJvm = StrictlyPositiveIntJvm.random
            val y: StrictlyPositiveIntJvm = StrictlyPositiveIntJvm.random
            // WHEN
            val result: PositiveIntJvm = x / y
            // THEN
            result.value assertEquals x.value / y.value
        }

        // ---------- NegativeInt ----------

        @Test
        fun `should return a NegativeInt with a NegativeInt other than 0`() {
            // GIVEN
            val x: StrictlyPositiveIntJvm = StrictlyPositiveIntJvm.random
            val y: NegativeInt = NegativeInt.random
            // WHEN
            val result: NegativeInt = assertPass { x / y }
            // THEN
            result.value assertEquals x.value / y.value
        }

        @Test
        fun `should throw an error with a NegativeInt that equals 0`() {
            // GIVEN
            val x: StrictlyPositiveIntJvm = StrictlyPositiveIntJvm.random
            val y = NegativeInt(0)
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        // ---------- StrictlyNegativeInt ----------

        @Test
        fun `should return a NegativeInt with a StrictlyNegativeInt`() {
            // GIVEN
            val x: StrictlyPositiveIntJvm = StrictlyPositiveIntJvm.random
            val y: StrictlyNegativeInt = StrictlyNegativeInt.random
            // WHEN
            val result: NegativeInt = x / y
            // THEN
            result.value assertEquals x.value / y.value
        }
    }

    // ---------- Conversions ----------

    @Test
    fun `toNonZeroInt() should pass`() {
        // GIVEN
        val x: StrictlyPositiveIntJvm = StrictlyPositiveIntJvm.random
        // WHEN
        val result: NonZeroIntJvm = x.toNonZeroInt()
        // THEN
        result.value assertEquals x.value
    }

    @Test
    fun `toPositiveInt() should pass`() {
        // GIVEN
        val x: StrictlyPositiveIntJvm = StrictlyPositiveIntJvm.random
        // WHEN
        val result: PositiveIntJvm = x.toPositiveInt()
        // THEN
        result.value assertEquals x.value
    }

    @Test
    fun `toString() should pass`() {
        // GIVEN
        val x: StrictlyPositiveIntJvm = StrictlyPositiveIntJvm.random
        // WHEN
        val result: String = x.toString()
        // THEN
        result assertEquals "${x.value}"
    }
}

class StrictlyPositiveIntJvmSerializerTest {
    @Test
    fun `should serialize properly the class`() {
        // GIVEN
        val x: StrictlyPositiveIntJvm = StrictlyPositiveIntJvm.random
        // WHEN
        val result: String = Json.encodeToString(x)
        // THEN
        result assertEquals "$x"
    }

    @Test
    fun `should deserialize properly the class`() {
        // GIVEN
        val x: StrictlyPositiveIntJvm = StrictlyPositiveIntJvm.random
        val encodedString: String = Json.encodeToString(x)
        // WHEN
        val result: StrictlyPositiveIntJvm =
            Json.decodeFromString(encodedString)
        // THEN
        result.value assertEquals x.value
    }
}
