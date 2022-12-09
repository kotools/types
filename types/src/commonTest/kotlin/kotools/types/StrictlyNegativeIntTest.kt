package kotools.types

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.test.Test

class StrictlyNegativeIntTest {
    @Test
    fun compareTo_should_pass() {
        val x: StrictlyNegativeInt = StrictlyNegativeInt.random()
        val y: StrictlyNegativeInt = StrictlyNegativeInt.random()
        val result: Int = x.compareTo(y)
        val xValue: Int = x.toInt()
        val yValue: Int = y.toInt()
        result assertEquals xValue.compareTo(yValue)
    }

    @Test
    fun toString_should_behave_like_an_Int() {
        val x: StrictlyNegativeInt = StrictlyNegativeInt.random()
        val value: Int = x.toInt()
        "$x" assertEquals "$value"
    }

    @Test
    fun int_toStrictlyNegativeInt_should_pass_with_a_strictly_negative_Int() {
        val value: Int = StrictlyNegativeInt.range.random()
        value.toStrictlyNegativeInt()
            .getOrThrow()
            .toInt() assertEquals value
    }

    @Test
    fun int_toStrictlyNegativeInt_should_fail_with_a_positive_Int() {
        val result: Result<StrictlyNegativeInt> = PositiveInt.range.random()
            .toStrictlyNegativeInt()
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }

    @Test
    fun serialization_should_behave_like_an_Int() {
        val x: StrictlyNegativeInt = StrictlyNegativeInt.random()
        val result: String = Json.encodeToString(x)
        val value: Int = x.toInt()
        result assertEquals Json.encodeToString(value)
    }

    @Test
    fun deserialization_should_pass_with_a_strictly_negative_Int() {
        val value: Int = StrictlyNegativeInt.range.random()
        val encoded: String = Json.encodeToString(value)
        val result: StrictlyNegativeInt = Json.decodeFromString(encoded)
        result.toInt() assertEquals value
    }

    @Test
    fun deserialization_should_fail_with_a_positive_Int() {
        val value: Int = PositiveInt.range.random()
        val encoded: String = Json.encodeToString(value)
        val exception: IllegalArgumentException = assertFailsWith {
            Json.decodeFromString<StrictlyNegativeInt>(encoded)
        }
        exception.message.assertNotNull()
            .isNotBlank()
            .assertTrue()
    }
}
