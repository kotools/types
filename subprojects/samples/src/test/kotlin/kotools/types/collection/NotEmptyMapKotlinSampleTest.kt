package kotools.types.collection

import org.kotools.types.assertPrints
import kotlin.test.Test

class NotEmptyMapKotlinSampleTest {
    private val sample: NotEmptyMapKotlinSample = NotEmptyMapKotlinSample()

    @Test
    fun `serialization processes should pass`() {
        val expected: String = listOf("{\"a\":1,\"b\":2}", "true")
            .joinToString(separator = "\n")
        assertPrints(expected, this.sample::serialization)
    }

    @Test
    fun `head should pass`() {
        val expected = "(a, 1)"
        assertPrints(expected, this.sample::head)
    }

    @Test
    fun `tail should pass`() {
        val expected = "{b=2, c=3}"
        assertPrints(expected, this.sample::tail)
    }

    @Test
    fun `entries should pass`() {
        val expected = "[a=1, b=2]"
        assertPrints(expected, this.sample::entries)
    }
}
