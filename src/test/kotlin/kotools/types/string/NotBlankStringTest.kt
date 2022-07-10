package kotools.types.string

import io.github.kotools.assert.assertEquals
import io.github.kotools.assert.assertNotNull
import io.github.kotools.assert.assertNull
import kotools.types.number.PositiveInt
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class NotBlankStringTest {
    @Nested
    inner class Constructor {
        @Test
        fun `should pass with 'hello'`() {
            // GIVEN
            val value = "hello"
            // WHEN
            val result: NotBlankString =
                assertDoesNotThrow { NotBlankString(value) }
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
            assertFailsWith<IllegalArgumentException> { NotBlankString(value) }
        }

        @Test
        fun `should throw an error with ' '`() {
            // GIVEN
            val value = " "
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException> { NotBlankString(value) }
        }

        @Nested
        inner class OrNull {
            @Test
            fun `should pass with 'world'`() {
                // GIVEN
                val value = "world"
                // WHEN
                val result: NotBlankString? = NotBlankString orNull value
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
                val result: NotBlankString? = NotBlankString orNull value
                // THEN
                result.assertNull()
            }

            @Test
            fun `should return null an error with '  '`() {
                // GIVEN
                val value = "  "
                // WHEN
                val result: NotBlankString? = NotBlankString orNull value
                // THEN
                result.assertNull()
            }
        }
    }

    @Nested
    inner class Get {
        @Test
        fun `should return the first character with 0`() {
            // GIVEN
            val string = NotBlankString("hello")
            val index = PositiveInt(0)
            // WHEN
            val result: Char = assertDoesNotThrow { string[index] }
            // THEN
            result assertEquals 'h'
            result assertEquals string.first
        }

        @Test
        fun `should throw an error with an index that is out of bounds`() {
            // GIVEN
            val string = NotBlankString("hi")
            val index = PositiveInt(10)
            // WHEN & THEN
            assertFailsWith<IndexOutOfBoundsException> { string[index] }
        }

        @Nested
        inner class OrNull {
            @Test
            fun `should return the second character with 1`() {
                // GIVEN
                val string = NotBlankString("world")
                val index = PositiveInt(1)
                // WHEN
                val result: Char? = string getOrNull index
                // THEN
                result.assertNotNull() assertEquals 'o'
            }

            @Test
            fun `should return null with an index that is out of bounds`() {
                // GIVEN
                val string = NotBlankString("hi")
                val index = PositiveInt(10)
                // WHEN
                val result: Char? = string getOrNull index
                // THEN
                result.assertNull()
            }
        }
    }

    @Nested
    inner class CompareTo {
        @Test
        fun `should return 0 with the same string`() {
            // GIVEN
            val string = NotBlankString("a")
            val other = NotBlankString("a")
            // WHEN
            val result: Int = string.compareTo(other)
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return a negative number with a greater string lexicographically`() {
            // GIVEN
            val string = NotBlankString("a")
            val other = NotBlankString("z")
            // WHEN
            val result: Int = string.compareTo(other)
            // THEN
            assertTrue { result < 0 }
        }

        @Test
        fun `should return a positive number with a less string lexicographically`() {
            // GIVEN
            val string = NotBlankString("z")
            val other = NotBlankString("a")
            // WHEN
            val result: Int = string.compareTo(other)
            // THEN
            assertTrue { result > 0 }
        }
    }

    @Nested
    inner class ToString {
        @Test
        fun `should return its value`() {
            // GIVEN
            val string = NotBlankString("Hello world")
            // WHEN
            val result: String = string.toString()
            // THEN
            result assertEquals string.value
        }
    }

    @Nested
    inner class Plus {
        @Test
        fun `should return 'hello world' with 'hello' + ' world'`() {
            // GIVEN
            val string = NotBlankString("hello")
            val other = " world"
            // WHEN
            val result: NotBlankString = string + other
            // THEN
            result.value assertEquals "hello world"
        }
    }
}
