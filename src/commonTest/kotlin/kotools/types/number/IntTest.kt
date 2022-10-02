package kotools.types.number

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotlin.test.Test

class NonZeroIntTest {
    // ---------- Builders ----------

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

    // ---------- Unary operations ----------

    @Test
    fun inc_should_return_1_with_a_value_that_equals_minus1() {
        // GIVEN
        var x = NonZeroInt(-1)
        // WHEN
        x++
        // THEN
        x.value assertEquals 1
    }

    @Test
    fun inc_should_return_the_minimum_value_with_the_maximum_value() {
        // GIVEN
        var x: NonZeroInt = NonZeroInt.max
        // WHEN
        x++
        // THEN
        x assertEquals NonZeroInt.min
    }

    @Test
    fun inc_should_increment_the_value_by_1_with_an_initial_value_other_than_minus1_and_the_maximum_value() {
        // GIVEN
        var x: NonZeroInt = NonZeroInt.random
        while (x.value == -1 || x == NonZeroInt.max) x = NonZeroInt.random
        val initialValue: Int = x.value
        // WHEN
        x++
        // THEN
        x.value assertEquals initialValue + 1
    }

    @Test
    fun dec_should_return_minus1_with_a_value_that_equals_1() {
        // GIVEN
        var x = NonZeroInt(1)
        // WHEN
        x--
        // THEN
        x.value assertEquals -1
    }

    @Test
    fun dec_should_return_the_maximum_value_with_the_minimum_value() {
        // GIVEN
        var x: NonZeroInt = NonZeroInt.min
        // WHEN
        x--
        // THEN
        x assertEquals NonZeroInt.max
    }

    @Test
    fun dec_should_decrement_the_value_by_1_with_an_initial_value_other_than_1_and_the_minimum_value() {
        // GIVEN
        var x: NonZeroInt = NonZeroInt.random
        while (x.value == 1 || x == NonZeroInt.min) x = NonZeroInt.random
        val initialValue: Int = x.value
        // WHEN
        x--
        // THEN
        x.value assertEquals initialValue - 1
    }

    @Test
    fun unaryMinus_should_pass() {
        // GIVEN
        val x: NonZeroInt = NonZeroInt.random
        // WHEN
        val result: NonZeroInt = -x
        // THEN
        result.value assertEquals -x.value
    }
}

class PositiveIntTest {
    // ---------- Builders ----------

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

    // ---------- Unary operations ----------

    @Test
    fun inc_should_return_the_minimum_value_with_the_maximum_value() {
        // GIVEN
        var x: PositiveInt = PositiveInt.max
        // WHEN
        x++
        // THEN
        x assertEquals PositiveInt.min
    }

    @Test
    fun inc_should_increment_the_value_by_1_with_an_initial_value_other_than_the_maximum_value() {
        // GIVEN
        var x: PositiveInt = PositiveInt.random
        while (x.value == PositiveInt.max.value) x = PositiveInt.random
        val initialValue: Int = x.value
        // WHEN
        x++
        // THEN
        x.value assertEquals initialValue + 1
    }

    @Test
    fun dec_should_return_the_maximum_value_with_the_minimum_value() {
        // GIVEN
        var x: PositiveInt = PositiveInt.min
        // WHEN
        x--
        // THEN
        x assertEquals PositiveInt.max
    }

    @Test
    fun dec_should_decrement_the_value_by_1_with_an_initial_value_other_than_the_minimum_value() {
        // GIVEN
        var x: PositiveInt = PositiveInt.random
        while (x.value == PositiveInt.min.value) x = PositiveInt.random
        val initialValue: Int = x.value
        // WHEN
        x--
        // THEN
        x.value assertEquals initialValue - 1
    }

    @Test
    fun unaryMinus_should_pass() {
        // GIVEN
        val x: PositiveInt = PositiveInt.random
        // WHEN
        val result: NegativeInt = -x
        // THEN
        result.value assertEquals -x.value
    }
}

class StrictlyPositiveIntTest {
    // ---------- Builders ----------

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

    @Suppress("TestFunctionName")
    @Test
    fun Int_toStrictlyPositiveInt_should_pass_with_a_strictly_positive_Int() {
        // GIVEN
        val value: Int = StrictlyPositiveInt.random.value
        // WHEN
        val result: StrictlyPositiveInt = value.toStrictlyPositiveInt()
        // THEN
        result.value assertEquals value
    }

