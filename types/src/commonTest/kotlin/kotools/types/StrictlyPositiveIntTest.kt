package kotools.types

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.test.Test

class StrictlyPositiveIntIncTest {
    @Test
    fun should_return_the_minimum_value_with_the_maximum() {
        var x: StrictlyPositiveInt = StrictlyPositiveInt.max
        x++
        x assertEquals StrictlyPositiveInt.min
    }

    @Test
    fun should_increment_its_value_with_a_value_other_than_the_maximum() {
        var x: StrictlyPositiveInt = StrictlyPositiveInt.random()
        while (x.toInt() == StrictlyPositiveInt.max.toInt())
            x = StrictlyPositiveInt.random()
        val initialValue: Int = x.toInt()
        x++
        x.toInt() assertEquals initialValue + 1
    }
}

class StrictlyPositiveIntDecTest {
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
}

class StrictlyPositiveIntUnaryMinusTest {
    @Test
    fun should_pass() {
        val x: StrictlyPositiveInt = StrictlyPositiveInt.random()
        val result: StrictlyNegativeInt = -x
        result.toInt() assertEquals -x.toInt()
    }
}

class StrictlyPositiveIntTimesTest {
    @Test
    fun should_return_a_NonZeroInt_with_a_NonZeroInt() {
        val x: StrictlyPositiveInt = StrictlyPositiveInt.random()
        val y: NonZeroInt = NonZeroInt.random()
        val result: NonZeroInt = x * y
        result.toInt() assertEquals x.toInt() * y.toInt()
    }

    @Test
    fun should_return_a_NonZeroInt_with_a_StrictlyPositiveInt() {
        val x: StrictlyPositiveInt = StrictlyPositiveInt.random()
        val y: StrictlyPositiveInt = StrictlyPositiveInt.random()
        val result: NonZeroInt = x * y
        result.toInt() assertEquals x.toInt() * y.toInt()
    }

    @Test
    fun should_return_a_NonZeroInt_with_a_StrictlyNegativeInt() {
        val x: StrictlyPositiveInt = StrictlyPositiveInt.random()
        val y: StrictlyNegativeInt = StrictlyNegativeInt.random()
        val result: NonZeroInt = x * y
        result.toInt() assertEquals x.toInt() * y.toInt()
    }
}

class IntDivStrictlyPositiveIntTest {
    @Test
    fun should_pass() {
        val x: Int = Random.nextInt()
        val y: StrictlyPositiveInt = StrictlyPositiveInt.random()
        x / y assertEquals x / y.toInt()
    }
}

class IntToStrictlyPositiveIntTest {
    @Test
    fun should_pass_with_a_strictly_positive_Int() {
        val value: Int = Random.nextInt(StrictlyPositiveInt.range)
        value.toStrictlyPositiveInt()
            .getOrThrow()
            .toInt() assertEquals value
    }

    @Test
    fun should_fail_with_a_negative_Int(): Unit = Random
        .nextInt(NegativeInt.range)
        .toStrictlyPositiveInt()
        .let { assertFailsWith<IllegalArgumentException>(it::getOrThrow) }
        .message
        .assertNotNull()
        .isNotBlank()
        .assertTrue()
}
