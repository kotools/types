package kotools.types

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.test.Test

class PositiveIntTest {
    // ---------- PositiveInt.Companion.of(Int) ----------

    @Test
    fun of_should_pass_with_a_positive_Int() {
        val value: Int = PositiveInt.random().toInt()
        PositiveInt.of(value)
            .getOrThrow()
            .toInt() assertEquals value
    }

    @Test
    fun of_should_fail_with_a_strictly_negative_Int() {
        val result: Result<PositiveInt> = PositiveInt of StrictlyNegativeInt
            .random()
            .toInt()
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }

    // ---------- PositiveInt.inc() ----------

    @Test
    fun inc_should_return_the_minimum_value_with_the_maximum() {
        var x: PositiveInt = PositiveInt.max
        x++
        x assertEquals PositiveInt.min
    }

    @Test
    fun inc_should_increment_the_given_value_that_is_other_than_the_maximum() {
        var x: PositiveInt = PositiveInt.random()
        while (x.toInt() == PositiveInt.max.toInt()) x = PositiveInt.random()
        val initialValue: Int = x.toInt()
        x++
        x.toInt() assertEquals initialValue + 1
    }
}
