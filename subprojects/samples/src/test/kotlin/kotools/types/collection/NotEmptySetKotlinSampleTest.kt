package kotools.types.collection

import org.kotools.types.assertPrints
import kotlin.test.Test

class NotEmptySetKotlinSampleTest {
    @Test
    fun `serialization processes should pass`() {
        val expected: String = listOf("[1,2,3]", true)
            .joinToString(separator = "\n")
        val sample = NotEmptySetKotlinSample()
        assertPrints(expected, sample::serialization)
    }
}
