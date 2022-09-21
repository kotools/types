package kotools.types.int

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotlin.random.Random
import kotlin.test.Test

class IntHolderTest {
    // ---------- Constructor ----------

    @Test
    fun constructor_should_pass_without_a_validator() {
        val value: Int = Random.nextInt()
        val result = IntHolder(value)
        result.value assertEquals value
    }

    @Test
    fun constructor_should_pass_with_a_succeeding_validator() {
        val range: IntRange = 1..Int.MAX_VALUE
        val value: Int = Random.nextInt(range.first, range.last)
        val result = IntHolder(value) { it in range }
        result.value assertEquals value
    }

    @Test
    fun constructor_should_throw_an_error_with_a_failing_validator() {
        val range: IntRange = 1..Int.MAX_VALUE
        val value: Int = Random.nextInt(range.first, range.last)
        assertFailsWith<IllegalArgumentException> {
            IntHolder(value) { it !in range }
        }
    }

    // ---------- Comparisons ----------

    @Test
    fun compareTo_should_pass_with_an_IntHolder() {
        val xValue: Int = Random.nextInt()
        val yValue: Int = Random.nextInt()
        val x = IntHolder(xValue)
        val y = IntHolder(yValue)
        val result: Int = x compareTo y
        result assertEquals xValue.compareTo(yValue)
    }
}
