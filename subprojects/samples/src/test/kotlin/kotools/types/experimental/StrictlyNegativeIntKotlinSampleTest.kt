package kotools.types.experimental

import org.kotools.types.assertPrints
import kotlin.test.Test

class StrictlyNegativeIntKotlinSampleTest {
    @Test
    fun `unaryMinus() should pass`() {
        val sample = StrictlyNegativeIntKotlinSample()
        assertPrints(expected = 1, sample::unaryMinusOperator)
    }
}
