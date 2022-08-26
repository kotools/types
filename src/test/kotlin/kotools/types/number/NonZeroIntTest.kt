package kotools.types.number

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.*

class NonZeroIntTest {
    @Nested
    inner class Constructor {
        @Test
        fun `should pass with 1`() {
            // GIVEN
            val x = 1
            // WHEN
            val result: NonZeroInt = assertPass { NonZeroInt(x) }
            // THEN
            result.value assertEquals x
        }

        @Test
        fun `should pass with -1`() {
            // GIVEN
            val x = -1
            // WHEN
            val result: NonZeroInt = assertPass { NonZeroInt(x) }
            // THEN
            result.value assertEquals x
        }

        @Test
        fun `should throw an error with 0`() {
            // GIVEN
            val x = 0
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException> { NonZeroInt(x) }
        }

        @Nested
        inner class OrNull {
            @Test
            fun `should pass with 1`() {
                // GIVEN
                val x = 1
                // WHEN
                val result: NonZeroInt? = NonZeroInt orNull x
                // THEN
                result.assertNotNull().value assertEquals x
            }

            @Test
            fun `should pass with -1`() {
                // GIVEN
                val x = -1
                // WHEN
                val result: NonZeroInt? = NonZeroInt orNull x
                // THEN
                result.assertNotNull().value assertEquals x
            }

            @Test
            fun `should return null with 0`() {
                // GIVEN
                val x = 0
                // WHEN
                val result: NonZeroInt? = NonZeroInt orNull x
                // THEN
                result.assertNull()
            }
        }
    }

    // ---------- Unary operations ----------

