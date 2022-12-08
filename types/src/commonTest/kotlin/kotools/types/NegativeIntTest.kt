package kotools.types

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.*
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.test.Test

class NegativeIntSerializationTest {
    @Test
    fun serialization_should_behave_like_an_Int() {
        val x: NegativeInt = NegativeInt.random()
        val result: String = Json.encodeToString(x)
        result assertEquals Json.encodeToString(x.toInt())
    }

    @Test
    fun deserialization_should_pass() {
        val x: NegativeInt = NegativeInt.random()
        val encoded: String = Json.encodeToString(x)
        val result: NegativeInt = Json.decodeFromString(encoded)
        result.toInt() assertEquals x.toInt()
    }
}

class NegativeIntCompanionTest {
    @Test
    fun min_should_equal_the_minimum_of_Int(): Unit =
        NegativeInt.min.toInt() assertEquals Int.MIN_VALUE

    @Test
    fun max_should_equal_zero(): Unit = NegativeInt.max.toInt() assertEquals 0

    @Test
    fun random_should_return_different_values() {
        val x: Int = NegativeInt.random().toInt()
        val y: Int = NegativeInt.random().toInt()
        x assertNotEquals y
    }
}

class NegativeIntUnaryMinusTest {
    @Test
    fun should_pass() {
        val x: NegativeInt = NegativeInt.random()
        val result: PositiveInt = -x
        result.toInt() assertEquals -x.toInt()
    }
}

class IntToNegativeIntTest {
    @Test
    fun should_pass_with_a_negative_Int() {
        val value: Int = Random.nextInt(NegativeInt.range)
        value.toNegativeInt()
            .getOrThrow()
            .toInt() assertEquals value
    }

    @Test
    fun should_fail_with_a_strictly_positive_Int(): Unit = Random
        .nextInt(StrictlyPositiveInt.range)
        .toNegativeInt()
        .let { assertFailsWith<IllegalArgumentException>(it::getOrThrow) }
        .message
        .assertNotNull()
        .isNotBlank()
        .assertTrue()
}
