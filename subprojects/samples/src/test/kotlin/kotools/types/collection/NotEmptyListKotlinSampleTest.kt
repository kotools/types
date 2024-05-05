package kotools.types.collection

import org.kotools.types.assertPrints
import kotlin.test.Test

class NotEmptyListKotlinSampleTest {
    @Test
    fun `notEmptyListOf(E, vararg E) should pass`() {
        val sample = NotEmptyListKotlinSample()
        val expected = "[1, 2, 3]"
        assertPrints(expected, sample::notEmptyListOf)
    }
}
