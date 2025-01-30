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
    fun orNullWithLong() {
        val zero: Zero? = Zero.orNull(0L)
        assertNotNull(zero)
    }

    @Test
    fun orNullWithFloat() {
        val zero: Zero? = Zero.orNull(0f)
        assertNotNull(zero)
    }

    @Test
    fun orNullWithDouble() {
        val zero: Zero? = Zero.orNull(0.0)
        assertNotNull(zero)
    }

    @Test
    fun orNullWithString() {
        listOf("0", "000", "0.0", "0.000", "000.0", "000.000")
            .map(Zero.Companion::orNull)
            .forEach(::assertNotNull)
    }

    @Test
    fun orThrowWithFloat() {
        val isSuccess: Boolean = try {
            Zero.orThrow(0f)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        assertTrue(isSuccess)
    }

    @Test
    fun orThrowWithDouble() {
        val isSuccess: Boolean = try {
            Zero.orThrow(0.0)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        assertTrue(isSuccess)
    }

    @Test
    fun orThrowWithString() {
        val isSuccess: Boolean = try {
            listOf("0", "000", "0.0", "0.000", "000.0", "000.000")
                .forEach(Zero.Companion::orThrow)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        assertTrue(isSuccess)
    }

    @Test
    fun orThrowWithAny() {
        val isSuccess: Boolean = try {
            listOf(0, "000", 0.0, "0.000", "000.0", 000.000)
                .forEach(Zero.Companion::orThrow)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        assertTrue(isSuccess)
    }
}
