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
}
