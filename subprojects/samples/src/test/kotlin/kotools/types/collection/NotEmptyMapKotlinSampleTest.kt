package kotools.types.collection

import org.kotools.types.assertPrints
import kotlin.test.Test

class NotEmptyMapKotlinSampleTest {
    @Test
    fun `serialization processes should pass`() {
        val expected: String = listOf("{\"a\":1,\"b\":2}", "true")
            .joinToString(separator = "\n")
        val sample = NotEmptyMapKotlinSample()
        assertPrints(expected, sample::serialization)
    }
}
