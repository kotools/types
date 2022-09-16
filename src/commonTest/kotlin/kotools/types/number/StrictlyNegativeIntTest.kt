package kotools.types.number

import kotools.assert.*
import kotlin.test.Test

class StrictlyNegativeIntTest {
    // ---------- Constants & Getters ----------

    @Test
    fun companionMin_should_be_the_minimum_value_of_NegativeInt(): Unit =
        StrictlyNegativeInt.min.value assertEquals NegativeInt.min.value

    @Test
    fun companionMax_should_be_minus1(): Unit =
        StrictlyNegativeInt.max.value assertEquals -1

    @Test
    fun companionRandom_should_pass(): Unit = StrictlyNegativeInt.random.value
        .assertNotEquals(StrictlyNegativeInt.random.value)

    // ---------- Builders ----------

    @Test
    fun constructor_should_pass_with_a_strictly_negative_Int() {
        val value: Int = StrictlyNegativeInt.random.value
        val result = StrictlyNegativeInt(value)
        result.value assertEquals value
    }

    @Test
    fun constructor_should_throw_an_error_with_a_positive_Int() {
        assertFailsWith<IllegalArgumentException> {
            StrictlyNegativeInt(PositiveInt.random.value)
        }
    }

    @Test
    fun companionOrNull_should_pass_with_a_strictly_negative_Int() {
        val value: Int = StrictlyNegativeInt.random.value
        val result: StrictlyNegativeInt? = StrictlyNegativeInt orNull value
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun companionOrNull_should_return_null_with_a_positive_Int(): Unit =
        (StrictlyNegativeInt orNull PositiveInt.random.value).assertNull()
}
