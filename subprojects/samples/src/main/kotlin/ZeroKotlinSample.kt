package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi

@OptIn(ExperimentalKotoolsTypesApi::class)
internal object ZeroKotlinSample {
    fun toByte() {
        val number: Byte = Zero.toByte()
        println(number) // 0
    } // END

    fun toStringSample() {
        val message: String = Zero.toString()
        println(message) // 0
    } // END
}
