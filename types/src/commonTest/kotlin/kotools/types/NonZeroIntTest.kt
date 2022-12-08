package kotools.types

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.random.Random
import kotlin.test.Test

@Throws(IllegalArgumentException::class)
private fun Int.toNonZeroIntOrThrow(): NonZeroInt = toNonZeroInt()
    .getOrThrow()

class NonZeroIntTest {
    @Test
    fun int_toNonZeroInt_should_pass_with_an_Int_other_than_zero() {
        var value: Int = Random.nextInt()
        while (value == 0) value = Random.nextInt()
        value.toNonZeroInt()
            .getOrThrow()
            .toInt() assertEquals value
    }

    @Test
    fun int_toNonZeroInt_should_fail_with_an_Int_that_equals_zero() {
        val result: Result<NonZeroInt> = 0.toNonZeroInt()
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }

    @Test
    fun compareTo_should_pass() {
        var value: Int = Random.nextInt()
        while (value == 0) value = Random.nextInt()
        val x: NonZeroInt = value.toNonZeroIntOrThrow()
        value = Random.nextInt()
        while (value == 0) value = Random.nextInt()
        val y: NonZeroInt = value.toNonZeroIntOrThrow()
        val result: Int = x.compareTo(y)
        val expectedResult: Int = x.toInt()
            .compareTo(y.toInt())
        result assertEquals expectedResult
    }

    @Test
    fun toString_should_behave_like_an_Int() {
        var value: Int = Random.nextInt()
        while (value == 0) value = Random.nextInt()
        val result: String = value.toNonZeroIntOrThrow()
            .toString()
        result assertEquals value.toString()
    }

    @Test
    fun serialization_should_behave_like_an_Int() {
        var value: Int = Random.nextInt()
        while (value == 0) value = Random.nextInt()
        val x: NonZeroInt = value.toNonZeroIntOrThrow()
        val result: String = Json.encodeToString(x)
        result assertEquals Json.encodeToString(value)
    }

    @Test
    fun deserialization_should_pass_with_an_Int_other_than_zero() {
        var value: Int = Random.nextInt()
        while (value == 0) value = Random.nextInt()
        val encoded: String = Json.encodeToString(value)
        val result: NonZeroInt = Json.decodeFromString(encoded)
        result.toInt() assertEquals value
    }

    @Test
    fun deserialization_should_fail_with_an_Int_that_equals_zero() {
        val encoded: String = Json.encodeToString(0)
        val exception: IllegalArgumentException =
            assertFailsWith { Json.decodeFromString<NonZeroInt>(encoded) }
        exception.message.assertNotNull()
            .isNotBlank()
            .assertTrue()
    }
}
