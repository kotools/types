package org.kotools.types.number

import org.kotools.types.ExperimentalApi

internal object ZeroKotlinSample {
    @OptIn(ExperimentalApi::class)
    fun toStringSample() {
        val message: String = Zero.toString()
        println(message) // 0
    }
}
