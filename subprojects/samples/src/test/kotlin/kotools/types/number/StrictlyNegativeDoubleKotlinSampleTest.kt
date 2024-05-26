package kotools.types.number

import org.kotools.types.assertPrints
import kotlin.test.Test

class StrictlyNegativeDoubleKotlinSampleTest {
    @Test
    fun `serialization processes should pass`() {
        val expected: String = listOf(-42.0, true)
            .joinToString(separator = "\n")
        val sample = StrictlyNegativeDoubleKotlinSample()
        assertPrints(expected, sample::serialization)
    }
}
