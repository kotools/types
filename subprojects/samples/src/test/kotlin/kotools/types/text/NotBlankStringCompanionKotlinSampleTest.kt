package kotools.types.text

import org.kotools.types.assertPrints
import kotlin.test.Test

class NotBlankStringCompanionKotlinSampleTest {
    @Test
    fun `create(Any) should pass`() {
        val sample = NotBlankStringCompanionKotlinSample()
        assertPrints(expected = "Kotools Types", sample::create)
    }

    @Test
    fun `createOrNull(Any) should pass`() {
        val sample = NotBlankStringCompanionKotlinSample()
        assertPrints(expected = "Kotools Types", sample::createOrNull)
    }
}
