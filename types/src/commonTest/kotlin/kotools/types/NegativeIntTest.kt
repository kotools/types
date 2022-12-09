package kotools.types

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.test.Test

class NegativeIntTest {
    @Test
    fun compareTo_should_pass() {
        val x: NegativeInt = NegativeInt.random()
        val y: NegativeInt = NegativeInt.random()
        val result: Int = x.compareTo(y)
        val expectedResult: Int = x.toInt()
            .compareTo(y.toInt())
        result assertEquals expectedResult
    }

    @Test
    fun toString_should_pass() {
        val value: Int = Random.nextInt(NegativeInt.range)
        value.toNegativeInt()
            .getOrThrow()
            .toString() assertEquals "$value"
    }

    @Test
    fun int_toNegativeInt_should_pass_with_a_negative_Int() {
        val value: Int = Random.nextInt(NegativeInt.range)
        value.toNegativeInt()
            .getOrThrow()
            .toInt() assertEquals value
    }

    @Test
    fun int_toNegativeInt_should_fail_with_a_strictly_positive_Int() {
        val result: Result<NegativeInt> = Random.nextInt(1..Int.MAX_VALUE)
            .toNegativeInt()
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }

    @Test
    fun serialization_should_behave_like_an_Int() {
        val x: NegativeInt = NegativeInt.random()
        val result: String = Json.encodeToString(x)
        result assertEquals Json.encodeToString(x.toInt())
    }

    @Test
    fun deserialization_should_pass_with_a_negative_Int() {
        val value: Int = Random.nextInt(NegativeInt.range)
        val encoded: String = Json.encodeToString(value)
        val result: NegativeInt = Json.decodeFromString(encoded)
        result.toInt() assertEquals value
    }

    @Test
    fun deserialization_should_fail_with_a_strictly_positive_Int() {
        val value: Int = Random.nextInt(1..Int.MAX_VALUE)
        val encoded: String = Json.encodeToString(value)
        val exception: IllegalArgumentException =
            assertFailsWith { Json.decodeFromString<NegativeInt>(encoded) }
        exception.message.assertNotNull()
            .isNotBlank()
            .assertTrue()
    }
}

private val NegativeInt.Companion.range: IntRange by lazy { Int.MIN_VALUE..0 }

private fun NegativeInt.Companion.random(): NegativeInt = Random.nextInt(range)
    .toNegativeInt()
    .getOrThrow()
