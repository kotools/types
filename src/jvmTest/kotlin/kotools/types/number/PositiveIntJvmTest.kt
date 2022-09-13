package kotools.types.number

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.*

class PositiveIntJvmTest {
    // ---------- Constants & Getters ----------

    @Test
    fun `the minimum value should be 0`(): Unit =
        PositiveIntJvm.min.value assertEquals 0

    @Test
    fun `the maximum value should be the maximum value of Int`(): Unit =
        PositiveIntJvm.max.value assertEquals Int.MAX_VALUE

    @Test
    fun `the random getter should pass`(): Unit =
        PositiveIntJvm.random.value assertNotEquals PositiveIntJvm.random.value

    // ---------- Builders ----------

    @Nested
    inner class Constructor {
        @Test
        fun `should pass with a positive Int`() {
            // GIVEN
            val value: Int = PositiveIntJvm.random.value
            // WHEN
            val result: PositiveIntJvm = assertPass { PositiveIntJvm(value) }
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with a strictly negative Int`() {
            // GIVEN
            var value = 0
            while (value == 0) value = PositiveIntJvm.random.value
            value = -value
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException> { PositiveIntJvm(value) }
        }
    }

    @Nested
    inner class OrNull {
        @Test
        fun `should pass with a positive Int`() {
            // GIVEN
            val value: Int = PositiveIntJvm.random.value
            // WHEN
            val result: PositiveIntJvm? = PositiveIntJvm orNull value
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with a strictly negative Int`() {
            // GIVEN
            var value = 0
            while (value == 0) value = PositiveIntJvm.random.value
            value = -value
            // WHEN
            val result: PositiveIntJvm? = PositiveIntJvm orNull value
            // THEN
            result.assertNull()
        }
    }

    // ---------- Unary operations ----------

    @Nested
    inner class Inc {
        @Test
        fun `should return the value incremented by 1 with a value other than the maximum value`() {
            // GIVEN
            var value: Int = PositiveIntJvm.max.value
            while (value == PositiveIntJvm.max.value)
                value = PositiveIntJvm.random.value
            var positiveInt = PositiveIntJvm(value)
            // WHEN
            positiveInt++
            // THEN
            positiveInt.value assertEquals value + 1
        }

        @Test
        fun `should return the minimum value with the maximum value`() {
            // GIVEN
            var x: PositiveIntJvm = PositiveIntJvm.max
            // WHEN
            x++
            // THEN
            x assertEquals PositiveIntJvm.min
        }
    }

    @Nested
    inner class Dec {
        @Test
        fun `should return the value decremented by 1 with a value other than the minimum value`() {
            // GIVEN
            var value: Int = PositiveIntJvm.min.value
            while (value == PositiveIntJvm.min.value)
                value = PositiveIntJvm.random.value
            var positiveInt = PositiveIntJvm(value)
            // WHEN
            positiveInt--
            // THEN
            positiveInt.value assertEquals value - 1
        }

        @Test
        fun `should return the maximum value with the minimum value`() {
            // GIVEN
            var x: PositiveIntJvm = PositiveIntJvm.min
            // WHEN
            x--
            // THEN
            x assertEquals PositiveIntJvm.max
        }
    }

    @Test
    fun `unaryMinus() should return a NegativeInt`() {
        // GIVEN
        val value: Int = PositiveIntJvm.random.value
        val x = PositiveIntJvm(value)
        // WHEN
        val result: NegativeInt = -x
        // THEN
        result.value assertEquals -value
    }

    // ---------- Binary operations ----------

    @Nested
    inner class Div {
        // ---------- PositiveInt ----------

        @Test
        fun `should return a PositiveInt with a PositiveInt other than 0`() {
            // GIVEN
            val x: PositiveIntJvm = PositiveIntJvm.random
            var value = 0
            while (value == 0) value = PositiveIntJvm.random.value
            val y = PositiveIntJvm(value)
            // WHEN
            val result: PositiveIntJvm = assertPass { x / y }
            // THEN
            result.value assertEquals x.value / y.value
        }

        @Test
        fun `should throw an error with a PositiveInt that equals 0`() {
            // GIVEN
            val x: PositiveIntJvm = PositiveIntJvm.random
            val y = PositiveIntJvm(0)
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        // ---------- StrictlyPositiveInt ----------

        @Test
        fun `should return a PositiveInt with a StrictlyPositiveInt`() {
            // GIVEN
            val x: PositiveIntJvm = PositiveIntJvm.random
            val y: StrictlyPositiveInt = StrictlyPositiveInt.random
            // WHEN
            val result: PositiveIntJvm = x / y
            // THEN
            result.value assertEquals x.value / y.value
        }

        // ---------- NegativeInt ----------

        @Test
        fun `should return a NegativeInt with a NegativeInt other than 0`() {
            // GIVEN
            val x: PositiveIntJvm = PositiveIntJvm.random
            var value = 0
            while (value == 0) value = NegativeInt.random.value
            val y = NegativeInt(value)
            // WHEN
            val result: NegativeInt = assertPass { x / y }
            // THEN
            result.value assertEquals x.value / y.value
        }

        @Test
        fun `should throw an error with a NegativeInt that equals 0`() {
            // GIVEN
            val x: PositiveIntJvm = PositiveIntJvm.random
            val y = NegativeInt(0)
            // WHEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        // ---------- StrictlyNegativeInt ----------

        @Test
        fun `should return a NegativeInt with a StrictlyNegativeInt`() {
            // GIVEN
            val x: PositiveIntJvm = PositiveIntJvm.random
            val y: StrictlyNegativeInt = StrictlyNegativeInt.random
            // WHEN
            val result: NegativeInt = x / y
            // THEN
            result.value assertEquals x.value / y.value
        }
    }

    // ---------- Conversions ----------

    @Nested
    inner class ToNonZeroInt {
        @Test
        fun `should pass with a PositiveInt holding a strictly positive Int`() {
            // GIVEN
            val value: Int = StrictlyPositiveInt.random.value
            val x = PositiveIntJvm(value)
            // WHEN
            val result: NonZeroIntJvm = assertPass(x::toNonZeroInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with 0`() {
            // GIVEN
            val x = PositiveIntJvm(0)
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(block = x::toNonZeroInt)
        }
    }

    @Nested
    inner class ToNonZeroIntOrNull {
        @Test
        fun `should pass with a PositiveInt holding a strictly positive Int`() {
            // GIVEN
            val value: Int = StrictlyPositiveInt.random.value
            val x = PositiveIntJvm(value)
            // WHEN
            val result: NonZeroIntJvm? = x.toNonZeroIntOrNull()
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with 0`() {
            // GIVEN
            val x = PositiveIntJvm(0)
            // WHEN
            val result: NonZeroIntJvm? = x.toNonZeroIntOrNull()
            // THEN
            result.assertNull()
        }
    }

    @Nested
    inner class ToStrictlyPositiveInt {
        @Test
        fun `should pass with a PositiveInt holding a strictly positive Int`() {
            // GIVEN
            val value: Int = StrictlyPositiveInt.random.value
            val x = PositiveIntJvm(value)
            // WHEN
            val result: StrictlyPositiveInt =
                assertPass(x::toStrictlyPositiveInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with 0`() {
            // GIVEN
            val x = PositiveIntJvm(0)
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(
                block = x::toStrictlyPositiveInt
            )
        }
    }

    @Nested
    inner class ToStrictlyPositiveIntOrNull {
        @Test
        fun `should pass with a PositiveInt holding a strictly positive Int`() {
            // GIVEN
            val value: Int = StrictlyPositiveInt.random.value
            val x = PositiveIntJvm(value)
            // WHEN
            val result: StrictlyPositiveInt? =
                x.toStrictlyPositiveIntOrNull()
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with 0`() {
            // GIVEN
            val x = PositiveIntJvm(0)
            // WHEN
            val result: StrictlyPositiveInt? =
                x.toStrictlyPositiveIntOrNull()
            // THEN
            result.assertNull()
        }
    }

    @Nested
    inner class ToNegativeInt {
        @Test
        fun `should pass with 0`() {
            // GIVEN
            val value = 0
            val x = PositiveIntJvm(value)
            // WHEN
            val result: NegativeInt = assertPass(x::toNegativeInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with a PositiveInt holding a strictly positive Int`() {
            // GIVEN
            val x = PositiveIntJvm(StrictlyPositiveInt.random.value)
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(block = x::toNegativeInt)
        }
    }

    @Nested
    inner class ToNegativeIntOrNull {
        @Test
        fun `should pass with 0`() {
            // GIVEN
            val value = 0
            val x = PositiveIntJvm(value)
            // WHEN
            val result: NegativeInt? = x.toNegativeIntOrNull()
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with a PositiveInt holding a strictly positive Int`() {
            // GIVEN
            val x = PositiveIntJvm(StrictlyPositiveInt.random.value)
            // WHEN
            val result: NegativeInt? = x.toNegativeIntOrNull()
            // THEN
            result.assertNull()
        }
    }

    @Test
    fun `toString() should return its value as a string`() {
        // GIVEN
        val x: PositiveIntJvm = PositiveIntJvm.random
        // WHEN
        val result: String = x.toString()
        // THEN
        result assertEquals "${x.value}"
    }
}

class PositiveIntSerializerTest {
    @Test
    fun `should serialize properly the class`() {
        // GIVEN
        val x: PositiveIntJvm = PositiveIntJvm.random
        // WHEN
        val result: String = Json.encodeToString(x)
        // THEN
        result assertEquals "$x"
    }

    @Test
    fun `should deserialize properly the class`() {
        // GIVEN
        val x: PositiveIntJvm = PositiveIntJvm.random
        val encodedString: String = Json.encodeToString(x)
        // WHEN
        val result: PositiveIntJvm = Json.decodeFromString(encodedString)
        // THEN
        result.value assertEquals x.value
    }
}
