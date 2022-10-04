package kotools.types.number

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotools.types.string.NotBlankString
import kotlin.random.Random
import kotlin.test.Test

private val intHolders: List<IntHolder> = listOf(
    NonZeroInt.random,
    PositiveInt.random,
    StrictlyPositiveInt.random,
    NegativeInt.random,
    StrictlyNegativeInt.random
)

private val nonZeroIntHolders: List<NonZeroIntHolder> = listOf(
    NonZeroInt.random,
    StrictlyPositiveInt.random,
    StrictlyNegativeInt.random
)

class IntHolderTest {
    // ---------- Binary operations ----------

    @Test
    fun plus_should_return_an_Int_when_adding_an_IntHolder_to_an_Int() {
        // GIVEN
        val x: Int = Random.nextInt()
        val y: IntHolder = intHolders.random()
        // WHEN
        val result: Int = x + y
        // THEN
        result assertEquals x + y.value
    }

    @Test
    fun plus_should_return_an_Int_with_an_Int() {
        // GIVEN
        val x: IntHolder = intHolders.random()
        val y: Int = Random.nextInt()
        // WHEN
        val result: Int = x + y
        // THEN
        result assertEquals x.value + y
    }

    @Test
    fun plus_should_return_an_Int_with_another_IntHolder() {
        // GIVEN
        val x: IntHolder = intHolders.random()
        val y: IntHolder = intHolders.random()
        // WHEN
        val result: Int = x + y
        // THEN
        result assertEquals x.value + y.value
    }

    @Test
    fun minus_should_return_an_Int_when_subtracting_an_IntHolder_from_an_Int() {
        // GIVEN
        val x: Int = Random.nextInt()
        val y: IntHolder = intHolders.random()
        // WHEN
        val result: Int = x - y
        // THEN
        result assertEquals x - y.value
    }

    @Test
    fun minus_should_return_an_Int_with_an_Int() {
        // GIVEN
        val x: IntHolder = intHolders.random()
        val y: Int = Random.nextInt()
        // WHEN
        val result: Int = x - y
        // THEN
        result assertEquals x.value - y
    }

    @Test
    fun minus_should_return_an_Int_with_an_IntHolder() {
        // GIVEN
        val x: IntHolder = intHolders.random()
        val y: IntHolder = intHolders.random()
        // WHEN
        val result: Int = x - y
        // THEN
        result assertEquals x.value - y.value
    }

    @Test
    fun times_should_return_an_Int_when_multiplying_an_Int_by_an_IntHolder() {
        // GIVEN
        val x: Int = Random.nextInt()
        val y: IntHolder = intHolders.random()
        // WHEN
        val result: Int = x * y
        // THEN
        result assertEquals x * y.value
    }

    @Test
    fun times_should_return_an_Int_with_an_Int() {
        // GIVEN
        val x: IntHolder = intHolders.random()
        val y: Int = Random.nextInt()
        // WHEN
        val result: Int = x * y
        // THEN
        result assertEquals x.value * y
    }

    @Test
    fun times_should_return_an_Int_with_an_IntHolder() {
        // GIVEN
        val x: IntHolder = intHolders.random()
        val y: IntHolder = intHolders.random()
        // WHEN
        val result: Int = x * y
        // THEN
        result assertEquals x.value * y.value
    }

    @Test
    fun div_should_return_an_Int_with_a_NonZeroIntHolder() {
        // GIVEN
        val x: IntHolder = intHolders.random()
        val y: NonZeroIntHolder = nonZeroIntHolders.random()
        // WHEN
        val result: Int = x / y
        // THEN
        result assertEquals x.value / y.value
    }

    // ---------- Conversions ----------

    @Test
    fun toString_should_behave_like_Int(): Unit = intHolders.forEach {
        // GIVEN & WHEN
        val result: String = it.toString()
        // THEN
        result assertEquals it.value.toString()
    }

    @Test
    fun toNotBlankString_should_pass(): Unit = intHolders.forEach {
        // GIVEN & WHEN
        val result: NotBlankString = it.toNotBlankString()
        // THEN
        result.value assertEquals it.value.toString()
    }
}

