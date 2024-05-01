package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi

@OptIn(ExperimentalKotoolsTypesApi::class)
internal object ZeroCompanionKotlinSample {
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

    fun fromShortOrNull() {
        val number: Short = 0
        val zero: Zero? = Zero.fromShortOrNull(number)
        println(zero != null) // true
    } // END
}
