package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi

internal object ZeroCompanionKotlinSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun fromByteOrNull() {
        val number: Byte = 0
        val zero: Zero? = Zero.fromByteOrNull(number)
        println(zero != null) // true
    } // END
}
