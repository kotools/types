package kotools.types.number

import org.kotools.types.assertPrints
import kotlin.test.Test

class ZeroIntKotlinSampleTest {
    @Test
    fun `serialization processes should pass`() {
        val expected: String = listOf(0, true)
            .joinToString(separator = "\n")
        val sample = ZeroIntKotlinSample()
        assertPrints(expected, sample::serialization)
    }
}
