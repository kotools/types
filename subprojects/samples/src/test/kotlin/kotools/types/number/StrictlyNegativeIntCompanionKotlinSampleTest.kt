package kotools.types.number

import org.kotools.types.assertPrints
import kotlin.test.Test

class StrictlyNegativeIntCompanionKotlinSampleTest {
    @Test
    fun `create(Number) should pass`() {
        val sample = StrictlyNegativeIntCompanionKotlinSample()
        assertPrints(expected = -7, sample::create)
    }

    @Test
    fun `createOrNull(Number) should pass`() {
        val sample = StrictlyNegativeIntCompanionKotlinSample()
        assertPrints(expected = -7, sample::createOrNull)
    }
}
