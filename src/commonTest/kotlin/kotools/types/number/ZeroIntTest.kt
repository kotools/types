/*
 * Copyright 2022-2023 Loïc Lamarque.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.number

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.Package
import kotools.types.shouldEqual
import kotools.types.shouldHaveAMessage
import kotlin.test.Test
import kotlin.test.assertFailsWith

class ZeroIntTest {
    @Test
    fun toInt_should_return_the_zero_integer(): Unit =
        ZeroInt.toInt() shouldEqual 0

    @Test
    fun toString_should_behave_like_the_zero_integer(): Unit =
        "$ZeroInt" shouldEqual "0"
}

class ZeroIntSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_have_the_qualified_name_of_ZeroInt_as_serial_name() {
        val result: String = ZeroInt.serializer().descriptor.serialName
        result shouldEqual "${Package.number}.ZeroInt"
    }

    @Test
    fun serialize_should_behave_like_the_zero_integer() {
        val result: String = Json.encodeToString(ZeroInt)
        result shouldEqual Json.encodeToString(0)
    }

    @Test
    fun deserialize_should_pass_with_the_zero_integer() {
        val result: ZeroInt = Json.decodeFromString("0")
        result shouldEqual ZeroInt
    }

    @Test
    fun deserialize_should_fail_with_an_Int_other_than_zero() {
        val value: Int = NonZeroInt.random()
            .toInt()
        val encoded: String = Json.encodeToString(value)
        val exception: SerializationException =
            assertFailsWith { Json.decodeFromString<ZeroInt>(encoded) }
        exception.shouldHaveAMessage()
    }
}
