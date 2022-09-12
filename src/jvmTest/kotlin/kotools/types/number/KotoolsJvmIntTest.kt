package kotools.types.number

import kotools.assert.*
import kotools.types.string.NotBlankString

class KotoolsJvmIntTest {
    // ---------- Binary operations ----------

    @Nested
    inner class Plus {
        // ---------- Int ----------

        @Test
        fun `should return an int with an int`() {
            // GIVEN
            val x: KotoolsJvmInt = NonZeroInt(1)
            val y = 2
            // WHEN
            val result: Int = x + y
            // THEN
            result assertEquals 3
        }

        @Test
        fun `should return an int when adding a kotools int to an int`() {
            // GIVEN
            val x = 1
            val y: KotoolsJvmInt = NonZeroInt(2)
            // WHEN
            val result: Int = x + y
            // THEN
            result assertEquals 3
        }

        // ---------- KotoolsInt ----------

        @Test
        fun `should return an int with a kotools int`() {
            // GIVEN
            val x = NonZeroInt(10)
            val y: KotoolsJvmInt = StrictlyNegativeInt(-x.value)
            // WHEN
            val result: Int = x + y
            // THEN
            result assertEquals 0
        }
    }

    @Nested
    inner class Minus {
        // ---------- Int ----------

        @Test
        fun `should return an int with an int`() {
            // GIVEN
            val x: KotoolsJvmInt = NonZeroInt(1)
            val y: Int = x.value
            // WHEN
            val result: Int = x - y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return an int when subtracting a kotools int from an int`() {
            // GIVEN
            val x = 1
            val y: KotoolsJvmInt = NonZeroInt(x)
            // WHEN
            val result: Int = x - y
            // THEN
            result assertEquals 0
        }

        // ---------- KotoolsInt ----------

        @Test
        fun `should return an int with a kotools int`() {
            // GIVEN
            val x = NonZeroInt(1)
            val y: KotoolsJvmInt = x.toPositiveInt()
            // WHEN
            val result: Int = x - y
            // THEN
            result assertEquals 0
        }
    }

    @Nested
    inner class Times {
        // ---------- Int ----------

        @Test
        fun `should return an int with an int`() {
            // GIVEN
            val x: KotoolsJvmInt = NonZeroInt(2)
            val y = 4
            // WHEN
            val result: Int = x * y
            // THEN
            result assertEquals 8
        }

        @Test
        fun `should return an int when multiplying an int by a kotools int`() {
            // GIVEN
            val x = 2
            val y: KotoolsJvmInt = StrictlyPositiveInt(4)
            // WHEN
            val result: Int = x * y
            // THEN
            result assertEquals 8
        }

        // ---------- KotoolsInt ----------

        @Test
        fun `should return an int with a kotools int`() {
            // GIVEN
            val x: KotoolsJvmInt = StrictlyPositiveInt(2)
            val y: KotoolsJvmInt = StrictlyNegativeInt(-4)
            // WHEN
            val result: Int = x * y
            // THEN
            result assertEquals -8
        }
    }

    @Nested
    inner class Div {
        // ---------- Int ----------
        @Test
        fun `should return an int with an int other than 0`() {
            // GIVEN
            val x: KotoolsJvmInt = PositiveInt(0)
            val y = 2
            // WHEN
            val result: Int = assertPass { x / y }
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should throw an error with an int that equals 0`() {
            // GIVEN
            val x: KotoolsJvmInt = PositiveInt(0)
            val y: Int = x.value
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        @Test
        fun `should return an int when dividing an int with a kotools int other than 0`() {
            // GIVEN
            val x = 8
            val y: KotoolsJvmInt = StrictlyPositiveInt(2)
            // WHEN
            val result: Int = assertPass { x / y }
            // THEN
            result assertEquals 4
        }

        @Test
        fun `should throw an error when dividing an an int with a kotools int that equals 0`() {
            // GIVEN
            val x = 8
            val y: KotoolsJvmInt = PositiveInt(0)
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        // ---------- KotoolsInt ----------

        @Test
        fun `should return an int with a kotools int other than 0`() {
            // GIVEN
            val x: KotoolsJvmInt = StrictlyPositiveInt(8)
            val y: KotoolsJvmInt = StrictlyNegativeInt(-4)
            // WHEN
            val result: Int = assertPass { x / y }
            // THEN
            result assertEquals -2
        }

        @Test
        fun `should throw an error with a kotools int that equals 0`() {
            // GIVEN
            val x: KotoolsJvmInt = StrictlyPositiveInt(8)
            val y: KotoolsJvmInt = PositiveInt(0)
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }
    }

    // ---------- Comparisons ----------

    @Nested
    inner class CompareTo {
        // ---------- Int ----------

        @Test
        fun `should return 0 with the same int`() {
            // GIVEN
            val value = 1
            val x: KotoolsJvmInt = NonZeroInt(value)
            // WHEN
            val result: Int = x compareTo value
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return 0 when comparing an int with the same kotools int`() {
            // GIVEN
            val value = 1
            val x: KotoolsJvmInt = NonZeroInt(1)
            // WHEN
            val result: Int = value compareTo x
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return a negative number with a greater int`() {
            // GIVEN
            val x: KotoolsJvmInt = NonZeroInt(1)
            val y = 2
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result < 0 }
        }

        @Test
        fun `should return a negative number when comparing an int with a greater kotools int`() {
            // GIVEN
            val x = 1
            val y: KotoolsJvmInt = NonZeroInt(2)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result < 0 }
        }

        @Test
        fun `should return a positive number with a less int`() {
            // GIVEN
            val x: KotoolsJvmInt = NonZeroInt(1)
            val y = 0
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result > 0 }
        }

        @Test
        fun `should return a positive number when comparing an int with a less kotools int`() {
            // GIVEN
            val x = 1
            val y: KotoolsJvmInt = NonZeroInt(-1)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result > 0 }
        }

        // ---------- KotoolsInt ----------

        @Test
        fun `should return 0 with the same kotools int`() {
            // GIVEN
            val x = NonZeroInt(1)
            val y: KotoolsJvmInt = x.toPositiveInt()
            // WHEN
            val result: Int = x compareTo y
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return a negative number with a greater kotools int`() {
            // GIVEN
            val x = NegativeInt(0)
            val y: KotoolsJvmInt = StrictlyPositiveInt(1)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result < 0 }
        }

        @Test
        fun `should return a positive number with a less kotools int`() {
            // GIVEN
            val x = PositiveInt(0)
            val y: KotoolsJvmInt = StrictlyNegativeInt(-1)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result > 0 }
        }
    }

    // ---------- Conversions ----------

    @Nested
    inner class ToNotBlankString {
        @Test
        fun `should return its value as a not blank string`() {
            // GIVEN
            val value = 1
            val x: KotoolsJvmInt = NonZeroInt(value)
            // WHEN
            val result: NotBlankString = x.toNotBlankString()
            // THEN
            result.value assertEquals value.toString()
        }
    }
}
