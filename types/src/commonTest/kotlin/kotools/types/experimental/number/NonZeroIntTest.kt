package kotools.types.experimental.number

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.random.Random
import kotlin.test.Test

@ExperimentalKotoolsTypesApi
class NonZeroIntTest {
    // ---------- NonZeroInt.toString() ----------

    @Test
    fun toString_should_behave_like_an_Int() {
        val x: NonZeroInt = NonZeroInt.random()
        x.toString() assertEquals x.value.toString()
    }

    // ---------- Int.nonZero ----------

    @Test
    fun nonZero_should_pass_with_an_Int_other_than_zero() {
        val value: Int = Random.nextInt()
            .takeIf { it != 0 }
            ?: Random.nextInt()
        value.nonZero.getOrThrow().value assertEquals value
    }

    @Test
    fun nonZero_should_fail_with_an_Int_that_equals_zero() {
        assertFailsWith<NonZeroNumber.Exception>(0.nonZero::getOrThrow)
            .message.assertNotNull()
            .isNotBlank()
            .assertTrue()
    }

    // ---------- NonZeroInt.inc() ----------

    @Test
    fun inc_should_return_1_with_minus1() {
        var x: NonZeroInt = (-1).nonZero.getOrThrow()
        x++
        x.value assertEquals 1
    }

    @Test
    fun inc_should_return_the_minimum_with_the_maximum() {
        var x: NonZeroInt = NonZeroInt.max
        x++
        x assertEquals NonZeroInt.min
    }

    @Test
    fun inc_should_increment_the_value_with_a_value_other_than_minus1_and_than_the_maximum() {
        var x: NonZeroInt
        do {
            x = NonZeroInt.random()
        } while (x.value == -1 || x == NonZeroInt.min)
        val initialValue: Int = x.value
        x++
        x.value assertEquals initialValue + 1
    }
}

@ExperimentalKotoolsTypesApi
class NonZeroIntCompanionTest {
    @Test
    fun min_should_equal_the_minimum_value_of_Int(): Unit =
        NonZeroInt.min.value assertEquals Int.MIN_VALUE

    @Test
    fun max_should_equal_the_maximum_value_of_Int(): Unit =
        NonZeroInt.max.value assertEquals Int.MAX_VALUE

    @Test
    fun random_should_pass(): Unit = repeat(100) { NonZeroInt.random() }
}
