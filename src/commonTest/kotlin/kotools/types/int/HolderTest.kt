package kotools.types.int

import kotools.assert.*
import kotlin.random.Random
import kotlin.test.Test

class IntHolderImplementationTest {
    // ---------- Constructor ----------

    @Test
    fun constructor_should_pass_without_a_validator() {
        val value: Int = Random.nextInt()
        val result: IntHolder = IntHolderImplementation(value)
        result.value assertEquals value
        result.validator.assertNull()
    }

    @Test
    fun constructor_should_pass_with_a_succeeding_validator() {
        val range: IntRange = 1..Int.MAX_VALUE
        val value: Int = Random.nextInt(range.first, range.last)
        val result: IntHolder = IntHolderImplementation(value) { it in range }
        result.value assertEquals value
        result.validator.assertNotNull()
            .isValid(value)
            .assertTrue()
    }

    @Test
    fun constructor_should_throw_an_error_with_a_failing_validator() {
        val range: IntRange = 1..Int.MAX_VALUE
        val value: Int = Random.nextInt(range.first, range.last)
        assertFailsWith<IllegalArgumentException> {
            IntHolderImplementation(value) { it !in range }
        }
    }

    // ---------- Comparisons ----------

    @Test
    fun compareTo_should_pass_with_an_IntHolder() {
        val xValue: Int = Random.nextInt()
        val x: IntHolder = IntHolderImplementation(xValue)
        val yValue: Int = Random.nextInt()
        val y: IntHolder = IntHolderImplementation(yValue)
        val result: Int = x.compareTo(y)
        result assertEquals xValue.compareTo(yValue)
    }
}
