package kotools.types.number

import kotools.assert.*
import kotlin.test.Test

class NegativeIntTest {
    // ---------- Constants & Getters ----------

    @Test
    fun companionMin_should_be_the_minimum_value_of_Int(): Unit =
        NegativeInt.min.value assertEquals Int.MIN_VALUE

    @Test
    fun companionMax_should_be_0(): Unit = NegativeInt.max.value assertEquals 0

    @Test
    fun companionRandom_should_pass(): Unit =
        NegativeInt.random.value assertNotEquals NegativeInt.random.value

    // ---------- Builders ----------

    @Test
    fun constructor_should_pass_with_a_negative_Int() {
        val value: Int = NegativeInt.random.value
        val result = NegativeInt(value)
        result.value assertEquals value
    }

    @Test
    fun constructor_should_throw_an_error_with_a_strictly_positive_Int() {
        val value: Int = StrictlyPositiveInt.random.value
        assertFailsWith<IllegalArgumentException> { NegativeInt(value) }
    }

    @Test
    fun companionOrNull_should_pass_with_a_negative_Int() {
        val value: Int = NegativeInt.random.value
        val result: NegativeInt? = NegativeInt orNull value
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun companionOrNull_should_return_null_with_a_strictly_positive_Int() {
        val value: Int = StrictlyPositiveInt.random.value
        val result: NegativeInt? = NegativeInt orNull value
        result.assertNull()
    }
}
