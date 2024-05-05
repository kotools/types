package kotools.types.collection

import org.kotools.types.assertPrints
import kotlin.test.Test

class NotEmptyListCompanionKotlinSampleTest {
    @Test
    fun `create(Collection) should pass`() {
        val expected = "[1, 2, 3]"
        val sample = NotEmptyListCompanionKotlinSample()
        assertPrints(expected, sample::createWithCollection)
    }

    @Test
    fun `create(MutableCollection) should pass`() {
        val expected: String = listOf(
            "[1, 2, 3]",
            "[1, 2, 3]",
            "[]",
            "[1, 2, 3]"
        ).joinToString(separator = "\n")
        val sample = NotEmptyListCompanionKotlinSample()
        assertPrints(expected, sample::createWithMutableCollection)
    }
}
