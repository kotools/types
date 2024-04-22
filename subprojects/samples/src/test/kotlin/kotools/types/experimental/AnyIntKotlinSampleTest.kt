package kotools.types.experimental

import org.kotools.types.assertPrints
import kotlin.test.Test

class AnyIntKotlinSampleTest {
    @Test
    fun `unaryMinus() should pass`() {
        val expected = "-1"
        val sample = AnyIntKotlinSample()
        assertPrints(expected, sample::unaryMinusOperator)
    }
}
