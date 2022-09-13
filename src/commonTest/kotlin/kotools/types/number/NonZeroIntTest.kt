package kotools.types.number

import kotools.assert.*
import kotlin.test.Test

class NonZeroIntTest {
    // ---------- Constants & Getters ----------

    @Test
    fun companionMin_should_be_the_minimum_value_of_Int() =
        NonZeroInt.min.value assertEquals Int.MIN_VALUE

    @Test
    fun companionMax_should_be_the_maximum_of_Int() =
        NonZeroInt.max.value assertEquals Int.MAX_VALUE

    @Test
    fun companionRanges_should_not_contain_0(): Unit =
        NonZeroInt.ranges.all { 0 !in it }.assertTrue()

    @Test
    fun companionRandom_should_pass(): Unit =
        NonZeroInt.random.value assertNotEquals NonZeroInt.random.value

    // ---------- Builders ----------

    @Test
    fun constructor_should_pass_with_an_Int_other_than_0(): Unit =
        NonZeroInt.random.value.let { NonZeroInt(it).value assertEquals it }

    @Test
    fun constructor_should_throw_an_error_with_an_Int_that_equals_0() {
        assertFailsWith<IllegalArgumentException> { NonZeroInt(0) }
    }

    @Test
    fun companionOrNull_should_pass_with_an_Int_other_than_0() =
        NonZeroInt.random.value.let {
            val result: NonZeroInt? = NonZeroInt orNull it
            result.assertNotNull().value assertEquals it
        }

    @Test
    fun companionOrNull_should_return_null_with_an_Int_that_equals_0() {
        val result: NonZeroInt? = NonZeroInt orNull 0
        result.assertNull()
    }

    // ---------- Unary operations ----------

    @Test
    fun inc_should_return_1_with_a_value_that_equals_minus1() {
        var x = NonZeroInt(-1)
        x++
        x.value assertEquals 1
    }

    @Test
    fun inc_should_return_the_minimum_value_with_the_maximum_value() {
        var x: NonZeroInt = NonZeroInt.max
        x++
        x assertEquals NonZeroInt.min
    }

    @Test
    fun inc_should_return_the_value_incremented_by_1_with_a_value_other_than_minus1_and_the_maximum_value() {
        var x: NonZeroInt = NonZeroInt.random
        while (x.value == -1 || x.value == NonZeroInt.max.value)
            x = NonZeroInt.random
        val value: Int = x.value
        x++
        x.value assertEquals value + 1
    }

    @Test
    fun dec_should_return_minus1_with_a_value_that_equals_1() {
        var x = NonZeroInt(1)
        x--
        x.value assertEquals -1
    }

    @Test
    fun dec_should_return_the_maximum_value_with_the_minimum_value() {
        var x: NonZeroInt = NonZeroInt.min
        x--
        x assertEquals NonZeroInt.max
    }

    @Test
    fun dec_should_return_the_value_decremented_by_1_with_a_value_other_than_1_and_the_minimum_value() {
        var x: NonZeroInt = NonZeroInt.random
        while (x.value == 1 || x.value == NonZeroInt.min.value)
            x = NonZeroInt.random
        val value: Int = x.value
        x--
        x.value assertEquals value - 1
    }

    @Test
    fun unaryMinus_should_pass() {
        val x: NonZeroInt = NonZeroInt.random
        val value: Int = x.value
        val result: NonZeroInt = -x
        result.value assertEquals -value
    }
}
