package kotools.types.experimental

import org.kotools.types.assertPrints
import kotlin.test.Test

class NegativeIntKotlinSampleTest {
    @Test
    fun `unaryMinus() should pass`() {
        val expected = "1"
        val sample = NegativeIntKotlinSample()
        assertPrints(expected, sample::unaryMinusOperator)
    }
}
