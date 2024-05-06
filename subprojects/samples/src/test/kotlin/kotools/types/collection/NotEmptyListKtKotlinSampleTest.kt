package kotools.types.collection

import org.kotools.types.assertPrints
import kotlin.test.Test

class NotEmptyListKtKotlinSampleTest {
    @Test
    fun `notEmptyListOf(E, vararg E) should pass`() {
        val expected = "[1, 2, 3]"
        val sample = NotEmptyListKtKotlinSample()
        assertPrints(expected, sample::notEmptyListOf)
    }

    @Test
    fun `toNotEmptyList() on Collection should pass`() {
        val exceptionMessage = "Given collection shouldn't be empty."
        val expected: String = listOf(
            "Success([1, 2, 3])",
            "Failure(java.lang.IllegalArgumentException: $exceptionMessage)"
        ).joinToString(separator = "\n")
        val sample = NotEmptyListKtKotlinSample()
        assertPrints(expected, sample::toNotEmptyListOnCollection)
    }

    @Test
    fun `toNotEmptyList() on MutableCollection should pass`() {
        val expected: String = listOf(
            "[1, 2, 3]",
            "[1, 2, 3]",
            "[]",
            "[1, 2, 3]"
        ).joinToString(separator = "\n")
        val sample = NotEmptyListKtKotlinSample()
        assertPrints(expected, sample::toNotEmptyListOnMutableCollection)
    }
}
