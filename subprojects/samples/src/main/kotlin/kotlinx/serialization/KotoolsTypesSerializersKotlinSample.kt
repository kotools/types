package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import org.kotools.types.Zero

@OptIn(ExperimentalKotoolsTypesApi::class)
internal object KotoolsTypesSerializersKotlinSample {
    fun all() {
        val format = Json { serializersModule = KotoolsTypesSerializers.all }
        val encoded: String = format.encodeToString(Zero)
        println(encoded) // 0
        val decoded: Zero = format.decodeFromString(encoded)
        println(Zero === decoded) // true
    } // END

    fun zero() {
        val format = Json { serializersModule = KotoolsTypesSerializers.zero }
        val encoded: String = format.encodeToString(Zero)
        println(encoded) // 0
        val decoded: Zero = format.decodeFromString(encoded)
        println(Zero === decoded) // true
    } // END

    @Suppress("FunctionName")
    fun toString_override() {
        val message: String = KotoolsTypesSerializers.toString()
        println(message) // KotoolsTypesSerializers
    } // END
}