class IntHolderCompanionTest {
    @Test
    fun min_should_pass() {
        // GIVEN & WHEN & THEN
        NonZeroInt.min.value assertEquals Int.MIN_VALUE
        PositiveInt.min.value assertEquals 0
        StrictlyPositiveInt.min.value assertEquals 1
        NegativeInt.min.value assertEquals Int.MIN_VALUE
        StrictlyNegativeInt.min.value assertEquals Int.MIN_VALUE
    }

    @Test
    fun max_should_pass() {
        // GIVEN & WHEN & THEN
        NonZeroInt.max.value assertEquals Int.MAX_VALUE
        PositiveInt.max.value assertEquals Int.MAX_VALUE
        StrictlyPositiveInt.max.value assertEquals Int.MAX_VALUE
        NegativeInt.max.value assertEquals 0
        StrictlyNegativeInt.max.value assertEquals -1
    }

    @Test
    fun invoke_should_pass_with_a_valid_value() {
        // GIVEN
        val holder: IntHolder = intHolders.random()
        // WHEN
        val result: IntHolder = when (holder) {
            is NonZeroInt -> NonZeroInt(holder.value)
            is PositiveInt -> PositiveInt(holder.value)
            is StrictlyPositiveInt -> StrictlyPositiveInt(holder.value)
            is NegativeInt -> NegativeInt(holder.value)
            is StrictlyNegativeInt -> StrictlyNegativeInt(holder.value)
        }
        // THEN
        result.value assertEquals holder.value
    }

    @Test
    fun invoke_should_throw_an_error_with_an_invalid_value() {
        // GIVEN
        val holder: IntHolder = intHolders.random()
        // WHEN & THEN
        assertFailsWith<IllegalArgumentException> {
            when (holder) {
                is NonZeroInt -> NonZeroInt(0)
                is PositiveInt -> PositiveInt(StrictlyNegativeInt.random.value)
                is StrictlyPositiveInt -> StrictlyPositiveInt(NegativeInt.random.value)
                is NegativeInt -> NegativeInt(StrictlyPositiveInt.random.value)
                is StrictlyNegativeInt -> StrictlyNegativeInt(PositiveInt.random.value)
            }
        }
    }

    @Test
    fun orNull_should_pass_with_a_valid_value() {
        // GIVEN
        val holder: IntHolder = intHolders.random()
        // WHEN
        val result: IntHolder? = when (holder) {
            is NonZeroInt -> NonZeroInt orNull holder.value
            is PositiveInt -> PositiveInt orNull holder.value
            is StrictlyPositiveInt -> StrictlyPositiveInt orNull holder.value
            is NegativeInt -> NegativeInt orNull holder.value
            is StrictlyNegativeInt -> StrictlyNegativeInt orNull holder.value
        }
        // THEN
        result.assertNotNull().value assertEquals holder.value
    }

    @Test
    fun orNull_should_return_null_with_an_invalid_value() {
        // GIVEN & WHEN
        val result: IntHolder? = when (intHolders.random()) {
            is NonZeroInt -> NonZeroInt orNull 0
            is PositiveInt -> PositiveInt orNull StrictlyNegativeInt.random.value
            is StrictlyPositiveInt -> StrictlyPositiveInt orNull NegativeInt.random.value
            is NegativeInt -> NegativeInt orNull StrictlyPositiveInt.random.value
            is StrictlyNegativeInt -> StrictlyNegativeInt orNull PositiveInt.random.value
        }
        // THEN
        result.assertNull()
    }
}

class NonZeroIntHolderTest {
    // ---------- Binary operations ----------

    @Test
    fun times_should_return_a_NonZeroInt() {
        // GIVEN
        val x: NonZeroIntHolder = nonZeroIntHolders.random()
        val y: NonZeroIntHolder = nonZeroIntHolders.random()
        // WHEN
        val result: NonZeroInt = x * y
        // THEN
        result.value assertEquals x.value * y.value
    }

