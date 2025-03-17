package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class PositiveIntegerCompanionCommonSample {
    @Test
    fun orThrowWithByte() {
        val number: Byte = (1..Byte.MAX_VALUE)
            .random()
            .toByte()
        val isSuccess: Boolean = try {
            PositiveInteger.orThrow(number)
            true
        } catch (_: IllegalArgumentException) {
            false
        }
        assertTrue(isSuccess)
    }
}