    @Suppress("TestFunctionName")
    @Test
    fun Int_toStrictlyPositiveInt_should_throw_an_error_with_a_negative_Int() {
        // GIVEN
        val value: Int = NegativeInt.random.value
        // WHEN & THEN
        assertFailsWith<IllegalArgumentException>(value::toStrictlyPositiveInt)
    }

    @Suppress("TestFunctionName")
    @Test
    fun Int_toStrictlyPositiveIntOrNull_should_pass_with_a_strictly_positive_Int() {
        // GIVEN
        val value: Int = StrictlyPositiveInt.random.value
        // WHEN
        val result: StrictlyPositiveInt? = value.toStrictlyPositiveIntOrNull()
        // THEN
        result.assertNotNull().value assertEquals value
    }

    @Suppress("TestFunctionName")
    @Test
    fun Int_toStrictlyPositiveIntOrNull_should_return_null_with_a_negative_Int() {
        // GIVEN
        val value: Int = NegativeInt.random.value
        // WHEN
        val result: StrictlyPositiveInt? = value.toStrictlyPositiveIntOrNull()
        // THEN
        result.assertNull()
    }

    // ---------- Unary operations ----------

    @Test
    fun inc_should_return_the_minimum_value_with_the_maximum_value() {
        // GIVEN
        var x: StrictlyPositiveInt = StrictlyPositiveInt.max
        // WHEN
        x++
        // THEN
        x assertEquals StrictlyPositiveInt.min
    }

    @Test
    fun inc_should_increment_the_value_by_one_with_an_initial_value_other_than_the_maximum() {
        // GIVEN
        var x: StrictlyPositiveInt = StrictlyPositiveInt.random
        while (x.value == StrictlyPositiveInt.max.value)
            x = StrictlyPositiveInt.random
        val initialValue: Int = x.value
        // WHEN
        x++
        // THEN
        x.value assertEquals initialValue + 1
    }

    @Test
    fun dec_should_return_the_maximum_value_with_the_minimum_value() {
        // GIVEN
        var x: StrictlyPositiveInt = StrictlyPositiveInt.min
        // WHEN
        x--
        // THEN
        x assertEquals StrictlyPositiveInt.max
    }

    @Test
    fun dec_should_decrement_the_value_by_one_with_an_initial_value_other_than_the_minimum() {
        // GIVEN
        var x: StrictlyPositiveInt = StrictlyPositiveInt.random
        while (x.value == StrictlyPositiveInt.min.value)
            x = StrictlyPositiveInt.random
        val initialValue: Int = x.value
        // WHEN
        x--
        // THEN
        x.value assertEquals initialValue - 1
    }

    @Test
    fun unaryMinus_should_pass() {
        // GIVEN
        val x: StrictlyPositiveInt = StrictlyPositiveInt.random
        // WHEN
        val result: StrictlyNegativeInt = -x
        // THEN
        result.value assertEquals -x.value
    }
}

class NegativeIntTest {
    // ---------- Builders ----------

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

    @Suppress("TestFunctionName")
    @Test
    fun Int_toNegativeInt_should_pass_with_a_negative_Int() {
        // GIVEN
        val value: Int = NegativeInt.random.value
        // WHEN
        val result: NegativeInt = value.toNegativeInt()
        // THEN
        result.value assertEquals value
    }

    @Suppress("TestFunctionName")
    @Test
    fun Int_toNegative_Int_should_throw_an_error_with_a_strictly_positive_Int() {
        // GIVEN
        val value: Int = StrictlyPositiveInt.random.value
        // WHEN & THEN
        assertFailsWith<IllegalArgumentException>(value::toNegativeInt)
    }

    @Suppress("TestFunctionName")
    @Test
    fun Int_toNegativeIntOrNull_should_pass_with_a_negative_Int() {
        // GIVEN
        val value: Int = NegativeInt.random.value
        // WHEN
        val result: NegativeInt? = value.toNegativeIntOrNull()
        // THEN
        result.assertNotNull().value assertEquals value
    }

    @Suppress("TestFunctionName")
    @Test
    fun Int_toNegativeIntOrNull_should_return_null_with_a_strictly_positive_Int() {
        // GIVEN
        val value: Int = StrictlyPositiveInt.random.value
        // WHEN
        val result: NegativeInt? = value.toNegativeIntOrNull()
        // THEN
        result.assertNull()
    }

    // ---------- Unary operations ----------

    @Test
    fun inc_should_return_the_minimum_value_with_the_maximum_value() {
        // GIVEN
        var x: NegativeInt = NegativeInt.max
        // WHEN
        x++
        // THEN
        x assertEquals NegativeInt.min
    }