    @Test
    fun div_should_return_an_Int_when_dividing_an_Int_by_a_NonZeroIntHolder() {
        // GIVEN
        val x: Int = Random.nextInt()
        val y: NonZeroIntHolder = nonZeroIntHolders.random()
        // WHEN
        val result: Int = x / y
        // THEN
        result assertEquals x / y.value
    }
}

class PositiveIntHolderTest {
    private val holders: List<PositiveIntHolder> =
        listOf(PositiveInt.random, StrictlyPositiveInt.random)

    @Test
    fun div_should_return_a_PositiveInt_with_a_StrictlyPositiveInt() {
        // GIVEN
        val x: PositiveIntHolder = holders.random()
        val y: StrictlyPositiveInt = StrictlyPositiveInt.random
        // WHEN
        val result: PositiveInt = x / y
        // THEN
        result.value assertEquals x.value / y.value
    }

    @Test
    fun div_should_return_a_NegativeInt_with_a_StrictlyNegativeInt() {
        // GIVEN
        val x: PositiveIntHolder = holders.random()
        val y: StrictlyNegativeInt = StrictlyNegativeInt.random
        // WHEN
        val result: NegativeInt = x / y
        // THEN
        result.value assertEquals x.value / y.value
    }
}

class NegativeIntHolderTest {
    private val holders: List<NegativeIntHolder> =
        listOf(NegativeInt.random, StrictlyNegativeInt.random)

    @Test
    fun div_should_return_a_NegativeInt_with_a_StrictlyPositiveInt() {
        // GIVEN
        val x: NegativeIntHolder = holders.random()
        val y: StrictlyPositiveInt = StrictlyPositiveInt.random
        // WHEN
        val result: NegativeInt = x / y
        // THEN
        result.value assertEquals x.value / y.value
    }

    @Test
    fun div_should_return_a_PositiveInt_with_a_StrictlyNegativeInt() {
        // GIVEN
        val x: NegativeIntHolder = holders.random()
        val y: StrictlyNegativeInt = StrictlyNegativeInt.random
        // WHEN
        val result: PositiveInt = x / y
        // THEN
        result.value assertEquals x.value / y.value
    }
}

class NonZeroIntTest {
    // ---------- Builders ----------

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

    // ---------- Serialization ----------

    @Test
    fun serialization_should_behave_like_an_Int() {
        // GIVEN
        val x: NonZeroInt = NonZeroInt.random
        // WHEN
        val result: String = Json.encodeToString(x)
        // THEN
        result assertEquals Json.encodeToString(x.value)
    }

    @Test
    fun deserialization_should_pass() {
        // GIVEN
        val value: Int = NonZeroInt.random.value
        val encoded: String = Json.encodeToString(value)
        // WHEN
        val result: NonZeroInt = Json.decodeFromString(encoded)
        // THEN
        result.value assertEquals value
    }

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

    // ---------- Conversions ----------

    @Test
    fun toPositiveInt_should_pass_with_a_positive_value() {
        // GIVEN
        var x: NonZeroInt = NonZeroInt.random
        while (x.value < 0) x = NonZeroInt.random
        // WHEN
        val result: PositiveInt = x.toPositiveInt()
        // THEN
        result.value assertEquals x.value
    }

    @Test
    fun toPositiveInt_should_throw_an_error_with_a_strictly_negative_value() {
        // GIVEN
        var x: NonZeroInt = NonZeroInt.random
        while (x.value >= 0) x = NonZeroInt.random
        // WHEN & THEN
        assertFailsWith<IllegalArgumentException>(x::toPositiveInt)
    }

    @Test
    fun toPositiveIntOrNull_should_pass_with_a_positive_value() {
        // GIVEN
        var x: NonZeroInt = NonZeroInt.random
        while (x.value < 0) x = NonZeroInt.random
        // WHEN
        val result: PositiveInt? = x.toPositiveIntOrNull()
        // THEN
        result.assertNotNull().value assertEquals x.value
    }

