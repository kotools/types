package kotools.types.number

import kotools.assert.Nested
import kotools.assert.Test
import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.types.string.NotBlankString
import kotlin.random.Random

class KotoolsIntTestJvm {
    // ---------- Binary operations ----------

    @Nested
    inner class Div {
        @Test
        fun `should throw an ArithmeticException with an Int that equals 0`() {
            // GIVEN
            val x: KotoolsInt = Random.nextInt().toKotoolsIntExample()
            val y = 0
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        @Test
        fun `should throw an ArithmeticException when dividing an Int by a KotoolsInt that equals 0`() {
            // GIVEN
            val x: Int = Random.nextInt()
            val y: KotoolsInt = KotoolsIntExample(0)
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        @Test
        fun `should throw an ArithmeticException with a KotoolsInt that equals 0`() {
            // GIVEN
            val x: KotoolsInt = Random.nextInt().toKotoolsIntExample()
            val y = KotoolsIntExample(0)
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }
    }

    // ---------- Conversions ----------

    @Nested
    inner class ToNotBlankString {
        @Test
        fun `should return its value as a not blank string`() {
            // GIVEN
            val value = 1
            val x: KotoolsIntJvm = NonZeroInt(value)
            // WHEN
            val result: NotBlankString = x.toNotBlankString()
            // THEN
            result.value assertEquals value.toString()
        }
    }
}
