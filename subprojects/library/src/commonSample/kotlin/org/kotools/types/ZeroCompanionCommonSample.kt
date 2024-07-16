package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class ZeroCompanionCommonSample {
    @Test
    fun fromByte() {
        val number: Byte = 0
        val isSuccess: Boolean = try {
            Zero.fromByte(number)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        assertTrue(isSuccess)
    }

    @Test
    fun fromByteOrNull() {
        val number: Byte = 0
        val zero: Zero? = Zero.fromByteOrNull(number)
        assertNotNull(zero)
    }
}
