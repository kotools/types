package kotools.types

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotools.types.core.RandomValueHolder
import kotlin.test.Test

@Suppress("TestFunctionName")
class NonZeroIntTest : RandomValueHolder {
    @Test
    fun NonZeroInt_should_pass_with_a_non_zero_Int(): Unit =
        randomNonZeroInt().value.let { NonZeroInt(it).value assertEquals it }

    @Test
    fun NonZeroInt_should_throw_an_error_with_an_Int_that_equals_zero() {
        assertFailsWith<NonZeroInt.ConstructionError> { NonZeroInt(0) }
            .message.assertNotNull()
    }

    @Test
    fun NonZeroIntOrNull_should_pass_with_a_non_zero_Int() {
        val value: Int = randomNonZeroInt().value
        val result: NonZeroInt? = NonZeroIntOrNull(value)
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun NonZeroIntOrNull_should_return_null_with_an_Int_that_equals_zero() {
        val result: NonZeroInt? = NonZeroIntOrNull(0)
        result.assertNull()
    }

    @Test
    fun unaryMinus_should_pass() {
        val x: NonZeroInt = randomNonZeroInt()
        val result: NonZeroInt = -x
        result.value assertEquals -x.value
    }

    @Test
    fun times_should_pass_with_a_NonZeroInt() {
        val x: NonZeroInt = randomNonZeroInt()
        val y: NonZeroInt = randomNonZeroInt()
        val result: NonZeroInt = x * y
        result.value assertEquals x.value * y.value
    }

    @Test
    fun div_should_pass_when_dividing_an_Int_by_a_NonZeroInt() {
        val x: Int = randomInt
        val y: NonZeroInt = randomNonZeroInt()
        val result: Int = x / y
        result assertEquals x / y.value
    }
}

@Suppress("unused")
class NonZeroIntSerializerTest : IntHolderSerializerTest<NonZeroInt>(
    NonZeroInt.Serializer,
    ::randomNonZeroInt
)
