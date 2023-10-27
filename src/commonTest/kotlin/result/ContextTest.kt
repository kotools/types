/*
 * Copyright 2023 Loïc Lamarque.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.result

import kotools.types.collection.NotEmptyList
import kotools.types.collection.NotEmptyMap
import kotools.types.collection.NotEmptySet
import kotools.types.contentShouldEqual
import kotools.types.experimental.ExperimentalNumberApi
import kotools.types.number.*
import kotools.types.shouldEqual
import kotools.types.shouldHaveAMessage
import kotools.types.text.NotBlankString
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertFailsWith

class ResultContextTest {
    @Test
    fun number_toNonZeroInt_should_pass_with_an_Int_other_than_zero() {
        val value: Number = NonZeroInt.random()
            .toInt()
        val result: Result<NonZeroInt> = resultOf { value.toNonZeroInt() }
        result.getOrThrow()
            .toInt() shouldEqual value
    }

    @Test
    fun number_toNonZeroInt_should_fail_with_an_Int_that_equals_zero() {
        val value: Number = ZeroInt.toInt()
        val result: Result<NonZeroInt> = resultOf { value.toNonZeroInt() }
        val exception: IllegalArgumentException =
            assertFailsWith(block = result::getOrThrow)
        exception.shouldHaveAMessage()
    }

    @Test
    fun number_toPositiveInt_should_pass_with_a_positive_Int() {
        val expected: Number = PositiveInt.random().toInt()
        val result: Result<PositiveInt> = resultOf { expected.toPositiveInt() }
        result.getOrThrow()
            .toInt() shouldEqual expected
    }

    @Test
    fun number_toPositiveInt_should_fail_with_a_strictly_negative_Int() {
        val number: Number = StrictlyNegativeInt.random().toInt()
        val result: Result<PositiveInt> = resultOf { number.toPositiveInt() }
        assertFailsWith<IllegalArgumentException>(block = result::getOrThrow)
            .shouldHaveAMessage()
    }

    @Test
    fun number_toNegativeInt_should_pass_with_a_negative_Int() {
        val value: Number = NegativeInt.random()
            .toInt()
        val result: Result<NegativeInt> = resultOf { value.toNegativeInt() }
        result.getOrThrow()
            .toInt() shouldEqual value
    }

    @Test
    fun number_toNegativeInt_should_fail_with_a_strictly_positive_Int() {
        val value: Number = StrictlyPositiveInt.random()
            .toInt()
        val result: Result<NegativeInt> = resultOf { value.toNegativeInt() }
        assertFailsWith<IllegalArgumentException>(block = result::getOrThrow)
            .shouldHaveAMessage()
    }

    @Test
    fun number_toStrictlyPositiveInt_should_pass_with_a_strictly_positive_Number() {
        val number: Number = StrictlyPositiveInt.random().toInt()
        val result: Result<StrictlyPositiveInt> =
            resultOf { number.toStrictlyPositiveInt() }
        result.getOrThrow()
            .toInt() shouldEqual number
    }

    @Test
    fun number_toStrictlyPositiveInt_should_fail_with_a_negative_Number() {
        val number: Number = NegativeInt.random().toInt()
        val result: Result<StrictlyPositiveInt> =
            resultOf { number.toStrictlyPositiveInt() }
        assertFailsWith<IllegalArgumentException>(block = result::getOrThrow)
            .shouldHaveAMessage()
    }

    @Test
    fun number_toStrictlyNegativeInt_should_pass_with_a_strictly_negative_Int() {
        val value: Number = StrictlyNegativeInt.random().toInt()
        val result: Result<StrictlyNegativeInt> =
            resultOf { value.toStrictlyNegativeInt() }
        result.getOrThrow()
            .toInt() shouldEqual value
    }

    @Test
    fun number_toStrictlyNegativeInt_should_fail_with_a_positive_Int() {
        val number: Number = PositiveInt.random().toInt()
        val result: Result<StrictlyNegativeInt> =
            resultOf { number.toStrictlyNegativeInt() }
        assertFailsWith<IllegalArgumentException>(block = result::getOrThrow)
            .shouldHaveAMessage()
    }

    @ExperimentalNumberApi
    @Test
    fun number_toStrictlyPositiveDouble_should_pass_with_a_strictly_positive_Number() {
        val value: Number =
            Random.nextDouble(from = 0.1, until = Double.MAX_VALUE)
        val result: Result<StrictlyPositiveDouble> =
            resultOf { value.toStrictlyPositiveDouble() }
        result.getOrThrow()
            .toDouble()
            .shouldEqual(value)
    }

    @ExperimentalNumberApi
    @Test
    fun number_toStrictlyPositiveDouble_should_fail_with_a_negative_Number() {
        val value: Number =
            -Random.nextDouble(from = 0.1, until = Double.MAX_VALUE)
        val result: Result<StrictlyPositiveDouble> =
            resultOf { value.toStrictlyPositiveDouble() }
        assertFailsWith<IllegalArgumentException>(block = result::getOrThrow)
            .shouldHaveAMessage()
    }

    @Test
    fun string_toNotBlankString_should_pass_with_a_not_blank_String() {
        val string = "hello world"
        val result: Result<NotBlankString> =
            resultOf { string.toNotBlankString() }
        "${result.getOrThrow()}" shouldEqual string
    }

    @Test
    fun string_toNotBlankString_should_fail_with_a_blank_String() {
        val result: Result<NotBlankString> = resultOf { "".toNotBlankString() }
        assertFailsWith<IllegalArgumentException>(block = result::getOrThrow)
            .shouldHaveAMessage()
    }

    @Test
    fun collection_toNotEmptyList_should_pass_with_a_not_empty_Collection() {
        val collection: Collection<Int> = List(3) { Random.nextInt() }
        val result: Result<NotEmptyList<Int>> =
            resultOf { collection.toNotEmptyList() }
        result.getOrThrow()
            .toList() contentShouldEqual collection
    }

    @Test
    fun collection_toNotEmptyList_should_fail_with_an_empty_Collection() {
        val collection: Collection<Int> = emptyList()
        val result: Result<NotEmptyList<Int>> =
            resultOf { collection.toNotEmptyList() }
        val exception: IllegalArgumentException =
            assertFailsWith(block = result::getOrThrow)
        exception.shouldHaveAMessage()
    }

    @Test
    fun collection_toNotEmptySet_should_pass_with_a_not_empty_Collection() {
        val elements: List<Int> = List(3) { Random.nextInt() }
        val result: Result<NotEmptySet<Int>> =
            resultOf { elements.toNotEmptySet() }
        result.getOrThrow()
            .toSet() contentShouldEqual elements
    }

    @Test
    fun collection_toNotEmptySet_should_fail_with_an_empty_Collection() {
        val collection: Collection<Int> = emptySet()
        val result: Result<NotEmptySet<Int>> =
            resultOf { collection.toNotEmptySet() }
        val exception: IllegalArgumentException =
            assertFailsWith(block = result::getOrThrow)
        exception.shouldHaveAMessage()
    }

    @Test
    fun map_toNotEmptyMap_should_pass_with_a_not_empty_Map() {
        val map: Map<String, Int> = mapOf(
            "a" to Random.nextInt(),
            "b" to Random.nextInt(),
            "c" to Random.nextInt()
        )
        val result: Result<NotEmptyMap<String, Int>> =
            resultOf { map.toNotEmptyMap() }
        result.getOrThrow()
            .toMap()
            .entries contentShouldEqual map.entries
    }

    @Test
    fun map_toNotEmptyMap_should_fail_with_an_empty_Map() {
        val map: Map<String, Int> = emptyMap()
        val result: Result<NotEmptyMap<String, Int>> =
            resultOf { map.toNotEmptyMap() }
        assertFailsWith<IllegalArgumentException>(block = result::getOrThrow)
            .shouldHaveAMessage()
    }

    @Test
    fun resultOf_should_pass() {
        val value: Int = Random.nextInt()
        val result: Result<Int> = resultOf { value }
        result.getOrThrow() shouldEqual value
    }
}
