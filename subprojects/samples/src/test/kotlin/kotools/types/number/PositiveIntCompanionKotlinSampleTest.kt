package kotools.types.number

import org.kotools.types.assertPrints
import kotlin.test.Test

class PositiveIntCompanionKotlinSampleTest {
    @Test
    fun `create(Number) should pass`() {
        val expected = "23"
        val sample = PositiveIntCompanionKotlinSample()
        assertPrints(expected, sample::create)
    }
}
