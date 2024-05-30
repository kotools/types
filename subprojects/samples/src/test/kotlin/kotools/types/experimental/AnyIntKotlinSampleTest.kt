package kotools.types.experimental

import org.kotools.types.assertPrints
import kotlin.test.Test

class AnyIntKotlinSampleTest {
    @Test
    fun `unaryMinus() should pass`() {
        val sample = AnyIntKotlinSample()
        assertPrints(expected = -1, sample::unaryMinusOperator)
    }
}
