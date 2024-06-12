package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi

@OptIn(ExperimentalKotoolsTypesApi::class)
internal object ZeroCompanionKotlinSample {
    fun pattern() {
        val numbers: List<Any> = listOf(
            0, 0.0, // TABS: 1
            "+0", "+000", "+0.000", "+000.000", // with unary plus // TABS: 1
            "-0", "-000", "-0.000", "-000.000" // with unary minus // TABS: 1
        )
        val regex = Regex(Zero.PATTERN)
        val numbersAreValid: Boolean = numbers.all { "$it" matches regex }
        println(numbersAreValid) // true
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

    fun orNull() {
        val number: Any = "-000.000"
        val zero: Zero? = Zero.orNull(number)
        println(zero != null) // true
    } // END

    fun orThrow() {
        val number: Any = "-000.000"
        val isSuccess: Boolean = try {
            Zero.orThrow(number) // TABS: 1
            true // TABS: 1
        } catch (exception: IllegalArgumentException) {
            false // TABS: 1
        }
        println(isSuccess) // true
    } // END
}
