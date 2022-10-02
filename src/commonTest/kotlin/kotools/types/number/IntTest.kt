package kotools.types.number

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotlin.test.Test

class NonZeroIntTest {
    @Test
    fun companion_min_should_equal_the_minimum_value_of_Int(): Unit =
        NonZeroInt.min.value assertEquals Int.MIN_VALUE

    @Test
    fun companion_max_should_equal_the_maximum_value_of_Int(): Unit =
        NonZeroInt.max.value assertEquals Int.MAX_VALUE

    @Test
    fun constructor_should_pass_with_an_Int_other_than_zero() {
        // GIVEN
        val value: Int = NonZeroInt.random.value
        // WHEN
        val result = NonZeroInt(value)
        // THEN
        result.value assertEquals value
    }

    @Test
    fun constructor_should_throw_an_error_with_an_Int_that_equals_zero() {
        // GIVEN & WHEN & THEN
        val error: IllegalArgumentException = assertFailsWith { NonZeroInt(0) }
        error.message.assertNotNull()
    }

    @Test
    fun companion_orNull_should_pass_with_an_Int_other_than_zero() {
        // GIVEN
        val value: Int = NonZeroInt.random.value
        // WHEN
        val result: NonZeroInt? = NonZeroInt orNull value
        // THEN
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun companion_orNull_should_return_null_with_an_Int_that_equals_zero() {
        // GIVEN & WHEN
        val result: NonZeroInt? = NonZeroInt orNull 0
        // THEN
        result.assertNull()
    }

    @Suppress("TestFunctionName")
    @Test
    fun Int_toNonZeroInt_should_pass_with_an_Int_other_than_zero() {
        // GIVEN
        val value: Int = NonZeroInt.random.value
        // WHEN
        val result: NonZeroInt = value.toNonZeroInt()
        // THEN
        result.value assertEquals value
    }

    @Suppress("TestFunctionName")
    @Test
    fun Int_to_NonZeroInt_should_throw_an_error_with_an_Int_that_equals_zero() {
        // GIVEN & WHEN & THEN
        assertFailsWith<IllegalArgumentException>(0::toNonZeroInt)
    }

    @Suppress("TestFunctionName")
    @Test
    fun Int_toNonZeroIntOrNull_should_pass_with_an_Int_other_than_zero() {
        // GIVEN
        val value: Int = NonZeroInt.random.value
        // WHEN
        val result: NonZeroInt? = value.toNonZeroIntOrNull()
        // THEN
        result.assertNotNull().value assertEquals value
    }

    @Suppress("TestFunctionName")
    @Test
    fun Int_toNonZeroIntOrNull_should_return_null_with_an_Int_that_equals_zero(): Unit =
        0.toNonZeroIntOrNull().assertNull()
}

class PositiveIntTest {
    @Test
    fun companion_min_should_equal_zero(): Unit =
        PositiveInt.min.value assertEquals 0

    @Test
    fun companion_max_should_equal_the_maximum_value_of_Int(): Unit =
        PositiveInt.max.value assertEquals Int.MAX_VALUE

    @Test
    fun constructor_should_pass_with_a_positive_Int() {
        // GIVEN
        val value: Int = PositiveInt.random.value
        // WHEN
        val result = PositiveInt(value)
        // THEN
        result.value assertEquals value
    }

    @Test
    fun constructor_should_throw_an_error_with_a_strictly_negative_Int() {
        // GIVEN
        val value: Int = StrictlyNegativeInt.random.value
        // WHEN & THEN
        val error: IllegalArgumentException =
            assertFailsWith { PositiveInt(value) }
        error.message.assertNotNull()
    }

    @Test
    fun companion_orNull_should_pass_with_a_positive_Int() {
        // GIVEN
        val value: Int = PositiveInt.random.value
        // WHEN
        val result: PositiveInt? = PositiveInt orNull value
        // THEN
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun companion_orNull_should_return_null_with_a_strictly_negative_Int() {
        // GIVEN
        val value: Int = StrictlyNegativeInt.random.value
        // WHEN
        val result: PositiveInt? = PositiveInt orNull value
        // THEN
        result.assertNull()
    }

    @Suppress("TestFunctionName")
    @Test
    fun Int_toPositiveInt_should_pass_with_a_positive_Int() {
        // GIVEN
        val value: Int = PositiveInt.random.value
        // WHEN
        val result: PositiveInt = value.toPositiveInt()
        // THEN
        result.value assertEquals value
    }

    @Suppress("TestFunctionName")
    @Test
    fun Int_toPositiveInt_should_throw_an_error_with_a_strictly_negative_Int() {
        // GIVEN & WHEN & THEN
        assertFailsWith<IllegalArgumentException>(
            StrictlyNegativeInt.random.value::toPositiveInt
        )
    }

    @Suppress("TestFunctionName")
    @Test
    fun Int_toPositiveIntOrNull_should_pass_with_a_positive_Int() {
        // GIVEN
        val value: Int = PositiveInt.random.value
        // WHEN
        val result: PositiveInt? = value.toPositiveIntOrNull()
        // THEN
        result?.value assertEquals value
    }

    @Suppress("TestFunctionName")
    @Test
    fun Int_toPositiveIntOrNull_should_return_null_with_a_strictly_negative_Int() {
        // GIVEN
        val value: Int = StrictlyNegativeInt.random.value
        // WHEN
        val result: PositiveInt? = value.toPositiveIntOrNull()
        // THEN
        result.assertNull()
    }
}

class StrictlyPositiveIntTest {
    @Test
    fun companion_min_should_equal_1(): Unit =
        StrictlyPositiveInt.min.value assertEquals 1

