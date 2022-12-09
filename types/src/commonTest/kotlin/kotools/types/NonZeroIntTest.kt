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

class NonZeroIntTest {
    @Test
    fun compareTo_should_pass() {
        val x: NonZeroInt = NonZeroInt.random()
        val y: NonZeroInt = NonZeroInt.random()
        val result: Int = x.compareTo(y)
        val xValue: Int = x.toInt()
        val yValue: Int = y.toInt()
        result assertEquals xValue.compareTo(yValue)
    }

    @Test
    fun toString_should_behave_like_an_Int() {
        var value: Int = Random.nextInt()
        while (value == 0) value = Random.nextInt()
        val x: NonZeroInt = NonZeroInt.random()
        val xValue: Int = x.toInt()
        "$x" assertEquals "$xValue"
    }

    @Test
    fun serialization_should_behave_like_an_Int() {
        val x: NonZeroInt = NonZeroInt.random()
        val result: String = Json.encodeToString(x)
        val xValue: Int = x.toInt()
        result assertEquals Json.encodeToString(xValue)
    }

    @Test
    fun deserialization_should_pass_with_an_Int_other_than_zero() {
        val value: Int = NonZeroInt.ranges.random()
            .random()
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

    @Test
    fun int_toNonZeroInt_should_pass_with_an_Int_other_than_zero() {
        val value: Int = NonZeroInt.ranges.random()
            .random()
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
}
