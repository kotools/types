/*
 * Copyright 2022-2023 Loïc Lamarque and Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.number

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotools.types.experimental.AnyInt
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.KotoolsTypesPackage
import kotools.types.internal.simpleNameOf
import kotools.types.shouldEqual
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AnyIntTest {
    @Test
    fun compareTo_should_return_zero_with_the_same_AnyInt() {
        val x: AnyInt = NonZeroInt.random()
        val y: AnyInt = x.toInt()
            .toNonZeroInt()
            .getOrThrow()
        val result: Int = x compareTo y
        result shouldEqual ZeroInt.toInt()
    }

    @Test
    fun compareTo_should_return_a_negative_number_with_a_greater_AnyInt() {
        val x: AnyInt = StrictlyNegativeInt.random()
        val y: AnyInt = StrictlyPositiveInt.random()
        val result: Int = x compareTo y
        assertTrue { result < ZeroInt.toInt() }
    }

    @Test
    fun compareTo_should_return_a_positive_number_with_a_less_AnyInt() {
        val x: AnyInt = StrictlyPositiveInt.random()
        val y: AnyInt = StrictlyNegativeInt.random()
        val result: Int = x compareTo y
        assertTrue { result > ZeroInt.toInt() }
    }

    @Test
    fun int_plus_should_pass_with_an_AnyInt() {
        val x: Int = Random.nextInt()
        val y: AnyInt = NonZeroInt.random()
        val result: Int = x + y
        result shouldEqual x + y.toInt()
    }

    @Test
    fun plus_should_pass_with_an_Int() {
        val x: AnyInt = PositiveInt.random()
        val y: Int = Random.nextInt()
        val result: Int = x + y
        result shouldEqual x.toInt() + y
    }

    @Test
    fun plus_should_pass_with_an_AnyInt() {
        val x: AnyInt = PositiveInt.random()
        val y: AnyInt = NegativeInt.random()
        val result: Int = x + y
        result shouldEqual x.toInt() + y.toInt()
    }

    @Test
    fun int_minus_should_pass_with_an_AnyInt() {
        val x: Int = Random.nextInt()
        val y: AnyInt = NonZeroInt.random()
        val result: Int = x - y
        result shouldEqual x - y.toInt()
    }

    @Test
    fun minus_should_pass_with_an_Int() {
        val x: AnyInt = PositiveInt.random()
        val y: Int = Random.nextInt()
        val result: Int = x - y
        result shouldEqual x.toInt() - y
    }

    @Test
    fun minus_should_pass_with_an_AnyInt() {
        val x: AnyInt = PositiveInt.random()
        val y: AnyInt = NegativeInt.random()
        val result: Int = x - y
        result shouldEqual x.toInt() - y.toInt()
    }

    @Test
    fun int_times_should_pass_with_an_AnyInt() {
        val x: Int = Random.nextInt()
        val y: AnyInt = NonZeroInt.random()
        val result: Int = x * y
        result shouldEqual x * y.toInt()
    }

    @Test
    fun times_should_pass_with_an_Int() {
        val x: AnyInt = PositiveInt.random()
        val y: Int = Random.nextInt()
        val result: Int = x * y
        result shouldEqual x.toInt() * y
    }

    @Test
    fun times_should_pass_with_an_AnyInt() {
        val x: AnyInt = PositiveInt.random()
        val y: AnyInt = NegativeInt.random()
        val result: Int = x * y
        result shouldEqual x.toInt() * y.toInt()
    }

    @Test
    fun div_should_pass_with_a_NonZeroInt() {
        val x: AnyInt = PositiveInt.random()
        val y: NonZeroInt = NonZeroInt.random()
        val result: Int = x / y
        result shouldEqual x.toInt() / y.toInt()
    }

    @Test
    fun rem_should_pass_with_a_NonZeroInt() {
        val x: AnyInt = PositiveInt.random()
        val y: NonZeroInt = NonZeroInt.random()
        val result: Int = x % y
        result shouldEqual x.toInt() % y.toInt()
    }
}

class AnyIntSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_serial_name_should_be_the_qualified_name_of_AnyInt() {
        val actual: String = serializer<AnyInt>().descriptor.serialName
        val expected = "${KotoolsTypesPackage.Number}." + simpleNameOf<AnyInt>()
        assertEquals(expected, actual)
    }

    @ExperimentalSerializationApi
    @Test
    fun descriptor_kind_should_be_PrimitiveKind_INT() {
        val actual: SerialKind = serializer<AnyInt>().descriptor.kind
        assertEquals(expected = PrimitiveKind.INT, actual)
    }

    @Test
    fun serialize_should_behave_like_an_Int() {
        val number: AnyInt = StrictlyPositiveInt.random()
        val actual: String = Json.encodeToString(number)
        val value: Int = number.toInt()
        val expected: String = Json.encodeToString(value)
        assertEquals(expected, actual)
    }

    @ExperimentalKotoolsTypesApi
    @Test
    fun deserialize_should_pass_with_an_Int() {
        val value: Int = Random.nextInt()
        val encoded: String = Json.encodeToString(value)
        val actual: AnyInt = Json.decodeFromString(encoded)
        val expected = AnyInt(value)
        assertEquals(expected, actual)
    }
}
