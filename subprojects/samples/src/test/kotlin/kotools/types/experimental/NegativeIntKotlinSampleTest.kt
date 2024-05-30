package kotools.types.experimental

import org.kotools.types.assertPrints
import kotlin.test.Test

class NegativeIntKotlinSampleTest {
    @Test
    fun `unaryMinus() should pass`() {
        val sample = NegativeIntKotlinSample()
        assertPrints(expected = 1, sample::unaryMinusOperator)
    }
}