    @Test
    fun companion_max_should_equal_the_maximum_value_of_Int(): Unit =
        StrictlyPositiveInt.max.value assertEquals Int.MAX_VALUE

    @Test
    fun constructor_should_pass_with_a_strictly_positive_Int() {
        // GIVEN
        val value: Int = StrictlyPositiveInt.random.value
        // WHEN
        val result = StrictlyPositiveInt(value)
        // THEN
        result.value assertEquals value
    }

    @Test
    fun constructor_should_throw_an_error_with_a_negative_Int() {
        // GIVEN
        val value: Int = NegativeInt.random.value
        // WHEN & THEN
        val error: IllegalArgumentException =
            assertFailsWith { StrictlyPositiveInt(value) }
        error.message.assertNotNull()
    }

    @Test
    fun companion_orNull_should_pass_with_a_strictly_positive_Int() {
        // GIVEN
        val value: Int = StrictlyPositiveInt.random.value
        // WHEN
        val result: StrictlyPositiveInt? = StrictlyPositiveInt orNull value
        // THEN
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun companion_orNull_should_return_null_with_a_negative_Int() {
        // GIVEN
        val value: Int = NegativeInt.random.value
        // WHEN
        val result: StrictlyPositiveInt? = StrictlyPositiveInt orNull value
        // THEN
        result.assertNull()
    }
}

class NegativeIntTest {
    @Test
    fun companion_min_should_equal_the_minimum_value_of_Int(): Unit =
        NegativeInt.min.value assertEquals Int.MIN_VALUE

    @Test
    fun companion_max_should_equal_zero(): Unit =
        NegativeInt.max.value assertEquals 0

    @Test
    fun constructor_should_pass_with_a_negativeInt() {
        // GIVEN
        val value: Int = NegativeInt.random.value
        // WHEN
        val result = NegativeInt(value)
        // THEN
        result.value assertEquals value
    }

    @Test
    fun constructor_should_throw_an_error_with_a_strictly_positive_Int() {
        // GIVEN
        val value: Int = StrictlyPositiveInt.random.value
        // WHEN & THEN
        val error: IllegalArgumentException =
            assertFailsWith { NegativeInt(value) }
        error.message.assertNotNull()
    }

    @Test
    fun companion_orNull_should_pass_with_a_negative_Int() {
        // GIVEN
        val value: Int = NegativeInt.random.value
        // WHEN
        val result: NegativeInt? = NegativeInt orNull value
        // THEN
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun companion_orNull_should_return_null_with_a_strictly_positive_Int() {
        // GIVEN
        val value: Int = StrictlyPositiveInt.random.value
        // WHEN
        val result: NegativeInt? = NegativeInt orNull value
        // THEN
        result.assertNull()
    }
}

class StrictlyNegativeIntTest {
    @Test
    fun companion_min_should_equal_the_minimum_value_of_Int(): Unit =
        StrictlyNegativeInt.min.value assertEquals Int.MIN_VALUE

    @Test
    fun companion_max_should_equal_minus1(): Unit =
        StrictlyNegativeInt.max.value assertEquals -1

    @Test
    fun constructor_should_pass_with_a_strictly_negative_Int() {
        // GIVEN
        val value: Int = StrictlyNegativeInt.random.value
        // WHEN
        val result = StrictlyNegativeInt(value)
        // THEN
        result.value assertEquals value
    }

    @Test
    fun constructor_should_throw_an_error_with_a_positive_Int() {
        // GIVEN
        val value: Int = PositiveInt.random.value
        // WHEN & THEN
        val error: IllegalArgumentException =
            assertFailsWith { StrictlyNegativeInt(value) }
        error.message.assertNotNull()
    }

    @Test
    fun companion_orNull_should_pass_with_a_strictly_negative_Int() {
        // GIVEN
        val value: Int = StrictlyNegativeInt.random.value
        // WHEN
        val result: StrictlyNegativeInt? = StrictlyNegativeInt orNull value
        // THEN
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun companion_orNull_return_null_with_a_positive_Int() {
        // GIVEN
        val value: Int = PositiveInt.random.value
        // WHEN
        val result: StrictlyNegativeInt? = StrictlyNegativeInt orNull value
        // THEN
        result.assertNull()
    }
}
