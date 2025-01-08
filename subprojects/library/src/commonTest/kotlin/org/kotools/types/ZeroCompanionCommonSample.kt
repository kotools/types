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
    fun orNullWithShort() {
        val number: Short = 0
        val zero: Zero? = Zero.orNull(number)
        assertNotNull(zero)
    }

    @Test
    fun orNullWithInt() {
        val zero: Zero? = Zero.orNull(0)
        assertNotNull(zero)
    }

    @Test
    fun orThrowWithByte() {
        val number: Byte = 0
        val isSuccess: Boolean = try {
            Zero.orThrow(number)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        assertTrue(isSuccess)
    }

    @Test
    fun orThrowWithShort() {
        val number: Short = 0
        val isSuccess: Boolean = try {
            Zero.orThrow(number)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        assertTrue(isSuccess)
    }

    @Test
    fun orThrowWithInt() {
        val isSuccess: Boolean = try {
            Zero.orThrow(0)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        assertTrue(isSuccess)
    }
}
