package kotools.types.number

import org.kotools.types.assertPrints
import kotlin.test.Test

class NonZeroIntCompanionKotlinSampleTest {
    @Test
    fun `create(Number) should pass`() {
        val expected = "23"
        val sample = NonZeroIntCompanionKotlinSample()
        assertPrints(expected, sample::create)
    }

    @Test
    fun `createOrNull(Number) should pass`() {
        val expected = "23"
        val sample = NonZeroIntCompanionKotlinSample()
        assertPrints(expected, sample::createOrNull)
    }
}
