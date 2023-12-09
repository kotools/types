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

class StrictlyPositiveDoubleSerializerNativeTest {
    @ExperimentalNumberApi
    @ExperimentalSerializationApi
    @Test
    fun `descriptor's serial name should be the qualified name of StrictlyPositiveDouble`() {
        val actual: String = serializer<StrictlyPositiveDouble>()
            .descriptor
            .serialName
        val expected: String = StrictlyPositiveDouble::class.qualifiedName
            ?: fail("Qualified name shouldn't be null")
        assertEquals(expected, actual)
    }
}
