/*
 * Copyright 2023 Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.number

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.serializer
import kotools.types.experimental.ExperimentalNumberApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class StrictlyPositiveDoubleSerializerJsTest {
    @ExperimentalNumberApi
    @ExperimentalSerializationApi
    @Test
    fun descriptor_serialName_should_be_the_simple_name_of_StrictlyPositiveDouble() {
        val actual: String = serializer<StrictlyPositiveDouble>()
            .descriptor
            .serialName
        val expected: String = StrictlyPositiveDouble::class.simpleName
            ?: fail("Simple name shouldn't be null")
        assertEquals(expected, actual)
    }
}
