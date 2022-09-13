package kotools.types.string

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.*
import kotools.types.number.*

class NotBlankStringJvmTest {
    @Nested
    inner class Constructor {
        @Test
        fun `should pass with 'hello'`() {
            // GIVEN
            val value = "hello"
            // WHEN
            val result: NotBlankStringJvm =
                assertPass { NotBlankStringJvm(value) }
            // THEN
            result.value assertEquals value
            result.length.value assertEquals value.length
            result.first assertEquals value[0]
        }

        @Test
        fun `should throw an error with ''`() {
            // GIVEN
            val value = ""
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException> {
                NotBlankStringJvm(value)
            }
        }

        @Test
        fun `should throw an error with ' '`() {
            // GIVEN
            val value = " "
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException> {
                NotBlankStringJvm(value)
            }
        }

        @Nested
        inner class OrNull {
            @Test
            fun `should pass with 'world'`() {
                // GIVEN
                val value = "world"
                // WHEN
                val result: NotBlankStringJvm? = NotBlankStringJvm orNull value
                // THEN
                result.assertNotNull().let {
                    it.value assertEquals value
                    it.length.value assertEquals value.length
                    it.first assertEquals value[0]
                }
            }

            @Test
            fun `should return null an error with ''`() {
                // GIVEN
                val value = ""
                // WHEN
                val result: NotBlankStringJvm? = NotBlankStringJvm orNull value
                // THEN
                result.assertNull()
            }

            @Test
            fun `should return null an error with '  '`() {
                // GIVEN
                val value = "  "
                // WHEN
                val result: NotBlankStringJvm? = NotBlankStringJvm orNull value
                // THEN
                result.assertNull()
            }
        }
    }

    // ---------- Positional access operations ----------

    @Nested
    inner class Get {
        @Test
        fun `should return the first character with 0`() {
            // GIVEN
            val string = NotBlankStringJvm("hello")
            val index = PositiveInt(0)
            // WHEN
            val result: Char = assertPass { string[index] }
            // THEN
            result assertEquals 'h'
            result assertEquals string.first
        }

        @Test
        fun `should throw an error with an index that is out of bounds`() {
            // GIVEN
            val string = NotBlankStringJvm("hi")
            val index = PositiveInt(10)
            // WHEN & THEN
            assertFailsWith<IndexOutOfBoundsException> { string[index] }
        }

        @Nested
        inner class OrNull {
            @Test
            fun `should return the second character with 1`() {
                // GIVEN
                val string = NotBlankStringJvm("world")
                val index = PositiveInt(1)
                // WHEN
                val result: Char? = string getOrNull index
                // THEN
                result.assertNotNull() assertEquals 'o'
            }

            @Test
            fun `should return null with an index that is out of bounds`() {
                // GIVEN
                val string = NotBlankStringJvm("hi")
                val index = PositiveInt(10)
                // WHEN
                val result: Char? = string getOrNull index
                // THEN
                result.assertNull()
            }
        }
    }

    // ---------- Binary operations ----------

    @Nested
    inner class Plus {
        @Test
        fun `should return 'hello world' with 'hello' + ' world'`() {
            // GIVEN
            val string = NotBlankStringJvm("hello")
            val other = " world"
            // WHEN
            val result: NotBlankStringJvm = string + other
            // THEN
            result.value assertEquals "hello world"
        }
    }

    // ---------- Comparisons ----------

