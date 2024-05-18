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

    @Test
    fun `toNotEmptyMap() on Map should pass`() {
        val exceptionMessage = "Given map shouldn't be empty."
        val expected: String = listOf(
            "Success({a=1, b=2})",
            "Failure(java.lang.IllegalArgumentException: $exceptionMessage)"
        ).joinToString(separator = "\n")
        val sample = NotEmptyMapKtKotlinSample()
        assertPrints(expected, sample::toNotEmptyMapOnMap)
    }

    @Test
    fun `toNotEmptyMap() on MutableMap should pass`() {
        val expected: String = listOf(
            "{a=1, b=2}",
            "{a=1, b=2}",
            "{}",
            "{a=1, b=2}"
        ).joinToString(separator = "\n")
        val sample = NotEmptyMapKtKotlinSample()
        assertPrints(expected, sample::toNotEmptyMapOnMutableMap)
    }
}
