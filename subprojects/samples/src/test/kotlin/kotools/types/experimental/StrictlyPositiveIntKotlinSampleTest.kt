package kotools.types.experimental

import org.kotools.types.assertPrints
import kotlin.test.Test

class StrictlyPositiveIntKotlinSampleTest {
    @Test
    fun `unaryMinus() should pass`() {
        val expected = "-1"
        val sample = StrictlyPositiveIntKotlinSample()
        assertPrints(expected, sample::unaryMinusOperator)
    }
}
