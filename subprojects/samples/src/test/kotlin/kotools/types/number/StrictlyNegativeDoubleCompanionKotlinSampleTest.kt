package kotools.types.number

import org.kotools.types.assertPrintsTrue
import kotlin.test.Test

class StrictlyNegativeDoubleCompanionKotlinSampleTest {
    @Test
    fun `create(Number) should pass`() {
        val sample = StrictlyNegativeDoubleCompanionKotlinSample()
        assertPrintsTrue(sample::create)
    }

    @Test
    fun `createOrNull(Number) should pass`() {
        val sample = StrictlyNegativeDoubleCompanionKotlinSample()
        assertPrintsTrue(sample::createOrNull)
    }
}
