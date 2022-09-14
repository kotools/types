package kotools.types.number

import kotools.assert.*
import kotlin.test.Test

class StrictlyPositiveIntTest {
    // ---------- Constants & Getters ----------

    @Test
    fun companionMin_should_be_1(): Unit =
        StrictlyPositiveInt.min.value assertEquals 1

    @Test
    fun companionMax_should_be_the_maximum_value_of_PositiveInt(): Unit =
        StrictlyPositiveInt.max.value assertEquals PositiveInt.max.value

    @Test
    fun companionRandom_should_pass(): Unit = StrictlyPositiveInt.random.value
        .assertNotEquals(StrictlyPositiveInt.random.value)

    // ---------- Builders ----------

    @Test
    fun constructor_should_pass_with_a_strictly_positive_Int() {
        val value: Int = StrictlyPositiveInt.random.value
        val result = StrictlyPositiveInt(value)
        result.value assertEquals value
    }

    @Test
    fun constructor_should_throw_an_error_with_a_negative_Int() {
        // TODO: Use NegativeInt.random.value instead
        val value: Int = NonZeroInt.negativeRange.random()
        assertFailsWith<IllegalArgumentException> { StrictlyPositiveInt(value) }
    }

    @Test
    fun companionOrNull_should_pass_with_a_strictly_positive_Int() {
        val value: Int = StrictlyPositiveInt.random.value
        val result: StrictlyPositiveInt? = StrictlyPositiveInt orNull value
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun companionOrNull_should_return_null_with_a_negative_Int() {
        // TODO: Use NegativeInt.random.value instead
        val value: Int = NonZeroInt.negativeRange.random()
        val result: StrictlyPositiveInt? = StrictlyPositiveInt orNull value
        result.assertNull()
    }
}
