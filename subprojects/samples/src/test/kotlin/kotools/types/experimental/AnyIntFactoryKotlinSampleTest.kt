package kotools.types.experimental

import org.kotools.types.assertPrints
import kotlin.test.Test

class AnyIntFactoryKotlinSampleTest {
    @Test
    fun `AnyInt(Int) should pass`() {
        val sample = AnyIntFactoryKotlinSample()
        assertPrints(expected = 1, sample::constructorLikeInt)
    }
}