    @Test
    fun inc_should_increment_the_value_by_one_with_an_initial_value_other_than_the_maximum() {
        // GIVEN
        var x: NegativeInt = NegativeInt.random
        while (x.value == NegativeInt.max.value) x = NegativeInt.random
        val initialValue: Int = x.value
        // WHEN
        x++
        // THEN
        x.value assertEquals initialValue + 1
    }

    @Test
    fun dec_should_return_the_maximum_value_with_the_minimum_value() {
        // GIVEN
        var x: NegativeInt = NegativeInt.min
        // WHEN
        x--
        // THEN
        x assertEquals NegativeInt.max
    }

    @Test
    fun dec_should_decrement_the_value_by_one_with_an_initial_value_other_than_the_minimum() {
        // GIVEN
        var x: NegativeInt = NegativeInt.random
        while (x.value == NegativeInt.min.value) x = NegativeInt.random
        val initialValue: Int = x.value
        // WHEN
        x--
        // THEN
        x.value assertEquals initialValue - 1
    }

    @Test
    fun unaryMinus_should_pass() {
        // GIVEN
        val x: NegativeInt = NegativeInt.random
        // WHEN
        val result: PositiveInt = -x
        // THEN
        result.value assertEquals -x.value
    }
}

class StrictlyNegativeIntTest {
    // ---------- Builders ----------

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

    @Suppress("TestFunctionName")
    @Test
    fun Int_toStrictlyNegativeInt_should_pass_with_a_strictly_negative_Int() {
        // GIVEN
        val value: Int = StrictlyNegativeInt.random.value
        // WHEN
        val result: StrictlyNegativeInt = value.toStrictlyNegativeInt()
        // THEN
        result.value assertEquals value
    }

    @Suppress("TestFunctionName")
    @Test
    fun Int_toStrictlyNegativeInt_should_throw_an_error_with_a_positive_Int() {
        // GIVEN
        val value: Int = PositiveInt.random.value
        // WHEN & THEN
        assertFailsWith<IllegalArgumentException>(value::toStrictlyNegativeInt)
    }

    @Suppress("TestFunctionName")
    @Test
    fun Int_toStrictlyNegativeIntOrNull_should_pass_with_a_strictly_negative_Int() {
        // GIVEN
        val value: Int = StrictlyNegativeInt.random.value
        // WHEN
        val result: StrictlyNegativeInt? = value.toStrictlyNegativeIntOrNull()
        // THEN
        result.assertNotNull().value assertEquals value
    }

    @Suppress("TestFunctionName")
    @Test
    fun Int_toStrictlyNegativeIntOrNull_should_return_null_with_a_positive_Int() {
        // GIVEN
        val value: Int = PositiveInt.random.value
        // WHEN
        val result: StrictlyNegativeInt? = value.toStrictlyNegativeIntOrNull()
        // THEN
        result.assertNull()
    }

    // ---------- Unary operations ----------

    @Test
    fun inc_should_return_the_minimum_value_with_maximum_value() {
        // GIVEN
        var x: StrictlyNegativeInt = StrictlyNegativeInt.max
        // WHEN
        x++
        // THEN
        x assertEquals StrictlyNegativeInt.min
    }

    @Test
    fun inc_should_increment_the_value_by_one_with_an_initial_value_other_than_the_maximum() {
        // GIVEN
        var x: StrictlyNegativeInt = StrictlyNegativeInt.random
        while (x.value == StrictlyNegativeInt.max.value)
            x = StrictlyNegativeInt.random
        val initialValue: Int = x.value
        // WHEN
        x++
        // THEN
        x.value assertEquals initialValue + 1
    }

    @Test
    fun dec_should_return_the_maximum_value_with_the_minimum_value() {
        // GIVEN
        var x: StrictlyNegativeInt = StrictlyNegativeInt.min
        // WHEN
        x--
        // THEN
        x assertEquals StrictlyNegativeInt.max
    }

    @Test
    fun dec_should_decrement_the_value_by_one_with_an_initial_value_other_than_the_minimum() {
        // GIVEN
        var x: StrictlyNegativeInt = StrictlyNegativeInt.random
        while (x.value == StrictlyNegativeInt.min.value)
            x = StrictlyNegativeInt.random
        val initialValue: Int = x.value
        // WHEN
        x--
        // THEN
        x.value assertEquals initialValue - 1
    }

    @Test
    fun unaryMinus_should_pass() {
        // GIVEN
        val x: StrictlyNegativeInt = StrictlyNegativeInt.random
        // WHEN
        val result: StrictlyPositiveInt = -x
        // THEN
        result.value assertEquals -x.value
    }
}
