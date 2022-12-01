package kotools.types

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.test.Test

class NonZeroIntTest {
    // ---------- NonZeroInt.Companion.of(Int) ----------

    @Test
    fun of_should_pass_with_an_Int_other_than_zero() {
        val value: Int = NonZeroInt.random().toInt()
        NonZeroInt.of(value)
            .getOrThrow()
            .toInt() assertEquals value
    }

    @Test
    fun of_should_fail_with_an_Int_that_equals_zero() {
        val result: Result<NonZeroInt> = NonZeroInt of 0
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }

    // ---------- NonZeroInt.inc() ----------

    @Test
    fun inc_should_return_the_minimum_value_with_the_maximum() {
        var x: NonZeroInt = NonZeroInt.max
        x++
        x assertEquals NonZeroInt.min
    }

    @Test
    fun inc_should_return_1_with_minus1() {
        var x: NonZeroInt = NonZeroInt.of(-1).getOrThrow()
        x++
        x.toInt() assertEquals 1
    }

    @Test
    fun inc_should_increment_the_given_value_that_is_other_than_the_maximum_and_minus1() {
        var x: NonZeroInt = NonZeroInt.random()
        val excludedValues: List<Int> = listOf(NonZeroInt.max.toInt(), -1)
        while (x.toInt() in excludedValues) x = NonZeroInt.random()
        val initialValue: Int = x.toInt()
        x++
        x.toInt() assertEquals initialValue + 1
    }

    // ---------- NonZeroInt.dec() ----------

    @Test
    fun dec_should_return_the_maximum_value_with_the_minimum() {
        var x: NonZeroInt = NonZeroInt.min
        x--
        x assertEquals NonZeroInt.max
    }

    @Test
    fun dec_should_return_minus1_with_1() {
        var x: NonZeroInt = 1.toNonZeroInt().getOrThrow()
        x--
        x.toInt() assertEquals -1
    }

    @Test
    fun dec_should_decrement_the_given_value_that_is_other_than_the_minimum_and_1() {
        var x: NonZeroInt = NonZeroInt.random()
        val excludedValues: List<Int> = listOf(NonZeroInt.min.toInt(), 1)
        while (x.toInt() in excludedValues) x = NonZeroInt.random()
        val initialValue: Int = x.toInt()
        x--
        x.toInt() assertEquals initialValue - 1
    }

    // ---------- NonZeroInt.unaryMinus() ----------

    @Test
    fun unaryMinus_should_pass() {
        val x: NonZeroInt = NonZeroInt.random()
        val result: NonZeroInt = -x
        result.toInt() assertEquals -x.toInt()
    }

    // ---------- NonZeroInt.times(NonZeroInt) ----------

    @Test
    fun times_should_pass_with_a_NonZeroInt() {
        val x: NonZeroInt = NonZeroInt.random()
        val y: NonZeroInt = NonZeroInt.random()
        val result: NonZeroInt = x * y
        result.toInt() assertEquals x.toInt() * y.toInt()
    }

    // ---------- NonZeroInt.times(StrictlyPositiveInt) ----------

    @Test
    fun times_should_pass_with_a_StrictlyPositiveInt() {
        val x: NonZeroInt = NonZeroInt.random()
        val y: StrictlyPositiveInt = StrictlyPositiveInt.random()
        val result: NonZeroInt = x * y
        result.toInt() assertEquals x.toInt() * y.toInt()
    }

    // ---------- NonZeroInt.times(StrictlyNegativeInt) ----------

    @Test
    fun times_should_pass_with_a_StrictlyNegativeInt() {
        val x: NonZeroInt = NonZeroInt.random()
        val y: StrictlyNegativeInt = StrictlyNegativeInt.random()
        val result: NonZeroInt = x * y
        result.toInt() assertEquals x.toInt() * y.toInt()
    }

    // ---------- NonZeroInt.toNotBlankString() ----------

    @Test
    fun toNotBlankString_should_pass() {
        val x: NonZeroInt = NonZeroInt.random()
        x.toNotBlankString().toString() assertEquals x.toInt().toString()
    }
}
