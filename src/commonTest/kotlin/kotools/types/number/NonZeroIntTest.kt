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
}
