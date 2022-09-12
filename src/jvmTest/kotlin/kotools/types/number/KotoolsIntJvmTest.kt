package kotools.types.number

import kotools.assert.Nested
import kotools.assert.Test
import kotools.assert.assertFailsWith
import kotlin.random.Random

class KotoolsIntJvmTest {
    @Nested
    inner class Div {
        @Test
        fun `should throw an ArithmeticException with an Int that equals 0`() {
            assertFailsWith<ArithmeticException> {
                Random.nextInt().toKotoolsIntExample() / 0
            }
        }

        @Test
        fun `should throw an ArithmeticException when dividing an Int by a KotoolsInt that equals 0`() {
            assertFailsWith<ArithmeticException> {
                Random.nextInt() / KotoolsIntExample(0)
            }
        }

        @Test
        fun `should throw an ArithmeticException with a KotoolsInt that equals 0`() {
            assertFailsWith<ArithmeticException> {
                Random.nextInt().toKotoolsIntExample() / KotoolsIntExample(0)
            }
        }
    }
}
