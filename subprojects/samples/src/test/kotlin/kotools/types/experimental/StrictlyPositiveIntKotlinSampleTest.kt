package kotools.types.experimental

import org.kotools.types.assertPrints
import kotlin.test.Test

class StrictlyPositiveIntKotlinSampleTest {
    @Test
    fun `unaryMinus() should pass`() {
        val sample = StrictlyPositiveIntKotlinSample()
        assertPrints(expected = -1, sample::unaryMinusOperator)
    }
}
