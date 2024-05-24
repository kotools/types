package kotools.types.collection

import org.kotools.types.assertPrints
import kotlin.test.Test

class NotEmptySetCompanionKotlinSampleTest {
    private val sample = NotEmptySetCompanionKotlinSample()

    @Test
    fun `create(Collection) should pass`() {
        val expected = "[1, 2, 3]"
        assertPrints(expected, this.sample::createWithCollection)
    }

    @Test
    fun `create(MutableCollection) should pass`() {
        val expected: String = listOf(
            "[1, 2, 3]",
            "[1, 2, 3]",
            "[]",
            "[1, 2, 3]"
        ).joinToString(separator = "\n")
        assertPrints(expected, this.sample::createWithMutableCollection)
    }

    @Test
    fun `createOrNull(Collection) should pass`() {
        val expected = "[1, 2, 3]"
        assertPrints(expected, this.sample::createOrNullWithCollection)
    }

    @Test
    fun `createOrNull(MutableCollection) should pass`() {
        val expected: String = listOf(
            "[1, 2, 3]",
            "[1, 2, 3]",
            "[]",
            "[1, 2, 3]"
        ).joinToString(separator = "\n")
        assertPrints(expected, this.sample::createOrNullWithMutableCollection)
    }

    @Test
    fun `of(E, vararg E) should pass`() {
        val expected = "[1, 2, 3]"
        assertPrints(expected, this.sample::of)
    }
}
