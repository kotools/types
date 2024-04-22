package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi

internal object ZeroCompanionKotlinSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
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

    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun fromByteOrNull() {
        val number: Byte = 0
        val zero: Zero? = Zero.fromByteOrNull(number)
        println(zero != null) // true
    } // END
}
