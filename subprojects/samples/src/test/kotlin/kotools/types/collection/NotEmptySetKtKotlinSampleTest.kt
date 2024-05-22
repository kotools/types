package kotools.types.collection

import org.kotools.types.assertPrints
import kotlin.test.Test

class NotEmptySetKtKotlinSampleTest {
    private val sample: NotEmptySetKtKotlinSample = NotEmptySetKtKotlinSample()

    @Test
    fun `notEmptySetOf(E, vararg E) should pass`(): Unit =
        assertPrints(expected = "[1, 2, 3]", this.sample::notEmptySetOf)

    @Test
    fun `toNotEmptySet() should pass on Collection`() {
        val exceptionMessage = "Given collection shouldn't be empty."
        val expected: String = listOf(
            "Success([1, 2, 3])",
            "Failure(java.lang.IllegalArgumentException: $exceptionMessage)"
        ).joinToString(separator = "\n")
        assertPrints(expected, this.sample::toNotEmptySetOnCollection)
    }

    @Test
    fun `toNotEmptySet() should pass on MutableCollection`() {
        val expected: String = listOf(
            "[1, 2, 3]",
            "[1, 2, 3]",
            "[]",
            "[1, 2, 3]"
        ).joinToString(separator = "\n")
        assertPrints(expected, this.sample::toNotEmptySetOnMutableCollection)
    }
}
