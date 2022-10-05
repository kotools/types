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
    fun compareTo_should_pass_when_comparing_an_Int_with_an_IntHolder() {
        // GIVEN
        val x: Int = Random.nextInt()
        val y: IntHolder = intHolders.random()
        // WHEN
        val result: Int = x.compareTo(y)
        // THEN
        result assertEquals x.compareTo(y.value)
    }

    @Test
    fun compareTo_should_pass_with_an_Int() {
        // GIVEN
        val x: IntHolder = intHolders.random()
        val y: Int = Random.nextInt()
        // WHEN
        val result: Int = x.compareTo(y)
        // THEN
        result assertEquals x.value.compareTo(y)
    }

    @Test
    fun compareTo_should_pass_with_another_IntHolder() {
        // GIVEN
        val x: IntHolder = intHolders.random()
        val y: IntHolder = intHolders.random()
        // WHEN
        val result: Int = x.compareTo(y)
        // THEN
        result assertEquals x.value.compareTo(y.value)
    }

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

    @Suppress("TestFunctionName")
    @Test
    fun String_toStrictlyNegativeInt_should_pass_with_a_String_representing_a_strictly_negative_number() {
        // GIVEN
        val value: Int = StrictlyNegativeInt.random.value
        val valueAsString: String = value.toString()
        // WHEN
        val result: StrictlyNegativeInt = valueAsString.toStrictlyNegativeInt()
        // THEN
        result.value assertEquals value
    }

    @Suppress("TestFunctionName")
    @Test
    fun String_toStrictlyNegativeInt_should_throw_an_error_with_an_invalid_String() {
        assertFailsWith<NumberFormatException>("a"::toStrictlyNegativeInt)
    }

    @Suppress("TestFunctionName")
    @Test
    fun String_toStrictlyNegativeInt_should_throw_an_error_with_a_String_representing_a_positive_number() {
        // GIVEN
        val value: String = PositiveInt.random.value.toString()
        // WHEN & THEN
        assertFailsWith<IllegalArgumentException>(value::toStrictlyNegativeInt)
    }

    @Suppress("TestFunctionName")
    @Test
    fun String_toStrictlyNegativeIntOrNull_should_pass_with_a_String_representing_a_strictly_negative_number() {
        // GIVEN
        val value: Int = StrictlyNegativeInt.random.value
        val valueAsString: String = value.toString()
        // WHEN
        val result: StrictlyNegativeInt? =
            valueAsString.toStrictlyNegativeIntOrNull()
        // THEN
        result.assertNotNull().value assertEquals value
    }

    @Suppress("TestFunctionName")
    @Test
    fun String_toStrictlyNegativeIntOrNull_should_return_null_with_an_invalid_String() {
        // GIVEN
        val value = "a"
        // WHEN
        val result: StrictlyNegativeInt? = value.toStrictlyNegativeIntOrNull()
        // THEN
        result.assertNull()
    }

    @Suppress("TestFunctionName")
    @Test
    fun String_toStrictlyNegativeIntOrNull_should_return_null_with_a_String_representing_a_positive_number() {
        // GIVEN
        val value: String = PositiveInt.random.value.toString()
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

    // ---------- Conversions ----------

    @Test
    fun toNonZeroInt_should_pass() {
        // GIVEN
        val x: StrictlyNegativeInt = StrictlyNegativeInt.random
        // WHEN
        val result: NonZeroInt = x.toNonZeroInt()
        // THEN
        result.value assertEquals x.value
    }

    @Test
    fun toNegativeInt_should_pass() {
        // GIVEN
        val x: StrictlyNegativeInt = StrictlyNegativeInt.random
        // WHEN
        val result: NegativeInt = x.toNegativeInt()
        // THEN
        result.value assertEquals x.value
    }
}
