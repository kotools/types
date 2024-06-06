package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi

@OptIn(ExperimentalKotoolsTypesApi::class)
internal object ZeroCompanionKotlinSample {
    fun pattern() {
        val pattern: String = Zero.PATTERN
        println(pattern) // ^[+-]?0+(?:\.0+)?$
    } // END

    fun fromByte() {
        val number: Byte = 0
        val isSuccess: Boolean = try {
            Zero.fromByte(number) // TABS: 1
            true // TABS: 1
        } catch (exception: IllegalArgumentException) {
            false // TABS: 1
        }
        println(isSuccess) // true
    } // END

    fun fromByteOrNull() {
        val number: Byte = 0
        val zero: Zero? = Zero.fromByteOrNull(number)
        println(zero != null) // true
    } // END

    fun fromShort() {
        val number: Short = 0
        val isSuccess: Boolean = try {
            Zero.fromShort(number) // TABS: 1
            true // TABS: 1
        } catch (exception: IllegalArgumentException) {
            false // TABS: 1
        }
        println(isSuccess) // true
    } // END

    fun fromShortOrNull() {
        val number: Short = 0
        val zero: Zero? = Zero.fromShortOrNull(number)
        println(zero != null) // true
    } // END

    fun fromInt() {
        val isSuccess: Boolean = try {
            Zero.fromInt(0) // TABS: 1
            true // TABS: 1
        } catch (exception: IllegalArgumentException) {
            false // TABS: 1
        }
        println(isSuccess) // true
    } // END

    fun fromIntOrNull() {
        val zero: Zero? = Zero.fromIntOrNull(0)
        println(zero != null) // true
    } // END

    fun fromLong() {
        val isSuccess: Boolean = try {
            Zero.fromLong(0L) // TABS: 1
            true // TABS: 1
        } catch (exception: IllegalArgumentException) {
            false // TABS: 1
        }
        println(isSuccess) // true
    } // END

    fun fromLongOrNull() {
        val zero: Zero? = Zero.fromLongOrNull(0L)
        println(zero != null) // true
    } // END

    fun fromFloat() {
        val isSuccess: Boolean = try {
            Zero.fromFloat(0f) // TABS: 1
            true // TABS: 1
        } catch (exception: IllegalArgumentException) {
            false // TABS: 1
        }
        println(isSuccess) // true
    } // END

    fun fromFloatOrNull() {
        val zero: Zero? = Zero.fromFloatOrNull(0f)
        println(zero != null) // true
    } // END

    fun fromDouble() {
        val isSuccess: Boolean = try {
            Zero.fromDouble(0.0) // TABS: 1
            true // TABS: 1
        } catch (exception: IllegalArgumentException) {
            false // TABS: 1
        }
        println(isSuccess) // true
    } // END

    fun fromDoubleOrNull() {
        val zero: Zero? = Zero.fromDoubleOrNull(0.0)
        println(zero != null) // true
    } // END

    fun fromStringOrNull() {
        val number: Any = "-000.000"
        val zero: Zero? = Zero.fromStringOrNull(number)
        println(zero != null) // true
    } // END
}
