package kotools.types.experimental

import org.kotools.types.assertPrints
import kotlin.test.Test

class NonZeroIntKotlinSampleTest {
    @Test
    fun `unaryMinus() should pass`() {
        val expected = "-1"
        val sample = NonZeroIntKotlinSample()
        assertPrints(expected, sample::unaryMinusOperator)
    }
}
