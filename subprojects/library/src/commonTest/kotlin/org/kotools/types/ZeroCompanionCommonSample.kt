package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class ZeroCompanionCommonSample {
    @Test
    fun orNull() {
        val number: Byte = 0
        val zero: Zero? = Zero.orNull(number)
        assertNotNull(zero)
    }

    @Test
    fun orThrow() {
        val number: Byte = 0
        val isSuccess: Boolean = try {
            Zero.orThrow(number)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        assertTrue(isSuccess)
    }
}
