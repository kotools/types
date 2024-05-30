package kotools.types.experimental

import org.kotools.types.assertPrints
import kotlin.test.Test

class NonZeroIntKotlinSampleTest {
    @Test
    fun `unaryMinus() should pass`() {
        val sample = NonZeroIntKotlinSample()
        assertPrints(expected = -1, sample::unaryMinusOperator)
    }
}