    @Test
    fun toPositiveIntOrNull_should_return_null_with_a_strictly_negative_value() {
        // GIVEN
        var x: NonZeroInt = NonZeroInt.random
        while (x.value >= 0) x = NonZeroInt.random
        // WHEN
        val result: PositiveInt? = x.toPositiveIntOrNull()
        // THEN
        result.assertNull()
    }

    @Test
    fun toStrictlyPositiveInt_should_pass_with_a_strictly_positive_value() {
        // GIVEN
        var x: NonZeroInt = NonZeroInt.random
        while (x.value <= 0) x = NonZeroInt.random
        // WHEN
        val result: StrictlyPositiveInt = x.toStrictlyPositiveInt()
        // THEN
        result.value assertEquals x.value
    }

    @Test
    fun toStrictlyPositiveInt_should_throw_an_error_with_a_negative_value() {
        // GIVEN
        var x: NonZeroInt = NonZeroInt.random
        while (x.value > 0) x = NonZeroInt.random
        // WHEN & THEN
        assertFailsWith<IllegalArgumentException>(x::toStrictlyPositiveInt)
    }

    @Test
    fun toStrictlyPositiveIntOrNull_should_pass_with_a_strictly_positive_value() {
        // GIVEN
        var x: NonZeroInt = NonZeroInt.random
        while (x.value <= 0) x = NonZeroInt.random
        // WHEN
        val result: StrictlyPositiveInt? = x.toStrictlyPositiveIntOrNull()
        // THEN
        result.assertNotNull().value assertEquals x.value
    }

    @Test
    fun toStrictlyPositiveIntOrNull_should_return_null_with_a_negative_value() {
        // GIVEN
        var x: NonZeroInt = NonZeroInt.random
        while (x.value > 0) x = NonZeroInt.random
        // WHEN
        val result: StrictlyPositiveInt? = x.toStrictlyPositiveIntOrNull()
        // THEN
        result.assertNull()
    }

    @Test
    fun toNegativeInt_should_pass_with_a_negative_value() {
        // GIVEN
        var x: NonZeroInt = NonZeroInt.random
        while (x.value > 0) x = NonZeroInt.random
        // WHEN
        val result: NegativeInt = x.toNegativeInt()
        // THEN
        result.value assertEquals x.value
    }

    @Test
    fun toNegativeInt_should_throw_an_error_with_a_strictly_positive_value() {
        // GIVEN
        var x: NonZeroInt = NonZeroInt.random
        while (x.value <= 0) x = NonZeroInt.random
        // WHEN & THEN
        assertFailsWith<IllegalArgumentException>(x::toNegativeInt)
    }

    @Test
    fun toNegativeIntOrNull_should_pass_with_a_negative_value() {
        // GIVEN
        var x: NonZeroInt = NonZeroInt.random
        while (x.value > 0) x = NonZeroInt.random
        // WHEN
        val result: NegativeInt? = x.toNegativeIntOrNull()
        // THEN
        result.assertNotNull().value assertEquals x.value
    }

    @Test
    fun toNegativeIntOrNull_should_return_null_with_a_strictly_positive_value() {
        // GIVEN
        var x: NonZeroInt = NonZeroInt.random
        while (x.value <= 0) x = NonZeroInt.random
        // WHEN
        val result: NegativeInt? = x.toNegativeIntOrNull()
        // THEN
        result.assertNull()
    }

    @Test
    fun toStrictlyNegativeInt_should_pass_with_a_strictly_negative_value() {
        // GIVEN
        var x: NonZeroInt = NonZeroInt.random
        while (x.value >= 0) x = NonZeroInt.random
        // WHEN
        val result: StrictlyNegativeInt = x.toStrictlyNegativeInt()
        // THEN
        result.value assertEquals x.value
    }

    @Test
    fun toStrictlyNegativeInt_should_throw_an_error_with_a_positive_value() {
        // GIVEN
        var x: NonZeroInt = NonZeroInt.random
        while (x.value < 0) x = NonZeroInt.random
        // WHEN & THEN
        assertFailsWith<IllegalArgumentException>(x::toStrictlyNegativeInt)
    }

