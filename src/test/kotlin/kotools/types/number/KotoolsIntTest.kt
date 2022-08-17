package kotools.types.number

import kotools.assert.Nested
import kotools.assert.Test
import kotools.assert.assertEquals
import kotools.assert.assertTrue

class KotoolsIntTest {
    @Nested
    inner class CompareTo {
        @Test
        fun `should return 0 with the same int`() {
            // GIVEN
            val value = 1
            val x: KotoolsInt = NonZeroInt(value)
            // WHEN
            val result: Int = x compareTo value
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return 0 when comparing an int with the same kotools int`() {
            // GIVEN
            val value = 1
            val x: KotoolsInt = NonZeroInt(1)
            // WHEN
            val result: Int = value compareTo x
            // THEN
            result assertEquals 0
        }

        @Test
        fun `should return a negative number with a greater int`() {
            // GIVEN
            val x: KotoolsInt = NonZeroInt(1)
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
            val y: KotoolsInt = NonZeroInt(2)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result < 0 }
        }

        @Test
        fun `should return a positive number with a less int`() {
            // GIVEN
            val x: KotoolsInt = NonZeroInt(1)
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
            val y: KotoolsInt = NonZeroInt(-1)
            // WHEN
            val result: Int = x compareTo y
            // THEN
            assertTrue { result > 0 }
        }
    }
}
