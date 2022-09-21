package kotools.types.int

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotlin.random.Random
import kotlin.test.Test

@Suppress("TestFunctionName")
class NonZeroIntTest {
    // ---------- Builders ----------

    @Test
    fun NonZeroInt_should_pass_with_an_Int_other_than_0() {
        var value: Int = Random.nextInt()
        while (value == 0) value = Random.nextInt()
        val result = NonZeroInt(value)
        result.value assertEquals value
    }

    @Test
    fun NonZeroInt_should_throw_an_error_with_an_Int_that_equals_0() {
        assertFailsWith<IllegalArgumentException> { NonZeroInt(0) }
    }

    @Test
    fun NonZeroIntOrNull_should_pass_with_an_Int_other_than_0() {
        var value: Int = Random.nextInt()
        while (value == 0) value = Random.nextInt()
        val result: NonZeroInt? = NonZeroIntOrNull(value)
        result.assertNotNull().value assertEquals value
    }


    @Test
    fun NonZeroIntOrNull_should_return_null_with_an_Int_that_equals_0(): Unit =
        NonZeroIntOrNull(0).assertNull()

    @Test
    fun String_toNonZeroInt_should_pass_with_a_String_representing_a_number_other_than_0() {
        var value: Int = Random.nextInt()
        while (value == 0) value = Random.nextInt()
        val result: NonZeroInt = value.toString().toNonZeroInt()
        result.value assertEquals value
    }

    @Test
    fun String_toNonZeroInt_should_throw_an_error_with_an_invalid_String() {
        assertFailsWith<NumberFormatException>("a"::toNonZeroInt)
    }

    @Test
    fun String_toNonZeroInt_should_throw_an_error_with_a_String_representing_an_Int_that_equals_0() {
        assertFailsWith<IllegalArgumentException>("0"::toNonZeroInt)
    }

    @Test
    fun String_toNonZeroIntOrNull_should_pass_with_a_String_representing_a_number_other_than_0() {
        var value: Int = Random.nextInt()
        while (value == 0) value = Random.nextInt()
        val result: NonZeroInt? = value.toString().toNonZeroIntOrNull()
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun String_toNonZeroIntOrNull_should_return_null_with_an_invalid_String(): Unit =
        "a".toNonZeroIntOrNull().assertNull()

    @Test
    fun String_toNonZeroIntOrNull_should_return_null_with_a_String_representing_an_Int_that_equals_0() =
        "0".toNonZeroIntOrNull().assertNull()
}
