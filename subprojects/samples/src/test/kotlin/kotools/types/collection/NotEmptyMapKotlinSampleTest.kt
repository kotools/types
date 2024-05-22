package kotools.types.collection

import org.kotools.types.assertPrints
import kotlin.test.Test

class NotEmptyMapKotlinSampleTest {
    @Test
    fun `serialization processes should pass`() {
        val expected: String = listOf("{\"a\":1,\"b\":2}", "true")
            .joinToString(separator = "\n")
        val sample = NotEmptyMapKotlinSample()
        assertPrints(expected, sample::serialization)
    }

    @Test
    fun `head should pass`() {
        val expected = "(a, 1)"
        val sample = NotEmptyMapKotlinSample()
        assertPrints(expected, sample::head)
    }

    @Test
    fun `tail should pass`() {
        val expected = "{b=2, c=3}"
        val sample = NotEmptyMapKotlinSample()
        assertPrints(expected, sample::tail)
    }
}
