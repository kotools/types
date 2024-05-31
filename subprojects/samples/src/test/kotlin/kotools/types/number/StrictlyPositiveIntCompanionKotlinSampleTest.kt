package kotools.types.number

import org.kotools.types.assertPrints
import kotlin.test.Test

class StrictlyPositiveIntCompanionKotlinSampleTest {
    @Test
    fun `create(Number) should pass`() {
        val sample = StrictlyPositiveIntCompanionKotlinSample()
        assertPrints(expected = 42, sample::create)
    }
}
