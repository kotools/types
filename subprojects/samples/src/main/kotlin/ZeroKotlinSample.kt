package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi

internal object ZeroKotlinSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun toStringSample() {
        val message: String = Zero.toString()
        println(message) // 0
    }
}