    @Nested
    inner class CompareTo {
        // ---------- String ----------

        @Test
        fun `should return 0 with the same string`() {
            // GIVEN
            val string = NotBlankStringJvm("a")
            val other: String = string.value
            // WHEN
            val result: Int = string compareTo other
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return 0 when comparing a string with the same not blank string`() {
            // GIVEN
            val string = "a"
            val other = NotBlankStringJvm(string)
            // WHEN
            val result: Int = string compareTo other
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return a negative number with a greater string lexicographically`() {
            // GIVEN
            val string = NotBlankStringJvm("a")
            val other = "z"
            // WHEN
            val result: Int = string compareTo other
            // THEN
            assertTrue { result < 0 }
        }

        @Test
        fun `should return a negative number when comparing a string with a greater not blank string lexicographically`() {
            // GIVEN
            val string = "a"
            val other = NotBlankStringJvm("z")
            // WHEN
            val result: Int = string compareTo other
            // THEN
            assertTrue { result < 0 }
        }

        @Test
        fun `should return a positive number with a less string lexicographically`() {
            // GIVEN
            val string = NotBlankStringJvm("z")
            val other = "a"
            // WHEN
            val result: Int = string compareTo other
            // THEN
            assertTrue { result > 0 }
        }

        @Test
        fun `should return a positive number when comparing a string with a less not blank string lexicographically`() {
            // GIVEN
            val string = "z"
            val other = NotBlankStringJvm("a")
            // WHEN
            val result: Int = string compareTo other
            // THEN
            assertTrue { result > 0 }
        }

        // ---------- NotBlankString ----------

        @Test
        fun `should return 0 with the same not blank string`() {
            // GIVEN
            val string = NotBlankStringJvm("a")
            val other = NotBlankStringJvm("a")
            // WHEN
            val result: Int = string compareTo other
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return a negative number with a greater not blank string lexicographically`() {
            // GIVEN
            val string = NotBlankStringJvm("a")
            val other = NotBlankStringJvm("z")
            // WHEN
            val result: Int = string compareTo other
            // THEN
            assertTrue { result < 0 }
        }

        @Test
        fun `should return a positive number with a less not blank string lexicographically`() {
            // GIVEN
            val string = NotBlankStringJvm("z")
            val other = NotBlankStringJvm("a")
            // WHEN
            val result: Int = string compareTo other
            // THEN
            assertTrue { result > 0 }
        }
    }

    // ---------- Conversions ----------

    @Nested
    inner class ToNonZeroInt {
        @Test
        fun `should return a non-zero int with a string representation of 1`() {
            // GIVEN
            val value = 1
            val string = NotBlankStringJvm(value.toString())
            // WHEN
            val result: NonZeroIntJvm = assertPass(string::toNonZeroInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should return a non-zero int with a string representation of -1`() {
            // GIVEN
            val value = -1
            val string = NotBlankStringJvm(value.toString())
            // WHEN
            val result: NonZeroIntJvm = assertPass(string::toNonZeroInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with an invalid string`() {
            // GIVEN
            val string = NotBlankStringJvm("hello")
            // WHEN & THEN
            assertFailsWith<NumberFormatException>(string::toNonZeroInt)
        }

        @Test
        fun `should throw an error with a string representation of 0`() {
            // GIVEN
            val string = NotBlankStringJvm("0")
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(string::toNonZeroInt)
        }
    }

    @Nested
    inner class ToNonZeroIntOrNull {
        @Test
        fun `should return a non-zero int with a string representation of 1`() {
            // GIVEN
            val value = 1
            val string = NotBlankStringJvm(value.toString())
            // WHEN
            val result: NonZeroIntJvm? = string.toNonZeroIntOrNull()
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return a non-zero int with a string representation of -1`() {
            // GIVEN
            val value = -1
            val string = NotBlankStringJvm(value.toString())
            // WHEN
            val result: NonZeroIntJvm? = string.toNonZeroIntOrNull()
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with an invalid string`() {
            // GIVEN
            val string = NotBlankStringJvm("hello")
            // WHEN
            val result: NonZeroIntJvm? = string.toNonZeroIntOrNull()
            // THEN
            result.assertNull()
        }

        @Test
        fun `should return null with a string representation of 0`() {
            // GIVEN
            val string = NotBlankStringJvm("0")
            // WHEN
            val result: NonZeroIntJvm? = string.toNonZeroIntOrNull()
            // THEN
            result.assertNull()
        }
    }

    @Nested
    inner class ToPositiveInt {
        @Test
        fun `should pass with a string representation of 0`() {
            // GIVEN
            val value = 0
            val string = NotBlankStringJvm("$value")
            // WHEN
            val result: PositiveInt = assertPass(string::toPositiveInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should pass with a string representation of 1`() {
            // GIVEN
            val value = 1
            val string = NotBlankStringJvm("$value")
            // WHEN
            val result: PositiveInt = assertPass(string::toPositiveInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with an invalid string`() {
            // GIVEN
            val string = NotBlankStringJvm("a")
            // WHEN & THEN
            assertFailsWith<NumberFormatException>(string::toPositiveInt)
        }

        @Test
        fun `should throw an error with a string representation of -1`() {
            // GIVEN
            val string = NotBlankStringJvm("-1")
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(string::toPositiveInt)
        }
    }

    @Nested
    inner class ToPositiveIntOrNull {
        @Test
        fun `should pass with a string representation of 0`() {
            // GIVEN
            val value = 0
            val string = NotBlankStringJvm("$value")
            // WHEN
            val result: PositiveInt? = string.toPositiveIntOrNull()
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should pass with a string representation of 1`() {
            // GIVEN
            val value = 1
            val string = NotBlankStringJvm("$value")
            // WHEN
            val result: PositiveInt? = string.toPositiveIntOrNull()
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with an invalid string`() {
            // GIVEN
            val string = NotBlankStringJvm("a")
            // WHEN
            val result: PositiveInt? = string.toPositiveIntOrNull()
            // THEN
            result.assertNull()
        }

        @Test
        fun `should return null with a string representation of -1`() {
            // GIVEN
            val string = NotBlankStringJvm("-1")
            // WHEN
            val result: PositiveInt? = string.toPositiveIntOrNull()
            // THEN
            result.assertNull()
        }
    }

    @Nested
    inner class ToStrictlyPositiveInt {
        @Test
        fun `should pass with a string representation of 1`() {
            // GIVEN
            val value = 1
            val string = NotBlankStringJvm("$value")
            // WHEN
            val result: StrictlyPositiveInt =
                assertPass(string::toStrictlyPositiveInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with an invalid string`() {
            // GIVEN
            val string = NotBlankStringJvm("a")
            // WHEN & THEN
            assertFailsWith<NumberFormatException>(
                string::toStrictlyPositiveInt
            )
        }

        @Test
        fun `should throw an error with a string representation of 0`() {
            // GIVEN
            val string = NotBlankStringJvm("0")
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(
                string::toStrictlyPositiveInt
            )
        }
    }

    @Nested
    inner class ToStrictlyPositiveIntOrNull {
        @Test
        fun `should pass with a string representation of 1`() {
            // GIVEN
            val value = 1
            val string = NotBlankStringJvm("$value")
            // WHEN
            val result: StrictlyPositiveInt? =
                string.toStrictlyPositiveIntOrNull()
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with an invalid string`() {
            // GIVEN
            val string = NotBlankStringJvm("a")
            // WHEN
            val result: StrictlyPositiveInt? =
                string.toStrictlyPositiveIntOrNull()
            // THEN
            result.assertNull()
        }

        @Test
        fun `should return null with a string representation of 0`() {
            // GIVEN
            val string = NotBlankStringJvm("0")
            // WHEN
            val result: StrictlyPositiveInt? =
                string.toStrictlyPositiveIntOrNull()
            // THEN
            result.assertNull()
        }
    }

    @Nested
    inner class ToNegativeInt {
        @Test
        fun `should pass with a string representation of 0`() {
            // GIVEN
            val value = 0
            val string = NotBlankStringJvm("$value")
            // WHEN
            val result: NegativeInt = assertPass(string::toNegativeInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should pass with a string representation of -1`() {
            // GIVEN
            val value = -1
            val string = NotBlankStringJvm("$value")
            // WHEN
            val result: NegativeInt = assertPass(string::toNegativeInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with an invalid string`() {
            // GIVEN
            val string = NotBlankStringJvm("a")
            // WHEN & THEN
            assertFailsWith<NumberFormatException>(string::toNegativeInt)
        }

        @Test
        fun `should throw an error with a string representation of 1`() {
            // GIVEN
            val string = NotBlankStringJvm("1")
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(string::toNegativeInt)
        }
    }

    @Nested
    inner class ToNegativeIntOrNull {
        @Test
        fun `should pass with a string representation of 0`() {
            // GIVEN
            val value = 0
            val string = NotBlankStringJvm("$value")
            // WHEN
            val result: NegativeInt? = string.toNegativeIntOrNull()
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should pass with a string representation of -1`() {
            // GIVEN
            val value = -1
            val string = NotBlankStringJvm("$value")
            // WHEN
            val result: NegativeInt? = string.toNegativeIntOrNull()
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should throw an error with an invalid string`() {
            // GIVEN
            val string = NotBlankStringJvm("a")
            // WHEN
            val result: NegativeInt? = string.toNegativeIntOrNull()
            // THEN
            result.assertNull()
        }

        @Test
        fun `should throw an error with a string representation of 1`() {
            // GIVEN
            val string = NotBlankStringJvm("1")
            // WHEN
            val result: NegativeInt? = string.toNegativeIntOrNull()
            // THEN
            result.assertNull()
        }
    }

    @Nested
    inner class ToStrictlyNegativeInt {
        @Test
        fun `should pass with a string representation of -1`() {
            // GIVEN
            val value = -1
            val string = NotBlankStringJvm("$value")
            // WHEN
            val result: StrictlyNegativeInt =
                assertPass(string::toStrictlyNegativeInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with an invalid string`() {
            // GIVEN
            val string = NotBlankStringJvm("a")
            // WHEN & THEN
            assertFailsWith<NumberFormatException>(
                string::toStrictlyNegativeInt
            )
        }

        @Test
        fun `should throw an error with a string representation of 0`() {
            // GIVEN
            val string = NotBlankStringJvm("0")
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(
                string::toStrictlyNegativeInt
            )
        }
    }

    @Nested
    inner class ToStrictlyNegativeIntOrNull {
        @Test
        fun `should pass with a string representation of -1`() {
            // GIVEN
            val value = -1
            val string = NotBlankStringJvm("$value")
            // WHEN
            val result: StrictlyNegativeInt? =
                string.toStrictlyNegativeIntOrNull()
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with an invalid string`() {
            // GIVEN
            val string = NotBlankStringJvm("a")
            // WHEN
            val result: StrictlyNegativeInt? =
                string.toStrictlyNegativeIntOrNull()
            // THEN
            result.assertNull()
        }

        @Test
        fun `should return null with a string representation of 0`() {
            // GIVEN
            val string = NotBlankStringJvm("0")
            // WHEN
            val result: StrictlyNegativeInt? =
                string.toStrictlyNegativeIntOrNull()
            // THEN
            result.assertNull()
        }
    }

    @Nested
    inner class ToString {
        @Test
        fun `should return its value`() {
            // GIVEN
            val string = NotBlankStringJvm("Hello world")
            // WHEN
            val result: String = string.toString()
            // THEN
            result assertEquals string.value
        }
    }
}

class NotBlankStringSerializerTest {
    @Test
    fun `should serialize properly this class`() {
        // GIVEN
        val string = NotBlankStringJvm("hello")
        // WHEN
        val result: String = Json.encodeToString(string)
        // THEN
        result assertEquals "\"$string\""
    }

    @Test
    fun `should deserialize properly this class`() {
        // GIVEN
        val string = NotBlankStringJvm("hello")
        val encodedString: String = Json.encodeToString(string)
        // WHEN
        val result: NotBlankStringJvm = Json.decodeFromString(encodedString)
        // THEN
        result.value assertEquals string.value
    }
}
