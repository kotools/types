package kotools.types.number

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.*

class NegativeIntTest {
    // ---------- Constants & Getters ----------

    @Test
    fun `the minimum value should be the minimum value of Int`(): Unit =
        NegativeIntJvm.min.value assertEquals Int.MIN_VALUE

    @Test
    fun `the maximum value should be 0`(): Unit =
        NegativeIntJvm.max.value assertEquals 0

    @Test
    fun `the random getter should pass`(): Unit =
        NegativeIntJvm.random.value assertNotEquals NegativeIntJvm.random.value

    // ---------- Builders ----------

    @Nested
    inner class Constructor {
        @Test
        fun `should pass with a negative Int`() {
            // GIVEN
            val value: Int = NegativeIntJvm.random.value
            // WHEN
            val result: NegativeIntJvm = assertPass { NegativeIntJvm(value) }
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with a strictly positive Int`() {
            // GIVEN
            var value = 0
            while (value == 0) value = -NegativeIntJvm.random.value
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException> { NegativeIntJvm(value) }
        }
    }

    @Nested
    inner class OrNull {
        @Test
        fun `should pass with a negative Int`() {
            // GIVEN
            val value: Int = NegativeIntJvm.random.value
            // WHEN
            val result: NegativeIntJvm? = NegativeIntJvm orNull value
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with a strictly positive Int`() {
            // GIVEN
            var value = 0
            while (value == 0) value = -NegativeIntJvm.random.value
            // WHEN
            val result: NegativeIntJvm? = NegativeIntJvm orNull value
            // THEN
            result.assertNull()
        }
    }

    @Nested
    inner class StringToNegativeInt {
        @Test
        fun `should pass with a string representation of a negative Int`() {
            // GIVEN
            val value: Int = NegativeIntJvm.random.value
            val string = "$value"
            // WHEN
            val result: NegativeIntJvm = assertPass(string::toNegativeIntJvm)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with an invalid string`() {
            // GIVEN
            val string = ""
            // WHEN & THEN
            assertFailsWith<NumberFormatException>(string::toNegativeIntJvm)
        }

        @Test
        fun `should throw an error with a string representation of strictly positive Int`() {
            // GIVEN
            val string = "${StrictlyPositiveIntJvm.random.value}"
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(string::toNegativeIntJvm)
        }
    }

    @Nested
    inner class StringToNegativeIntOrNull {
        @Test
        fun `should pass with a string representation of a negative Int`() {
            // GIVEN
            val value: Int = NegativeIntJvm.random.value
            val string = "$value"
            // WHEN
            val result: NegativeIntJvm? = string.toNegativeIntJvmOrNull()
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with an invalid string`() {
            // GIVEN
            val string = ""
            // WHEN
            val result: NegativeIntJvm? = string.toNegativeIntJvmOrNull()
            // THEN
            result.assertNull()
        }

        @Test
        fun `should return null with a string representation of a strictly positive Int`() {
            // GIVEN
            val string = "${StrictlyPositiveIntJvm.random.value}"
            // WHEN
            val result: NegativeIntJvm? = string.toNegativeIntJvmOrNull()
            // THEN
            result.assertNull()
        }
    }

    // ---------- Unary operations ----------

    @Nested
    inner class Increment {
        @Test
        fun `should increment the value by 1 with a value other than the maximum value`() {
            // GIVEN
            var x: NegativeIntJvm = NegativeIntJvm.max
            while (x == NegativeIntJvm.max) x = NegativeIntJvm.random
            val oldValue: Int = x.value
            // WHEN
            x++
            // THEN
            x.value assertEquals oldValue + 1
        }

        @Test
        fun `should return the minimum value with the maximum value`() {
            // GIVEN
            var x = NegativeIntJvm.max
            // WHEN
            x++
            // THEN
            x assertEquals NegativeIntJvm.min
        }
    }

    @Nested
    inner class Decrement {
        @Test
        fun `should decrement the value by 1 with a value other than the minimum value`() {
            // GIVEN
            var x: NegativeIntJvm = NegativeIntJvm.min
            while (x == NegativeIntJvm.min) x = NegativeIntJvm.random
            val oldValue: Int = x.value
            // WHEN
            x--
            // THEN
            x.value assertEquals oldValue - 1
        }

        @Test
        fun `should return the maximum value with the minimum value`() {
            // GIVEN
            var x = NegativeIntJvm.min
            // WHEN
            x--
            // THEN
            x assertEquals NegativeIntJvm.max
        }
    }

    @Test
    fun `unaryMinus() should return a PositiveInt`() {
        // GIVEN
        val x: NegativeIntJvm = NegativeIntJvm.random
        // WHEN
        val result: PositiveIntJvm = -x
        // THEN
        result.value assertEquals -x.value
    }

    // ---------- Binary operations ----------

    @Nested
    inner class Div {
        // ---------- PositiveInt ----------

        @Test
        fun `should return a NegativeInt with a PositiveInt other than 0`() {
            // GIVEN
            val x: NegativeIntJvm = NegativeIntJvm.random
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
            val x: NegativeIntJvm = NegativeIntJvm.random
            val y = PositiveIntJvm(0)
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        // ---------- StrictlyPositiveInt ----------

        @Test
        fun `should return a NegativeInt with a StrictlyPositiveInt`() {
            // GIVEN
            val x: NegativeIntJvm = NegativeIntJvm.random
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
            val x: NegativeIntJvm = NegativeIntJvm.random
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
            val x: NegativeIntJvm = NegativeIntJvm.random
            val y = NegativeIntJvm(0)
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        // ---------- StrictlyNegativeInt ----------

        @Test
        fun `should return a PositiveInt with a StrictlyNegativeInt`() {
            // GIVEN
            val x: NegativeIntJvm = NegativeIntJvm.random
            val y: StrictlyNegativeInt = StrictlyNegativeInt.random
            // WHEN
            val result: PositiveIntJvm = x / y
            // THEN
            result.value assertEquals x.value / y.value
        }
    }

    // ---------- Conversions ----------

    @Nested
    inner class ToNonZeroInt {
        @Test
        fun `should pass with a NegativeInt holding a strictly negative Int`() {
            // GIVEN
            val value: Int = StrictlyNegativeInt.random.value
            val x = NegativeIntJvm(value)
            // WHEN
            val result: NonZeroIntJvm = assertPass(x::toNonZeroInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with 0`() {
            // GIVEN
            val x = NegativeIntJvm(0)
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(block = x::toNonZeroInt)
        }
    }

    @Nested
    inner class ToNonZeroIntOrNull {
        @Test
        fun `should pass with a NegativeInt holding a strictly negative Int`() {
            // GIVEN
            val value: Int = StrictlyNegativeInt.random.value
            val x = NegativeIntJvm(value)
            // WHEN
            val result: NonZeroIntJvm? = x.toNonZeroIntOrNull()
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with 0`() {
            // GIVEN
            val x = NegativeIntJvm(0)
            // WHEN
            val result: NonZeroIntJvm? = x.toNonZeroIntOrNull()
            // THEN
            result.assertNull()
        }
    }

    @Nested
    inner class ToPositiveInt {
        @Test
        fun `should pass with 0`() {
            // GIVEN
            val value = 0
            val x = NegativeIntJvm(value)
            // WHEN
            val result: PositiveIntJvm = assertPass(x::toPositiveInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with a NegativeInt holding a strictly negative Int`() {
            // GIVEN
            val x = NegativeIntJvm(StrictlyNegativeInt.random.value)
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(block = x::toPositiveInt)
        }
    }

    @Nested
    inner class ToPositiveIntOrNull {
        @Test
        fun `should pass with 0`() {
            // GIVEN
            val value = 0
            val x = NegativeIntJvm(value)
            // WHEN
            val result: PositiveIntJvm? = x.toPositiveIntOrNull()
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with a NegativeInt holding a strictly negative Int`() {
            // GIVEN
            val x = NegativeIntJvm(StrictlyNegativeInt.random.value)
            // WHEN
            val result: PositiveIntJvm? = x.toPositiveIntOrNull()
            // THEN
            result.assertNull()
        }
    }

    @Nested
    inner class ToStrictlyNegativeInt {
        @Test
        fun `should pass with a NegativeInt holding a strictly negative Int`() {
            // GIVEN
            val value: Int = StrictlyNegativeInt.random.value
            val x = NegativeIntJvm(value)
            // WHEN
            val result: StrictlyNegativeInt =
                assertPass(x::toStrictlyNegativeInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with 0`() {
            // GIVEN
            val x = NegativeIntJvm(0)
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(
                block = x::toStrictlyNegativeInt
            )
        }
    }

    @Nested
    inner class ToStrictlyNegativeIntOrNull {
        @Test
        fun `should pass with a NegativeInt holding a strictly negative Int`() {
            // GIVEN
            val value: Int = StrictlyNegativeInt.random.value
            val x = NegativeIntJvm(value)
            // WHEN
            val result: StrictlyNegativeInt? =
                x.toStrictlyNegativeIntOrNull()
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with 0`() {
            // GIVEN
            val x = NegativeIntJvm(0)
            // WHEN
            val result: StrictlyNegativeInt? =
                x.toStrictlyNegativeIntOrNull()
            // THEN
            result.assertNull()
        }
    }

    @Test
    fun `toString() should return its value as a string`() {
        // GIVEN
        val x: NegativeIntJvm = NegativeIntJvm.random
        // WHEN
        val result: String = x.toString()
        // THEN
        result assertEquals "${x.value}"
    }
}

class NegativeIntSerializerTest {
    @Test
    fun `should serialize properly the class`() {
        // GIVEN
        val x: NegativeIntJvm = NegativeIntJvm.random
        // WHEN
        val result: String = Json.encodeToString(x)
        // THEN
        result assertEquals "$x"
    }

    @Test
    fun `should deserialize properly the class`() {
        // GIVEN
        val x: NegativeIntJvm = NegativeIntJvm.random
        val encodedString: String = Json.encodeToString(x)
        // WHEN
        val result: NegativeIntJvm = Json.decodeFromString(encodedString)
        // THEN
        result.value assertEquals x.value
    }
}
