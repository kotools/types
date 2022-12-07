package kotools.types

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.test.Test

class ExplicitIntTest {
    // ---------- ExplicitInt.toNotBlankString() ----------

    @Test
    fun toNotBlankString_should_pass() {
        val x: ExplicitInt = NonZeroInt.random()
        x.toNotBlankString().toString() assertEquals x.toInt().toString()
    }
}

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
}

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

    // ---------- PositiveInt.dec() ----------

    @Test
    fun dec_should_return_the_maximum_value_with_the_minimum() {
        var x: PositiveInt = PositiveInt.min
        x--
        x assertEquals PositiveInt.max
    }

    @Test
    fun dec_should_decrement_the_given_value_that_is_other_than_the_minimum() {
        var x: PositiveInt = PositiveInt.random()
        while (x.toInt() == PositiveInt.min.toInt()) x = PositiveInt.random()
        val initialValue: Int = x.toInt()
        x--
        x.toInt() assertEquals initialValue - 1
    }

    // ---------- PositiveInt.unaryMinus() ----------

    @Test
    fun unaryMinus_should_pass() {
        val x: PositiveInt = PositiveInt.random()
        val result: NegativeInt = -x
        result.toInt() assertEquals -x.toInt()
    }
}

class NegativeIntTest {
    // ---------- NegativeInt.Companion.of(Int) ----------

    @Test
    fun of_should_pass_with_a_negative_Int() {
        val value: Int = NegativeInt.random().toInt()
        NegativeInt.of(value)
            .getOrThrow()
            .toInt() assertEquals value
    }

    @Test
    fun of_should_fail_with_a_strictly_positive_Int() {
        val result: Result<NegativeInt> = StrictlyPositiveInt.random()
            .toInt()
            .let(NegativeInt.Companion::of)
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }

    // ---------- NegativeInt.unaryMinus() ----------

    @Test
    fun unaryMinus_should_pass() {
        val x: NegativeInt = NegativeInt.random()
        val result: PositiveInt = -x
        result.toInt() assertEquals -x.toInt()
    }
}

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

class StrictlyNegativeIntTest {
    // ---------- StrictlyNegativeInt.Companion.of(Int) ----------

    @Test
    fun of_should_pass_with_a_strictly_negative_Int() {
        val value: Int = StrictlyNegativeInt.random()
            .toInt()
        StrictlyNegativeInt.of(value)
            .getOrThrow()
            .toInt() assertEquals value
    }

    @Test
    fun of_should_pass_with_a_positive_Int() {
        val result: Result<StrictlyNegativeInt> = Random
            .nextInt(0..Int.MAX_VALUE)
            .let(StrictlyNegativeInt.Companion::of)
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }

    // ---------- StrictlyNegativeInt.unaryMinus() ----------

    @Test
    fun unaryMinus_should_pass() {
        val x: StrictlyNegativeInt = StrictlyNegativeInt.random()
        val result: StrictlyPositiveInt = -x
        result.toInt() assertEquals -x.toInt()
    }
}