    @Nested
    inner class Increment {
        @Test
        fun `should return 2 with 1`() {
            // GIVEN
            var x = NonZeroInt(1)
            // WHEN
            x++
            // THEN
            x.value assertEquals 2
        }

        @Test
        fun `should return 1 with -1`() {
            // GIVEN
            var x = NonZeroInt(-1)
            // WHEN
            x++
            // THEN
            x.value assertEquals 1
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
        fun `should return 1 with 2`() {
            // GIVEN
            var x = NonZeroInt(2)
            // WHEN
            x--
            // THEN
            x.value assertEquals 1
        }

        @Test
        fun `should return -1 with 1`() {
            // GIVEN
            var x = NonZeroInt(1)
            // WHEN
            x--
            // THEN
            x.value assertEquals -1
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

    @Nested
    inner class UnaryPlus {
        @Test
        fun `should return the same non zero int`() {
            // GIVEN
            val x = NonZeroInt(1)
            // WHEN
            val result: NonZeroInt = +x
            // THEN
            result assertEquals x
        }
    }

    @Nested
    inner class UnaryMinus {
        @Test
        fun `should return -1 with 1`() {
            // GIVEN
            val x = NonZeroInt(1)
            // WHEN
            val result: NonZeroInt = -x
            // THEN
            result.value assertEquals -1
        }
    }

    // ---------- Binary operations ----------

    @Nested
    inner class Times {
        @Test
        fun `should return a non zero int with a non zero int`() {
            // GIVEN
            val x = NonZeroInt(-1)
            val y = NonZeroInt(2)
            // WHEN
            val result: NonZeroInt = x * y
            // THEN
            result.value assertEquals -2
        }

        @Test
        fun `should return a non zero int with a strictly positive int`() {
            // GIVEN
            val x = NonZeroInt(-1)
            val y = StrictlyPositiveInt(2)
            // WHEN
            val result: NonZeroInt = x * y
            // THEN
            result.value assertEquals -2
        }

        @Test
        fun `should return a non zero int with a strictly negative int`() {
            // GIVEN
            val x = NonZeroInt(2)
            val y = StrictlyNegativeInt(-1)
            // WHEN
            val result: NonZeroInt = x * y
            // THEN
            result.value assertEquals -2
        }
    }

    // ---------- Conversions ----------

    @Nested
    inner class StringToNonZeroInt {
        @Test
        fun `should return a non-zero int with a string representation of 1`() {
            // GIVEN
            val value = 1
            val string: String = value.toString()
            // WHEN
            val result: NonZeroInt = assertPass(string::toNonZeroInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should return a non-zero int with a string representation of -1`() {
            // GIVEN
            val value = -1
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
        fun `should return a non-zero int with a string representation of 1`() {
            // GIVEN
            val value = 1
            val string: String = value.toString()
            // WHEN
            val result: NonZeroInt? = string.toNonZeroIntOrNull()
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return a non-zero int with a string representation of -1`() {
            // GIVEN
            val value = -1
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

    @Nested
    inner class ToPositiveInt {
        @Test
        fun `should return its value as a positive int with 1`() {
            // GIVEN
            val value = 1
            val x = NonZeroInt(value)
            // WHEN
            val result: PositiveInt = assertPass(x::toPositiveInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with -1`() {
            // GIVEN
            val x = NonZeroInt(-1)
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(block = x::toPositiveInt)
        }

        @Nested
        inner class OrNull {
            @Test
            fun `should return its value as a positive int with 2`() {
                // GIVEN
                val value = 2
                val x = NonZeroInt(2)
                // WHEN
                val result: PositiveInt? = x.toPositiveIntOrNull()
                // THEN
                result.assertNotNull().value assertEquals value
            }

            @Test
            fun `should return null with -2`() {
                // GIVEN
                val x = NonZeroInt(-2)
                // WHEN
                val result: PositiveInt? = x.toPositiveIntOrNull()
                // THEN
                result.assertNull()
            }
        }
    }

    @Nested
    inner class ToStrictlyPositiveInt {
        @Test
        fun `should return its value as a strictly positive int with 1`() {
            // GIVEN
            val value = 1
            val x = NonZeroInt(value)
            // WHEN
            val result: StrictlyPositiveInt =
                assertPass(x::toStrictlyPositiveInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with -1`() {
            // GIVEN
            val x = NonZeroInt(-1)
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(
                block = x::toStrictlyPositiveInt
            )
        }

        @Nested
        inner class OrNull {
            @Test
            fun `should return its value as a strictly positive int with 2`() {
                // GIVEN
                val value = 2
                val x = NonZeroInt(value)
                // WHEN
                val result: StrictlyPositiveInt? =
                    x.toStrictlyPositiveIntOrNull()
                // THEN
                result.assertNotNull().value assertEquals value
            }

            @Test
            fun `should return null with -2`() {
                // GIVEN
                val x = NonZeroInt(-2)
                // WHEN
                val result: StrictlyPositiveInt? =
                    x.toStrictlyPositiveIntOrNull()
                // THEN
                result.assertNull()
            }
        }
    }

    @Nested
    inner class ToNegativeInt {
        @Test
        fun `should return its value as a negative int with -1`() {
            // GIVEN
            val value = -1
            val x = NonZeroInt(value)
            // WHEN
            val result: NegativeInt = assertPass(x::toNegativeInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with 1`() {
            // GIVEN
            val x = NonZeroInt(1)
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(block = x::toNegativeInt)
        }

        @Nested
        inner class OrNull {
            @Test
            fun `should return its value as a negative int with -2`() {
                // GIVEN
                val value = -2
                val x = NonZeroInt(value)
                // WHEN
                val result: NegativeInt? = x.toNegativeIntOrNull()
                // THEN
                result.assertNotNull().value assertEquals value
            }

            @Test
            fun `should return null with 2`() {
                // GIVEN
                val x = NonZeroInt(2)
                // WHEN
                val result: NegativeInt? = x.toNegativeIntOrNull()
                // THEN
                result.assertNull()
            }
        }
    }

    @Nested
    inner class ToStrictlyNegativeInt {
        @Test
        fun `should return its value as a strictly negative int with -1`() {
            // GIVEN
            val value = -1
            val x = NonZeroInt(value)
            // WHEN
            val result: StrictlyNegativeInt =
                assertPass(x::toStrictlyNegativeInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with 1`() {
            // GIVEN
            val x = NonZeroInt(1)
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(
                block = x::toStrictlyNegativeInt
            )
        }

        @Nested
        inner class OrNull {
            @Test
            fun `should return its value as a strictly negative int with -2`() {
                // GIVEN
                val value = -2
                val x = NonZeroInt(value)
                // WHEN
                val result: StrictlyNegativeInt? =
                    x.toStrictlyNegativeIntOrNull()
                // THEN
                result.assertNotNull().value assertEquals value
            }

            @Test
            fun `should return null with 2`() {
                // GIVEN
                val x = NonZeroInt(2)
                // WHEN
                val result: StrictlyNegativeInt? =
                    x.toStrictlyNegativeIntOrNull()
                // THEN
                result.assertNull()
            }
        }
    }

    @Nested
    inner class ToString {
        @Test
        fun `should return its value as a string`() {
            // GIVEN
            val value = 1
            val x = NonZeroInt(value)
            // WHEN
            val result: String = x.toString()
            // THEN
            result assertEquals "$value"
        }
    }
}

class NonZeroIntSerializerTest {
    @Test
    fun `should serialize properly the class`() {
        // GIVEN
        val x = NonZeroInt(10)
        // WHEN
        val result: String = Json.encodeToString(x)
        // THEN
        result assertEquals "$x"
    }

    @Test
    fun `should deserialize properly the class`() {
        // GIVEN
        val x = NonZeroInt(10)
        val encodedString: String = Json.encodeToString(x)
        // WHEN
        val result: NonZeroInt = Json.decodeFromString(encodedString)
        // THEN
        result.value assertEquals x.value
    }
}
