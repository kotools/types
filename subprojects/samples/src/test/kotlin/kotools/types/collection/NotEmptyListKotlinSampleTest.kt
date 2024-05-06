package kotools.types.collection

import org.kotools.types.assertPrints
import kotlin.test.Test

class NotEmptyListKotlinSampleTest {
    @Test
    fun `serialization processes should pass`() {
        val expected: String = listOf("[1,2,3]", true)
            .joinToString(separator = "\n")
        val sample = NotEmptyListKotlinSample()
        assertPrints(expected, sample::serialization)
    }

    @Test
    fun `toList() should pass`() {
        val expected = "[1, 2, 3]"
        val sample = NotEmptyListKotlinSample()
        assertPrints(expected, sample::toList)
    }
}
