package kotools.types.number

import org.kotools.types.assertPrints
import kotlin.test.Test

class NegativeIntCompanionKotlinSampleTest {
    @Test
    fun `create(Number) should pass`() {
        val expected = "-7"
        val sample = NegativeIntCompanionKotlinSample()
        assertPrints(expected, sample::create)
    }

    @Test
    fun `createOrNull(Number) should pass`() {
        val expected = "-7"
        val sample = NegativeIntCompanionKotlinSample()
        assertPrints(expected, sample::createOrNull)
    }
}
