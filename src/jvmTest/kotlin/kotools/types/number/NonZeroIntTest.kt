package kotools.types.number

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.*

class NonZeroIntTest {
    // ---------- Constants & Getters ----------

    @Test
    fun `the minimum value of NonZeroInt should be the minimum value of Int`() =
        NonZeroInt.min.value assertEquals Int.MIN_VALUE

    @Test
    fun `the maximum value of NonZeroInt should be the maximum of Int`() =
        NonZeroInt.max.value assertEquals Int.MAX_VALUE

    @Test
    fun `the ranges of NonZeroInt shouldn't contain 0`() {
        // GIVEN
        val x = 0
        // WHEN
        val result: Boolean = NonZeroInt.ranges.all { x !in it }
        // THEN
        result.assertTrue()
    }

    @Test
    fun `the random getter should pass`(): Unit =
        NonZeroInt.random.value assertNotEquals NonZeroInt.random.value

    // ---------- Builders ----------

    @Nested
    inner class Constructor {
        @Test
        fun `should pass with an Int other than 0`() {
            // GIVEN
            val value: Int = NonZeroInt.random.value
            // WHEN
            val result: NonZeroInt = assertPass { NonZeroInt(value) }
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with 0`() {
            // GIVEN
            val value = 0
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException> { NonZeroInt(value) }
        }
    }

    @Nested
    inner class OrNull {
        @Test
        fun `should pass with an Int other than 0`() {
            // GIVEN
            val value: Int = NonZeroInt.random.value
            // WHEN
            val result: NonZeroInt? = NonZeroInt orNull value
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with 0`() {
            // GIVEN
            val value = 0
            // WHEN
            val result: NonZeroInt? = NonZeroInt orNull value
            // THEN
            result.assertNull()
        }
    }

    @Nested
    inner class StringToNonZeroInt {
        @Test
        fun `should pass with a string representation of an Int other than 0`() {
            // GIVEN
            val value: Int = NonZeroInt.random.value
            val string: String = value.toString()
            // WHEN
            val result: NonZeroInt = assertPass(string::toNonZeroInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with an invalid string`() {
            // GIVEN
            val string = "hello"
            // WHEN & THEN
            assertFailsWith<NumberFormatException>(string::toNonZeroInt)
        }

        @Test
        fun `should throw an error with a string representation of 0`() {
            // GIVEN
            val string = "0"
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(string::toNonZeroInt)
        }
    }

    @Nested
    inner class StringToNonZeroIntOrNull {
        @Test
        fun `should pass with a string representation of an Int other than 0`() {
            // GIVEN
            val value: Int = NonZeroInt.random.value
            val string: String = value.toString()
            // WHEN
            val result: NonZeroInt? = string.toNonZeroIntOrNull()
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with an invalid string`() {
            // GIVEN
            val string = "hello"
            // WHEN
            val result: NonZeroInt? = string.toNonZeroIntOrNull()
            // THEN
            result.assertNull()
        }

        @Test
        fun `should return null with a string representation of 0`() {
            // GIVEN
            val string = "0"
            // WHEN
            val result: NonZeroInt? = string.toNonZeroIntOrNull()
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
            var x: NonZeroInt = NonZeroInt.max
            while (x == NonZeroInt.max) x = NonZeroInt.random
            val oldValue: Int = x.value
            // WHEN
            x++
            // THEN
            x.value assertEquals oldValue + 1
        }

        @Test
        fun `should return the minimum value with the maximum value`() {
            // GIVEN
            var x = NonZeroInt.max
            // WHEN
            x++
            // THEN
            x assertEquals NonZeroInt.min
        }
    }

    @Nested
    inner class Decrement {
        @Test
        fun `should decrement the value by 1 with a value other than the minimum value`() {
            // GIVEN
            var x: NonZeroInt = NonZeroInt.min
            while (x == NonZeroInt.min) x = NonZeroInt.random
            val oldValue: Int = x.value
            // WHEN
            x--
            // THEN
            x.value assertEquals oldValue - 1
        }

        @Test
        fun `should return the maximum value with the minimum value`() {
            // GIVEN
            var x = NonZeroInt.min
            // WHEN
            x--
            // THEN
            x assertEquals NonZeroInt.max
        }
    }

    @Test
    fun `unaryMinus() should return a NonZeroInt`() {
        // GIVEN
        val x: NonZeroInt = NonZeroInt.random
        // WHEN
        val result: NonZeroInt = -x
        // THEN
        result.value assertEquals -x.value
    }

    // ---------- Binary operations ----------

    @Nested
    inner class Times {
        @Test
        fun `should return a non zero int with a non zero int`() {
            // GIVEN
            val x: NonZeroInt = NonZeroInt.random
            val y: NonZeroInt = NonZeroInt.random
            // WHEN
            val result: NonZeroInt = x * y
            // THEN
            result.value assertEquals x.value * y.value
        }

        @Test
        fun `should return a non zero int with a strictly positive int`() {
            // GIVEN
            val x: NonZeroInt = NonZeroInt.random
            val y: StrictlyPositiveInt = StrictlyPositiveInt.random
            // WHEN
            val result: NonZeroInt = x * y
            // THEN
            result.value assertEquals x.value * y.value
        }

        @Test
        fun `should return a non zero int with a strictly negative int`() {
            // GIVEN
            val x: NonZeroInt = NonZeroInt.random
            val y: StrictlyNegativeInt = StrictlyNegativeInt.random
            // WHEN
            val result: NonZeroInt = x * y
            // THEN
            result.value assertEquals x.value * y.value
        }
    }

    // ---------- Conversions ----------

    @Nested
    inner class ToPositiveInt {
        @Test
        fun `should pass with a positive NonZeroInt`() {
            // GIVEN
            val value: Int = NonZeroInt.positiveRange.random()
            val x = NonZeroInt(value)
            // WHEN
            val result: PositiveInt = assertPass(x::toPositiveInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with a negative NonZeroInt`() {
            // GIVEN
            val value: Int = NonZeroInt.negativeRange.random()
            val x = NonZeroInt(value)
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(block = x::toPositiveInt)
        }
    }

    @Nested
    inner class ToPositiveIntOrNull {
        @Test
        fun `should pass with a positive NonZeroInt`() {
            // GIVEN
            val value: Int = NonZeroInt.positiveRange.random()
            val x = NonZeroInt(value)
            // WHEN
            val result: PositiveInt? = x.toPositiveIntOrNull()
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with a negative NonZeroInt`() {
            // GIVEN
            val value: Int = NonZeroInt.negativeRange.random()
            val x = NonZeroInt(value)
            // WHEN
            val result: PositiveInt? = x.toPositiveIntOrNull()
            // THEN
            result.assertNull()
        }
    }

    @Nested
    inner class ToStrictlyPositiveInt {
        @Test
        fun `should pass with a positive NonZeroInt`() {
            // GIVEN
            val value: Int = NonZeroInt.positiveRange.random()
            val x = NonZeroInt(value)
            // WHEN
            val result: StrictlyPositiveInt =
                assertPass(x::toStrictlyPositiveInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with a negative NonZeroInt`() {
            // GIVEN
            val x: NonZeroInt = NonZeroInt.negativeRange.random().toNonZeroInt()
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(x::toStrictlyPositiveInt)
        }
    }

    @Nested
    inner class ToStrictlyPositiveIntOrNull {
        @Test
        fun `should pass with a positive NonZeroInt`() {
            // GIVEN
            val value: Int = NonZeroInt.positiveRange.random()
            val x = NonZeroInt(value)
            // WHEN
            val result: StrictlyPositiveInt? = x.toStrictlyPositiveIntOrNull()
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with a negative NonZeroInt`() {
            // GIVEN
            val x: NonZeroInt = NonZeroInt.negativeRange.random().toNonZeroInt()
            // WHEN
            val result: StrictlyPositiveInt? = x.toStrictlyPositiveIntOrNull()
            // THEN
            result.assertNull()
        }
    }

    @Nested
    inner class ToNegativeInt {
        @Test
        fun `should pass with a negative NonZeroInt`() {
            // GIVEN
            val value: Int = NonZeroInt.negativeRange.random()
            val x = NonZeroInt(value)
            // WHEN
            val result: NegativeInt = assertPass(x::toNegativeInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with a positive NonZeroInt`() {
            // GIVEN
            val x: NonZeroInt = NonZeroInt.positiveRange.random().toNonZeroInt()
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(block = x::toNegativeInt)
        }
    }

    @Nested
    inner class ToNegativeIntOrNull {
        @Test
        fun `should pass with a negative NonZeroInt`() {
            // GIVEN
            val value: Int = NonZeroInt.negativeRange.random()
            val x = NonZeroInt(value)
            // WHEN
            val result: NegativeInt? = x.toNegativeIntOrNull()
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with a positive NonZeroInt`() {
            // GIVEN
            val x: NonZeroInt = NonZeroInt.positiveRange.random().toNonZeroInt()
            // WHEN
            val result: NegativeInt? = x.toNegativeIntOrNull()
            // THEN
            result.assertNull()
        }
    }

    @Nested
    inner class ToStrictlyNegativeInt {
        @Test
        fun `should pass with a negative NonZeroInt`() {
            // GIVEN
            val value: Int = NonZeroInt.negativeRange.random()
            val x = NonZeroInt(value)
            // WHEN
            val result: StrictlyNegativeInt =
                assertPass(x::toStrictlyNegativeInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with a positive NonZeroInt`() {
            // GIVEN
            val x: NonZeroInt = NonZeroInt.positiveRange.random().toNonZeroInt()
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(
                block = x::toStrictlyNegativeInt
            )
        }
    }

    @Nested
    inner class ToStrictlyNegativeIntOrNull {
        @Test
        fun `should pass with a negative NonZeroInt`() {
            // GIVEN
            val value: Int = NonZeroInt.negativeRange.random()
            val x = NonZeroInt(value)
            // WHEN
            val result: StrictlyNegativeInt? = x.toStrictlyNegativeIntOrNull()
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with a positive NonZeroInt`() {
            // GIVEN
            val x: NonZeroInt = NonZeroInt.positiveRange.random().toNonZeroInt()
            // WHEN
            val result: StrictlyNegativeInt? = x.toStrictlyNegativeIntOrNull()
            // THEN
            result.assertNull()
        }
    }

    @Test
    fun `toString() should return its value as a string`() {
        // GIVEN
        val x: NonZeroInt = NonZeroInt.random
        // WHEN
        val result: String = x.toString()
        // THEN
        result assertEquals "${x.value}"
    }
}

class NonZeroIntSerializerTest {
    @Test
    fun `should serialize properly the class`() {
        // GIVEN
        val x: NonZeroInt = NonZeroInt.random
        // WHEN
        val result: String = Json.encodeToString(x)
        // THEN
        result assertEquals "$x"
    }

    @Test
    fun `should deserialize properly the class`() {
        // GIVEN
        val x: NonZeroInt = NonZeroInt.random
        val encodedString: String = Json.encodeToString(x)
        // WHEN
        val result: NonZeroInt = Json.decodeFromString(encodedString)
        // THEN
        result.value assertEquals x.value
    }
}
