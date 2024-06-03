package kotools.types.text

import org.kotools.types.assertPrints
import kotlin.test.Test

class NotBlankStringKotlinSampleTest {
    @Test
    fun `serialization processes should pass`() {
        val expected: String = listOf("\"hello world\"", true)
            .joinToString(separator = "\n")
        val sample = NotBlankStringKotlinSample()
        assertPrints(expected, sample::serialization)
    }

    @Test
    fun `plus(Any) should pass`() {
        val sample = NotBlankStringKotlinSample()
        assertPrints(expected = "hello world", sample::plusOperator)
    }
}
