package kotools.types.experimental

import org.kotools.types.assertPrints
import kotlin.test.Test

class AnyIntFactoryKotlinSampleTest {
    @Test
    fun `AnyInt(Int) should pass`() {
        val expected = "1"
        val sample = AnyIntFactoryKotlinSample()
        assertPrints(expected, sample::constructorLikeInt)
    }
}
