package kotools.types.experimental

import org.kotools.types.assertPrints
import kotlin.test.Test

class PositiveIntKotlinSampleTest {
    @Test
    fun `unaryMinus() should pass`() {
        val sample = PositiveIntKotlinSample()
        assertPrints(expected = -1, sample::unaryMinusOperator)
    }
}
