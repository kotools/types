package kotools.types.collection

import org.kotools.types.assertPrints
import kotlin.test.Test

class NotEmptyCollectionKotlinSampleTest {
    @Test
    fun `head should pass`() {
        val expected = "1"
        val sample = NotEmptyCollectionKotlinSample()
        assertPrints(expected, sample::head)
    }

    @Test
    fun `tail should pass`() {
        val expected: String = listOf("[2, 3]", "null")
            .joinToString(separator = "\n")
        val sample = NotEmptyCollectionKotlinSample()
        assertPrints(expected, sample::tail)
    }
}
