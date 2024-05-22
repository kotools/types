package kotools.types.collection

import org.kotools.types.assertPrints
import kotlin.test.Test

class NotEmptySetKtKotlinSampleTest {
    @Test
    fun `notEmptySetOf(E, vararg E) should pass`() {
        val sample = NotEmptySetKtKotlinSample()
        assertPrints(expected = "[1, 2, 3]", sample::notEmptySetOf)
    }
}
