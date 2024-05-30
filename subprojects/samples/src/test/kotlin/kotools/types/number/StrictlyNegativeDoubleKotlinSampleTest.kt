package kotools.types.number

import org.kotools.types.assertPrints
import org.kotools.types.assertPrintsTrue
import kotlin.test.Test

class StrictlyNegativeDoubleKotlinSampleTest {
    private val sample: StrictlyNegativeDoubleKotlinSample =
        StrictlyNegativeDoubleKotlinSample()

    @Test
    fun `serialization processes should pass`() {
        val expected: String = listOf(-42.0, true)
            .joinToString(separator = "\n")
        assertPrints(expected, this.sample::serialization)
    }

    @Test
    fun `equals(nullable Any) should pass`(): Unit =
        assertPrintsTrue(this.sample::equalsOverride)

    @Test
    fun `hashCode() should pass`(): Unit =
        assertPrintsTrue(this.sample::hashCodeOverride)

    @Test
    fun `toDouble() should pass`() {
        val expected = "-7.0"
        assertPrints(expected, this.sample::toDouble)
    }

    @Test
    fun `toString() should pass`(): Unit =
        assertPrints(expected = "-23.0", this.sample::toStringOverride)
}
