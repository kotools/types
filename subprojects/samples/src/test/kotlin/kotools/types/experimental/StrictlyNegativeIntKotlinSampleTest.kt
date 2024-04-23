package kotools.types.experimental

import org.kotools.types.assertPrints
import kotlin.test.Test

class StrictlyNegativeIntKotlinSampleTest {
    @Test
    fun `unaryMinus() should pass`() {
        val expected = "1"
        val sample = StrictlyNegativeIntKotlinSample()
        assertPrints(expected, sample::unaryMinusOperator)
    }
}
