package kotools.types.number

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.*

class NegativeIntTest {
    // ---------- Constants & Getters ----------

    @Test
    fun `the minimum value should be the minimum value of Int`(): Unit =
        NegativeInt.min.value assertEquals Int.MIN_VALUE

    @Test
    fun `the maximum value should be 0`(): Unit =
        NegativeInt.max.value assertEquals 0

    @Test
    fun `the random getter should pass`(): Unit =
        NegativeInt.random.value assertNotEquals NegativeInt.random.value

    // ---------- Builders ----------

    @Nested
    inner class Constructor {
        @Test
        fun `should pass with a negative Int`() {
            // GIVEN
            val value: Int = NegativeInt.random.value
            // WHEN
            val result: NegativeInt = assertPass { NegativeInt(value) }
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with a strictly positive Int`() {
            // GIVEN
            var value = 0
            while (value == 0) value = -NegativeInt.random.value
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException> { NegativeInt(value) }
        }
    }

    @Nested
    inner class OrNull {
        @Test
        fun `should pass with a negative Int`() {
            // GIVEN
            val value: Int = NegativeInt.random.value
            // WHEN
            val result: NegativeInt? = NegativeInt orNull value
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with a strictly positive Int`() {
            // GIVEN
            var value = 0
            while (value == 0) value = -NegativeInt.random.value
            // WHEN
            val result: NegativeInt? = NegativeInt orNull value
            // THEN
            result.assertNull()
        }
    }

    @Nested
    inner class StringToNegativeInt {
        @Test
        fun `should pass with a string representation of a negative Int`() {
            // GIVEN
            val value: Int = NegativeInt.random.value
            val string = "$value"
            // WHEN
            val result: NegativeInt = assertPass(string::toNegativeInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with an invalid string`() {
            // GIVEN
            val string = ""
            // WHEN & THEN
            assertFailsWith<NumberFormatException>(string::toNegativeInt)
        }

        @Test
        fun `should throw an error with a string representation of strictly positive Int`() {
            // GIVEN
            val string = "${StrictlyPositiveInt.random.value}"
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(string::toNegativeInt)
        }
    }

    @Nested
    inner class StringToNegativeIntOrNull {
        @Test
        fun `should pass with a string representation of a negative Int`() {
            // GIVEN
            val value: Int = NegativeInt.random.value
            val string = "$value"
            // WHEN
            val result: NegativeInt? = string.toNegativeIntOrNull()
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with an invalid string`() {
            // GIVEN
            val string = ""
            // WHEN
            val result: NegativeInt? = string.toNegativeIntOrNull()
            // THEN
            result.assertNull()
        }

        @Test
        fun `should return null with a string representation of a strictly positive Int`() {
            // GIVEN
            val string = "${StrictlyPositiveInt.random.value}"
            // WHEN
            val result: NegativeInt? = string.toNegativeIntOrNull()
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
            var x: NegativeInt = NegativeInt.max
            while (x == NegativeInt.max) x = NegativeInt.random
            val oldValue: Int = x.value
            // WHEN
            x++
            // THEN
            x.value assertEquals oldValue + 1
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
        fun `should decrement the value by 1 with a value other than the minimum value`() {
            // GIVEN
            var x: NegativeInt = NegativeInt.min
            while (x == NegativeInt.min) x = NegativeInt.random
            val oldValue: Int = x.value
            // WHEN
            x--
            // THEN
            x.value assertEquals oldValue - 1
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

    @Test
    fun `unaryMinus() should return a PositiveInt`() {
        // GIVEN
        val x: NegativeInt = NegativeInt.random
        // WHEN
        val result: PositiveInt = -x
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
            val x: NegativeInt = NegativeInt.random
            var value = 0
            while (value == 0) value = PositiveInt.random.value
            val y = PositiveInt(value)
            // WHEN
            val result: NegativeInt = assertPass { x / y }
            // THEN
            result.value assertEquals x.value / y.value
        }

        @Test
        fun `should throw an error with a PositiveInt that equals 0`() {
            // GIVEN
            val x: NegativeInt = NegativeInt.random
            val y = PositiveInt(0)
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        // ---------- StrictlyPositiveInt ----------

        @Test
        fun `should return a NegativeInt with a StrictlyPositiveInt`() {
            // GIVEN
            val x: NegativeInt = NegativeInt.random
            val y: StrictlyPositiveInt = StrictlyPositiveInt.random
            // WHEN
            val result: NegativeInt = x / y
            // THEN
            result.value assertEquals x.value / y.value
        }

        // ---------- NegativeInt ----------

        @Test
        fun `should return a PositiveInt with a NegativeInt other than 0`() {
            // GIVEN
            val x: NegativeInt = NegativeInt.random
            var value = 0
            while (value == 0) value = NegativeInt.random.value
            val y = NegativeInt(value)
            // WHEN
            val result: PositiveInt = assertPass { x / y }
            // THEN
            result.value assertEquals x.value / y.value
        }

        @Test
        fun `should throw an error with a NegativeInt that equals 0`() {
            // GIVEN
            val x: NegativeInt = NegativeInt.random
            val y = NegativeInt(0)
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        // ---------- StrictlyNegativeInt ----------

        @Test
        fun `should return a PositiveInt with a StrictlyNegativeInt`() {
            // GIVEN
            val x: NegativeInt = NegativeInt.random
            val y: StrictlyNegativeInt = StrictlyNegativeInt.random
            // WHEN
            val result: PositiveInt = x / y
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
            val x = NegativeInt(value)
            // WHEN
            val result: NonZeroIntJvm = assertPass(x::toNonZeroInt)
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
    }

    @Nested
    inner class ToNonZeroIntOrNull {
        @Test
        fun `should pass with a NegativeInt holding a strictly negative Int`() {
            // GIVEN
            val value: Int = StrictlyNegativeInt.random.value
            val x = NegativeInt(value)
            // WHEN
            val result: NonZeroIntJvm? = x.toNonZeroIntOrNull()
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with 0`() {
            // GIVEN
            val x = NegativeInt(0)
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
            val x = NegativeInt(value)
            // WHEN
            val result: PositiveInt = assertPass(x::toPositiveInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with a NegativeInt holding a strictly negative Int`() {
            // GIVEN
            val x = NegativeInt(StrictlyNegativeInt.random.value)
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
            val x = NegativeInt(value)
            // WHEN
            val result: PositiveInt? = x.toPositiveIntOrNull()
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with a NegativeInt holding a strictly negative Int`() {
            // GIVEN
            val x = NegativeInt(StrictlyNegativeInt.random.value)
            // WHEN
            val result: PositiveInt? = x.toPositiveIntOrNull()
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
    }

    @Nested
    inner class ToStrictlyNegativeIntOrNull {
        @Test
        fun `should pass with a NegativeInt holding a strictly negative Int`() {
            // GIVEN
            val value: Int = StrictlyNegativeInt.random.value
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

    @Test
    fun `toString() should return its value as a string`() {
        // GIVEN
        val x: NegativeInt = NegativeInt.random
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
        val x: NegativeInt = NegativeInt.random
        // WHEN
        val result: String = Json.encodeToString(x)
        // THEN
        result assertEquals "$x"
    }

    @Test
    fun `should deserialize properly the class`() {
        // GIVEN
        val x: NegativeInt = NegativeInt.random
        val encodedString: String = Json.encodeToString(x)
        // WHEN
        val result: NegativeInt = Json.decodeFromString(encodedString)
        // THEN
        result.value assertEquals x.value
    }
}
