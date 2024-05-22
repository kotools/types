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
    fun `head should pass`(): Unit =
        assertPrints(expected = "(a, 1)", this.sample::head)

    @Test
    fun `tail should pass`(): Unit =
        assertPrints(expected = "{b=2, c=3}", this.sample::tail)

    @Test
    fun `entries should pass`(): Unit =
        assertPrints(expected = "[a=1, b=2]", this.sample::entries)

    @Test
    fun `keys should pass`(): Unit =
        assertPrints(expected = "[a, b]", this.sample::keys)

    @Test
    fun `values should pass`(): Unit =
        assertPrints(expected = "[1, 2]", this.sample::values)

    @Test
    fun `size should pass`(): Unit =
        assertPrints(expected = "2", this.sample::size)
}
