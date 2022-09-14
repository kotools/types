package kotools.types.number

import kotools.assert.*
import kotlin.test.Test

class PositiveIntTest {
    // ---------- Constants & Getters ----------

    @Test
    fun companionMin_should_be_0(): Unit = PositiveInt.min.value assertEquals 0

    @Test
    fun companionMax_should_be_the_maximum_value_of_Int(): Unit =
        PositiveInt.max.value assertEquals Int.MAX_VALUE

    @Test
    fun companionRandom_should_pass(): Unit =
        PositiveInt.random.value assertNotEquals PositiveInt.random.value

    // ---------- Builders ----------

    @Test
    fun constructor_should_pass_with_a_positive_Int() {
        val value: Int = PositiveInt.random.value
        PositiveInt(value).value assertEquals value
    }

    @Test
    fun constructor_should_throw_an_error_with_a_strictly_negative_Int() {
        // TODO: Use StrictlyNegativeInt.random.value instead
        val value: Int = NonZeroInt.negativeRange.random()
        assertFailsWith<IllegalArgumentException> { PositiveInt(value) }
    }

    @Test
    fun companionOrNull_should_pass_with_a_positive_Int() {
        val value: Int = PositiveInt.random.value
        val result: PositiveInt? = PositiveInt orNull value
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun companionOrNull_should_return_null_with_a_strictly_negative_Int() {
        // TODO: Use StrictlyNegativeInt.random.value instead
        val value: Int = NonZeroInt.negativeRange.random()
        val result: PositiveInt? = PositiveInt orNull value
        result.assertNull()
    }
}