    @Test
    fun toStrictlyNegativeIntOrNull_should_pass_with_a_strictly_negative_value() {
        // GIVEN
        var x: NonZeroInt = NonZeroInt.random
        while (x.value >= 0) x = NonZeroInt.random
        // WHEN
        val result: StrictlyNegativeInt? = x.toStrictlyNegativeIntOrNull()
        // THEN
        result.assertNotNull().value assertEquals x.value
    }

    @Test
    fun toStrictlyNegativeIntOrNull_should_return_null_with_a_positive_value() {
        // GIVEN
        var x: NonZeroInt = NonZeroInt.random
        while (x.value < 0) x = NonZeroInt.random
        // WHEN
        val result: StrictlyNegativeInt? = x.toStrictlyNegativeIntOrNull()
        // THEN
        result.assertNull()
    }
}

class PositiveIntTest {
    // ---------- Builders ----------

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

    // ---------- Serialization ----------

    @Test
    fun serialization_should_behave_like_an_Int() {
        // GIVEN
        val x: PositiveInt = PositiveInt.random
        // WHEN
        val result: String = Json.encodeToString(x)
        // THEN
        result assertEquals Json.encodeToString(x.value)
    }

    @Test
    fun deserialization_should_pass() {
        // GIVEN
        val value: Int = PositiveInt.random.value
        val encoded: String = Json.encodeToString(value)
        // WHEN
        val result: PositiveInt = Json.decodeFromString(encoded)
        // THEN
        result.value assertEquals value
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

    // ---------- Conversions ----------

    @Test
    fun toNonZeroInt_should_pass_with_a_strictly_positive_value() {
        // GIVEN
        var x: PositiveInt = PositiveInt.random
        while (x.value == 0) x = PositiveInt.random
        // WHEN
        val result: NonZeroInt = x.toNonZeroInt()
        // THEN
        result.value assertEquals x.value
    }

    @Test
    fun toNonZeroInt_should_throw_an_error_with_a_value_that_equals_zero() {
        // GIVEN
        val x = PositiveInt(0)
        // WHEN & THEN
        assertFailsWith<IllegalArgumentException>(x::toNonZeroInt)
    }

    @Test
    fun toNonZeroIntOrNull_should_pass_with_a_strictly_positive_value() {
        // GIVEN
        var x: PositiveInt = PositiveInt.random
        while (x.value == 0) x = PositiveInt.random
        // WHEN
        val result: NonZeroInt? = x.toNonZeroIntOrNull()
        // THEN
        result.assertNotNull().value assertEquals x.value
    }

    @Test
    fun toNonZeroIntOrNull_should_return_null_with_a_value_that_equals_zero() {
        // GIVEN
        val x = PositiveInt(0)
        // WHEN
        val result: NonZeroInt? = x.toNonZeroIntOrNull()
        // THEN
        result.assertNull()
    }

    @Test
    fun toStrictlyPositiveInt_should_pass_with_a_strictly_positive_value() {
        // GIVEN
        var x: PositiveInt = PositiveInt.random
        while (x.value == 0) x = PositiveInt.random
        // WHEN
        val result: StrictlyPositiveInt = x.toStrictlyPositiveInt()
        // THEN
        result.value assertEquals x.value
    }

    @Test
    fun toStrictlyPositiveInt_should_throw_an_error_with_a_value_that_equals_zero() {
        // GIVEN
        val x = PositiveInt(0)
        // WHEN & THEN
        assertFailsWith<IllegalArgumentException>(x::toStrictlyPositiveInt)
    }

    @Test
    fun toStrictlyPositiveIntOrNull_should_pass_with_a_strictly_positive_value() {
        // GIVEN
        var x: PositiveInt = PositiveInt.random
        while (x.value == 0) x = PositiveInt.random
        // WHEN
        val result: StrictlyPositiveInt? = x.toStrictlyPositiveIntOrNull()
        // THEN
        result.assertNotNull().value assertEquals x.value
    }

    @Test
    fun toStrictlyPositiveIntOrNull_should_return_null_with_a_value_that_equals_zero() {
        // GIVEN
        val x = PositiveInt(0)
        // WHEN
        val result: StrictlyPositiveInt? = x.toStrictlyPositiveIntOrNull()
        // THEN
        result.assertNull()
    }

    @Test
    fun toNegativeInt_should_pass_with_a_value_that_equals_zero() {
        // GIVEN
        val x = PositiveInt(0)
        // WHEN
        val result: NegativeInt = x.toNegativeInt()
        // THEN
        result.value assertEquals x.value
    }

    @Test
    fun toNegativeInt_should_throw_an_error_with_a_strictly_positive_value() {
        // GIVEN
        var x: PositiveInt = PositiveInt.random
        while (x.value == 0) x = PositiveInt.random
        // WHEN & THEN
        assertFailsWith<IllegalArgumentException>(x::toNegativeInt)
    }

    @Test
    fun toNegativeIntOrNull_should_pass_with_a_value_that_equals_zero() {
        // GIVEN
        val x = PositiveInt(0)
        // WHEN
        val result: NegativeInt? = x.toNegativeIntOrNull()
        // THEN
        result.assertNotNull().value assertEquals x.value
    }

    @Test
    fun toNegativeIntOrNull_should_return_null_with_a_strictly_positive_value() {
        // GIVEN
        var x: PositiveInt = PositiveInt.random
        while (x.value == 0) x = PositiveInt.random
        // WHEN
        val result: NegativeInt? = x.toNegativeIntOrNull()
        // THEN
        result.assertNull()
    }
}

class StrictlyPositiveIntTest {
    // ---------- Builders ----------

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

