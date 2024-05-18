package kotools.types.collection

import org.kotools.types.assertPrints
import kotlin.test.Test

class NotEmptyMapKtKotlinSampleTest {
    @Test
    fun `notEmptyMapOf(Pair, vararg Pair) should pass`() {
        val expected = "{a=1, b=2}"
        val sample = NotEmptyMapKtKotlinSample()
        assertPrints(expected, sample::notEmptyMapOf)
    }
}
