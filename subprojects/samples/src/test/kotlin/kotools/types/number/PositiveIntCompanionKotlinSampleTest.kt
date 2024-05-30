package kotools.types.number

import org.kotools.types.assertPrints
import kotlin.test.Test

class PositiveIntCompanionKotlinSampleTest {
    @Test
    fun `create(Number) should pass`() {
        val sample = PositiveIntCompanionKotlinSample()
        assertPrints(expected = 23, sample::create)
    }

    @Test
    fun `createOrNull(Number) should pass`() {
        val sample = PositiveIntCompanionKotlinSample()
        assertPrints(expected = 23, sample::createOrNull)
    }
}
