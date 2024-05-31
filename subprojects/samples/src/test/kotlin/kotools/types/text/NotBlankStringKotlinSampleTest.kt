package kotools.types.text

import org.kotools.types.assertPrints
import kotlin.test.Test

class NotBlankStringKotlinSampleTest {
    @Test
    fun `plus(Any) should pass`() {
        val sample = NotBlankStringKotlinSample()
        assertPrints(expected = "hello world", sample::plusOperator)
    }
}
