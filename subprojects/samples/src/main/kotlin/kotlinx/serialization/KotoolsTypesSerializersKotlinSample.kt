package org.kotools.types.kotlinx.serialization

import kotools.types.experimental.ExperimentalKotoolsTypesApi

internal object KotoolsTypesSerializersKotlinSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Suppress("FunctionName")
    fun toString_override() {
        val message: String = KotoolsTypesSerializers.toString()
        println(message) // KotoolsTypesSerializers
    } // END
}
