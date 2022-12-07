package kotools.types

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.test.Test

class StrictlyPositiveIntTest {
    // ---------- StrictlyPositiveInt.Companion.of(Int) ----------

    @Test
    fun of_should_pass_with_a_strictly_positive_Int() {
        val value: Int = StrictlyPositiveInt.random()
            .toInt()
        StrictlyPositiveInt.of(value)
            .getOrThrow()
            .toInt() assertEquals value
    }

    @Test
    fun of_should_fail_with_a_negative_Int() {
        val value: Int = Random.nextInt(Int.MIN_VALUE..0)
        val result: Result<StrictlyPositiveInt> = StrictlyPositiveInt of value
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }

    // ---------- StrictlyPositiveInt.inc() ----------

    @Test
    fun inc_should_return_the_minimum_value_with_the_maximum() {
        var x: StrictlyPositiveInt = StrictlyPositiveInt.max
        x++
        x assertEquals StrictlyPositiveInt.min
    }

    @Test
    fun inc_should_increment_its_value_with_a_value_other_than_the_maximum() {
        var x: StrictlyPositiveInt = StrictlyPositiveInt.random()
        while (x.toInt() == StrictlyPositiveInt.max.toInt())
            x = StrictlyPositiveInt.random()
        val initialValue: Int = x.toInt()
        x++
        x.toInt() assertEquals initialValue + 1
    }

    // ---------- StrictlyPositiveInt.dec() ----------

    @Test
    fun dec_should_return_the_minimum_value_with_the_maximum() {
        var x: StrictlyPositiveInt = StrictlyPositiveInt.min
        x--
        x assertEquals StrictlyPositiveInt.max
    }

    @Test
    fun dec_should_decrement_its_value_with_a_value_other_than_the_minimum() {
        var x: StrictlyPositiveInt = StrictlyPositiveInt.random()
        while (x.toInt() == StrictlyPositiveInt.min.toInt())
            x = StrictlyPositiveInt.random()
        val initialValue: Int = x.toInt()
        x--
        x.toInt() assertEquals initialValue - 1
    }

    // ---------- StrictlyPositiveInt.unaryMinus() ----------

    @Test
    fun unaryMinus_should_pass() {
        val x: StrictlyPositiveInt = StrictlyPositiveInt.random()
        val result: StrictlyNegativeInt = -x
        result.toInt() assertEquals -x.toInt()
    }

    // ---------- StrictlyPositiveInt.times(NonZeroInt) ----------

    @Test
    fun times_should_pass_with_a_NonZeroInt() {
        val x: StrictlyPositiveInt = StrictlyPositiveInt.random()
        val y: NonZeroInt = listOf(Int.MIN_VALUE..-1, 1..Int.MAX_VALUE)
            .random()
            .random()
            .toNonZeroInt()
            .getOrThrow()
        val result: NonZeroInt = x * y
        result.toInt() assertEquals x.toInt() * y.toInt()
    }
}