    // ---------- Serialization ----------

    @Test
    fun serialization_should_behave_like_an_Int() {
        // GIVEN
        val x: StrictlyPositiveInt = StrictlyPositiveInt.random
        // WHEN
        val result: String = Json.encodeToString(x)
        // THEN
        result assertEquals Json.encodeToString(x.value)
    }

    @Test
    fun deserialization_should_pass() {
        // GIVEN
        val value: Int = StrictlyPositiveInt.random.value
        val encoded: String = Json.encodeToString(value)
        // WHEN
        val result: StrictlyPositiveInt = Json.decodeFromString(encoded)
        // THEN
        result.value assertEquals value
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

    // ---------- Conversions ----------

    @Test
    fun toNonZeroInt_should_pass() {
        // GIVEN
        val x: StrictlyPositiveInt = StrictlyPositiveInt.random
        // WHEN
        val result: NonZeroInt = x.toNonZeroInt()
        // THEN
        result.value assertEquals x.value
    }

    @Test
    fun toPositiveInt_should_pass() {
        // GIVEN
        val x: StrictlyPositiveInt = StrictlyPositiveInt.random
        // WHEN
        val result: PositiveInt = x.toPositiveInt()
        // THEN
        result.value assertEquals x.value
    }
}

class NegativeIntTest {
    // ---------- Builders ----------

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

    // ---------- Serialization ----------

    @Test
    fun serialization_should_behave_like_an_Int() {
        // GIVEN
        val x: NegativeInt = NegativeInt.random
        // WHEN
        val result: String = Json.encodeToString(x)
        // THEN
        result assertEquals Json.encodeToString(x.value)
    }

    @Test
    fun deserialization_should_pass() {
        // GIVEN
        val value: Int = NegativeInt.random.value
        val encoded: String = Json.encodeToString(value)
        // WHEN
        val result: NegativeInt = Json.decodeFromString(encoded)
        // THEN
        result.value assertEquals value
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

    // ---------- Serialization ----------

    @Test
    fun serialization_should_behave_like_an_Int() {
        // GIVEN
        val x: StrictlyNegativeInt = StrictlyNegativeInt.random
        // WHEN
        val result: String = Json.encodeToString(x)
        // THEN
        result assertEquals Json.encodeToString(x.value)
    }

    @Test
    fun deserialization_should_pass() {
        // GIVEN
        val value: Int = StrictlyNegativeInt.random.value
        val encoded: String = Json.encodeToString(value)
        // WHEN
        val result: StrictlyNegativeInt = Json.decodeFromString(encoded)
        // THEN
        result.value assertEquals value
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
